package net.poundex.sentinel.caretaker.zwave

import com.whizzosoftware.wzwave.commandclass.CentralSceneCommandClass
import groovy.transform.CompileStatic
import net.poundex.sentinel.caretaker.home.ButtonPushEvent
import net.poundex.sentinel.caretaker.home.DataBus
import net.poundex.sentinel.caretaker.home.SensorDevice

import java.time.LocalDateTime

@CompileStatic
class ZWaveSceneController extends ZWaveNodeDevice implements SensorDevice
{
	static final String PORT_SCENECONTROL_BUTTON = "PORT_SCENECONTROL_BUTTON"

	private int lastSeenSequenceNumber = Integer.MIN_VALUE

	ZWaveSceneController(ZWaveModem hardware, byte nodeId)
	{
		super(hardware, nodeId)
	}

	@Override
	Set<String> getPorts()
	{
		return [ PORT_SCENECONTROL_BUTTON ].toSet()
	}

	protected void handleCommandClass(CentralSceneCommandClass cc, DataBus dataBus)
	{
		if (cc.sequenceNumber <= lastSeenSequenceNumber)
			return

		dataBus.announcePortValue(new ButtonPushEvent(
				this,
				getTriggerTypeForSceneCommand(cc.sceneCommand),
				cc.sceneNumber,
				cc.pushCount,
				LocalDateTime.now()))

		lastSeenSequenceNumber = cc.sequenceNumber
	}

	static ButtonPushEvent.TriggerType getTriggerTypeForSceneCommand(CentralSceneCommandClass.SceneCommand cmd)
	{
		switch(cmd)
		{
			case CentralSceneCommandClass.SceneCommand.PUSHED:
				return ButtonPushEvent.TriggerType.PUSHED
			case CentralSceneCommandClass.SceneCommand.RELEASED_AFTER_HOLD:
				return ButtonPushEvent.TriggerType.RELEASED_AFTER_HOLD
			case CentralSceneCommandClass.SceneCommand.BEING_HELD:
				return ButtonPushEvent.TriggerType.BEING_HELD_DOWN
		}
	}
}
