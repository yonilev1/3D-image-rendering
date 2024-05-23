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
 * Unit test for primitives.Vector class
 *  @author Yoni leventhal, adiel yekutiel
 */
class VectorTest {
	
	 Point  p1         = new Point(1, 2, 3);
     Point  p2         = new Point(2, 4, 6);
     Point  p3         = new Point(2, 4, 5);

     Vector v1         = new Vector(1, 2, 3);
     Vector v1Opposite = new Vector(-1, -2, -3);
     Vector v2         = new Vector(-2, -4, -6);
     Vector v3         = new Vector(0, 3, -2);
     Vector v4         = new Vector(1, 2, 2);

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		
	    // ============ Equivalence Partitions Tests ============== 
		assertEquals(v1Opposite,v1.add(v2),"ERROR: Vector + Vector does not work correctly");
		
	    // =============== Boundary Values Tests ================== 
		assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite), "ERROR: Vector + -itself does not throw an exception"); 
		
		//not correct
		assertThrows(Exception.class, () -> v1.add(v1Opposite), "ERROR: Vector + itself throws wrong exception"); 

	}
	
	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	void tesSubtrCTVector() {
		
	    // ============ Equivalence Partitions Tests ============== 
		assertEquals(new Vector(3,6,9),v1.subtract(v2),"ERROR: Vector + Vector does not work correctly");
		
	    // =============== Boundary Values Tests ================== 
		assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1), "ERROR: Vector - itself does not throw an exception");  
		
		//not correct
		assertThrows(Exception.class, () -> v1.subtract(v1), "ERROR: Vector + itself throws wrong exception"); 

	}
	
	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
				
	    // ============ Equivalence Partitions Tests ============== 
		assertEquals(v1.dotProduct(v2)+28,Point.ZERO,"ERROR: dotProduct() wrong value");
		
	    // =============== Boundary Values Tests ================== 
		assertEquals(v1.dotProduct(v3),Point.ZERO,"ERROR: dotProduct() for orthogonal vectors is not zero");
	}
	
	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		
	    // ============ Equivalence Partitions Tests ============== 
	    Vector vr = v1.crossProduct(v3);
		assertEquals(vr.length() - v1.length() * v3.length(),Point.ZERO,"ERROR: crossProduct() wrong result length");
		assertEquals((vr.crossProduct(v1)),Point.ZERO,"ERROR: crossProduct() result is not orthogonal to its operands");
		assertEquals((vr.crossProduct(v3)),Point.ZERO,"ERROR: crossProduct() result is not orthogonal to its operands");

		// =============== Boundary Values Tests ================== 
		assertThrows(Exception.class, () ->v1.crossProduct(v2), "ERROR: crossProduct() for parallel vectors does not throw an exception"); 
	}   
    
	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		
	    Vector v = new Vector(1, 2, 3);
	    Vector u = v.normalize();
	    boolean bol = true;
	    if (v.dotProduct(u) < 0) {bol = false;}
	    
	    // ============ Equivalence Partitions Tests ============== 
		assertEquals(u.length() - 1,Point.ZERO,"ERROR: the normalized vector is not a unit vector");
		
		// =============== Boundary Values Tests ================== 
		assertThrows(Exception.class, () -> v.crossProduct(u), "ERROR: the normalized vector is not parallel to the original one"); 
		assertTrue(bol,"ERROR: the normalized vector is opposite to the original one");
	}   
  
  

}
