package net.poundex.sentinel.caretaker.home

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class SensorReader<T extends SensorPortValue>
{
	String deviceId
	String portId
	AbstractPersistentSensor<T> sensor

	static belongsTo = [sensor: AbstractPersistentSensor]

    static constraints = {
    }

	T readValue(SensorPortValue sensorValue)
	{
		return sensor.readValue(sensorValue)
	}
}
