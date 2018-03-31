package net.poundex.sentinel.caretaker.home.trigger

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class BinaryTrigger extends Trigger<Boolean>
{
	Boolean triggerValue

    static constraints = {
    }
}
