package net.poundex.sentinel.emissary

interface RecognitionProvider
{
	void waitForHotword()

	byte[] getSpeech()
}
