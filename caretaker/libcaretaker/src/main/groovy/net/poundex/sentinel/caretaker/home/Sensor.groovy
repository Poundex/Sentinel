package net.poundex.sentinel.caretaker.home

import groovy.transform.CompileStatic
import net.poundex.sentinel.caretaker.environment.Room
import org.springframework.scheduling.Trigger

@CompileStatic
interface Sensor<T extends SensorPortValue>
{
	Room getRoom()
//	String getDeviceId()
//	String getSensorPort()
//	Set<SensorRole> getRoles()
	T readValue(SensorPortValue sensorValue)
	Object aggregate(Collection<? extends T> portValues)
}
