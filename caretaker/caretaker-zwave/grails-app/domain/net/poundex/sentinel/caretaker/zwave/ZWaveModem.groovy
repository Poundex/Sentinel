package net.poundex.sentinel.caretaker.zwave

import grails.compiler.GrailsCompileStatic
import groovy.transform.ToString
import net.poundex.sentinel.caretaker.home.AbstractPersistentHardware
import net.poundex.sentinel.caretaker.home.Hardware

@GrailsCompileStatic
@ToString(includeSuper = true, includePackage = false)
class ZWaveModem extends AbstractPersistentHardware implements Hardware
{
	String modemDevice

	static constraints = {
    }
}
