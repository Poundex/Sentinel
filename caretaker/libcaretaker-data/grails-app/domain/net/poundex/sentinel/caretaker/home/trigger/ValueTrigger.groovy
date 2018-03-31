package net.poundex.sentinel.caretaker.home.trigger

import grails.compiler.GrailsCompileStatic

import javax.measure.Quantity

@GrailsCompileStatic
class ValueTrigger extends Trigger<Double>
{
	ValueTriggerType valueTriggerType
	Double triggerValue

	static enum ValueTriggerType
	{
		GREATER_THAN
	}
}
