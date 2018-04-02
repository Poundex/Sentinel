package net.poundex.sentinel.caretaker.home.trigger

abstract class ApplianceControlValue<T>
{
	String portId
	abstract T getControlValue()
}
