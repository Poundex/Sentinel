package net.poundex.sentinel.caretaker.home.trigger

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ConditionalTrigger extends Trigger
{
	static hasMany = [conditions: Condition]

	@Override
	boolean isTriggeredBy(Object object)
	{
		return conditions.every { it.isTriggeredBy(object) }
	}
}
