package net.poundex.sentinel.caretaker.home

import grails.compiler.GrailsCompileStatic
import groovy.transform.ToString


@GrailsCompileStatic
@ToString(includePackage = false)
abstract class AbstractPersistentHardware implements Hardware
{
	String name

	static constraints = {
		name(unique: true)
	}
}
