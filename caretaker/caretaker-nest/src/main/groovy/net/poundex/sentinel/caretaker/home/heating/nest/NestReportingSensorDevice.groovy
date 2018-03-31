package net.poundex.sentinel.caretaker.home.heating.nest

import groovy.transform.CompileStatic
import groovy.transform.ToString
import net.poundex.sentinel.caretaker.home.ReportingSensorDevice
import net.poundex.sentinel.caretaker.home.SensorBus
import net.poundex.sentinel.caretaker.home.ValueSensorValue
import systems.uom.common.USCustomary
import tec.units.indriya.quantity.Quantities
import tec.units.indriya.unit.Units

import java.time.LocalDateTime

@CompileStatic
@ToString(includePackage = false)
class NestReportingSensorDevice implements ReportingSensorDevice, NestEventTarget
{
	static final String PORT_TEMPERATURE_C = "ROOM_TEMP_C"
	static final String PORT_TEMPERATURE_F = "ROOM_TEMP_F"
	static final String PORT_HUMIDITY = "HUMIDITY"
	static final Set<String> PORTS = [ PORT_TEMPERATURE_C, PORT_TEMPERATURE_F, PORT_HUMIDITY ].toSet().asImmutable()

	final NestThermostat hardware
	private final SensorBus sensorBus

	NestReportingSensorDevice(NestThermostat hardware, SensorBus sensorBus)
	{
		this.hardware = hardware
		this.sensorBus = sensorBus
	}

	@Override
	String getDeviceId()
	{
		return createDeviceId(hardware)
	}

	static String createDeviceId(NestThermostat hardware)
	{
		return "nest:${hardware.nestDeviceId}:sensor_r"
	}

	@Override
	void handleEvent(NestPayload nestPayload)
	{
		sensorBus.publish(new ValueSensorValue(
				this,
				PORT_TEMPERATURE_C,
				Quantities.getQuantity(nestPayload.data.ambientTemperatureC, Units.CELSIUS),
				LocalDateTime.now()))
		sensorBus.publish(new ValueSensorValue(
				this,
				PORT_TEMPERATURE_F,
				Quantities.getQuantity(nestPayload.data.ambientTemperatureC, USCustomary.FAHRENHEIT),
				LocalDateTime.now()))
		sensorBus.publish(new ValueSensorValue(
				this,
				PORT_HUMIDITY,
				Quantities.getQuantity(nestPayload.data.humidity, Units.PERCENT),
				LocalDateTime.now()))
	}

	@Override
	Set<String> getPorts()
	{
		return PORTS
	}
}
