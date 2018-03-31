package net.poundex.sentinel.vox

import java.nio.file.Path

class SpeechPostProcessorService
{
    private final List<SpeechPostProcessor> postProcessors

	SpeechPostProcessorService(List<SpeechPostProcessor> postProcessors)
	{
		this.postProcessors = postProcessors
	}

	Path process(Path speechFile)
	{
		return postProcessors.inject(speechFile) { path, processor ->
			processor.process(path)
		}
	}
}
