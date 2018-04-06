package net.poundex.sentinel.caretaker.zwave

import com.whizzosoftware.wzwave.commandclass.BinarySensorCommandClass
import com.whizzosoftware.wzwave.commandclass.MultilevelSensorCommandClass
import groovy.transform.CompileStatic
import groovy.transform.ToString
import net.poundex.sentinel.caretaker.home.BinaryPortValue
import net.poundex.sentinel.caretaker.home.DataBus
import net.poundex.sentinel.caretaker.home.QuantityPortValue
import net.poundex.sentinel.caretaker.home.SensorDevice
import systems.uom.common.USCustomary
import tec.units.indriya.quantity.Quantities
import tec.units.indriya.unit.BaseUnit
import tec.units.indriya.unit.Units

import javax.measure.Quantity
import javax.measure.Unit
import java.time.LocalDateTime

@CompileStatic
@ToString(includePackage = false)
class ZWaveSensorDevice extends ZWaveNodeDevice implements SensorDevice
{
	static final String PORT_BINARY = "BINARY"
	static final String PORT_MULTILEVEL_TEMPERATURE = "MLS_TEMPERATURE"
	static final String PORT_MULTILEVEL_HUMIDITY = "MLS_HUMIDITY"

	ZWaveSensorDevice(ZWaveModem hardware, byte nodeId)
	{
		super(hardware, nodeId)
	}

	@Override
	Set<String> getPorts()
	{
		return [ PORT_MULTILEVEL_HUMIDITY, PORT_MULTILEVEL_TEMPERATURE, PORT_BINARY ].toSet()
	}

	protected handleCommandClass(MultilevelSensorCommandClass cc, DataBus dataBus)
	{
		dataBus.announcePortValue(new QuantityPortValue(
				this,
				getPortForMultilevelType(cc.type),
				Quantities.getQuantity(cc.values.first(), getUnit(cc.scale)),
				LocalDateTime.now()))
	}

	protected handleCommandClass(BinarySensorCommandClass cc, DataBus dataBus)
	{
		dataBus.announcePortValue(new BinaryPortValue(
				this,
				PORT_BINARY,
				! cc.isIdle,
				LocalDateTime.now()))
	}

	static String getPortForMultilevelType(MultilevelSensorCommandClass.Type type)
	{
		switch (type)
		{
			case MultilevelSensorCommandClass.Type.AirTemperature:
				return PORT_MULTILEVEL_TEMPERATURE
			case MultilevelSensorCommandClass.Type.Humidity:
				return PORT_MULTILEVEL_HUMIDITY
			default:
					return "PORT_UNKNOWN"
		}
	}

	static <Q extends Quantity<Q>> Unit<Q> getUnit(MultilevelSensorCommandClass.Scale scale)
	{
		switch(scale)
		{
			case MultilevelSensorCommandClass.Scale.Celsius:
				return Units.CELSIUS as Unit<Q>
			case MultilevelSensorCommandClass.Scale.Fahrenheit:
				return USCustomary.FAHRENHEIT as Unit<Q>
			case MultilevelSensorCommandClass.Scale.AbsoluteHumidity:
			case MultilevelSensorCommandClass.Scale.PercentageValue:
				return Units.PERCENT as Unit<Q> // TODO !
			default:
				return new BaseUnit<Q>("?")
		}
	}
}
