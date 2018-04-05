package net.poundex.sentinel.caretaker.home

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import net.poundex.sentinel.caretaker.environment.PersistentRoom
import net.poundex.sentinel.caretaker.home.trigger.Trigger

@ToString(includePackage = false)
@GrailsCompileStatic
@EqualsAndHashCode(includes = 'id')
abstract class Monitor<T>// implements Sensor<T>
{
	PersistentRoom room
	List<Trigger> triggers = []
//	abstract Collection<SensorReader<PortValue<T>>> getSensorReaders()
	abstract PortValue<T> readPortValue(PortValue<?> portValue)
	abstract T getMonitorValueForPortValues(Collection<PortValue<T>> portValues, PortValue<T> triggeringPortValue)

	static hasMany = [triggers: Trigger, readers: SensorReader]

    static constraints = {
    }

//	static mapping = {
//		tablePerHierarchy false
//	}
}
