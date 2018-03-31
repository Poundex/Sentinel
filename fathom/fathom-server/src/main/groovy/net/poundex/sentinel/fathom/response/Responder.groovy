package net.poundex.sentinel.fathom.response

import net.poundex.sentinel.fathom.IntentResult

interface Responder<T extends IntentResult>
{
	def respond(T intentResult)
}
