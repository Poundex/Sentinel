package net.poundex.sentinel.emissary.panel

import eu.hansolo.tilesfx.Tile
import eu.hansolo.tilesfx.TileBuilder
import eu.hansolo.tilesfx.tools.FlowGridPane
import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.stage.Stage

class EmissaryPanelApplication extends Application
{
	public static void main(String[] args)
	{
		launch(EmissaryPanelApplication, args)
	}

	@Override
	void start(Stage primaryStage) throws Exception {
		primaryStage.width = 800
		primaryStage.height = 480
		FlowGridPane pane = new FlowGridPane(6, 4,
				TileBuilder.create().skinType(Tile.SkinType.SWITCH).prefSize(150, 150).build(),
				TileBuilder.create().skinType(Tile.SkinType.SWITCH).prefSize(300, 150).build(),
				TileBuilder.create().skinType(Tile.SkinType.SWITCH).prefSize(150, 150).build(),
		TileBuilder.create().skinType(Tile.SkinType.SWITCH).prefSize(150, 150).build(),
		TileBuilder.create().skinType(Tile.SkinType.SWITCH).prefSize(150, 150).build(),
		TileBuilder.create().skinType(Tile.SkinType.SWITCH).prefSize(150, 150).build())
		pane.setHgap(5)
		pane.setVgap(5)
		pane.setAlignment(Pos.CENTER)
		pane.setCenterShape(true)
		pane.setPadding(new Insets(5))
		//pane.setPrefSize(800, 600);
		pane.setBackground(new Background(new BackgroundFill(Color.web("#101214"), CornerRadii.EMPTY, Insets.EMPTY)))
		primaryStage.scene = new Scene(pane)
		primaryStage.show()
	}
}
