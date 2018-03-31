package net.poundex.sentinel.caretaker.home

import groovy.transform.CompileStatic
import net.poundex.sentinel.caretaker.environment.Environment
import net.poundex.sentinel.caretaker.environment.Room

@CompileStatic
class EnvironmentService
{
	private Map<Room, Environment> map = [:]

	Environment getEnvironment(Room room)
	{
		if( ! map[room])
			map[room] = new Environment()

		return map[room]
	}

	void publishSensorReading(Sensor sensor, SensorPortValue value)
	{
		getEnvironment(sensor.room).postValue(sensor, value)
	}
}
