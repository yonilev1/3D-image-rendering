/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import geometries.Cylinder;
import primitives.Point;
import primitives.Ray;

import primitives.Vector;


/**
 *  Testing Cylinder
 * 
 *  @author Yoni Leventhal, Adiel Yekutiel
 * 
 */
class CylinderTest {

	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Cylinder c = new Cylinder(6, new Ray(new Point(4, 0, 0), new Vector(1, 0, 0)),2);

		// ============ Equivalence Partitions Tests ==============
		
		Vector v1 = c.getNormal(new Point(7,-1,0));
		Vector v2 = c.getNormal(new Point(4, 0, 1));
		Vector v3 = c.getNormal(new Point(10, 0, 1));

		// TC01: Test that the normal is the right one
		assertEquals(new Vector(0, -1, 0), v1, "getNormal() wrong result");
		
		// TC02: Test that getNormal works for the ray base
	    assertEquals(new Vector(-1, 0, 0), v2, "getNormal() not working properly for the bottom base");

		// TC03: Test that getNormal works for the second base
		assertEquals(new Vector(1, 0, 0), v3, "getNormal() not working properly for the top base");


		// =============== Boundary Values Tests ==================
		
		Vector v4 = c.getNormal(new Point(4, 0, 0));
		Vector v5 = c.getNormal(new Point(10, 0, 0));

		// TC11: Test that getNormal works for the center point of the ray base
		assertEquals(new Vector(-1, 0, 0), v4, "getNormal() not working properly for the center point of the bottom base");

		// TC12: Test that getNormal works for the center point of the top base
		assertEquals(new Vector(1, 0, 0), v5, "getNormal() not working properly for the center point of the top base");

	}

}
	









