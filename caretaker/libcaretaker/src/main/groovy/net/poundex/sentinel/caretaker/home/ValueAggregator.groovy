package net.poundex.sentinel.caretaker.home

import groovy.transform.CompileStatic
import tec.units.indriya.quantity.Quantities

@CompileStatic
enum ValueAggregator
{
	AVERAGE({ Collection<QuantityPortValue> it ->
		Quantities.getQuantity(
				(Number) it.collect { it.value.value }.sum() / it.size(),
				it.first().value.unit)}),
	BINARY_ANY({ Collection<BinaryPortValue> it ->
		it*.value.any()
	}),

	NEWEST_VALUE_WINS({ Collection<QuantityPortValue> values ->
		values.max { it.reportTime }.value
	})

	private final Closure function

	ValueAggregator(Closure function)
	{
		this.function = function
	}

	public <T> T aggregate(Collection<PortValue<T>> values)
	{
		return function(values)
	}
}
