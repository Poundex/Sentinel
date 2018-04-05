package net.poundex.sentinel.caretaker.zwave

import net.poundex.sentinel.caretaker.home.SensorDevice

class ZWaveSceneController extends ZWaveNodeDevice implements SensorDevice
{
	static final String PORT_SCENECONTROL_BUTTON_PUSH = "PORT_SCENECONTROL_BUTTON_PUSH"
	static final String PORT_SCENECONTROL_BUTTON_BEING_HELD = "PORT_SCENECONTROL_BUTTON_BEING_HELD"
	static final String PORT_SCENECONTROL_BUTTON_RELEASED_AFTER_HOLD = "PORT_SCENECONTROL_BUTTON_RELEASED_AFTER_HOLD"

	ZWaveSceneController(ZWaveModem hardware, byte nodeId)
	{
		super(hardware, nodeId)
	}

	@Override
	Set<String> getPorts()
	{
		return [ PORT_SCENECONTROL_BUTTON_BEING_HELD, PORT_SCENECONTROL_BUTTON_PUSH, PORT_SCENECONTROL_BUTTON_RELEASED_AFTER_HOLD ]
	}
}
