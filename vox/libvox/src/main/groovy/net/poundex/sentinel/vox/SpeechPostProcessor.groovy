package net.poundex.sentinel.vox

import java.nio.file.Path

interface SpeechPostProcessor
{
	Path process(Path speechFile)
}
