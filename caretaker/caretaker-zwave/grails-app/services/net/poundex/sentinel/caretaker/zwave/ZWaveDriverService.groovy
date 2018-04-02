package net.poundex.sentinel.caretaker.zwave

import groovy.transform.CompileStatic
import net.poundex.sentinel.caretaker.home.DataBus
import net.poundex.sentinel.caretaker.home.DeviceManager
import net.poundex.sentinel.caretaker.home.Driver
import net.poundex.sentinel.caretaker.home.Hardware

@CompileStatic
class ZWaveDriverService implements Driver
{
	@Override
	void createDevices(Hardware hardware, DeviceManager deviceManager, DataBus dataBus)
    {
        if( ! (hardware instanceof ZWaveModem))
	        return

	    ZWaveModem modem = (ZWaveModem)hardware

	    ZWaveModemDevice controllerDevice = new ZWaveModemDevice(modem, deviceManager, dataBus)
	    deviceManager.register(controllerDevice)
	    controllerDevice.start()
    }
}
