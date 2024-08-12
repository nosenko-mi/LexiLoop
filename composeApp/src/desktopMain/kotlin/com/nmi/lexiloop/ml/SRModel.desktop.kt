package com.nmi.lexiloop.ml

// check how to load pytorch model in jvm
actual val srModel: SpeechRecognitionModel = SR(modelPath = "assets/whatever")

class SR(
    override val modelPath: String,
    override val audioLenInSecond: Int = 6,
    override val sampleRate: Int = 16000,
    override val recordingLength: Int = audioLenInSecond * sampleRate
): SpeechRecognitionModel{
    override fun runInference(buffer: FloatArray): String {
        return "not yet implemented"
    }

    override fun load() {
    }

    override fun close() {
    }
}