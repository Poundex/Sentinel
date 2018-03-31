package net.poundex.sentinel.caretaker.zwave

import net.poundex.sentinel.caretaker.home.DeviceManager
import net.poundex.sentinel.caretaker.home.Driver
import net.poundex.sentinel.caretaker.home.Hardware

class ZWaveDriverService implements Driver
{
	@Override
	void createDevices(Hardware hardware, DeviceManager deviceManager)
    {
        if( ! (hardware instanceof ZWaveModem))
	        return

	    ZWaveModemDevice controllerDevice = new ZWaveModemDevice(hardware, deviceManager)
	    deviceManager.register(controllerDevice)
	    controllerDevice.start()
    }
}
