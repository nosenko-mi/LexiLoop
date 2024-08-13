package com.nmi.lexiloop.ml

import android.content.Context
import android.util.Log
import org.koin.mp.KoinPlatform.getKoin
import org.pytorch.IValue
import org.pytorch.LiteModuleLoader
import org.pytorch.Module
import org.pytorch.Tensor
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

// use application context so no memory leak happens
// https://medium.com/@banmarkovic/what-is-context-in-android-and-which-one-should-you-use-e1a8c6529652
actual val srModel: SpeechRecognitionModel =
    SR(modelPath = "wav2vec2.ptl", context = getKoin().get())

class SR(
    override val modelPath: String,
    override val audioLenInSecond: Int = ModelFormat.AUDIO_LEN_IN_SECONDS,
    override val sampleRate: Int = ModelFormat.SAMPLE_RATE,
    override val recordingLength: Int = audioLenInSecond * sampleRate,
    private val context: Context
) : SpeechRecognitionModel {

    private var module: Module? = null

    companion object {
        const val TAG = "SR"

    }

    init {
        load()
    }

    @Throws(RuntimeException::class)
    override fun runInference(buffer: FloatArray): String {
        if (module == null) {
            throw(RuntimeException("Speech recognition module is not initialized"))
        }

        val moduleInput = DoubleArray(recordingLength)
        for (n in 0 until recordingLength) {
            moduleInput[n] = buffer[n].toDouble()
        }

        val inTensorBuffer = Tensor.allocateFloatBuffer(recordingLength)
        for (i in moduleInput) {
            inTensorBuffer.put(i.toFloat())
        }

        val inTensor = Tensor.fromBlob(inTensorBuffer, longArrayOf(1, recordingLength.toLong()))
        val result = module!!.forward(IValue.from(inTensor)).toStr()

        return result
    }

    override fun load() {
        val modulePath = assetFilePath(modelPath)
        if (modulePath == null) {
            Log.e(TAG, "Could not load asset: $modelPath")
            return
        }

        module = LiteModuleLoader.load(modulePath)
        if (module != null) {
            Log.i(TAG, "Speech recognition module loaded successfully")
        } else{
            Log.e(TAG, "Speech recognition module in not loaded")
        }
    }

    override fun close() {
        module?.destroy()
    }

    // https://github.com/pytorch/android-demo-app/blob/master/SpeechRecognition/app/src/main/java/org/pytorch/demo/speechrecognition/MainActivity.java#L114
    private fun assetFilePath(assetName: String): String? {
        val file = File(context.filesDir, assetName)
        Log.i(TAG, "Loading asset: ${file.absolutePath}")
        if (file.exists() && file.length() > 0) {
            return file.absolutePath
        }

        try {
            context.assets.open(assetName).use { inputStream ->
                FileOutputStream(file).use { os ->
                    val buffer = ByteArray(4 * 1024)
                    var read: Int
                    while ((inputStream.read(buffer).also { read = it }) != -1) {
                        os.write(buffer, 0, read)
                    }
                    os.flush()
                }
                return file.absolutePath
            }
        } catch (e: IOException) {
            Log.e(TAG, "$assetName: ${e.localizedMessage}")
        }
        return null
    }
}