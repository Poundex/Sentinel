package net.poundex.sentinel.caretaker.home.trigger

import net.poundex.sentinel.caretaker.home.AbstractPersistentAppliance

abstract class ControlApplianceAction<T> extends Action
{
	AbstractPersistentAppliance appliance
	String portId
	abstract T getControlValue()

	static constraints = {
	}
}
