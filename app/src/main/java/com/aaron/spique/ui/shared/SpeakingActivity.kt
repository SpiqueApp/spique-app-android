package com.aaron.spique.ui.shared

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
abstract class SpeakingActivity : AppCompatActivity() {

    private lateinit var textToSpeechEngine: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: Perform checks to make sure language is installed
        textToSpeechEngine = TextToSpeech(this) { status ->
            when (status) {
                TextToSpeech.SUCCESS -> textToSpeechEngine.language = Locale.US
            }
        }
        textToSpeechEngine.language = Locale.US

    }

    fun speak(text: String) {
        textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, text)
    }

    override fun onPause() {
        super.onPause()
        textToSpeechEngine.stop()
    }

    override fun onStop() {
        super.onStop()
        textToSpeechEngine.shutdown()
    }
}