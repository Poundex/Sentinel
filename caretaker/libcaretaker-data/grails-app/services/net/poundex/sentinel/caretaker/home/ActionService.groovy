package net.poundex.sentinel.caretaker.home

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import net.poundex.sentinel.caretaker.home.trigger.Action
import net.poundex.sentinel.caretaker.home.trigger.ControlApplianceAction
import net.poundex.sentinel.caretaker.home.trigger.DummyAction

@CompileStatic
@Slf4j
class ActionService
{
	private final Map<Class<? extends Action>, Closure> actionHandlers = [
			(DummyAction): this.&runDummyAction,
			(ControlApplianceAction): this.&runApplianceAction
	]

	void runAction(Action action)
	{
		Class<? extends Action> actionClass = actionHandlers.keySet().find {
			it.isAssignableFrom(action.class)
		}
		if(actionClass)
			actionHandlers[actionClass](action)
		else
			log.warn("No handler found for action type ${action.class.simpleName}")
	}

	private void runDummyAction(DummyAction dummyAction)
	{
		log.info("Dummy Action! ${dummyAction}")
	}

	private void runApplianceAction(ControlApplianceAction controlApplianceAction)
	{
		println "Doing ${controlApplianceAction.controlValue} => ${controlApplianceAction.portId} @ ${controlApplianceAction.appliance}"
	}
}
