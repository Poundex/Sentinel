package net.poundex.sentinel.caretaker.home

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class SensorBusService implements SensorBus
{
	private final EnvironmentService environmentService

	SensorBusService(EnvironmentService environmentService)
	{
		this.environmentService = environmentService
	}

	@Override
	void publish(SensorPortValue sensorValue)
	{
		log.debug("Received value to publish ${sensorValue}")
		SensorReader.withNewSession {
			List<SensorReader> readers = SensorReader.findAllByDeviceIdAndPortId(
					sensorValue.sourceDevice.deviceId,
					sensorValue.sourcePort)

			if (readers.empty)
				return

			readers.each { r ->
				sensorValue = r.readValue(sensorValue)
				if( ! sensorValue)
					return

				environmentService.publishSensorReading(r.sensor, sensorValue)
			}
		}
	}
}
