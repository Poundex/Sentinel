package net.poundex.sentinel.caretaker.home

class ButtonMonitor extends Monitor<ButtonPushEvent>
{
    static constraints = {
    }

	@Override
	PortValue<ButtonPushEvent> readPortValue(PortValue<?> portValue)
	{
		if(portValue instanceof ButtonPushEvent)
			return (ButtonPushEvent) portValue

		return null
	}

	@Override
	ButtonPushEvent getMonitorValueForPortValues(Collection<PortValue<ButtonPushEvent>> portValues, PortValue<ButtonPushEvent> triggeringPortValue)
	{
		return triggeringPortValue as ButtonPushEvent
	}
}
