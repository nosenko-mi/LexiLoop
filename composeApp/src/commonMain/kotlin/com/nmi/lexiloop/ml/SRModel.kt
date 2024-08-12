package com.nmi.lexiloop.ml

//https://kotlinlang.org/docs/multiplatform-expect-actual.html#interfaces-with-expected-and-actual-functions

expect val srModel: SpeechRecognitionModel

interface SpeechRecognitionModel{
    val modelPath: String
    val audioLenInSecond: Int
    val sampleRate: Int
    val recordingLength: Int
    fun runInference(buffer: FloatArray): String
    fun load()
    fun close()
}


// still in beta ->
//expect class SpeechRecognizer(
//    val modelPath: String,
//    val audioLenInSecond: Int = 6,
//    val sampleRate: Int = 16000,
//    val recordingLength: Int = sampleRate * audioLenInSecond,
//): SpeechRecognitionModel