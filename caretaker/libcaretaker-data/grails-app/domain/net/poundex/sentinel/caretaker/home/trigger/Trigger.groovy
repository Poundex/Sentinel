package net.poundex.sentinel.caretaker.home.trigger

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import net.poundex.sentinel.caretaker.home.Monitor

@GrailsCompileStatic
@EqualsAndHashCode(includes = 'id')
abstract class Trigger
{
	Monitor monitor
	List<Action> actions

	static belongsTo = [monitor: Monitor]

	abstract boolean isTriggeredBy(Object object)

	boolean shouldRetrigger()
	{
		return false
	}
}
