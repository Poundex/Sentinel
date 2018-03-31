package net.poundex.sentinel.scribe.google.ctx

import net.poundex.sentinel.scribe.SpeechProcessor
import net.poundex.sentinel.scribe.google.GoogleSpeechProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GoogleSpeechConfiguration
{
	@Bean
	SpeechProcessor speechProcessor()
	{
		return new GoogleSpeechProcessor()
	}
}
