package net.poundex.sentinel.vox

import feign.Headers
import feign.RequestLine

interface SpeechSynthClient
{
	@RequestLine("POST /speech/synth")
	@Headers(["Content-type: application/json", "Accept: application/json"])
	SpeechSynthResponse synthSpeech(SpeechSynthRequest speechSynthRequest)
}
