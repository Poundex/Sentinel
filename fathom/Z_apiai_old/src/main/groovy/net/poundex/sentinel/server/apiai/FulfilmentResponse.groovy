package net.poundex.sentinel.server.apiai

import groovy.transform.Canonical

@Canonical
class FulfilmentResponse
{
	String speech
	String displayText
	Map data
}
