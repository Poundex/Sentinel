package net.poundex.sentinel.caretaker.home

class DriverService implements DriverManager
{
	private final Set<Driver> driverRegistry

	DriverService(Collection<Driver> drivers)
	{
		this.driverRegistry = drivers.toSet()
	}

//	@Override
//	void register(Driver driver)
//	{
//		driverRegistry << driver
//	}

	Set<Driver> getRegisteredDrivers()
	{
		return driverRegistry.asImmutable()
	}
}
