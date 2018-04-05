package net.poundex.sentinel.caretaker.home.trigger

import net.poundex.sentinel.caretaker.home.ButtonPushEvent

class ButtonTrigger extends Trigger
{
	int buttonNumberOnDevice
	ButtonPushEvent.TriggerType triggerType
	Integer pushCount

	static constraints = {
		pushCount nullable: true, min: 1, max: 5
	}

	@Override
	boolean isTriggeredBy(Object object)
	{
		if( ! (object instanceof ButtonPushEvent))
			return false

		ButtonPushEvent bpe = (ButtonPushEvent) object
		return bpe.buttonId == buttonNumberOnDevice &&
				bpe.triggerType == triggerType &&
				(pushCount == null || bpe.pushCount == pushCount)
	}

	@Override
	boolean shouldRetrigger()
	{
		return true
	}

}
