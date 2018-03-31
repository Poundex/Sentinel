package net.poundex.sentinel.emissary.pocketsphinx.ctx;

import net.poundex.sentinel.emissary.RecognitionProvider;
import net.poundex.sentinel.emissary.pocketsphinx.PocketsphinxRecognitionProvider;
import net.poundex.sentinel.emissary.pocketsphinx.RecogniserConfigBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class PocketsphinxConfiguration
{
	@Bean("pocketsphinxAssets")
	Path pocketsphinxAssets()
	{
		return Paths.get("/home/poundex/workspaces/pounder/pocketsphinx4j/sync");
	}

	@Bean
	RecognitionProvider recognitionProvider(
			@Qualifier("pocketsphinxAssets") Path assets)
	{
		return new PocketsphinxRecognitionProvider(RecogniserConfigBuilder
				.withDefaults()
				.acousticModel(assets.resolve("en-us-ptm"))
				.dictionary(assets.resolve("cmudict-en-us.dict"))
				.keywordThreshold(1e-31f)
				.defaultLogging()
				.build());
	}
}
