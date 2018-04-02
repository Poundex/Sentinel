package net.poundex.sentinel.caretaker.home.trigger
//@GrailsCompileStatic
abstract class Condition<T>
{
	protected abstract T getTriggerValue()
	abstract boolean isTriggeredBy(Object value)
	static belongsTo = [trigger: Trigger]

	static mapping = {
		tablePerHierarchy false
	}
}
