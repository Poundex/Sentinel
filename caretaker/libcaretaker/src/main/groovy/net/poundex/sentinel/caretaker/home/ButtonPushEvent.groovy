package net.poundex.sentinel.caretaker.home

import java.time.LocalDateTime

class ButtonPushEvent implements PortValue<ButtonPushEvent>
{
	final SensorDevice device
	final Integer buttonId
	final Integer pushCount
	final TriggerType triggerType
	final LocalDateTime reportTime

	ButtonPushEvent(SensorDevice device, TriggerType triggerType, Integer buttonId, Integer pushCount, LocalDateTime reportTime)
	{
		this.device = device
		this.buttonId = buttonId
		this.pushCount = pushCount
		this.reportTime = reportTime
		this.triggerType = triggerType
	}

	@Override
	ButtonPushEvent getValue()
	{
		return this
	}

	@Override
	String getPortId()
	{
		return "PORT_SCENECONTROL_BUTTON"
	}

	static enum TriggerType
	{
		PUSHED, BEING_HELD_DOWN, RELEASED_AFTER_HOLD
	}
}
