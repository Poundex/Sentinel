package net.poundex.sentinel.fathom

class NoSuchIntent extends IntentResult
{
	@Override
	boolean isSuccess()
	{
		return false
	}

	@Override
	List<Response> respond()
	{
		return [ new SpeechResponse("Can't help with that") ]
	}
}
