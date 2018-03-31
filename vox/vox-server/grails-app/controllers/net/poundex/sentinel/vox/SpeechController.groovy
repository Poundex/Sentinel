package net.poundex.sentinel.vox

import grails.converters.JSON

import java.nio.file.Path

class SpeechController
{
	private final SpeechSynthesizer speechSynthesizer
	private final SpeechPostProcessorService speechPostProcessorService

	SpeechController(SpeechSynthesizer speechSynthesizer, SpeechPostProcessorService speechPostProcessorService)
	{
		this.speechSynthesizer = speechSynthesizer
		this.speechPostProcessorService = speechPostProcessorService
	}

	def synth(SpeechSynthRequest speechSynthRequest)
	{
		Path speechFile = speechSynthesizer.synthSpeech(speechSynthRequest)
		Path result = speechPostProcessorService.process(speechFile)
		render(new SpeechSynthResponse(result.toFile().bytes.encodeBase64().toString()) as JSON)
	}
}
