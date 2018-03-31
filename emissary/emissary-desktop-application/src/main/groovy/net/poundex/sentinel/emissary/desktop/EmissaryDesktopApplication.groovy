package net.poundex.sentinel.emissary.desktop

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Stage
import net.poundex.sentinel.emissary.desktop.ctx.DesktopApplicationConfiguration
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class EmissaryDesktopApplication extends Application
{
	static void main(String[] args)
	{
		launch(EmissaryDesktopApplication, args)
	}

	@Override
	void start(Stage primaryStage) throws Exception {
		Runtime.getRuntime().load0(GroovyClassLoader, "/home/poundex/workspaces/pounder/libpocketsphinxjava/lib/libpocketsphinx_jni.so")
		ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(DesktopApplicationConfiguration)
		primaryStage.scene = new Scene(ctx.getBean("mainPane", VBox))
		primaryStage.show()
	}
}
