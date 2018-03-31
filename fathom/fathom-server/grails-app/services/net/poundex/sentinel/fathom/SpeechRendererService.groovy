package net.poundex.sentinel.fathom

import com.ibm.icu.text.RuleBasedNumberFormat

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SpeechRendererService
{
	private final Map<String, SpeechRenderer> formatRenderers = [:]
	private final Map<Class, SpeechRenderer> typeRenderers = [
			(LocalDate): this.&renderDate
	]

	String renderSpeech(String speech, Object... args)
	{
		if(args.length == 0)
			return speech

		Iterator<Object> ai = args.iterator()
		return speech.replaceAll(/\{([a-z]+)?}/, { ph, String format ->
			Object arg = ai.next()
			if(format)
				formatRenderers[format].render(arg)
			else
				findTypeRenderer(arg.class).render(arg)
		})
    }

	private SpeechRenderer findTypeRenderer(Class klass)
	{
		if(klass == Object)
			return null
		if(typeRenderers[klass])
			return typeRenderers[klass]
		return findTypeRenderer(klass.superclass)
	}

	private String renderDate(LocalDate date)
	{
		int day = date.dayOfMonth
		RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(RuleBasedNumberFormat.ORDINAL)
		return date.format(DateTimeFormatter.ofPattern("EEEE 'the ${ruleBasedNumberFormat.format(day)} of' MMMM YYYY"))
	}
}
