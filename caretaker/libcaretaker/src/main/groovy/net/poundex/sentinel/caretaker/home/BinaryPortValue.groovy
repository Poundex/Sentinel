package net.poundex.sentinel.caretaker.home

import groovy.transform.ToString

import java.time.LocalDateTime

@ToString(includePackage = false)
class BinaryPortValue implements PortValue<Boolean>
{
	final Device device
	final String portId
	final Boolean value
	final LocalDateTime reportTime

	BinaryPortValue(Device device, String portId, Boolean value, LocalDateTime reportTime)
	{
		this.device = device
		this.portId = portId
		this.value = value
		this.reportTime = reportTime
	}
}
