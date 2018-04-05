package net.poundex.sentinel.caretaker.zwave

import groovy.transform.CompileStatic
import net.poundex.sentinel.caretaker.home.SensorDevice

@CompileStatic
class ZWaveSceneController extends ZWaveNodeDevice implements SensorDevice
{
	static final String PORT_SCENECONTROL_BUTTON = "PORT_SCENECONTROL_BUTTON"

	ZWaveSceneController(ZWaveModem hardware, byte nodeId)
	{
		super(hardware, nodeId)
	}

	@Override
	Set<String> getPorts()
	{
		return [ PORT_SCENECONTROL_BUTTON ].toSet()
	}
}
