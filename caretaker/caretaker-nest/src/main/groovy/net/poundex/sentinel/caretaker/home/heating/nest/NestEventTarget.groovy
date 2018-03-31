package net.poundex.sentinel.caretaker.home.heating.nest

interface NestEventTarget
{
	void handleEvent(NestPayload nestPayload)
}
