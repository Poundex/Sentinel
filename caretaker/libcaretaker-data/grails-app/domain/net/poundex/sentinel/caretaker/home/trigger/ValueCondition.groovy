package net.poundex.sentinel.caretaker.home.trigger

import grails.compiler.GrailsCompileStatic

import javax.measure.Quantity

//@GrailsCompileStatic
class ValueCondition extends Condition<Double>
{
	ValueTriggerType valueTriggerType
	Double triggerValue

	@Override
	boolean isTriggeredBy(Object value)
	{
		Number v
		if(value instanceof Quantity)
			v = value.value
		else if(value instanceof Number)
			v = (Number) value
		else
			return false

		return valueTriggerType.isTriggeredBy(triggerValue, v)
	}

	static enum ValueTriggerType
	{
		GREATER_THAN({ Number triggerValue, Number value ->
			value > triggerValue
		})

		private final Closure<Boolean> function

		ValueTriggerType(Closure<Boolean> function)
		{
			this.function = function
		}

		boolean isTriggeredBy(Number triggerValue, Number value)
		{
			return function.call(triggerValue, value)
		}
	}
}
