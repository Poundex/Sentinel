package net.poundex.sentinel.emissary.pocketsphinx

import edu.cmu.pocketsphinx.Config
import edu.cmu.pocketsphinx.Decoder

import java.nio.file.Files
import java.nio.file.Path

class RecogniserConfigBuilder
{
//	static {
//		System.loadLibrary("pocketsphinx_jni")
//		println "Library loaded"
//	}

	private final Config config

	RecogniserConfigBuilder(Config config)
	{
		this.config = config
	}

	static RecogniserConfigBuilder withDefaults()
	{
//		System.loadLibrary("pocketsphinx_jni");
		return new RecogniserConfigBuilder(Decoder.defaultConfig())
	}

	Config build()
	{
		return config
	}

	RecogniserConfigBuilder acousticModel(Path path)
	{
		config.setString("-hmm", path.toString())
		return this
	}

	RecogniserConfigBuilder dictionary(Path path)
	{
		config.setString("-dict", path.toString())
		return this
	}

	RecogniserConfigBuilder defaultLogging()
	{
		return logDir(Files.createTempDirectory("sphinxlogs"))
	}

	RecogniserConfigBuilder logDir(Path path)
	{
		config.setString("-rawlogdir", path.toString())
		return this
	}

	RecogniserConfigBuilder keywordThreshold(float value) {
		config.setFloat("-kws_threshold", value)
		return this
	}

}
