/**
 *
 */
package unittests.pirimitives;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;

/**
 * Unit test for primitives.Point class
 *  @author Yoni leventhal, adiel yekutiel
 */
class PointTest {
    Point  p3 = new Point(2, 4, 5);
	Point p2 = new Point(2,3,4);
	Point p1 = new Point(1,2,3);
	Vector v1 = new Vector(1,1,1);
	Vector v1Opposite = new Vector(-1, -2, -3);

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		
		  // ============ Equivalence Partitions Tests ============== 
		//checks if adding vector to point works correctly
		assertEquals(p1.add(v1),p2,"ERROR: (point + vector) = other point does not work correctly"); 
		
		 // =============== Boundary Values Tests ================== 
		//checks if adding minus numbers will give 0
		assertEquals(p1.add(v1Opposite),Point.ZERO,"ERROR: (point + vector) = center of coordinates does not work correctly"); 
	}

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
	    
	    // ============ Equivalence Partitions Tests ============== 
	    // Test that subtracting p1 from p2 returns the correct vector v1.
	    // p2.subtract(p1) should be equal to v1.
	    assertEquals(p2.subtract(p1), v1, "ERROR: (point2 - point1) does not work correctly");
	    
	    // =============== Boundary Values Tests ================== 
	    
	    // Test that subtracting a point from itself does not throw an exception.
	    // assertDoesNotThrow is used to verify that p1.subtract(p1) does not throw any exception.
	    assertThrows(IllegalArgumentException.class, () -> {
	        p1.subtract(p1);
	    }, "ERROR: (point - itself) does not throw an exception");
	    
	    // Test that subtracting a point from itself throws an exception.
	    // assertThrows is used to verify that p1.subtract(p1) throws an exception of any type.
	    // The assertion fails if no exception is thrown or if a different exception type is thrown.
	    
		//not correct
	    assertThrows(Exception.class, () -> {
	        p1.subtract(p1);
	    }, "ERROR: (point - itself) throws wrong exception");
	}


	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
			
		assertEquals(p1.distance(p1),Point.ZERO,"ERROR: point distance to itself is not zero");
		
		assertEquals(p1.distance(p3)-3,Point.ZERO,"ERROR: distance between points is wrong");
		
		assertEquals(p3.distance(p1)-3,Point.ZERO,"ERROR: distance between points is wrong");


	}
	
	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		
		assertEquals(p1.distanceSquared(p1),Point.ZERO,"ERROR: point squared distance to itself is not zero");
		
		assertEquals(p1.distanceSquared(p3)-9,Point.ZERO,"ERROR: squared distance between points is wrong");

		assertEquals(p3.distanceSquared(p1)-9,Point.ZERO,"ERROR: squared distance between points is wrong");

	}

}
