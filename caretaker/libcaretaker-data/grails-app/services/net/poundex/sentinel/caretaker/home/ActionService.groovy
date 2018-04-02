package net.poundex.sentinel.caretaker.home

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import net.poundex.sentinel.caretaker.home.trigger.Action
import net.poundex.sentinel.caretaker.home.trigger.ControlApplianceAction
import net.poundex.sentinel.caretaker.home.trigger.DummyAction
import org.springframework.beans.factory.annotation.Qualifier

import java.util.concurrent.BlockingQueue
import java.util.concurrent.ThreadPoolExecutor

@CompileStatic
@Slf4j
class ActionService
{
	private final DeviceManager deviceManager
	private final ThreadPoolExecutor pool
	private final Map<Class<? extends Action>, Closure> actionHandlers = [
			(DummyAction): this.&runDummyAction,
			(ControlApplianceAction): this.&runApplianceAction
	]

	ActionService(DeviceManager deviceManager,
	              @Qualifier("caretakerWorkerPool") ThreadPoolExecutor pool)
	{
		this.deviceManager = deviceManager
		this.pool = pool
	}

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
		pool.execute {
			log.info("Dummy Action! ${dummyAction}")
		}
	}

	private void runApplianceAction(ControlApplianceAction controlApplianceAction)
	{
		pool.execute {
			log.info("Device Action: ${controlApplianceAction}")
			ControllableDevice device = deviceManager.getDevice(controlApplianceAction.appliance.deviceId, ControllableDevice)
			device.setPortValues(controlApplianceAction.controlValues.collectEntries {
				[it.portId, it.controlValue]
			})
		}
	}
}
