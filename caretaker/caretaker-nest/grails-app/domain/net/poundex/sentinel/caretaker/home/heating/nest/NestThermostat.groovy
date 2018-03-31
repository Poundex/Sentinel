package net.poundex.sentinel.caretaker.home.heating.nest

import grails.compiler.GrailsCompileStatic
import groovy.transform.ToString
import net.poundex.sentinel.caretaker.home.AbstractPersistentHardware

@GrailsCompileStatic
@ToString(includes = 'nestDeviceId', includeSuper = true, includePackage = false)
class NestThermostat extends AbstractPersistentHardware
{
	String nestDeviceId
	String nestAccessToken
	String serviceUrl

    static constraints = {
	    serviceUrl nullable: true
    }
}
