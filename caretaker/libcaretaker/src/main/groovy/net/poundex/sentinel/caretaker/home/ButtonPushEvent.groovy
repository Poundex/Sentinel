package net.poundex.sentinel.caretaker.home

import java.time.LocalDateTime

class ButtonPushEvent implements PortValue<ButtonPushEvent>
{
	final SensorDevice device
	final String portId
	final Integer buttonId
	final Integer pushCount
	final LocalDateTime reportTime

	ButtonPushEvent(SensorDevice device, String portId, Integer buttonId, Integer pushCount, LocalDateTime reportTime)
	{
		this.device = device
		this.buttonId = buttonId
		this.pushCount = pushCount
		this.reportTime = reportTime
		this.portId = portId
	}

	@Override
	ButtonPushEvent getValue()
	{
		return this
	}

	TriggerType getTriggerType()
	{
		switch(portId)
		{
			case "PORT_SCENECONTROL_BUTTON_BEING_HELD":
				return TriggerType.BEING_HELD_DOWN
			case "PORT_SCENECONTROL_BUTTON_RELEASED_AFTER_HOLD":
				return TriggerType.RELEASED_AFTER_HOLD
			case "PORT_SCENECONTROL_BUTTON_PUSH":
				return TriggerType.PUSHED
			default:
				return null
		}
	}

	static enum TriggerType
	{
		PUSHED, BEING_HELD_DOWN, RELEASED_AFTER_HOLD
	}
}
