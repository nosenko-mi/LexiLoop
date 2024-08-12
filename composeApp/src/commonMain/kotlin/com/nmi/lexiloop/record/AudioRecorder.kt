package com.nmi.lexiloop.record

interface AudioRecorder {
    fun start()
    fun stop()
    fun readData(): FloatArray
    fun getState(): Int
    fun getSampleRate(): Int
}