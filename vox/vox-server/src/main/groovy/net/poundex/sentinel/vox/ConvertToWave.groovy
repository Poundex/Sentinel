package net.poundex.sentinel.vox

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

import java.nio.file.Path

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
class ConvertToWave implements SpeechPostProcessor
{
	@Override
	Path process(Path speechFile)
	{
		Path converted = speechFile.parent.resolve(
				speechFile.fileName.toString()
						.substring(0, speechFile.fileName.toString().length() - 3) + "wav")
		"sox ${speechFile} ${converted}".execute().waitFor()
		return converted
	}
}
