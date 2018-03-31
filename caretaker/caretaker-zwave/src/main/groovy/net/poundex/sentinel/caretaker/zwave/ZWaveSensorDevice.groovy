package net.poundex.sentinel.caretaker.zwave

import groovy.transform.CompileStatic
import groovy.transform.ToString
import net.poundex.sentinel.caretaker.home.SensorDevice

@CompileStatic
@ToString(includePackage = false)
class ZWaveSensorDevice extends ZWaveNodeDevice implements SensorDevice
{
	ZWaveSensorDevice(ZWaveModem hardware, byte nodeId)
	{
		super(hardware, nodeId)
	}

	@Override
	Set<String> getPorts()
	{
		return [ZWaveModemDevice.PORT_MULTILEVEL_HUMIDITY, ZWaveModemDevice.PORT_MULTILEVEL_TEMPERATURE, ZWaveModemDevice.PORT_BINARY].toSet()
	}
}
