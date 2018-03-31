package net.poundex.sentinel.emissary.desktop.ui

import javafx.application.Platform
import javafx.scene.control.Label
import net.poundex.sentinel.emissary.ControlListener
import net.poundex.sentinel.scribe.ProcessSpeechResult

class SpeechLabel extends Label implements ControlListener
{
	@Override
	void onListeningStarted()
	{

	}

	@Override
	void onHotwordRecognised()
	{

	}

	@Override
	void onSpeechProcessed(ProcessSpeechResult processSpeechResult)
	{
		Platform.runLater {
			setText(processSpeechResult.speech)
		}
	}

	@Override
	void onSpeechReceived(byte[] bytes)
	{

	}
}
