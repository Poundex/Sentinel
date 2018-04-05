package net.poundex.sentinel.caretaker.home

import grails.compiler.GrailsCompileStatic
import groovy.transform.ToString

@GrailsCompileStatic
@ToString(includePackage = false, includeSuper = true)
class BinaryMonitor extends Monitor<Boolean>
{
	boolean coerceNonBinaryValues = false

	static constraints = {
	}

	@Override
	BinaryPortValue readPortValue(PortValue portValue)
	{
		return (portValue instanceof BinaryPortValue
				? portValue
				: (coerceNonBinaryValues
					? new BinaryPortValue(portValue.device, portValue.portId, (portValue.value as boolean), portValue.reportTime)
					: null))
	}

	@Override
	Boolean getMonitorValueForPortValues(Collection<PortValue<Boolean>> portValues, PortValue<Boolean> triggeringPortValue)
	{
		// TODO
		return portValues.any { it.value }
	}
}
