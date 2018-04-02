package net.poundex.sentinel.caretaker.home

interface ControllableDevice extends Device
{
	void setPortValues(Map<String, Object> portValues)
}
