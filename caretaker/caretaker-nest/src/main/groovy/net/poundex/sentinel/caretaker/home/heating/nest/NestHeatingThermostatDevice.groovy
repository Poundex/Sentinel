package net.poundex.sentinel.caretaker.home.heating.nest

import groovy.util.logging.Slf4j
import net.poundex.sentinel.caretaker.home.heating.HeatingThermostatDevice

@Slf4j
class NestHeatingThermostatDevice implements HeatingThermostatDevice, NestEventTarget
{
	final NestThermostat hardware

	NestHeatingThermostatDevice(NestThermostat hardware)
	{
		this.hardware = hardware
	}

	@Override
	String getDeviceId()
	{
		return createDeviceId(hardware)
	}

	static String createDeviceId(NestThermostat hardware)
	{
		return "nest:${hardware.nestDeviceId}:thermostat"
	}

	@Override
	void handleEvent(NestPayload nestPayload)
	{

	}

	@Override
	void setPortValues(Map<String, Object> portValues)
	{

	}
}
