package net.poundex.sentinel.scribe.google

import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.speech.v1.RecognitionAudio
import com.google.cloud.speech.v1.RecognitionConfig
import com.google.cloud.speech.v1.RecognizeResponse
import com.google.cloud.speech.v1.SpeechClient
import com.google.cloud.speech.v1.SpeechRecognitionAlternative
import com.google.cloud.speech.v1.SpeechRecognitionResult
import com.google.cloud.speech.v1.SpeechSettings
import com.google.protobuf.ByteString
import net.poundex.sentinel.scribe.SpeechProcessor

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class GoogleSpeechProcessor implements SpeechProcessor
{
	@Override
	String processSpeech(byte[] wave)
	{
		FileInputStream credentialsStream = new FileInputStream(
				(String)Class.forName("net.poundex.sentinel.server.StupidSecretsProvider").getInstance().secrets.googlespeech.credentialsFile
		)
		GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
		FixedCredentialsProvider credentialsProvider = FixedCredentialsProvider.create(credentials);

		SpeechSettings speechSettings =
				SpeechSettings.newBuilder()
						.setCredentialsProvider(credentialsProvider)
						.build();

		SpeechClient speech = SpeechClient.create(speechSettings);
		ByteString audioBytes = ByteString.copyFrom(wave)

		RecognitionConfig config = RecognitionConfig.newBuilder()
				.setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
				.setSampleRateHertz(16000)
				.setLanguageCode("en-GB")
				.build();
		RecognitionAudio audio = RecognitionAudio.newBuilder()
				.setContent(audioBytes)
				.build();

		RecognizeResponse response = speech.recognize(config, audio);
		List<SpeechRecognitionResult> results = response.getResultsList();

		String retval = null
		for (SpeechRecognitionResult result: results) {
			SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
			if( ! retval)
				retval = alternative.transcript
			System.out.printf("Transcription: %s%n", alternative.getTranscript());
		}
		speech.close()
		return retval ?: "???"
	}
}
