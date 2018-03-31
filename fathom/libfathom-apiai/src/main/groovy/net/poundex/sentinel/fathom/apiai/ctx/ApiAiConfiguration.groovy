package net.poundex.sentinel.fathom.apiai.ctx

import feign.Feign
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import net.poundex.sentinel.fathom.apiai.ApiAiQueryClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApiAiConfiguration
{
	@Bean
	ApiAiQueryClient apiAiQueryClient()
	{
		return Feign
				.builder()
				.encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder())
				.target(ApiAiQueryClient, ApiAiQueryClient.BASE_URL)
	}
}
