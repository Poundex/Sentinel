package net.poundex.sentinel.emissary

import net.poundex.sentinel.scribe.ProcessSpeechResult

class ControlManager implements ControlListener
{
	private final List<ControlListener> listeners = []

	void addControlListener(ControlListener controlListener)
	{
		listeners << controlListener
	}

	@Override
	void onListeningStarted()
	{
		listeners*.onListeningStarted()
	}

	@Override
	void onHotwordRecognised()
	{
		listeners*.onHotwordRecognised()
	}

	@Override
	void onSpeechProcessed(ProcessSpeechResult processSpeechResult)
	{
		listeners*.onSpeechProcessed(processSpeechResult)
	}

	@Override
	void onSpeechReceived(byte[] bytes)
	{
		listeners*.onSpeechReceived(bytes)
	}
}
