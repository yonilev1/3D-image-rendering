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
 * Testing Cylinder
 * 
 * @author Yoni Leventhal, Adiel Yekutiel
 * 
 */
class CylinderTest {

	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Cylinder c = new Cylinder(6, new Ray(new Point(4, 0, 0), new Vector(1, 0, 0)), 2);

		// ============ Equivalence Partitions Tests ==============

<<<<<<< HEAD
		Vector v1 = c.getNormal(new Point(7, -1, 0));
=======
		Vector v1 = c.getNormal(new Point(7,-1,0));
>>>>>>> branch 'master' of https://github.com/yonilev1/ISE5784_1446_9978.git
		Vector v2 = c.getNormal(new Point(4, 0, 1));
		Vector v3 = c.getNormal(new Point(10, 0, 1));

		// TC01: Test that the normal is the right one
		assertEquals(new Vector(0, -1, 0), v1, "getNormal() wrong result");

		// TC02: Test that getNormal works for the ray base
<<<<<<< HEAD
		assertEquals(new Vector(-1, 0, 0), v2, "getNormal() not working properly for the ray base");
=======
	    assertEquals(new Vector(-1, 0, 0), v2, "getNormal() not working properly for the ray base");
>>>>>>> branch 'master' of https://github.com/yonilev1/ISE5784_1446_9978.git

		// TC03: Test that getNormal works for the second base
		assertEquals(new Vector(1, 0, 0), v3, "getNormal() not working properly for the second base");
<<<<<<< HEAD
=======

>>>>>>> branch 'master' of https://github.com/yonilev1/ISE5784_1446_9978.git

		// =============== Boundary Values Tests ==================

		Vector v4 = c.getNormal(new Point(4, 0, 0));
		Vector v5 = c.getNormal(new Point(10, 0, 0));
		Vector v6 = c.getNormal(new Point(4, 1, 0));
		Vector v7 = c.getNormal(new Point(10, 1, 0));

		// TC04: Test that getNormal works for the center point of the ray base
		assertEquals(new Vector(-1, 0, 0), v4, "getNormal() not working properly for the center point of the ray base");

		// TC05: Test that getNormal works for the center point of the top base
<<<<<<< HEAD
		assertEquals(new Vector(1, 0, 0), v5,//
				"getNormal() not working properly for the center point of the second base");
=======
		assertEquals(new Vector(1, 0, 0), v5, "getNormal() not working properly for the center point of the second base");

		// TC06: Test that getNormal works for the point on the edge of the ray base
		assertEquals(new Vector(-1, 0, 0), v6, "getNormal() not working properly for a point on the edge of the ray base");

		// TC07: Test that getNormal works for the point on the edge of the second base
		assertEquals(new Vector(1, 0, 0), v7, "getNormal() not working properly for a point on the edge of the second base");
>>>>>>> branch 'master' of https://github.com/yonilev1/ISE5784_1446_9978.git

		// TC06: Test that getNormal works for the point on the edge of the ray base
		assertEquals(new Vector(-1, 0, 0), v6,//
				"getNormal() not working properly for a point on the edge of the ray base");

		// TC07: Test that getNormal works for the point on the edge of the second base
		assertEquals(new Vector(1, 0, 0), v7,//
				"getNormal() not working properly for a point on the edge of the second base");

	}
	
	/**
	 * Test method for {@link geometries.Cylinder#findIntersections()}.
	 */
	@Test
	void testFindIntersections() {
		
		
		
	}

}
<<<<<<< HEAD
=======

>>>>>>> branch 'master' of https://github.com/yonilev1/ISE5784_1446_9978.git
