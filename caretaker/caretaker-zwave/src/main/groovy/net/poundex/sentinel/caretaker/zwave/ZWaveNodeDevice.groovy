package net.poundex.sentinel.caretaker.zwave

import net.poundex.sentinel.caretaker.home.Device

class ZWaveNodeDevice implements Device
{
	private final ZWaveModem hardware
	protected final byte nodeId

	ZWaveNodeDevice(ZWaveModem hardware, byte nodeId)
	{
		this.hardware = hardware
		this.nodeId = nodeId
	}

	@Override
	String getDeviceId()
	{
		return createDeviceId(hardware, nodeId)
	}

	static String createDeviceId(ZWaveModem hardware, byte nodeId)
	{
		return "zwave:${hardware.modemDevice}:node:${nodeId}"
	}

	ZWaveModem getHardware()
	{
		return hardware
	}

	@Override
	void setControlValues(Map<String, Object> portValues)
	{

	}
}
