package net.poundex.sentinel.caretaker.home.heating.nest

import groovy.transform.CompileStatic
import net.poundex.sentinel.caretaker.home.*

@CompileStatic
class NestDriverService implements Driver
{
	private final NestEventSourceService nestEventSourceService
//	private final DataBus dataBus

	NestDriverService(NestEventSourceService nestEventSourceService)
	{
		this.nestEventSourceService = nestEventSourceService
//		this.dataBus = dataBus
	}

	@Override
	void createDevices(Hardware hardware, DeviceManager deviceManager, DataBus dataBus)
	{
		if ( ! (hardware instanceof NestThermostat))
			return
		
		NestThermostat thermostat = (NestThermostat) hardware

		NestHeatingControllerDevice controllerDevice =
				new NestHeatingControllerDevice(thermostat)
		NestHeatingThermostatDevice thermostatDevice =
				new NestHeatingThermostatDevice(thermostat)
		NestReportingSensorDevice sensorDevice =
				new NestReportingSensorDevice(thermostat, dataBus)

		nestEventSourceService.addEventTarget(thermostat, controllerDevice)
		nestEventSourceService.addEventTarget(thermostat, thermostatDevice)
		nestEventSourceService.addEventTarget(thermostat, sensorDevice)

		deviceManager.register(controllerDevice)
		deviceManager.register(thermostatDevice)
		deviceManager.register(sensorDevice)
	}
}
