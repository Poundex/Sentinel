package net.poundex.sentinel.emissary

import net.poundex.sentinel.fathom.ResponsePackage

interface Responder
{
	void respond(ResponsePackage responsePackage)
}
