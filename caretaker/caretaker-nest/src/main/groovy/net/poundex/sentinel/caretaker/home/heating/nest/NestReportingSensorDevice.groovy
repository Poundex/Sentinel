package net.poundex.sentinel.caretaker.home.heating.nest

import groovy.transform.CompileStatic
import groovy.transform.ToString
import net.poundex.sentinel.caretaker.home.ReportingSensorDevice
import net.poundex.sentinel.caretaker.home.DataBus
import net.poundex.sentinel.caretaker.home.QuantityPortValue
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
	private final DataBus dataBus

	NestReportingSensorDevice(NestThermostat hardware, DataBus dataBus)
	{
		this.hardware = hardware
		this.dataBus = dataBus
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
		dataBus.announcePortValue(new QuantityPortValue(
				this,
				PORT_TEMPERATURE_C,
				Quantities.getQuantity(nestPayload.data.ambientTemperatureC, Units.CELSIUS),
				LocalDateTime.now()))
		dataBus.announcePortValue(new QuantityPortValue(
				this,
				PORT_TEMPERATURE_F,
				Quantities.getQuantity(nestPayload.data.ambientTemperatureC, USCustomary.FAHRENHEIT),
				LocalDateTime.now()))
		dataBus.announcePortValue(new QuantityPortValue(
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
