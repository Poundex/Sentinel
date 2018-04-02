package net.poudnex.sentinel.caretaker.home.lighting.hue

import net.poundex.sentinel.caretaker.home.Device
import net.poundex.sentinel.caretaker.ligting.hue.HueBridge

class HueBulbDevice implements Device
{
	final HueBridge hardware
	final String bulbId
	private final HueBridgeClient hueBridgeClient

	HueBulbDevice(HueBridge hardware, String bulbId, HueBridgeClient hueBridgeClient)
	{
		this.hardware = hardware
		this.bulbId = bulbId
		this.hueBridgeClient = hueBridgeClient
	}

	@Override
	String getDeviceId()
	{
		return createDeviceId(hardware, bulbId)
	}

	static String createDeviceId(HueBridge hardware, String bulbId)
	{
		return "hue:${hardware.bridgeAddress}:bulbs:${bulbId}"
	}

	@Override
	void setControlValues(Map<String, Object> portValues)
	{
		Map<String, Object> request = [:]
		portValues.each { p, v ->
			if(p == "PORT_GENERIC_POWER")
				request.on = (boolean) v
		}
		println hueBridgeClient.setLightState(bulbId, request)
	}
}
