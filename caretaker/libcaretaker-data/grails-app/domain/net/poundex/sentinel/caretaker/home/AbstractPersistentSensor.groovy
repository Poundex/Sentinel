package net.poundex.sentinel.caretaker.home

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import net.poundex.sentinel.caretaker.environment.PersistentRoom
import net.poundex.sentinel.caretaker.home.trigger.Trigger

@ToString(includePackage = false)
@GrailsCompileStatic
@EqualsAndHashCode(includes = 'id')
abstract class AbstractPersistentSensor<T extends SensorPortValue> implements Sensor<T>
{
	PersistentRoom room
	List<Trigger> triggers = []

	static hasMany = [readers: SensorReader, triggers: Trigger]

    static constraints = {
    }
}
