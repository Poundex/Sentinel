package net.poundex.sentinel.caretaker.home.trigger

import grails.compiler.GrailsCompileStatic
import net.poundex.sentinel.caretaker.home.AbstractPersistentSensor

@GrailsCompileStatic
class Trigger
{
	AbstractPersistentSensor sensor
	List<Action> actions

	static belongsTo = [sensor: AbstractPersistentSensor]
	static hasMany = [conditions: Condition]

}
