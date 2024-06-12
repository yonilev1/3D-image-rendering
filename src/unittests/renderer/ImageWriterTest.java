/**
 * 
 */
package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Color;
import renderer.ImageWriter;

/**
 * @author Yoni and adiel
 */
class ImageWriterTest {

	/**
	 * Test method for {@link renderer.ImageWriter#ImageWriter(java.lang.String, int, int)}.
	 */
	@Test
	void testImageWriter() {
		ImageWriter imageWriter = new ImageWriter("testMyImage", 801, 501);
		Color background = new Color(255, 255, 0);
		Color grid = new Color(255,0,0);

		// Separately colors each pixel based on the ray tracer's findings
		for (int i = 0; i <= 500; ++i)
			for (int j = 0; j <= 800; ++j)
				imageWriter.writePixel(j, i, i % 50 == 0 || j % 50 == 0 ? grid : background);
		imageWriter.writeToImage();
	}
}
