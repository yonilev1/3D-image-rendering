/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import primitives.Point;
import primitives.Vector;

/**
 *  Testing Sphere
 * 
 *   @author Yoni Leventhal, Adiel Yekutiel
 * 
 * 
 */
class SphereTest {

	/**
	 * Test method for {@link geometries.Sphere#Sphere(primitives.Point, double)}.
	 */
	@Test
	void testSphere() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: test getNurmal() works properly
		Sphere s= new Sphere (new Point (1,2,3), 1 );
		Vector v = s.getNormal(new Point (1,3,3));
		assertEquals(new Vector (0,1,0), v,"getNormal() wrong result");
		
	}

}
