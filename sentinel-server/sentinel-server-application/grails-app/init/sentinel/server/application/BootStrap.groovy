package sentinel.server.application

import grails.converters.JSON
import net.poundex.sentinel.caretaker.environment.PersistentRoom
import net.poundex.sentinel.caretaker.home.DeviceManager
import net.poundex.sentinel.caretaker.home.EnvironmentService
import net.poundex.sentinel.caretaker.home.OccupancyMonitor
import net.poundex.sentinel.caretaker.home.SensorReader
import net.poundex.sentinel.caretaker.home.SensorRole
import net.poundex.sentinel.caretaker.home.TemperatureSensor
import net.poundex.sentinel.caretaker.home.ValueSensor
import net.poundex.sentinel.caretaker.home.heating.nest.NestReportingSensorDevice
import net.poundex.sentinel.caretaker.home.heating.nest.NestThermostat
import net.poundex.sentinel.caretaker.home.trigger.BinaryTrigger
import net.poundex.sentinel.caretaker.home.trigger.ValueTrigger
import net.poundex.sentinel.caretaker.zwave.ZWaveModem
import net.poundex.sentinel.caretaker.zwave.ZWaveModemDevice
import net.poundex.sentinel.caretaker.zwave.ZWaveSensorDevice
import net.poundex.sentinel.server.StupidSecretsProvider
import org.grails.datastore.gorm.GormEntity
import org.springframework.context.ApplicationContext

class BootStrap
{
    def init = { servletContext ->
        JSON.registerObjectMarshaller(Enum) { Enum it ->
            return it.name()
        }

	    ApplicationContext ctx = servletContext.getAttribute("org.springframework.web.context.WebApplicationContext.ROOT")
		DeviceManager deviceManager = ctx.getBean(DeviceManager)
	    EnvironmentService environmentService = ctx.getBean(EnvironmentService)

	    PersistentRoom livingRoom = save new PersistentRoom(name: "Living Room")

	    NestThermostat nestThermostat = save new NestThermostat(
			    nestDeviceId: StupidSecretsProvider.instance.secrets.nest.deviceId,
			    name: "Living Room Thermostat",
			    nestAccessToken: StupidSecretsProvider.instance.secrets.nest.accessToken)

	    ZWaveModem zWaveController = save new ZWaveModem(
			    name: "ZWave Controller 1",
			    modemDevice: "/dev/ttyACM0",
	    )

	    TemperatureSensor livingRoomTempMon = save new TemperatureSensor(room: livingRoom)
	    ValueSensor livingRoomHumidMod = save new ValueSensor(room: livingRoom)
	    OccupancyMonitor livingRoomOccupancyMon = save new OccupancyMonitor(room: livingRoom)

	    save new SensorReader(
			    sensor: livingRoomTempMon,
			    deviceId: NestReportingSensorDevice.createDeviceId(nestThermostat),
			    portId: NestReportingSensorDevice.PORT_TEMPERATURE_C)

	    save new SensorReader(
			    sensor: livingRoomHumidMod,
			    deviceId: NestReportingSensorDevice.createDeviceId(nestThermostat),
			    portId: NestReportingSensorDevice.PORT_HUMIDITY)

	    save new SensorReader<>(
			    sensor: livingRoomTempMon,
			    deviceId: ZWaveSensorDevice.createDeviceId(zWaveController, 2.byteValue()),
			    portId: ZWaveModemDevice.PORT_MULTILEVEL_TEMPERATURE)

	    save new SensorReader<>(
			    sensor: livingRoomOccupancyMon,
			    deviceId: ZWaveSensorDevice.createDeviceId(zWaveController, 2.byteValue()),
			    portId: ZWaveModemDevice.PORT_BINARY)

	    save new SensorReader<>(
			    sensor: livingRoomHumidMod,
			    deviceId: ZWaveSensorDevice.createDeviceId(zWaveController, 2.byteValue()),
			    portId: ZWaveModemDevice.PORT_MULTILEVEL_HUMIDITY)

	    save new ValueTrigger(
			    sensor: livingRoomTempMon,
			    valueTriggerType: ValueTrigger.ValueTriggerType.GREATER_THAN,
			    triggerValue: 20)

	    save new BinaryTrigger(
			    sensor: livingRoomOccupancyMon,
			    triggerValue: true)

	    deviceManager.refresh()
	    println deviceManager.getDevices()
    }

    def destroy = {
    }

	static <T extends GormEntity<T>> T save(T obj, flush = false)
	{
		return obj.save(flush: flush, failOnError: true)
	}
}
