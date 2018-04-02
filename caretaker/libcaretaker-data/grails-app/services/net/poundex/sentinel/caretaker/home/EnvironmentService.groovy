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
//	private final DataBus dataBus

	private final Map<Trigger, Boolean> alreadyTriggered = [:]

	EnvironmentService(ActionService actionService, DataBus dataBus)
	{
		this.actionService = actionService
		dataBus.addPortValueAnnouncementListener(this.&publishReadingToMonitor)
	}

	private Map<Room, Environment> map = [:]

	Environment getEnvironment(Room room)
	{
		if( ! map[room])
			map[room] = new Environment(this)

		return map[room]
	}

	public <T> void publishReadingToMonitor(Monitor<T> monitor, PortValue<?> portValue)
	{
		PortValue<T> readValue = monitor.readPortValue(portValue)
		if( ! readValue)
			return
		getEnvironment(monitor.room).publishReadingToMonitor(monitor, readValue)
	}

	@Override
	public <T> void updateMonitorValue(Monitor<T> monitor, T value)
	{
		monitor.triggers.each { t ->
			boolean triggered = alreadyTriggered[t]
			boolean couldRun = t.conditions.every { c -> c.isTriggeredBy(value) }
			alreadyTriggered[t] = couldRun

			if(triggered && couldRun)
				return
			if( ! couldRun)
				return

//			t.actions.each {
//				println "Would run action ${it}"
//			}
			t.actions.each(actionService.&runAction)
		}
	}

//	void setControlValues(
}
