package net.poundex.sentinel.vox

import java.nio.file.Path

interface SpeechSynthesizer
{
	Path synthSpeech(SpeechSynthRequest speechSynthRequest)
}
