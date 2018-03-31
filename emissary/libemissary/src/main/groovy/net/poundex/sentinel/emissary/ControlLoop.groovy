package net.poundex.sentinel.emissary

import net.poundex.sentinel.scribe.ProcessSpeechCommand
import net.poundex.sentinel.scribe.ProcessSpeechResult
import net.poundex.sentinel.scribe.ScribeClient

class ControlLoop
{
	private final RecognitionProvider recognitionProvider
	private final ScribeClient scribeClient
	private final ControlManager controlManager

	private volatile boolean running

	private Thread loopThread

	ControlLoop(RecognitionProvider recognitionProvider, ScribeClient scribeClient, ControlManager controlManager)
	{
		this.recognitionProvider = recognitionProvider
		this.scribeClient = scribeClient
		this.controlManager = controlManager
	}

	void start()
	{
		running = true
		loopThread = Thread.startDaemon {
			while (running) {
				controlManager.onListeningStarted()
				recognitionProvider.waitForHotword()
				controlManager.onHotwordRecognised()
				byte[] speechSound = recognitionProvider.getSpeech()
				controlManager.onSpeechReceived(speechSound)
				ProcessSpeechResult speechResult = scribeClient.processSpeech(
						new ProcessSpeechCommand(speechSound.encodeBase64().toString()))
				controlManager.onSpeechProcessed(speechResult)
//				ResponsePackage queryResponse = queryClient.processQuery(speech)
			}
		}
	}

	void stop()
	{
		running = false
		loopThread.interrupt()
	}
}
