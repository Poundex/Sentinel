package net.poundex.sentinel.caretaker.home.trigger

import grails.compiler.GrailsCompileStatic
import groovy.transform.ToString

@GrailsCompileStatic
@ToString(includePackage = false, includes = 'name')
abstract class Action
{
	String name

	static mapping = {
		tablePerHierarchy false
	}
}
