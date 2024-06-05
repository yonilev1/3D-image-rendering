/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * 
 * Testing Plane
 * 
 * @author Yoni Leventhal, Adiel Yekutiel
 */
public class PlaneTest {

	private final double DELTA = 0.000001;

	@Test
	void testCtorThreePoints() {
		// =============== Boundary Values Tests ==================

		// TC01: not throws exception from ctor
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(0, 0, 1), new Point(0, 0, 1), new Point(1, 0, 0)), //
				"two point the same dose not throws exception");

		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(3, 3, 3)), //
				"three point are on the same line dose not throw exception");

	}
	/**
	 * Test method for {@link geometries.Plane#GetNormal()}.
	 */
	@Test
	void testGetNormal() {

		// ============ Equivalence Partitions Tests ==============

		Plane plane = new Plane(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0));
		Vector result = plane.getNormal(new Point(0, 0, 1));

		// TC01: ensure |result| = 1
		assertEquals(1, result.length(), DELTA, "Plane's normal is not a unit vector");
		// test if the normal is orthogonal to the plane
		assertEquals(0,result.dotProduct(new Vector(-1, 1, 0)), "Plane's normal is not orthogonal");
		assertEquals(0,result.dotProduct(new Vector(0, -1, 1)), "Plane's normal is not orthogonal");

	}
	
	/**
	 * Test method for {@link geometries.Plane#findIntersections()}.
	 */
	@Test
	void testFindIntersections() {
		//???? Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)); the normal is (1,1,1) switch point a and c ?????
		Plane plane = new Plane(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0)); // the normal is upside (-1,-1,-1)
		
		// ============ Equivalence Partitions Tests ==============
		
		//TC01: The ray intersect the plane
		final var result = plane.findIntersections(new Ray(new Point(1, 1, 2), new Vector(1, 1, -3)));
		assertEquals(1,result.size(),"ERROR: findIntersections() did not return the right number of points");
		assertEquals(List.of(new Point(4,4,-7)), result, "ERROR: Incorrect intersection points");
		
		//TC02: The ray not intersect the plane
		final var result2 = plane.findIntersections(new Ray(new Point(2, 3, 1), new Vector(-2, 3, 1)));
		assertNull(result2, "ERROR: findIntersections() sould retutn null");
		
		// =============== Boundary Values Tests ==================
		
		//TC11: Ray is parallel to the plane and is include in the plane
		final var result3 = plane.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, -1,0)));
		assertNull(result3, "ERROR: findIntersections() sould retutn null");
		
		//TC12: Ray is parallel to the plane and isn't include in the plane
		final var result4 = plane.findIntersections(new Ray(new Point(2,3,4), new Vector(1, -1,0)));
		assertNull(result4, "ERROR: findIntersections() sould retutn null");
		
		//TC13: The ray is orthogonal and starts before the plane
		final var result5 = plane.findIntersections(new Ray(new Point(-5, -4, 3), new Vector(1, 1, 1)));
		assertEquals(1,result5.size(),"ERROR: findIntersections() did not return the right number of points");
		assertEquals(List.of(new Point(-2.666666666666667,-1.666666666666667,5.333333333333333)), result5, "ERROR: Incorrect intersection points");

		//TC14: The ray is orthogonal and starts in the plane
		final var result6 = plane.findIntersections(new Ray(new Point(0, 0, 1), new Vector(1, 1, 1)));
		assertNull(result6, "ERROR: findIntersections() sould retutn null");
		
		//TC15: The ray is orthogonal and starts after the plane
		final var result7 = plane.findIntersections(new Ray(new Point(4, 3, 3), new Vector(1, 1, 1)));
		assertNull(result7, "ERROR: findIntersections() sould retutn null");
		
		//TC16: Ray is neither orthogonal nor parallel to and begins at the point reference the plane
		final var result8 = plane.findIntersections(new Ray(new Point(0, 0, 1), new Vector(1, 1, 1)));
		assertNull(result8, "ERROR: findIntersections() sould retutn null");
		
		//TC17:Ray is neither orthogonal nor parallel to and begins at any point in the plane
		final var result9 = plane.findIntersections(new Ray(new Point(-4,3,2), new Vector(2, 4, 1)));
		assertNull(result9, "ERROR: findIntersections() sould retutn null");
		
		
	}

}
