package net.poundex.sentinel.emissary.desktop.ui

import javafx.application.Platform
import javafx.scene.control.Label
import net.poundex.sentinel.emissary.ControlListener
import net.poundex.sentinel.scribe.ProcessSpeechResult

class StatusLabel extends Label implements ControlListener
{
	StatusLabel()
	{
		super()
		status = "None"
	}

	void setStatus(String status)
	{
		Platform.runLater {
			setText("Status: ${status}")
		}
	}

	@Override
	void onListeningStarted()
	{
		status = "Listening"
	}

	@Override
	void onHotwordRecognised()
	{
		status = "Recording"
	}

	@Override
	void onSpeechProcessed(ProcessSpeechResult processSpeechResult)
	{
		status = "Responding"
	}

	@Override
	void onSpeechReceived(byte[] bytes)
	{
		status = "Processing"
	}
}
