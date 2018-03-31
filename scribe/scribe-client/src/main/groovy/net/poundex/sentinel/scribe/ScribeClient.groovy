package net.poundex.sentinel.scribe

import feign.Body
import feign.Headers
import feign.RequestLine

interface ScribeClient
{
	@RequestLine("POST /processSpeech/process")
	@Headers("Content-type: application/json")
	ProcessSpeechResult processSpeech(ProcessSpeechCommand processSpeechCommand)
}
