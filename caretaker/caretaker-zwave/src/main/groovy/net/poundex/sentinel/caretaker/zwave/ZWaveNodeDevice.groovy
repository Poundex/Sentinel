package net.poundex.sentinel.caretaker.zwave

import com.whizzosoftware.wzwave.commandclass.BatteryCommandClass
import com.whizzosoftware.wzwave.commandclass.CommandClass
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import net.poundex.sentinel.caretaker.home.DataBus
import net.poundex.sentinel.caretaker.home.Device

@Slf4j
@CompileStatic
class ZWaveNodeDevice implements Device
{
	private final ZWaveModem hardware
	protected final byte nodeId

	ZWaveNodeDevice(ZWaveModem hardware, byte nodeId)
	{
		this.hardware = hardware
		this.nodeId = nodeId
	}

	@Override
	String getDeviceId()
	{
		return createDeviceId(hardware, nodeId)
	}

	static String createDeviceId(ZWaveModem hardware, byte nodeId)
	{
		return "zwave:${hardware.modemDevice}:node:${nodeId}"
	}

	ZWaveModem getHardware()
	{
		return hardware
	}

	@CompileDynamic
	final void handleNodeUpdate(Collection<CommandClass> commandClasses, DataBus dataBus)
	{
		commandClasses.each {
			handleCommandClass(it, dataBus)
		}
	}

	protected handleCommandClass(CommandClass cc, DataBus dataBus)
	{
		log.debug("Ignoring command class {} for node {}", cc.class.simpleName, this.deviceId)
	}

	protected handleCommandClass(BatteryCommandClass cc, DataBus dataBus)
	{
		log.warn("Should be handling this battery data: {} for node {}", cc.level, this.deviceId)
	}
}
