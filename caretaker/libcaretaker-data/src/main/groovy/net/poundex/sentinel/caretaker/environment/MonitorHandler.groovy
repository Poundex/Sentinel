package net.poundex.sentinel.caretaker.environment

import groovy.transform.CompileStatic
import net.poundex.sentinel.caretaker.home.Monitor

@CompileStatic
interface MonitorHandler
{
	public <T> void updateMonitorValue(Monitor<T> monitor, T value)
}
