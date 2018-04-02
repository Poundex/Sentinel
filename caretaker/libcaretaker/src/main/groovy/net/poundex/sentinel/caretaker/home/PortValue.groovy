package net.poundex.sentinel.caretaker.home

import java.time.LocalDateTime

interface PortValue<T>
{
	Device getDevice()
	String getPortId()
	T getValue()
	LocalDateTime getReportTime()
}
