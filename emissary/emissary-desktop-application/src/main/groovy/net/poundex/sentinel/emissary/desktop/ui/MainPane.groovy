package net.poundex.sentinel.emissary.desktop.ui

import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component("mainPane")
class MainPane extends VBox
{
	MainPane(
			@Qualifier("buttons") HBox buttons,
			@Qualifier("statusLabel") Label statusLabel,
			@Qualifier("speechLabel") Label speechLabel,
			@Qualifier("textInputPane") BorderPane textInputPane,
			@Qualifier("responseTextLabel") Label responseTextLabel,
			@Qualifier("speechTestPane") Pane speechTestPane)
	{
		super(buttons, statusLabel, speechLabel, textInputPane, responseTextLabel, speechTestPane)
	}
}
