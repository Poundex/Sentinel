package net.poundex.sentinel.caretaker.home

class DriverService implements DriverManager
{
	private final Set<Driver> driverRegistry
	private final DataBus dataBus

	DriverService(Collection<Driver> drivers, DataBus dataBus)
	{
		this.driverRegistry = drivers.toSet()
		this.dataBus = dataBus
	}

//	@Override
//	void register(Driver driver)
//	{
//		driverRegistry << driver
//	}

	void createDevices(DeviceManager deviceManager)
	{
		AbstractPersistentHardware.list().each { hw ->
			driverRegistry.each { d ->
				d.createDevices(hw, deviceManager, dataBus)
			}
		}
	}
}
