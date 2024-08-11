package unittests.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;
import geometries.*;

/**
 * Integration tests for Camera ray construction and intersection with
 * geometries.
 * 
 * @author Yoni and adiel
 */
public class cameraIntegrationWithGeometriesTest {

	/**
	 * The first Camera instance with specific settings. It is located at (0, 0, 0)
	 * and faces the negative z-axis.
	 */
	final private Camera camera = Camera.getBuilder() //
			.setRayTracer(new SimpleRayTracer(new Scene("Test"))) // Set Ray)Tracer
			.setImageWriter(new ImageWriter("Test", 1, 1)) // Set ImageWriter
			.setLocation(new Point(0, 0, 0)) // Set Camera location
			.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)) // Set Camera direction
			.setVpSize(3, 3) // Set view plane size
			.setVpDistance(1) // Set view plane distance
			.build();

	/**
	 * The second Camera instance with similar settings to the first one but located
	 * at (0, 0, 0.5). It also faces the negative z-axis.
	 */
	final private Camera camera1 = Camera.getBuilder() //
			.setRayTracer(new SimpleRayTracer(new Scene("Test"))) // Set RayTracer
			.setImageWriter(new ImageWriter("Test", 1, 1)) // Set ImageWriter
			.setLocation(new Point(0, 0, 0.5)) // Set Camera location
			.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)) // Set Camera direction
			.setVpSize(3, 3) // Set view plane size
			.setVpDistance(1) // Set view plane distance
			.build();

	/**
	 * Helper method to generate rays through all pixels of a view plane and count
	 * intersections with a given geometry.
	 * 
	 * @param camera   The camera object.
	 * @param geometry The geometry object to test intersection with.
	 * @param nX       Number of pixels in X direction.
	 * @param nY       Number of pixels in Y direction.
	 * @return The total number of intersection points.
	 */
	private int countIntersections(Camera camera, Intersectable geometry, int nX, int nY) {
		int count = 0;
		for (int i = 0; i < nY; i++) {
			for (int j = 0; j < nX; j++) {
				Ray ray = camera.constructRay(nX, nY, j, i);
				var intersections = geometry.findIntersections(ray);
				if (intersections != null) {
					count += intersections.size();
				}
			}
		}
		return count;
	}

	/**
	 * Test for intersection of rays with a sphere. This test checks various
	 * scenarios where rays from the camera intersect with a sphere. It verifies the
	 * number of intersection points for each scenario.
	 */
	@Test
	void testSphereIntersection() {
		final String wrongNumberOfIntersections = "Unexpected number of intersections with sphere";

		// TC01: 2 intersection points
		assertEquals(2, countIntersections(camera, new Sphere(new Point(0, 0, -3), 1), 3, 3), //
				wrongNumberOfIntersections);

		// TC02: 18 intersection points
		assertEquals(18, countIntersections(camera1, new Sphere(new Point(0, 0, -2.5), 2.5), 3, 3), //
				wrongNumberOfIntersections);

		// TC03: 10 intersection points
		assertEquals(10, countIntersections(camera1, new Sphere(new Point(0, 0, -2), 2), 3, 3), //
				wrongNumberOfIntersections);

		// TC04: 9 intersection points
		assertEquals(9, countIntersections(camera1, new Sphere(new Point(0, 0, 0), 4), 3, 3), //
				wrongNumberOfIntersections);

		// TC05: 0 intersection points
		assertEquals(0, countIntersections(camera1, new Sphere(new Point(0, 0, 1), 0.5), 3, 3), //
				wrongNumberOfIntersections);
	}

	/**
	 * Test for intersection of rays with a plane. This test checks various
	 * scenarios where rays from the camera intersect with a plane. It verifies the
	 * number of intersection points for each scenario.
	 */
	@Test
	void testPlaneIntersection() {
		final String wrongNumberOfIntersections = "Unexpected number of intersections with plane";

		// TC01: 9 intersection points
		assertEquals(9, countIntersections(camera, new Plane(new Point(0, 0, -3), new Vector(0, 0, 1)), 3, 3), //
				wrongNumberOfIntersections);

		// TC02: 9 intersection points
		assertEquals(9, countIntersections(camera, new Plane(new Point(0, 0.2, -1), new Vector(0, 0, 1)), 3, 3), //
				wrongNumberOfIntersections);

		// TC03: 6 intersection points
		assertEquals(6, countIntersections(camera, new Plane(new Point(0, 0, -3), new Vector(0, 1, -1)), 3, 3), //
				wrongNumberOfIntersections);
	}

	/**
	 * Test for intersection of rays with a triangle. This test checks various
	 * scenarios where rays from the camera intersect with a triangle. It verifies
	 * the number of intersection points for each scenario.
	 */
	@Test
	void testTriangleIntersection() {
		final String wrongNumberOfIntersections = "Unexpected number of intersections with triangle";

		// TC01: 1 intersection points
		assertEquals(1, countIntersections(camera, new Triangle(new Point(0, 1, -2), //
				new Point(1, -1, -2), new Point(-1, -1, -2)), 3, 3), wrongNumberOfIntersections);

		// TC02: 2 intersection points
		assertEquals(2, countIntersections(camera, new Triangle(new Point(0, 20, -2), //
				new Point(1, -1, -2), new Point(-1, -1, -2)), 3, 3), wrongNumberOfIntersections);
	}
}
