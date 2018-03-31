package net.poundex.sentinel.emissary

import net.poundex.sentinel.scribe.ProcessSpeechResult

interface ControlListener
{

	void onListeningStarted()

	void onHotwordRecognised()

	void onSpeechProcessed(ProcessSpeechResult processSpeechResult)

	void onSpeechReceived(byte[] bytes)
}
