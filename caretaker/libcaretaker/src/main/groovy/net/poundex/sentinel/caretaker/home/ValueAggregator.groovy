package net.poundex.sentinel.caretaker.home

import groovy.transform.CompileStatic
import tec.units.indriya.quantity.Quantities

@CompileStatic
enum ValueAggregator
{
	AVERAGE({ Collection<ValueSensorValue> it ->
		Quantities.getQuantity(
				(Number) it.collect { it.value.value }.sum() / it.size(),
				it.first().value.unit)}),
	BINARY_ANY({ Collection<BinarySensorValue> it ->
		it*.value.any()
	}),

	NEWEST_VALUE_WINS({ Collection<ValueSensorValue> values ->
		values.max { it.reportTime }.value
	})

	private final Closure function

	ValueAggregator(Closure function)
	{
		this.function = function
	}

	Object aggregate(Collection<? extends SensorPortValue> values)
	{
		return function(values)
	}
}
