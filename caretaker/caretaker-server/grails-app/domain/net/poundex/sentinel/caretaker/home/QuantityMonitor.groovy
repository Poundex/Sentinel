package net.poundex.sentinel.caretaker.home

import grails.compiler.GrailsCompileStatic
import groovy.transform.ToString

import javax.measure.Quantity

@GrailsCompileStatic
@ToString(includePackage = false, includeSuper = true)
class QuantityMonitor extends Monitor<Quantity>
{
	static constraints = {
	}

	@Override
	QuantityPortValue readPortValue(PortValue portValue)
	{
		return (portValue instanceof QuantityPortValue
				? portValue
				: null)
	}

	@Override
	Quantity getMonitorValueForPortValues(Collection<PortValue<Quantity>> portValues)
	{
		// TODO
		return ValueAggregator.AVERAGE.aggregate(portValues)
	}
}
