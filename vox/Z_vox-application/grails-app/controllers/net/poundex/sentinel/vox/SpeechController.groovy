package net.poundex.sentinel.vox

import grails.converters.JSON

import java.nio.file.Path

class SpeechController
{
	private final SpeechSynthesizer speechSynthesizer

	SpeechController(SpeechSynthesizer speechSynthesizer)
	{
		this.speechSynthesizer = speechSynthesizer
	}

	def synth(SpeechSynthRequest speechSynthRequest)
	{
		Path speechFile = speechSynthesizer.synthSpeech(speechSynthRequest)
		Path converted = speechFile.parent.resolve(speechFile.fileName.toString().substring(0, speechFile.fileName.toString().length() - 3) + "wav")
		String dot = "ffmpeg -i ${speechFile} ${converted}"
		println dot
		dot.execute().waitFor()
		render(new SpeechSynthResponse(converted.toFile().bytes.encodeBase64().toString()) as JSON)
	}
}
