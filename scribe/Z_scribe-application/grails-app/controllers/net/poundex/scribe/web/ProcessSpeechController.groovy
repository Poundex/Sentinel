package net.poundex.scribe.web

import grails.converters.JSON
import net.poundex.sentinel.scribe.ProcessSpeechCommand
import net.poundex.sentinel.scribe.ProcessSpeechResult
import net.poundex.sentinel.scribe.SpeechProcessor

class ProcessSpeechController
{
	private final SpeechProcessor speechProcessor;

	ProcessSpeechController(SpeechProcessor speechProcessor)
	{
		this.speechProcessor = speechProcessor
	}

	def process(ProcessSpeechCommand speech)
	{
		render(new ProcessSpeechResult(speechProcessor.processSpeech(speech.wave.decodeBase64())) as JSON)
	}
}
