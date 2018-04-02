package net.poundex.sentinel.caretaker.home

interface Device
{
	String getDeviceId()
	Hardware getHardware()

	void setControlValues(Map<String, Object> portValues)
}
