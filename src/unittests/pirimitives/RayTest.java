/**
 * 
 */
package unittests.pirimitives;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Ray test
 * 
 * @author Yoni leventhal adiel yekutiel
 */
class RayTest {

	/**
	 * Test method for {@link primitives.Ray#getPoint(double)}.
	 */
	@Test
	void testGetPoint() {

		Ray ray = new Ray(new Point(1, 1, 1), new Vector(1, 0, 0));

		// ============ Equivalence Partitions Tests ==============

		// TC01: t is positive
		Point expectedPositivePoint = new Point(3, 1, 1);
		assertEquals(expectedPositivePoint, ray.getPoint(2), "ERROR: getPoint() for positive distance is incorrect");

		// TC02: t is negative
		Point expectedNegativePoint = new Point(0, 1, 1);
		assertEquals(expectedNegativePoint, ray.getPoint(-1), "ERROR: getPoint() for negative distance is incorrect");

		// =============== Boundary Values Tests ==================

		// TC11: t is 0
		Point expectedZeroPoint = new Point(1, 1, 1);
		assertEquals(expectedZeroPoint, ray.getPoint(0), "ERROR: getPoint() for distance 0 is incorrect");
	}

	/**
	 * Test method for {@link primitives.Ray#findClosestPoint(List<Point>)}.
	 */
	@Test
	void testFindClosestPoint() {
		Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 1, 0));
		Point a = new Point(2, 2, 0);
		Point b = new Point(3, 3, 0);
		Point c = new Point(4, 4, 0);
		List<Point> points = List.of(b, a, c);
		// ============ Equivalence Partitions Tests ==============

		// TC01: Closest point is in the middle of the list
		assertEquals(a, ray.findClosestPoint(points), "wrong result");

		// ============ Boundary Values Tests ==============
		points = new ArrayList<>();
		// TC02: list is empty (should return null)
		assertNull(ray.findClosestPoint(points), "Should have returned null");

		// TC03: closest point is at start of list
		points = List.of(a, b, c);
		assertEquals(a, ray.findClosestPoint(points), "wrong result");

		// TC04: closest point is at end of list
		points = List.of(b, c, a);
		assertEquals(a, ray.findClosestPoint(points), "wrong result");
	}

}
