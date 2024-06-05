/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * 
 * Testing Triangle
 * 
 * @author Yoni Leventhal, Adiel Yekutiel
 * 
 */
class TriangleTest {

	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		Point[] pts = { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0) };
		Triangle tr = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));

		// TC01: ensure there are no exceptions

		assertDoesNotThrow(() -> tr.getNormal(new Point(0, 0, 1)), "");
		// generate the test result
		Vector result = tr.getNormal(new Point(0, 0, 1));
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.000001, "Polygon's normal is not a unit vector");
		// ensure the result is orthogonal to all the edges
		for (int i = 0; i < 2; i++)
			assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1])), 0.000001,
					"Triangle's normal is not orthogonal to one of the edges");

	}
	
	/**
	 * Test method for {@link geometries.Triangle#findIntersections()}.
	 */
	@Test
	void testFindIntersections() {
		
		Triangle triangle = new Triangle(new Point(0,0,1),new Point(1,0,0), new Point(0,1,0));
		// ============ Equivalence Partitions Tests ==============
		
		// TC01: Ray intersects the Polygon
		final var result = triangle.findIntersections(new Ray(new Point(0,0,0), new Vector(1,1,1)));
		assertEquals(1, result.size(), "ERROR: findIntersections() did not return the right number of points");
		assertEquals(List.of(new Point(1d/3, 1d/3, 1d/3)), result, "Incorrect intersection points");

		// TC02: Ray outside against edge
		assertNull(triangle.findIntersections(new Ray(new Point(0,0,2), new Vector(2,0,0))),
				"There shouldn't be any intersections");

		// TC03: Ray outside against vertex
		assertNull(triangle.findIntersections(new Ray(new Point(0.5, 1.5, 1), new Vector(-0.5, 1.5, -1))),
				"There shouldn't be any intersections");		

		// =============== Boundary Values Tests ==================

		// TC11: Ray on edge
		assertNull(triangle.findIntersections(new Ray(new Point(0,0,1), new Vector(1,0,0))),
				"There shouldn't be any intersections");

		// TC12: Ray on vertex
		assertNull(triangle.findIntersections(new Ray(new Point(0,-2,0), new Vector(1.7,1.41,0))),
				"There shouldn't be any intersections");	
		
		// TC13: Ray after edge
		assertNull(triangle.findIntersections(new Ray(new Point(0,0,1), new Vector(-1,0,2))),
				"There shouldn't be any intersections");	
		
		}
	
	
	
	
    
		

		
		
}
	