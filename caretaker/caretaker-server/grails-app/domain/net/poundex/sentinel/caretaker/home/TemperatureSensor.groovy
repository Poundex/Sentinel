package net.poundex.sentinel.caretaker.home

import grails.compiler.GrailsCompileStatic
import groovy.transform.ToString

import javax.measure.Unit
import javax.measure.quantity.Temperature

@GrailsCompileStatic
@ToString(includePackage = false, includeSuper = true)
class TemperatureSensor extends ValueSensor<ValueSensorValue>
{
//	Unit<Temperature> targetUnit

	static constraints = {
    }

//	@Override
//	ValueSensorValue readValue(SensorPortValue sensorValue)
//	{
//		ValueSensorValue value = super.readValue(sensorValue)
//		if( ! value)
//			return null
//
////		if( ! (value.value instanceof Temperature))
////			return null
//
////		if( ! value.value.unit != targetUnit)
////			value = new ValueSensorValue<>(
////					sensorValue.sourceDevice,
////					sensorValue.sourcePort,
////					value.value.to(targetUnit),
////					sensorValue.reportTime)
//
//		return value
//	}

	@Override
	Object aggregate(Collection<? extends ValueSensorValue> sensorPortValues)
	{
		return ValueAggregator.AVERAGE.aggregate(sensorPortValues)
	}
}
