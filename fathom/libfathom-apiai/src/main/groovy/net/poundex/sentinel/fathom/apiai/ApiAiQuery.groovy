package net.poundex.sentinel.fathom.apiai

import groovy.transform.Immutable

@Immutable
class ApiAiQuery
{
	String query
	String sessionId

	String getLang()
	{
		return "en-GB"
	}
}
