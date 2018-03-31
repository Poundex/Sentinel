package net.poundex.sentinel.fathom

class SpeechResponse implements Response
{
	final String speech
	final Object[] args

	SpeechResponse(String speech, Object[] args)
	{
		this.speech = speech
		this.args = args
	}
}
