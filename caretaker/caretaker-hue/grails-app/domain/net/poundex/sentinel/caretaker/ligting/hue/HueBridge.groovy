package net.poundex.sentinel.caretaker.ligting.hue

import net.poundex.sentinel.caretaker.home.AbstractPersistentHardware

class HueBridge extends AbstractPersistentHardware
{
	String bridgeAddress
	String bridgeUsername

	static constraints = {
	}
}
