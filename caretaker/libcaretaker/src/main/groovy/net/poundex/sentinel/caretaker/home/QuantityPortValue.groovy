package net.poundex.sentinel.caretaker.home

import groovy.transform.CompileStatic
import groovy.transform.ToString

import javax.measure.Quantity
import java.time.LocalDateTime

@ToString(includePackage = false)
@CompileStatic
class QuantityPortValue implements PortValue<Quantity>
{
	final Device device
	final String portId
	final Quantity value
	final LocalDateTime reportTime

	QuantityPortValue(Device device, String portId, Quantity value, LocalDateTime reportTime)
	{
		this.device = device
		this.portId = portId
		this.value = value
		this.reportTime = reportTime
	}
}
