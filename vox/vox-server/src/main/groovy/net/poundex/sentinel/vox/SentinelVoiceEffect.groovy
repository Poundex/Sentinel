package net.poundex.sentinel.vox

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

import java.nio.file.Path

@Component
@Order(1)
class SentinelVoiceEffect implements SpeechPostProcessor
{
	@Override
	Path process(Path speechFile)
	{
		Path converted = speechFile.parent.resolve(
				speechFile.fileName.toString()
						.substring(0, speechFile.fileName.toString().length() - 4) + "-processed.wav")
		"applyplugin ${speechFile} ${converted} autotalent.so autotalent 440 -9 0.444 -1 0 -1 1 -1 0 1 -1 1 -1 1 1 1 0 -0.8 0 0 5 0 0 0 0 0 1 1 ".execute().waitFor()
		return converted
	}
}
