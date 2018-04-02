package net.poundex.sentinel.caretaker.home.trigger

import net.poundex.sentinel.caretaker.home.AbstractPersistentAppliance

class ControlApplianceAction extends Action
{
	AbstractPersistentAppliance appliance
	static hasMany = [controlValues: ApplianceControlValue]

	static constraints = {
	}
}
