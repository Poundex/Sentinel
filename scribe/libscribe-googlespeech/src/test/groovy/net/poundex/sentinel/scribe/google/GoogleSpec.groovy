package net.poundex.sentinel.scribe.google

import spock.lang.Specification

import java.nio.file.Paths

class GoogleSpec extends Specification
{
	void "nothing"()
	{
		expect:
		new GoogleSpeechProcessor().processSpeech(Paths.get("/home/poundex/sentence.wav").bytes)
	}
}
