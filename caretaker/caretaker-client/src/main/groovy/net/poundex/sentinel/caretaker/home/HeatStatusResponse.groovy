package net.poundex.sentinel.caretaker.home;

import groovy.transform.Canonical;

@Canonical
class HeatStatusResponse
{
	boolean success
	String failureReason
	Boolean heatOn
}
