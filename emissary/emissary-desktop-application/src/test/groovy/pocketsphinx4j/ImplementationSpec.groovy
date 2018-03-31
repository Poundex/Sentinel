package pocketsphinx4j

import net.poundex.sentinel.emissary.desktop.EmissaryDesktopApplication
import net.poundex.sentinel.emissary.pocketsphinx.PocketsphinxRecognitionProvider
import net.poundex.sentinel.emissary.pocketsphinx.RecogniserConfigBuilder
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class ImplementationSpec extends Specification
{
	void "Setup thing"()
	{
		setup:
		EmissaryDesktopApplication.printstuff();
		return
//		if(System.properties) return
		Path assets = Paths.get("/home/poundex/workspaces/pounder/pocketsphinx4j/sync")
		expect:
		System.loadLibrary("pocketsphinx_jni")
		PocketsphinxRecognitionProvider r = new PocketsphinxRecognitionProvider(RecogniserConfigBuilder
				.withDefaults()
				.acousticModel(assets.resolve("en-us-ptm"))
				.dictionary(assets.resolve("cmudict-en-us.dict"))
				.keywordThreshold(1e-31)
				.defaultLogging()
				.build())

		r.start()

	}
}
