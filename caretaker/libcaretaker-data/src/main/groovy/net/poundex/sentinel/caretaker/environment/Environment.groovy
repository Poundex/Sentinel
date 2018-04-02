package net.poundex.sentinel.caretaker.environment

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import net.poundex.sentinel.caretaker.home.Appliance
import net.poundex.sentinel.caretaker.home.Device
import net.poundex.sentinel.caretaker.home.Monitor
import net.poundex.sentinel.caretaker.home.PortValue

@Slf4j
@CompileStatic
class Environment
{
	private final MonitorHandler monitorHandler

	private final Map<Monitor, Map<Device, PortValue>> portValuesByDeviceByMonitor = [:]
	private final Map<Monitor, Object> valuesByMonitor = [:]

	Environment(MonitorHandler monitorHandler)
	{
		this.monitorHandler = monitorHandler
	}

	public <T> void publishReadingToMonitor(Monitor<T> monitor, PortValue<T> portValue)
	{
		if( ! portValuesByDeviceByMonitor[monitor])
			portValuesByDeviceByMonitor[monitor] = [:]

		PortValue previousPortValue =
				portValuesByDeviceByMonitor[monitor][portValue.device]
		portValuesByDeviceByMonitor[monitor][portValue.device] = portValue
		if(previousPortValue == portValue)
			return

		T newMonitorValue = monitor.getMonitorValueForPortValues(portValuesByDeviceByMonitor[monitor].values())
		if(valuesByMonitor[monitor] == newMonitorValue)
			return

		valuesByMonitor[monitor] = newMonitorValue
		log.debug("Monitor ${monitor} has new value ${newMonitorValue}")
		monitorHandler.updateMonitorValue(monitor, newMonitorValue)
	}
}
