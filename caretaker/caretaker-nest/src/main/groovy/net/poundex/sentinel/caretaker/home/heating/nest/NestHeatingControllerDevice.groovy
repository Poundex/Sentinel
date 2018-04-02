package net.poundex.sentinel.caretaker.home.heating.nest

import net.poundex.sentinel.caretaker.home.heating.HeatingControllerDevice

class NestHeatingControllerDevice implements HeatingControllerDevice, NestEventTarget
{
	final NestThermostat hardware

	NestHeatingControllerDevice(NestThermostat hardware)
	{
		this.hardware = hardware
	}

	@Override
	String getDeviceId()
	{
		return createDeviceId(hardware)
	}

	@Override
	void setControlValues(Map<String, Object> portValues)
	{

	}

	static String createDeviceId(NestThermostat hardware)
	{
		return "nest:${hardware.nestDeviceId}:controller"
	}

	@Override
	void handleEvent(NestPayload nestPayload)
	{

	}
}
