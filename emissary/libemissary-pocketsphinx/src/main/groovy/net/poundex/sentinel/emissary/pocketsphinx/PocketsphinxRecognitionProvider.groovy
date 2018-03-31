package net.poundex.sentinel.emissary.pocketsphinx

import edu.cmu.pocketsphinx.Config
import edu.cmu.pocketsphinx.Decoder
import net.poundex.sentinel.emissary.RecognitionProvider

import javax.sound.sampled.AudioFileFormat
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine.Info
import javax.sound.sampled.TargetDataLine
import java.nio.ByteBuffer
import java.nio.ByteOrder

class PocketsphinxRecognitionProvider implements RecognitionProvider
{
//	static {
//		System.loadLibrary("pocketsphinx_jni");
//	}

	private final static float BUFFER_SIZE_SECONDS = 0.4f

	private final Decoder decoder
	private final double sampleRate
	private final int bufferSize
	private final AudioFormat inputAudioFormat

	private TargetDataLine targetDataLine
	private Info inputDataLineInfo

	PocketsphinxRecognitionProvider(Config config)
	{
		decoder = new Decoder(config)
		sampleRate = decoder.config.getFloat("-samprate")
//		sampleRate = 44100
		bufferSize = Math.round(sampleRate * BUFFER_SIZE_SECONDS)
		inputAudioFormat = new AudioFormat(sampleRate.floatValue(), 16, 1, true, false)
		inputDataLineInfo = new Info(TargetDataLine, inputAudioFormat)
		if( ! AudioSystem.isLineSupported(inputDataLineInfo))
			throw new RuntimeException("Line is not supported")
		targetDataLine = AudioSystem.getLine(inputDataLineInfo) as TargetDataLine
	}

	void start()
	{
		targetDataLine.open(inputAudioFormat)
		targetDataLine.start()
		decoder.setKeyphrase("wakeup", "sentinel")
		decoder.setSearch("wakeup")
		decoder.startUtt()
		byte[] bytes = new byte[bufferSize]
		int i = 0
		boolean inSpeech = decoder.inSpeech
		while(i < 256)
		{
			int bytesRead = targetDataLine.read(bytes, 0, bufferSize)
//			println "Read ${bytesRead} bytes"
			ByteBuffer bb = ByteBuffer.wrap(bytes)
			bb.order(ByteOrder.LITTLE_ENDIAN)
			short[] s = new short[bytesRead / 2]
			bb.asShortBuffer().get(s)
			decoder.processRaw(s, bytesRead / 2 as long, false, false)
			if(decoder.inSpeech != inSpeech)
			{
				println "In speech changed: ${decoder.getInSpeech()}"
				inSpeech = !inSpeech
//				if( ! inSpeech)
					if(decoder.hyp()?.hypstr == "sentinel")
					{
						println "!!!\nMATCH!\n!!!\n"
						break
					}
			}
			i++
		}
		decoder.endUtt()
		targetDataLine.close()
		start()
//		println decoder.hyp().hypstr

//		decoder.setRawdataSize(300000);
//		byte[] b = new byte[4096];
//		int nbytes;
//		decoder.startUtt()
//		while ((nbytes = targetDataLine.read(b, 0, targetDataLine.bufferSize / 5 as int)) >= 0) {
//			ByteBuffer bb = ByteBuffer.wrap(b, 0, nbytes);
//			bb.order(ByteOrder.LITTLE_ENDIAN);
//			short[] s = new short[nbytes/2];
//			bb.asShortBuffer().get(s);
//			decoder.processRaw(s, nbytes/2 as long, false, false)
//			println decoder.inSpeech
//		}
//		decoder.endUtt();
//		System.out.println(decoder.hyp().getHypstr());
	}

	@Override
	void waitForHotword()
	{
		targetDataLine.open(inputAudioFormat)
		targetDataLine.start()
		decoder.setKeyphrase("wakeup", "sentinel")
		decoder.setSearch("wakeup")
		decoder.startUtt()
		byte[] bytes = new byte[bufferSize]
		boolean inSpeech = decoder.inSpeech
		println targetDataLine.getFormat()
		while(true) {
			int bytesRead = targetDataLine.read(bytes, 0, bufferSize)
			ByteBuffer bb = ByteBuffer.wrap(bytes)
			bb.order(ByteOrder.LITTLE_ENDIAN)
			short[] s = new short[bytesRead / 2]
			bb.asShortBuffer().get(s)
			decoder.processRaw(s, bytesRead / 2 as long, false, false)
			if (decoder.inSpeech != inSpeech) {
				inSpeech = ! inSpeech
				println "In speech changed: ${decoder.getInSpeech()}"
				if (decoder.hyp()?.hypstr == "sentinel") {
					break
				}
			}
		}
		decoder.endUtt()
		targetDataLine.close()
	}

	@Override
	byte[] getSpeech()
	{
		targetDataLine.open(inputAudioFormat)
		targetDataLine.start()
		decoder.startUtt()
		byte[] bytes = new byte[bufferSize]
		boolean everInSpeech = false
		boolean inSpeech = decoder.inSpeech
		if(inSpeech) everInSpeech = true
		ByteArrayOutputStream heard = new ByteArrayOutputStream()
		while(true) {
			int bytesRead = targetDataLine.read(bytes, 0, bufferSize)
			heard.write(bytes)
			ByteBuffer bb = ByteBuffer.wrap(bytes)
			bb.order(ByteOrder.LITTLE_ENDIAN)
			short[] s = new short[bytesRead / 2]
			bb.asShortBuffer().get(s)
			decoder.processRaw(s, bytesRead / 2 as long, false, false)
			if (decoder.inSpeech != inSpeech) {
				inSpeech = ! inSpeech
				println "In speech changed: ${decoder.getInSpeech()}"
				if (! inSpeech && everInSpeech) {
					break
				}
				if(inSpeech)
					everInSpeech = true
			}
		}
		decoder.endUtt()
		targetDataLine.close()
		println "Heard ${heard.size()} bytes"
//		ByteBuffer bb2 = ByteBuffer.wrap(heard.toByteArray()).order(ByteOrder.LITTLE_ENDIAN)
//		AudioInputStream ais = AudioSystem.getAudioInputStream(inputAudioFormat,
//				AudioSystem.getAudioInputStream(new ByteArrayInputStream(heard.toByteArray())))
		byte[] heardBytes = heard.toByteArray()
		AudioInputStream ais = new AudioInputStream(
				new ByteArrayInputStream(heardBytes),
				inputAudioFormat,
				heardBytes.length.intdiv(inputAudioFormat.frameSize).longValue())
		File outfile = File.createTempFile("heard", ".wav")
		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream()
		AudioSystem.write(ais, AudioFileFormat.Type.WAVE, bytesOut)
		println "Out file is ${outfile.absolutePath}"
		byte[] out = bytesOut.toByteArray()
		outfile.bytes = out
		return out
//		"aplay ${outfile.absolutePath}".execute().waitFor()
	}
}


