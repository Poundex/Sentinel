package net.poundex.sentinel.caretaker.home

import grails.compiler.GrailsCompileStatic
import groovy.transform.ToString
import net.poundex.sentinel.caretaker.environment.PersistentRoom
import net.poundex.sentinel.caretaker.home.trigger.Trigger

@ToString(includePackage = false)
@GrailsCompileStatic
abstract class AbstractPersistentSensor<T extends SensorPortValue> implements Sensor<T>
{
	PersistentRoom room
	List<Trigger> triggers = []

	static hasMany = [readers: SensorReader, triggers: Trigger]

    static constraints = {
    }

	boolean equals(o)
	{
		if (this.is(o)) return true
		if (!(o instanceof AbstractPersistentSensor)) return false

		AbstractPersistentSensor that = (AbstractPersistentSensor) o

		if (id != that.id) return false

		return true
	}

	int hashCode()
	{
		return (id != null ? id.hashCode() : 0)
	}
}
