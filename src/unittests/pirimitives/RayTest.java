/**
 * 
 */
package unittests.pirimitives;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * 
 */
class RayTest {

	/**
	 * Test method for {@link primitives.Ray#getPoint(double)}.
	 */
	@Test
	void testGetPoint() {
		
        Ray ray = new Ray(new Point(1, 1, 1), new Vector(1, 0, 0));
        
        // ============ Equivalence Partitions Tests ==============

        //TC01: t is positive
        Point expectedPositivePoint = new Point(3, 1, 1);
        assertEquals(expectedPositivePoint, ray.getPoint(2), "ERROR: getPoint() for positive distance is incorrect");

        // t is negative
        Point expectedNegativePoint = new Point(0,1,1);
        assertEquals(expectedNegativePoint,ray.getPoint(-1), "ERROR: getPoint() for negative distance is incorrect");

        // =============== Boundary Values Tests ==================

        //TC11: t is 0
        Point expectedZeroPoint = new Point(1, 1, 1);
        assertEquals(expectedZeroPoint, ray.getPoint(0), "ERROR: getPoint() for distance 0 is incorrect");
    }

}
