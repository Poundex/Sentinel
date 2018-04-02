package net.poundex.sentinel.caretaker.home.trigger

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import net.poundex.sentinel.caretaker.home.Monitor

@GrailsCompileStatic
@EqualsAndHashCode(includes = 'id')
class Trigger
{
	Monitor sensor
	List<Action> actions

	static belongsTo = [sensor: Monitor]
	static hasMany = [conditions: Condition]

}
