package net.poundex.sentinel.caretaker.home

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class SensorReader<T>
{
	String deviceId
	String portId
	Monitor<T> monitor

	static belongsTo = [monitor: Monitor]

    static constraints = {
    }
}
