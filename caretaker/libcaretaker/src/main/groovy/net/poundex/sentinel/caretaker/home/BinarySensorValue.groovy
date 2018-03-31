package net.poundex.sentinel.caretaker.home

import groovy.transform.ToString

import java.time.LocalDateTime

@ToString(includePackage = false)
class BinarySensorValue implements SensorPortValue<Boolean>
{
	final Device sourceDevice
	final String sourcePort
	final Boolean value
	final LocalDateTime reportTime

	BinarySensorValue(Device sourceDevice, String sourcePort, Boolean value, LocalDateTime reportTime)
	{
		this.sourceDevice = sourceDevice
		this.sourcePort = sourcePort
		this.value = value
		this.reportTime = reportTime
	}
}
