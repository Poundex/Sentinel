package net.poundex.sentinel.caretaker.environment

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import net.poundex.sentinel.caretaker.home.Device
import net.poundex.sentinel.caretaker.home.Sensor
import net.poundex.sentinel.caretaker.home.SensorPortValue

@Slf4j
@CompileStatic
class Environment
{
	private final MonitorHandler monitorHandler

	private final Map<Sensor, Map<Device, SensorPortValue>> portValuesByDeviceBySensor = [:]
	private final Map<Sensor, Object> valuesBySensor = [:]

	Environment(MonitorHandler monitorHandler)
	{
		this.monitorHandler = monitorHandler
	}

	void postValue(Sensor sensor, SensorPortValue sensorPortValue)
	{
		SensorPortValue valueAsRead = sensor.readValue(sensorPortValue)
		if( ! valueAsRead)
			return

		if( ! portValuesByDeviceBySensor[sensor])
			portValuesByDeviceBySensor[sensor] = [:]

		SensorPortValue previousSensorPortValue =
				portValuesByDeviceBySensor[sensor][sensorPortValue.sourceDevice]
		portValuesByDeviceBySensor[sensor][sensorPortValue.sourceDevice] = sensorPortValue
		if(previousSensorPortValue == sensorPortValue)
			return

		Object newSensorValue = sensor.aggregate(portValuesByDeviceBySensor[sensor].values())
		if(valuesBySensor[sensor] == newSensorValue)
			return

		valuesBySensor[sensor] = newSensorValue
		log.debug("Monitor ${sensor} has new value ${newSensorValue}")
		monitorHandler.publish(sensor, newSensorValue)
	}
}
