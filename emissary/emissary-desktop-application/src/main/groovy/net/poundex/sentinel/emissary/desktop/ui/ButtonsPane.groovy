package net.poundex.sentinel.emissary.desktop.ui

import javafx.scene.control.Button
import javafx.scene.layout.HBox
import net.poundex.sentinel.emissary.ControlLoop
import org.springframework.stereotype.Component

@Component("buttons")
class ButtonsPane extends HBox
{
	private final ControlLoop controlLoop

	ButtonsPane(ControlLoop controlLoop)
	{
		super(
				[new Button("Start listening").with {
					setOnAction {
						controlLoop.start()
					}
					it
				},
				new Button("Stop listeneing").with {
					setOnAction {
						controlLoop.stop()
					}
					it
				}] as Button[])
		this.controlLoop = controlLoop
	}
}
