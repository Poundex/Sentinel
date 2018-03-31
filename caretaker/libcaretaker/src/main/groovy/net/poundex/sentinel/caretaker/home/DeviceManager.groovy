package net.poundex.sentinel.caretaker.home

import net.poundex.sentinel.caretaker.home.heating.HeatingControllerDevice

interface DeviceManager
{
	public <T extends Device> T getDevice(String deviceId, Class<T> deviceType)

	void refresh()

	void register(Device device)

	Map<String, Device> getDevices()

	SensorBus getSensorBus()
}
