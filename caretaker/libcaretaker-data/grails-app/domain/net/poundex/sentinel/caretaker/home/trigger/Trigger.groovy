package net.poundex.sentinel.caretaker.home.trigger

import grails.compiler.GrailsCompileStatic
import net.poundex.sentinel.caretaker.home.AbstractPersistentSensor
import net.poundex.sentinel.caretaker.home.SensorPortValue

@GrailsCompileStatic
abstract class Trigger<T>
{
	AbstractPersistentSensor sensor

	abstract T getTriggerValue()
	static belongsTo = [sensor: AbstractPersistentSensor]
}
