/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Triangle;
import primitives.Point;
import primitives.Vector;

/**
 * 
 *  Testing Triangle
 * 
 *  @author Yoni Leventhal, Adiel Yekutiel
 * 
 */
class TriangleTest {

	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		 Point[] pts =
	         { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)};
	      Triangle tr =new Triangle (new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
	      
	      //  TC01: ensure there are no exceptions
	      
	      assertDoesNotThrow(() -> tr.getNormal(new Point(0, 0, 1)), "");
	      // generate the test result
	      Vector result = tr.getNormal(new Point(0, 0, 1));
	      // ensure |result| = 1
	      assertEquals(1, result.length(), 0.000001, "Polygon's normal is not a unit vector");
	      // ensure the result is orthogonal to all the edges
	      for (int i = 0; i < 3; ++i)
	          assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])),0.000001,
	                       "Triangle's normal is not orthogonal to one of the edges");
	      
	   
		
		
	}

}
