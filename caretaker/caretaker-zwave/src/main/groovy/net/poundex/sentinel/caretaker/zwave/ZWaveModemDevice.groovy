package net.poundex.sentinel.caretaker.zwave

import com.whizzosoftware.wzwave.commandclass.BinarySensorCommandClass
import com.whizzosoftware.wzwave.commandclass.CommandClass
import com.whizzosoftware.wzwave.commandclass.MultilevelSensorCommandClass
import com.whizzosoftware.wzwave.controller.ZWaveController
import com.whizzosoftware.wzwave.controller.netty.NettyZWaveController
import com.whizzosoftware.wzwave.node.ZWaveEndpoint
import com.whizzosoftware.wzwave.node.ZWaveNode
import com.whizzosoftware.wzwave.node.generic.MultilevelSensor
import groovy.transform.CompileStatic
import groovy.transform.ToString
import net.poundex.sentinel.caretaker.home.*
import si.uom.NonSI
import systems.uom.common.USCustomary
import tec.units.indriya.quantity.Quantities
import tec.units.indriya.unit.BaseUnit
import tec.units.indriya.unit.Units

import javax.measure.Quantity
import javax.measure.Unit
import java.nio.file.Files
import java.time.LocalDateTime

@CompileStatic
@ToString(includePackage = false)
class ZWaveModemDevice implements Device, ZWaveControllerListenerAdapter
{
	static final String PORT_BINARY = "BINARY"
	static final String PORT_MULTILEVEL_TEMPERATURE = "MLS_TEMPERATURE"
	static final String PORT_MULTILEVEL_HUMIDITY = "MLS_HUMIDITY"

	final ZWaveModem hardware

	private final Map<Byte, ZWaveNodeDevice> nodes = [:]
	private final ZWaveController zWaveController
	private final DeviceManager deviceManager

	ZWaveModemDevice(ZWaveModem hardware, DeviceManager deviceManager)
	{
		this.hardware = hardware
		this.zWaveController = new NettyZWaveController(hardware.modemDevice,
				Files.createTempDirectory("zwavetmp").toFile())
		this.deviceManager = deviceManager
	}

	void start()
	{
		zWaveController.listener = this
		zWaveController.start()
	}

	ZWaveNodeDevice getDeviceAtNode(byte node)
	{
		return nodes[node]
	}

	@Override
	String getDeviceId()
	{
		return createDeviceId(hardware)
	}

	@Override
	void setControlValues(Map<String, Object> portValues)
	{

	}

	static String createDeviceId(ZWaveModem controller)
	{
		return "zwave:${controller.modemDevice}:controller"
	}


	void registerNode(ZWaveNode zWaveNode)
	{
		ZWaveNodeDevice nodeDevice
		switch (zWaveNode)
		{
			case MultilevelSensor:
				nodeDevice = new ZWaveSensorDevice(hardware, zWaveNode.nodeId)
				break
			default:
				nodeDevice = new ZWaveNodeDevice(hardware, zWaveNode.nodeId)
		}
		nodes[zWaveNode.nodeId] = nodeDevice
		deviceManager.register(nodeDevice)
	}


	private handleNodeUpdate(ZWaveEndpoint node)
	{
		ZWaveNodeDevice nodeDevice = getDeviceAtNode(node.nodeId)
		if( ! nodeDevice)
			return

		LocalDateTime now = LocalDateTime.now()
		node.commandClasses.each {CommandClass cc ->
			switch(cc)
			{
				case MultilevelSensorCommandClass:
					MultilevelSensorCommandClass ccc  = (MultilevelSensorCommandClass) cc
					deviceManager.sensorBus.publish(new ValueSensorValue(
							nodeDevice,
							getSourcePortForMultilevelType(ccc.type),
							Quantities.getQuantity(ccc.values.first(), getUnit(ccc.scale)),
							now))
					break
				case BinarySensorCommandClass:
					BinarySensorCommandClass ccc = (BinarySensorCommandClass) cc
					deviceManager.sensorBus.publish(
							new BinarySensorValue(nodeDevice, PORT_BINARY, ! ccc.isIdle, now))
					break
			}
		}
	}

	static String getSourcePortForMultilevelType(MultilevelSensorCommandClass.Type type)
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

	@Override
	void onZWaveNodeAdded(ZWaveEndpoint node)
	{
		registerNode(node as ZWaveNode)
	}

	@Override
	void onZWaveNodeUpdated(ZWaveEndpoint node)
	{
		handleNodeUpdate(node)
	}
}
