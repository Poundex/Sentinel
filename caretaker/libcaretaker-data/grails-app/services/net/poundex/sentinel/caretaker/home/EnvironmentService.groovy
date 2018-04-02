package net.poundex.sentinel.caretaker.home

import groovy.transform.CompileStatic
import net.poundex.sentinel.caretaker.environment.Environment
import net.poundex.sentinel.caretaker.environment.MonitorHandler
import net.poundex.sentinel.caretaker.environment.Room
import net.poundex.sentinel.caretaker.home.trigger.Trigger

@CompileStatic
class EnvironmentService implements MonitorHandler
{
	private final ActionService actionService

	private final Map<Trigger, Boolean> alreadyTriggered = [:]

	EnvironmentService(ActionService actionService)
	{
		this.actionService = actionService
	}
	private Map<Room, Environment> map = [:]

	Environment getEnvironment(Room room)
	{
		if( ! map[room])
			map[room] = new Environment(this)

		return map[room]
	}

	void publishSensorReading(Sensor sensor, SensorPortValue value)
	{
		getEnvironment(sensor.room).postValue(sensor, value)
	}

	@Override
	void publish(Sensor sensor, Object value)
	{
		sensor.triggers.each { t ->
			boolean triggered = alreadyTriggered[t]
			boolean couldRun = t.conditions.every { c -> c.isTriggeredBy(value) }
			alreadyTriggered[t] = couldRun

			if(triggered && couldRun)
				return
			if( ! couldRun)
				return

			t.actions.each(actionService.&runAction)
		}
	}
}
