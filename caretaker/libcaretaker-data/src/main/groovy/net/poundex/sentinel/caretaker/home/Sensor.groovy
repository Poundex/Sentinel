package net.poundex.sentinel.caretaker.home

import groovy.transform.CompileStatic
import net.poundex.sentinel.caretaker.environment.Room
import net.poundex.sentinel.caretaker.home.trigger.Trigger

@CompileStatic
interface Sensor<T extends PortValue>
{
	Room getRoom()
	T readValue(PortValue sensorValue)
	Object aggregate(Collection<? extends T> portValues)
	Collection<Trigger> getTriggers()
}
