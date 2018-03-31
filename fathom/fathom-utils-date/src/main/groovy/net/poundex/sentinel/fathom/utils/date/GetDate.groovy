package net.poundex.sentinel.fathom.utils.date

import net.poundex.sentinel.fathom.Answer
import net.poundex.sentinel.fathom.Intent
import net.poundex.sentinel.fathom.IntentRegistry
import net.poundex.sentinel.fathom.IntentResult
import org.springframework.stereotype.Component

import java.time.LocalDate

@Component
class GetDate implements Intent
{
	@Override
	void registerIn(IntentRegistry intentRegistry)
	{
		intentRegistry.register(this, "sentinel.utils.date.get")
	}

	@Override
	IntentResult run()
	{
		return new Answer("Today's date is {}", LocalDate.now())
	}
}
