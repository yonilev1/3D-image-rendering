/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Tube;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Testing Tube
 * 
 * @author Yoni Leventhal, Adiel Yekutiel
 * 
 */
class TubeTest {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {

		// ============ Equivalence Partitions Tests ==============

		// getNormal function should work for any point on the surface
		Tube t = new Tube(1, new Ray(new Point(4, 0, 0), new Vector(6, 0, 0)));
		Vector v = t.getNormal(new Point(7, -1, 0));
		assertEquals(new Vector(0, -1, 0), v, "getNormal() wrong result");

		// =============== Boundary Values Tests ==================

		// if the vector from the surface point to head point is orthogonal to the
		// direction point
		Vector v2 = t.getNormal(new Point(4, 1, 0));
		assertEquals(new Vector(0, 1, 0), v2,
				"getNormal() wrong when point on surface is peralle to head point result");
	}

}
