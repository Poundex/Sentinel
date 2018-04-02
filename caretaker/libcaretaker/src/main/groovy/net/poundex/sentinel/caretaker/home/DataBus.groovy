package net.poundex.sentinel.caretaker.home

interface DataBus
{
	public <T> void announcePortValue(PortValue<T> value)

	void addPortValueAnnouncementListener(Closure listener)

	void setPortValues(Appliance appliance, Map<String, Object> portValues)
}
