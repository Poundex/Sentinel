package net.poundex.sentinel.caretaker.home.trigger

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
abstract class Action
{
	String name

	static mapping = {
		tablePerHierarchy false
	}
}
