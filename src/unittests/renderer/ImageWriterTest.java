package unittests.renderer;

import org.junit.jupiter.api.Test;

import primitives.Color;
import renderer.ImageWriter;

/**
 * Unit tests for the {@link renderer.ImageWriter} class. This class tests the
 * functionality of writing pixels to an image. It verifies that pixels are
 * correctly colored based on the provided colors.
 * <p>
 * Tests cover the initialization of an {@link renderer.ImageWriter} instance,
 * writing pixels with different colors to verify correctness.
 * </p>
 * 
 * @author Yoni and adiel
 */
class ImageWriterTest {

	/**
	 * Test method for
	 * {@link renderer.ImageWriter#ImageWriter(java.lang.String, int, int)}.
	 * Initializes an {@link renderer.ImageWriter} instance, writes pixels with
	 * alternating colors to simulate a grid pattern, and verifies the output image.
	 */
	@Test
	void testImageWriter() {
		final int nx = 801, ny = 501, step = 50;
		// Create a new ImageWriter instance with dimensions 800x500 pixels
		ImageWriter imageWriter = new ImageWriter("testMyImage", nx, ny);

		// Define colors for background and grid lines
		final Color background = new Color(255, 255, 0); // Yellow background
		final Color grid = new Color(255, 0, 0); // Red grid lines

		// Write pixels to simulate a grid pattern
		for (int i = 0; i < ny; ++i) {
			for (int j = 0; j < nx; ++j) {
				// Alternate between grid color and background color based on grid pattern
				imageWriter.writePixel(j, i, i % step == 0 || j % step == 0 ? grid : background);
			}
		}

		// Write the image to file
		imageWriter.writeToImage();
	}
}
