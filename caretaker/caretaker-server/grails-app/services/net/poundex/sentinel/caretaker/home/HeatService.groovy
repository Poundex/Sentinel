package net.poundex.sentinel.caretaker.home

import grails.gorm.transactions.Transactional
import net.poundex.sentinel.caretaker.NoApplicableTargetDeviceException
import net.poundex.sentinel.caretaker.NodeNotAvailableException
import net.poundex.sentinel.caretaker.home.heating.HeatingControllerDevice
import net.poundex.sentinel.server.ConfigService

import static net.poundex.sentinel.caretaker.CaretakerConfigKeys.MASTER_HEATING_CONTROLLER

@Transactional
class HeatService
{
	private final ConfigService configService
	private final DeviceManager deviceManager

	HeatService(ConfigService configService, DeviceManager deviceManager)
	{
		this.configService = configService
		this.deviceManager = deviceManager
	}

	boolean isHeatingActive() throws NoApplicableTargetDeviceException, NodeNotAvailableException
	{
		HeatingController heatingController = findHeatingController()
		if( ! heatingController)
			throw new NoApplicableTargetDeviceException()

		HeatingControllerDevice heatingControllerDevice =
				deviceManager.getDevice(heatingController.deviceId, HeatingControllerDevice)

		return heatingControllerDevice.isOn()
	}

	private HeatingController findHeatingController()
	{
		return configService.getEntity(MASTER_HEATING_CONTROLLER, HeatingController) ?: {
			List<HeatingController> hcl
			((hcl = HeatingController.list())).size() == 1 ? hcl[0] : null
		}()
	}
}
