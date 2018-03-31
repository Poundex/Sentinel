package net.poundex.sentinel.fathom

interface Intent
{
	void registerIn(IntentRegistry intentRegistry)
	IntentResult run()
}
