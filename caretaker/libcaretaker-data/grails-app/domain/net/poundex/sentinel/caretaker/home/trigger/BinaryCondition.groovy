package net.poundex.sentinel.caretaker.home.trigger

import grails.compiler.GrailsCompileStatic

//@GrailsCompileStatic
class BinaryCondition extends Condition<Boolean>
{
	Boolean triggerValue

    static constraints = {
    }

	static mapping = {

	}

	@Override
	boolean isTriggeredBy(Object value)
	{
		return (boolean) value
	}
}
