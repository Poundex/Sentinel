package net.poundex.sentinel.caretaker.home

interface UnitOfMeasurement { }

interface TemperatureUnit extends UnitOfMeasurement { }

enum TemperatureUnits implements TemperatureUnit
{
	DEG_C, DEG_F, K
}

interface HumidityUnit extends UnitOfMeasurement { }

enum HumidityUnits implements HumidityUnit
{
	RH
}
