package net.poundex.sentinel.caretaker.home

import grails.compiler.GrailsCompileStatic
import groovy.transform.ToString

@GrailsCompileStatic
@ToString(includePackage = false, includeSuper = true)
class OccupancyMonitor extends AbstractPersistentSensor<BinarySensorValue>
{
	@Override
	BinarySensorValue readValue(SensorPortValue sensorValue)
	{
		if( ! (sensorValue instanceof BinarySensorValue))
			return null

		return (BinarySensorValue) sensorValue
	}

	@Override
	Object aggregate(Collection<? extends BinarySensorValue> portValues)
	{
		return ValueAggregator.BINARY_ANY.aggregate(portValues)
	}
}
