package net.poundex.sentinel.caretaker.zwave

import com.whizzosoftware.wzwave.controller.ZWaveController
import com.whizzosoftware.wzwave.controller.netty.NettyZWaveController
import com.whizzosoftware.wzwave.node.ZWaveEndpoint
import com.whizzosoftware.wzwave.node.ZWaveNode
import com.whizzosoftware.wzwave.node.generic.MultilevelSensor
import com.whizzosoftware.wzwave.node.generic.PortableSceneController
import groovy.transform.CompileStatic
import groovy.transform.ToString
import net.poundex.sentinel.caretaker.home.DataBus
import net.poundex.sentinel.caretaker.home.Device
import net.poundex.sentinel.caretaker.home.DeviceManager

import java.nio.file.Files

@CompileStatic
@ToString(includePackage = false)
class ZWaveModemDevice implements Device, ZWaveControllerListenerAdapter
{
	final ZWaveModem hardware

	private final Map<Byte, ZWaveNodeDevice> nodes = [:]
	private final ZWaveController zWaveController
	private final DeviceManager deviceManager
	private final DataBus dataBus

	ZWaveModemDevice(ZWaveModem hardware, DeviceManager deviceManager, DataBus dataBus)
	{
		this.hardware = hardware
		this.zWaveController = new NettyZWaveController(hardware.modemDevice,
				Files.createTempDirectory("zwavetmp").toFile())
		this.deviceManager = deviceManager
		this.dataBus = dataBus
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
			case PortableSceneController:
				nodeDevice = new ZWaveSceneController(hardware, zWaveNode.nodeId)
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
		if ( ! nodeDevice)
			return

		nodeDevice.handleNodeUpdate(node.commandClasses, dataBus)
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
