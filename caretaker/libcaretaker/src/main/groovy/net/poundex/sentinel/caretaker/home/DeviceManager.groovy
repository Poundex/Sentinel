package net.poundex.sentinel.caretaker.home

interface DeviceManager
{
	public <T extends Device> T getDevice(String deviceId, Class<T> deviceType)

	void refresh()

	void register(Device device)

	Map<String, Device> getDevices()

//	DataBus getSensorBus()
}
