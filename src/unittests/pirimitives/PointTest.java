/**
 *
 */
package unittests.pirimitives;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;

/**
 * Unit test for primitives.Point class
 * 
 * @author Yoni Leventhal, Adiel Yekutiel
 */
class PointTest {
	private double DELTA = 0.0001;
	private Point p3 = new Point(2, 4, 5);
	private Point p2 = new Point(2, 3, 4);
	private Point p1 = new Point(1, 2, 3);
	private Vector v1 = new Vector(1, 1, 1);

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: checks if adding vector to point works correctly
		assertEquals(p2, p1.add(v1), "ERROR: (point + vector) = other point does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that subtracting p1 from p2 returns the correct vector v1.
		// p2.subtract(p1) should be equal to v1.
		assertEquals(v1, p2.subtract(p1), "ERROR: (point2 - point1) does not work correctly");

		// =============== Boundary Values Tests ==================
		// TC11: Test that subtracting a point from itself throws an exception.
		assertThrows(IllegalArgumentException.class, () -> {
			p1.subtract(p1);
		}, "ERROR: (point - itself) does not throw an exception");
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that the distance between p1 and p3 is correct
		assertEquals(3, p1.distance(p3), DELTA, "ERROR: distance between points is wrong");

		// Test that the distance between p3 and p1 is correct
		assertEquals(3, p3.distance(p1), DELTA, "ERROR: distance between points is wrong");

		// =============== Boundary Values Tests ==================
		// TC11: Test that the distance from a point to itself is zero
		assertEquals(0, p1.distance(p1), DELTA, "ERROR: point distance to itself is not zero");
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that the squared distance between p1 and p3 is correct
		assertEquals(9, p1.distanceSquared(p3), DELTA, "ERROR: distanceSquared() between points is wrong");

		//  Test that the squared distance between p3 and p1 is correct
		assertEquals(9, p3.distanceSquared(p1), DELTA, "ERROR: distanceSquared() between points is wrong");

		// =============== Boundary Values Tests ==================
		// TC11: Test that the squared distance from a point to itself is zero
		assertEquals(0, p1.distanceSquared(p1), DELTA, "ERROR: point distanceSquared() to itself is not zero");
	}
}
