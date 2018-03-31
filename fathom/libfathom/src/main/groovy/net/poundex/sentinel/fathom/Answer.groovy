package net.poundex.sentinel.fathom

import groovy.transform.Immutable

@Immutable
class Answer extends IntentResult
{
	String answer
	Object[] params

	@Override
	List<Response> respond()
	{
		return [ new SpeechResponse(answer, (Object[]) params) ]
	}
}
