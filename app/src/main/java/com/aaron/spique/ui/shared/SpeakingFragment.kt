package com.aaron.spique.ui.shared

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aaron.spique.ui.shared.models.Utterance
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
abstract class SpeakingFragment : Fragment() {

    private lateinit var textToSpeechEngine: TextToSpeech

    abstract val viewModel: SpeakingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.utterances.collect { utterances ->
                    if (!textToSpeechEngine.isSpeaking) {
                        utterances.firstOrNull()?.let { speak(it) }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        textToSpeechEngine = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine.language = Locale.US
                textToSpeechEngine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        viewModel.updateIsBeingSpoken(utteranceId)
                    }

                    override fun onDone(utteranceId: String?) {
                        viewModel.removeUtterance(utteranceId)
                    }

                    override fun onError(utteranceId: String?) {
                        viewModel.removeUtterance(utteranceId)
                    }
                })
            }
        }
    }

    private fun speak(utterance: Utterance) {
        textToSpeechEngine.speak(utterance.phrase, TextToSpeech.QUEUE_FLUSH, null, utterance.id)
    }

    override fun onPause() {
        super.onPause()
        textToSpeechEngine.stop()
        textToSpeechEngine.shutdown()
    }
}