package sentinel.server.application

import grails.converters.JSON
import net.poudnex.sentinel.caretaker.home.lighting.hue.HueBulbDevice
import net.poundex.sentinel.caretaker.environment.PersistentRoom
import net.poundex.sentinel.caretaker.home.*
import net.poundex.sentinel.caretaker.home.heating.nest.NestHeatingControllerDevice
import net.poundex.sentinel.caretaker.home.heating.nest.NestReportingSensorDevice
import net.poundex.sentinel.caretaker.home.heating.nest.NestThermostat
import net.poundex.sentinel.caretaker.home.trigger.*
import net.poundex.sentinel.caretaker.ligting.hue.HueBridge
import net.poundex.sentinel.caretaker.zwave.ZWaveModem
import net.poundex.sentinel.caretaker.zwave.ZWaveSceneController
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
	    DataBusService sbs = ctx.getBean(DataBusService)

	    PersistentRoom livingRoom = save new PersistentRoom(name: "Living Room")

	    NestThermostat nestThermostat = save new NestThermostat(
			    nestDeviceId: StupidSecretsProvider.instance.secrets.nest.deviceId,
			    name: "Living Room Thermostat",
			    nestAccessToken: StupidSecretsProvider.instance.secrets.nest.accessToken)

	    ZWaveModem zWaveController = save new ZWaveModem(
			    name: "ZWave Controller 1",
			    modemDevice: "/dev/ttyACM0")

		HueBridge hueBridge = save new HueBridge(
				name: "Hue Bridge",
				bridgeAddress: "192.168.0.22",
				bridgeUsername: StupidSecretsProvider.instance.secrets.hue.bridgeUser)

	    HeatingController heatingController = save new HeatingController(
			    deviceId: NestHeatingControllerDevice.createDeviceId(nestThermostat))

	    QuantityMonitor livingRoomTempMon = save new QuantityMonitor(room: livingRoom)
	    QuantityMonitor livingRoomHumidMod = save new QuantityMonitor(room: livingRoom)
	    BinaryMonitor occupancyMon = save new BinaryMonitor(room: livingRoom)

	    save new SensorReader(
			    monitor: livingRoomTempMon,
			    deviceId: NestReportingSensorDevice.createDeviceId(nestThermostat),
			    portId: NestReportingSensorDevice.PORT_TEMPERATURE_C)

	    save new SensorReader(
			    monitor: livingRoomHumidMod,
			    deviceId: NestReportingSensorDevice.createDeviceId(nestThermostat),
			    portId: NestReportingSensorDevice.PORT_HUMIDITY)

	    save new SensorReader<>(
			    monitor: livingRoomTempMon,
			    deviceId: ZWaveSensorDevice.createDeviceId(zWaveController, 2.byteValue()),
			    portId: ZWaveSensorDevice.PORT_MULTILEVEL_TEMPERATURE)

	    save new SensorReader<>(
			    monitor: occupancyMon,
			    deviceId: ZWaveSensorDevice.createDeviceId(zWaveController, 2.byteValue()),
			    portId: ZWaveSensorDevice.PORT_BINARY)

	    save new SensorReader<>(
			    monitor: livingRoomHumidMod,
			    deviceId: ZWaveSensorDevice.createDeviceId(zWaveController, 2.byteValue()),
			    portId: ZWaveSensorDevice.PORT_MULTILEVEL_HUMIDITY)

	    Trigger dummy1 = save new ConditionalTrigger<>(
			    monitor: livingRoomTempMon,
			    actions: [ new DummyAction(name: 'ACTION TRIGGERED: Living Room Temp > 20') ]),
			    true

	    save new ValueCondition(
			    trigger: dummy1,
			    valueTriggerType: ValueCondition.ValueTriggerType.GREATER_THAN,
			    triggerValue: 20),
			    true

	    Bulb southLamp = save new Bulb(deviceId: HueBulbDevice.createDeviceId(hueBridge, "2"))

		Bulb bedroomCeiling = save new Bulb(deviceId: HueBulbDevice.createDeviceId(hueBridge, "3"))
		Bulb bedroomLamp = save new Bulb(deviceId: HueBulbDevice.createDeviceId(hueBridge, "7"))

	    Trigger lightOnMotion = save new ConditionalTrigger<>(
			    monitor: occupancyMon,
			    actions: [ new ControlApplianceAction(
					    name: 'Turn on',
					    appliance: bedroomCeiling,
					    controlValues: [
							    new BinaryControlValue(
									    portId: "PORT_BINARY_APPLIANCE_POWER",
									    controlValue: true)
					    ]), new ControlApplianceAction(
					    name: 'Turn on',
					    appliance: bedroomLamp,
					    controlValues: [
							    new BinaryControlValue(
									    portId: "PORT_BINARY_APPLIANCE_POWER",
									    controlValue: true)
					    ])],
			    conditions: [ new BinaryCondition(triggerValue: true) ])

	    Trigger lightOffOnNoMotion = save new ConditionalTrigger<>(
			    monitor: occupancyMon,
			    actions: [ new ControlApplianceAction(
					    name: 'Turn off',
					    appliance: bedroomLamp,
					    controlValues: [
							    new BinaryControlValue(
									    portId: "PORT_BINARY_APPLIANCE_POWER",
									    controlValue: false)
					    ]), new ControlApplianceAction(
					    name: 'Turn off',
					    appliance: bedroomCeiling,
					    controlValues: [
							    new BinaryControlValue(
									    portId: "PORT_BINARY_APPLIANCE_POWER",
									    controlValue: false)
					    ])],
			    conditions: [ new BinaryCondition(triggerValue: false) ])


		save new ButtonMonitor(room: livingRoom,
				triggers: [
						new ButtonTrigger(
								buttonNumberOnDevice: 1,
								triggerType: ButtonPushEvent.TriggerType.PUSHED,
								actions: [ new DummyAction(name: "Button #1 pushed") ]),
						new ButtonTrigger(
								buttonNumberOnDevice: 2,
								triggerType: ButtonPushEvent.TriggerType.PUSHED,
								pushCount: 2,
								actions: [ new DummyAction(name: "Button #2 double pushed") ]),
						new ButtonTrigger(
								buttonNumberOnDevice: 3,
								triggerType: ButtonPushEvent.TriggerType.BEING_HELD_DOWN,
								actions: [ new DummyAction(name: "Button #3 being held") ]),
						new ButtonTrigger(
								buttonNumberOnDevice: 3,
								triggerType: ButtonPushEvent.TriggerType.RELEASED_AFTER_HOLD,
								actions: [ new DummyAction(name: "Button #3 released") ] )],
				readers: [
					new SensorReader(portId: ZWaveSceneController.PORT_SCENECONTROL_BUTTON, deviceId: ZWaveSensorDevice.createDeviceId(zWaveController, 3.byteValue()))
				])

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
