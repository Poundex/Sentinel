package net.poundex.sentinel.emissary.desktop

import javafx.application.Platform
import javafx.scene.control.Label
import net.poundex.sentinel.emissary.Responder
import net.poundex.sentinel.fathom.ResponsePackage
import net.poundex.sentinel.vox.SpeechSynthClient
import net.poundex.sentinel.vox.SpeechSynthRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

import static net.poundex.sentinel.fathom.ResponseCommand.Type.SPEAK

@Service
class DesktopResponder implements Responder
{
	private final Label responseTextLabel
	private final SpeechSynthClient speechSynthClient

	DesktopResponder(@Qualifier("responseTextLabel") Label responseTextLabel, SpeechSynthClient speechSynthClient)
	{
		this.responseTextLabel = responseTextLabel
		this.speechSynthClient = speechSynthClient
	}

	@Override
	void respond(ResponsePackage responsePackage)
	{
		String _tmpResponse = responsePackage.responseCommands.find { it.command == SPEAK }.args['speech']
		Platform.runLater {
			responseTextLabel.text = _tmpResponse
		}
		File speech = File.createTempFile("localspeech", ".wav")
		speech.bytes = speechSynthClient.synthSpeech(
				new SpeechSynthRequest(_tmpResponse)).speechData.decodeBase64()

		"cvlc --play-and-exit ${speech.absolutePath}".execute()
	}
}
