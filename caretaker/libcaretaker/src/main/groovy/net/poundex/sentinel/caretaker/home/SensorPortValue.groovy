package net.poundex.sentinel.caretaker.home

import java.time.LocalDateTime

interface SensorPortValue<T>
{
	Device getSourceDevice()
	String getSourcePort()
	T getValue()
	LocalDateTime getReportTime()
}
