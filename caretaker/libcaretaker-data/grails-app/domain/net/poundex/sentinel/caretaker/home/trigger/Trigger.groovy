package net.poundex.sentinel.caretaker.home.trigger

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import net.poundex.sentinel.caretaker.home.AbstractPersistentSensor

@GrailsCompileStatic
@EqualsAndHashCode(includes = 'id')
class Trigger
{
	AbstractPersistentSensor sensor
	List<Action> actions

	static belongsTo = [sensor: AbstractPersistentSensor]
	static hasMany = [conditions: Condition]

}
