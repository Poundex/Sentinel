package net.poundex.sentinel.vox.awspolly

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.polly.AmazonPolly
import com.amazonaws.services.polly.AmazonPollyClientBuilder
import com.amazonaws.services.polly.model.OutputFormat
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest
import com.amazonaws.services.polly.model.VoiceId
import net.poundex.sentinel.vox.SpeechSynthRequest
import net.poundex.sentinel.vox.SpeechSynthesizer
import org.springframework.stereotype.Service

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class AwsPollySpeechSynthesizer implements SpeechSynthesizer
{
	private static final BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
			(String)Class.forName('net.poundex.sentinel.server.StupidSecretsProvider').getInstance().secrets.awspolly.accessKey,
			(String)Class.forName('net.poundex.sentinel.server.StupidSecretsProvider').getInstance().secrets.awspolly.secretKey)

	private final AmazonPolly client = AmazonPollyClientBuilder
			.standard()
			.withRegion(Regions.EU_WEST_1)
			.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
			.build()

	@Override
	Path synthSpeech(SpeechSynthRequest speechSynthRequest)
	{
		SynthesizeSpeechRequest synthesizeSpeechRequest = new SynthesizeSpeechRequest()
				.withOutputFormat(OutputFormat.Ogg_vorbis)
				.withVoiceId(VoiceId.Emma)
				.withText(speechSynthRequest.speech)

		Path out = Files.createTempFile("speech", ".ogg")
		Files.write(out, client.synthesizeSpeech(synthesizeSpeechRequest).getAudioStream().bytes)
		println "Vox: File written to ${out.toAbsolutePath()}"
		return out
	}
}
