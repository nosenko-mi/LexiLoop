package com.nmi.lexiloop.record

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import androidx.core.app.ActivityCompat
import com.nmi.lexiloop.ml.ModelFormat

class AndroidAudioRecorder(
    private val context: Context,
    private val intervalSeconds: Int = ModelFormat.AUDIO_LEN_IN_SECONDS,
    private val sampleRate: Int = ModelFormat.SAMPLE_RATE,
    private val channelConfig: Int = AudioFormat.CHANNEL_IN_MONO,
    private val audioFormat: Int = AudioFormat.ENCODING_PCM_16BIT,
    private val audioSource: Int = MediaRecorder.AudioSource.DEFAULT
) : AudioRecorder {

    private val minBufferSize: Int =
        AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
    private val recordingSize: Int = (intervalSeconds * sampleRate * Float.SIZE_BYTES)
    private val queue: ArrayDeque<Float> = ArrayDeque()

    @Volatile
    private var isRecordingAudio = false
    private var recorder: AudioRecord? = null

    companion object {
        const val TAG = "AndroidAudioRecorder"
    }

    override fun start() {
        createRecorder()
        if (recorder?.state != AudioRecord.STATE_INITIALIZED) {
            Log.e(TAG, "error initializing AudioRecord")
            return
        }
        isRecordingAudio = true
        recorder!!.startRecording()
    }

    override fun stop() {
        isRecordingAudio = false
        recorder?.stop()
        recorder?.release()
        recorder = null
    }


    override fun readData(): FloatArray {
        if (!isRecordingAudio || recorder == null) {
            return floatArrayOf()
        }
        val newData = FloatArray(recorder!!.channelCount * recorder!!.bufferSizeInFrames)
        val loadedValues = recorder!!.read(newData, 0, newData.size, AudioRecord.READ_NON_BLOCKING)
        Log.d(TAG, "readData: expected size=${newData.size}; loaded values=${loadedValues}")
        if (loadedValues <= 0) return floatArrayOf()

        if (queue.isEmpty()) {
            queue.addAll(newData.toTypedArray())
            while (queue.size < newData.size) {
                queue.add(0f)
            }
        } else {
            queue.addAll(newData.toTypedArray())
            while (queue.size > newData.size) {
                queue.removeFirst()
            }
        }
        Log.d(TAG, "readData: queue size=${queue.size}; values=${queue.toList()}")

        return queue.toFloatArray()

    }

    override fun recordXSeconds(x: Int): FloatArray {
        start()
        if (!isRecordingAudio || recorder == null) {
            return floatArrayOf()
        }

        val recordingLength = x * sampleRate
        var shortsRead: Long = 0
        var recordingOffset = 0
        val audioBuffer = ShortArray(minBufferSize / 2)
        val recordingBuffer = ShortArray(recordingLength)

        while (shortsRead < recordingLength) {
            val numberOfShort: Int = recorder!!.read(audioBuffer, 0, audioBuffer.size)
            Log.i(TAG, "number of shorts: $numberOfShort")
            shortsRead += numberOfShort.toLong()
            System.arraycopy(audioBuffer, 0, recordingBuffer, recordingOffset, numberOfShort)
            recordingOffset += numberOfShort
        }

        stop()

//        val floatInputBuffer = FloatArray(recordingLength)
//        for (i in 0 until recordingLength) {
//            floatInputBuffer[i] = recordingBuffer[i] / Short.MAX_VALUE.toFloat()
//        }

        // feed in float values between -1.0f and 1.0f by dividing the signed 16-bit inputs.
        val floatBuffer: FloatArray =
            recordingBuffer.map { it / Short.MAX_VALUE.toFloat() }.toFloatArray()

        return floatBuffer
    }

    override fun getState(): Int {
        return if (!isRecordingAudio) {
            AudioRecord.STATE_UNINITIALIZED
        } else {
            recorder!!.state
        }
    }

    override fun getSampleRate(): Int {
        return sampleRate
    }

    private fun createRecorder() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "Permissions denied")
            return
        }
        Log.d(TAG, "recording size: $recordingSize; min size: ${this.minBufferSize}")
        recorder = AudioRecord(audioSource, sampleRate, channelConfig, audioFormat, minBufferSize)
        if (recorder?.state != AudioRecord.STATE_INITIALIZED) {
            Log.e(TAG, "Audio Record can't initialize!")
            return
        }
    }

}