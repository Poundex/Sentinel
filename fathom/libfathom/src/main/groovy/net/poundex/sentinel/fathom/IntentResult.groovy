package net.poundex.sentinel.fathom

abstract class IntentResult
{
	boolean isSuccess()
	{
		return true
	}

	abstract List<Response> respond()
}
