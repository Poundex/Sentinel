package net.poudnex.sentinel.caretaker.home.lighting.hue.ctx

import feign.Feign
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import net.poudnex.sentinel.caretaker.home.lighting.hue.HueBridgeClient
import org.springframework.context.annotation.Configuration

//@Configuration
class HueConfiguration
{
	HueBridgeClient hueBridgeClient()
	{
		return Feign
				.builder()
				.encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder())
				.target(HueBridgeClient, "")
	}
}
