package net.poundex.sentinel.emissary.ctx;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import net.poundex.sentinel.emissary.ControlLoop;
import net.poundex.sentinel.emissary.ControlManager;
import net.poundex.sentinel.emissary.QueryProcessor;
import net.poundex.sentinel.emissary.RecognitionProvider
import net.poundex.sentinel.fathom.QueryClient;
import net.poundex.sentinel.scribe.ScribeClient
import net.poundex.sentinel.vox.SpeechSynthClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration

@Configuration
public class EmissaryConfiguration
{
	@Bean
	ControlLoop controlLoop(RecognitionProvider recognitionProvider, ScribeClient scribeClient, ControlManager controlManager)
	{
		return new ControlLoop(recognitionProvider, scribeClient, controlManager);
	}

	@Bean
	ScribeClient scribeClient()
	{
		return Feign
				.builder()
				.encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder())
				.target(ScribeClient.class, "http://localhost:10199");
	}

	@Bean
	ControlManager controlManager()
	{
		return new ControlManager();
	}

	@Bean
	QueryClient queryClient()
	{
		return Feign
				.builder()
				.encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder())
				.target(QueryClient.class, "http://localhost:10199");
	}

	@Bean
	SpeechSynthClient speechSynthClient()
	{
		return Feign
				.builder()
				.encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder())
				.target(SpeechSynthClient.class, "http://localhost:10199");
	}
}
