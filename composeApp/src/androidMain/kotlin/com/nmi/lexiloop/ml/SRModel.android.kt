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
    SR(modelPath = "assets/ml/wav2vec2.ptl", context = getKoin().get())

class SR(
    override val modelPath: String,
    override val audioLenInSecond: Int = 6,
    override val sampleRate: Int = 16000,
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

//    @Throws(RuntimeException::class)
    override fun runInference(buffer: FloatArray): String {
        if (module == null) {
            return "" // at this point model should be initialized. mb throw exception?
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
        val modulePath = assetFilePath(modelPath) ?: return
        module = LiteModuleLoader.load(modulePath)
        if (module != null) {
            Log.i(TAG, "module loaded successfully")
        }
    }

    override fun close() {
        module?.destroy()
    }

    // https://github.com/pytorch/android-demo-app/blob/master/SpeechRecognition/app/src/main/java/org/pytorch/demo/speechrecognition/MainActivity.java#L114
    private fun assetFilePath(assetName: String): String? {
        val file = File(context.filesDir, assetName)
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
            Log.e(TAG, assetName + ": " + e.localizedMessage)
        }
        return null
    }
}