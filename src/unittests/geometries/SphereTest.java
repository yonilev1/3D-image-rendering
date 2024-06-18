/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import primitives.*;
import primitives.Vector;

/**
 * Testing Sphere
 * 
 * @author Yoni Leventhal, Adiel Yekutiel
 * 
 * 
 */
class SphereTest {

	/**
	 * point for test
	 */
	private static final Point p100 = new Point(1, 0, 0);

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: test getNurmal() works properly
		Sphere s = new Sphere(new Point(1, 2, 3), 1);
		Vector v = s.getNormal(new Point(1, 3, 3));
		assertEquals(new Vector(0, 1, 0), v, "getNormal() wrong result");

	}

	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Sphere sphere = new Sphere(p100, 1d);

		final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
		final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
		final var exp = List.of(gp1, gp2);

		final Vector v310 = new Vector(3, 1, 0);
		final Vector v110 = new Vector(1, 1, 0);

		final Point p01 = new Point(-1, 0, 0);

		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray's line is outside the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");

		// TC02: Ray starts before and crosses the sphere (2 points)
		var result1 = sphere.findIntersections(new Ray(p01, v310)).stream()
				.sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
		assertEquals(2, result1.size(), "Wrong number of points");
		assertEquals(exp, result1, "Ray crosses sphere");

		// TC03: Ray starts inside the sphere (1 point)
		assertEquals(List.of(gp2), sphere.findIntersections(new Ray(new Point(0.8, 0.6, 0), v310)),
				"Ray crosses sphere one time only, at gp2");

		// TC04: Ray starts after the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), v110)), "There shouldn't be any intersections");

		// =============== Boundary Values Tests ==================

		// **** Group: Ray's line crosses the sphere (but not the center)
		// TC11: Ray starts at sphere and goes inside (1 points)
		result1 = sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 1, 0)));
		assertEquals(1, result1.size(), "There should be one intersection");
		assertEquals(List.of(new Point(1, 1, 0)), result1, "Incorrect intersection point");

		// TC12: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(1, 0, 1), new Vector(1, 0, 3))),
				"There shouldn't be any intersections");

		// **** Group: Ray's line goes through the center
		// TC13: Ray starts before the sphere (2 points)
		result1 = sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 0, 0))).stream()
				.sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
		assertEquals(2, result1.size(), "There should be 2 intersection");
		assertEquals(List.of(new Point(0, 0, 0), new Point(2, 0, 0)), result1, "Incorrect intersection point");

		// TC14: Ray starts at sphere and goes inside (1 points)
		result1 = sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)));
		assertEquals(List.of(new Point(2, 0, 0)), result1, "Incorrect intersection point");

		// TC15: Ray starts inside (1 points)
		result1 = sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0)));
		assertEquals(List.of(new Point(2, 0, 0)), result1, "Incorrect intersection point");

		// TC16: Ray starts at the center (1 points)
		result1 = sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, 0, 0)));
		assertEquals(List.of(new Point(2, 0, 0)), result1, "Incorrect intersection point");

		// TC17: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(-1.5, 0, 0))),
				"There shouldn't be any intersections");

		// TC18: Ray starts after sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(-1.5, 0, 0))),
				"There shouldn't be any intersections");

		// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
		// TC19: Ray starts before the tangent point
		assertNull(sphere.findIntersections(new Ray(new Point(0, 1, 0), new Vector(1, 1, 0))),
				"There shouldn't be any intersections");

		// TC20: Ray starts at the tangent point
		assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 0), new Vector(2, 1, 0))),
				"There shouldn't be any intersections");

		// TC21: Ray starts after the tangent point
		assertNull(sphere.findIntersections(new Ray(new Point(2, 1, 0), new Vector(3, 1, 0))),
				"There shouldn't be any intersections");

		// **** Group: Special cases
		// TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's
		// center line
		assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(-1, 1, 0))),
				"There shouldn't be any intersections");

	}

}
