package net.poundex.sentinel.caretaker.home

import groovy.transform.CompileStatic
import net.poundex.sentinel.caretaker.home.trigger.Action
import net.poundex.sentinel.caretaker.home.trigger.DummyAction

@CompileStatic
class ActionService
{
	private final Map<Class, Closure> actionHandlers = [
			(DummyAction): this.&runDummyAction,
	]

	void runAction(Action action)
	{
		if( ! actionHandlers[action.class])
			return

		actionHandlers[action.class](action)
	}

	private void runDummyAction(DummyAction dummyAction)
	{
		println "Running action ${dummyAction.name}"
	}
}
