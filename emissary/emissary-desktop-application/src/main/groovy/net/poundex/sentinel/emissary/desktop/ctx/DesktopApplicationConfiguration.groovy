package net.poundex.sentinel.emissary.desktop.ctx;

import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox
import net.poundex.sentinel.emissary.ControlLoop
import net.poundex.sentinel.emissary.ControlManager
import net.poundex.sentinel.emissary.Responder
import net.poundex.sentinel.emissary.desktop.ui.SpeechLabel
import net.poundex.sentinel.emissary.desktop.ui.StatusLabel
import net.poundex.sentinel.fathom.ResponseCommand
import net.poundex.sentinel.fathom.ResponsePackage
import net.poundex.sentinel.scribe.ProcessSpeechResult
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "net.poundex.sentinel.emissary")
class DesktopApplicationConfiguration
{
	@Bean("statusLabel")
	Label statusLabel(ControlManager controlManager)
	{
		return new StatusLabel().with {
			controlManager.addControlListener(it)
			it
		}
	}

	@Bean("speechLabel")
	Label speechLabel(ControlManager controlManager)
	{
		return new SpeechLabel().with {
			controlManager.addControlListener(it)
			it
		}
	}

	@Bean("textInputPane")
	BorderPane textInputPane(ControlManager controlManager)
	{
		TextArea textArea = new TextArea()
		return new BorderPane(textArea).with {
			bottom = new Button("Send").with {
				setOnAction {
					controlManager.onSpeechProcessed(new ProcessSpeechResult(textArea.text))
					textArea.text = ""
				}
				it
			}
			it
		}
	}

	@Bean("speechTestPane")
	BorderPane speechTestPane(Responder responder)
	{
		TextArea textArea = new TextArea()
		return new BorderPane(textArea).with {
			bottom = new Button("Test").with {
				setOnAction {
					responder.respond(new ResponsePackage(
							[new ResponseCommand(ResponseCommand.Type.SPEAK,
									["speech": textArea.text]
					)]))
				}
				it
			}
			it
		}
	}

	@Bean("responseTextLabel")
	Label responseTextLabel()
	{
		return new Label()
	}
}
