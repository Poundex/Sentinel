package net.poundex.sentinel.caretaker.home

import grails.compiler.GrailsCompileStatic
import groovy.transform.ToString

@GrailsCompileStatic
@ToString(includePackage = false, includeSuper = true)
class ValueSensor<T extends ValueSensorValue> extends AbstractPersistentSensor<T>
{
	static constraints = {
//	    sensorRole nullable: true
	}

	@Override
	ValueSensorValue readValue(SensorPortValue sensorValue)
	{
		if( ! (sensorValue instanceof ValueSensorValue))
			return null

		return (ValueSensorValue)sensorValue
	}

	@Override
	Object aggregate(Collection<? extends T> sensorPortValues)
	{
		ValueAggregator.NEWEST_VALUE_WINS.aggregate(sensorPortValues)
	}
}
