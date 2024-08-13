package com.nmi.lexiloop.record

class JvmAudioRecorder(
    private val intervalSeconds: Int = 6,
    private val sampleRate: Int = 16000,
//    private val channelConfig: Int = AudioFormat.CHANNEL_IN_MONO,
//    private val audioFormat: Int = AudioFormat.ENCODING_PCM_FLOAT,
//    private val rawAudioSource: Int = MediaRecorder.AudioSource.UNPROCESSED
) : AudioRecorder {
    override fun start() {
    }

    override fun stop() {
    }

    override fun readData(): FloatArray {
        return floatArrayOf()
    }

    override fun recordXSeconds(x: Int): FloatArray {
        return floatArrayOf()
    }

    override fun getState(): Int {
        return -1
    }

    override fun getSampleRate(): Int {
        return -1
    }

}