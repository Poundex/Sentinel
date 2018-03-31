package net.poundex.sentinel.caretaker.home

import groovy.transform.CompileStatic
import groovy.transform.ToString

import javax.measure.Quantity
import java.time.LocalDateTime

@ToString(includePackage = false)
@CompileStatic
class ValueSensorValue<T extends Quantity<T>> implements SensorPortValue<T>
{
	final Device sourceDevice
	final String sourcePort
	final Quantity<T> value
	final LocalDateTime reportTime

	ValueSensorValue(Device sourceDevice, String sourcePort, Quantity<T> value, LocalDateTime reportTime)
	{
		this.sourceDevice = sourceDevice
		this.sourcePort = sourcePort
		this.value = value
		this.reportTime = reportTime
	}
}
