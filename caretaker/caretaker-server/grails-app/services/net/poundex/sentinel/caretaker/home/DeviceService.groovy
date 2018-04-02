package net.poundex.sentinel.caretaker.home

import net.poundex.sentinel.caretaker.NoApplicableTargetDeviceException

class DeviceService implements DeviceManager
{
	private final Map<String, Device> deviceRegistry = [:]
	private final DriverService driverService
//	final DataBus sensorBus

	DeviceService(DriverService driverService)
	{
		this.driverService = driverService
//		this.sensorBus = sensorBus
	}

	@Override
	public <T extends Device> T getDevice(String deviceId, Class<T> deviceType)
	{
		Device d = deviceRegistry[deviceId]
		if( ! d || ! deviceType.isAssignableFrom(d.class))
			throw new NoApplicableTargetDeviceException()
		return (T)d
	}

	@Override
	void refresh()
	{
//		Collection<Hardware> hardware = AbstractPersistentHardware.list()
		deviceRegistry.clear()
//		driverService.registeredDrivers.each { driver ->
//			hardware.each { hw ->
//				driver.createDevices(hw, this)
//			}
		driverService.createDevices(this)
	}

	@Override
	void register(Device device)
	{
		deviceRegistry[device.deviceId] = device
	}

	@Override
	Map<String, Device> getDevices()
	{
		return deviceRegistry.asImmutable()
	}
}
