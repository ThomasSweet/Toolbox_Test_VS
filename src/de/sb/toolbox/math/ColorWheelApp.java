package de.sb.toolbox.math;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.UnaryOperator;
import javax.imageio.ImageIO;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import de.sb.toolbox.math.Complex.MutableDoublePrecision;


/**
 * Java FX based color wheel app for complex functions.
 */
public class ColorWheelApp extends Application {
	static private Image COLOR_WHEEL;

	static public void main (final String[] args) throws URISyntaxException, IOException {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final UnaryOperator<MutableDoublePrecision<?>> function = z -> ComplexMath.exp(-2,(MutableDoublePrecision) z);
		final Path path = args.length > 0 ? Paths.get(args[0] + ".png") : null;
		final double left = args.length > 1 ? Double.parseDouble(args[1]) : -5;
		final double low = args.length > 2 ? Double.parseDouble(args[2]) : -5;
		final double right = args.length > 3 ? Double.parseDouble(args[3]) : +5;
		final double high = args.length > 4 ? Double.parseDouble(args[4]) : +5;
		final double magnification = args.length > 5 ? Double.parseDouble(args[5]) : 100;

		COLOR_WHEEL = ComplexMath.plotColorWheel(function, left, low, right, high, magnification, .75f);
		if (path != null) ImageIO.write(SwingFXUtils.fromFXImage(COLOR_WHEEL, null), "png", path.toFile());

		launch();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start (final Stage window) {
		final ImageView imageView = new ImageView(COLOR_WHEEL);
		final StackPane rootPane = new StackPane(imageView);
		imageView.fitHeightProperty().bind(rootPane.heightProperty());
		imageView.fitWidthProperty().bind(rootPane.widthProperty());

		final Scene sceneGraph = new Scene(rootPane, 640, 480);
		window.setScene(sceneGraph);
		window.setTitle("Complex Color Wheel");
		window.getIcons().add(COLOR_WHEEL);
		window.show();
	}
}