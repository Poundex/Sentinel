package net.poundex.sentinel.caretaker.home.heating.nest

import net.poundex.sentinel.caretaker.home.*

class NestDriverService implements Driver
{
	private final NestEventSourceService nestEventSourceService

	NestDriverService(NestEventSourceService nestEventSourceService)
	{
		this.nestEventSourceService = nestEventSourceService
	}

	@Override
	void createDevices(Hardware hardware, DeviceManager deviceManager)
	{
		if ( ! (hardware instanceof NestThermostat))
			return

		NestHeatingControllerDevice controllerDevice =
				new NestHeatingControllerDevice(hardware)
		NestHeatingThermostatDevice thermostatDevice =
				new NestHeatingThermostatDevice(hardware)
		NestReportingSensorDevice sensorDevice =
				new NestReportingSensorDevice(hardware, deviceManager.sensorBus)

		nestEventSourceService.addEventTarget(hardware, controllerDevice)
		nestEventSourceService.addEventTarget(hardware, thermostatDevice)
		nestEventSourceService.addEventTarget(hardware, sensorDevice)

		deviceManager.register(controllerDevice)
		deviceManager.register(thermostatDevice)
		deviceManager.register(sensorDevice)
	}
}
