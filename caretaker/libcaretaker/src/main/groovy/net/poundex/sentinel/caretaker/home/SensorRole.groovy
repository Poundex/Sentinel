package net.poundex.sentinel.caretaker.home

enum SensorRole
{
	AIR_TEMPERATURE(ValueAggregator.AVERAGE),
	HUMIDITY(ValueAggregator.AVERAGE),
	OCCUPANCY(ValueAggregator.BINARY_ANY)


	private final ValueAggregator aggregator

	SensorRole(ValueAggregator aggregator)
	{
		this.aggregator = aggregator
	}

	public <T> T aggregate(Collection<T> values)
	{
		aggregator.aggregate(values.collect { it.first() })
	}
}
