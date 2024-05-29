/**
 *
 */
package unittests.pirimitives;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Double3;
import primitives.Vector;

/**
 * Unit test for primitives.Vector class
 * 
 * @author Yoni Leventhal, Adiel Yekutiel
 */
class VectorTest {

	private double DELTA = 0.0001;
	private Vector v1 = new Vector(1, 2, 3);
	private Vector v1Opposite = new Vector(-1, -2, -3);
	private Vector v2 = new Vector(-2, -4, -6);
	private Vector v3 = new Vector(0, 3, -2);
	private Vector v4 = new Vector(1, 2, 2);
	private Vector v5 = new Vector(1, 0, 0);
	private Vector v6 = new Vector(0, -3, 1);

	/**
	 * Test method for {@link primitives.Vector#Vector()}.
	 */
	@Test
	void testVector() {
		// =============== Boundary Values Tests ==================
		// TC01: Test that creating a zero vector throws an IllegalArgumentException
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0),
				"Does not throw IllegalArgumentException when gets Vector 0");

		// TC02: Test that creating a zero vector when gets Double3 throws an
		// IllegalArgumentException
		assertThrows(IllegalArgumentException.class, () -> new Vector(new Double3(0, 0, 0)),
				"Does not throw IllegalArgumentException when gets Vector 0");
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that lengthSquared returns the correct value
		assertEquals(9, v4.lengthSquared(), DELTA, "ERROR: lengthSquared() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		// TC01: ============ Equivalence Partitions Tests ==============
		// Test that length returns the correct value
		assertEquals(3, v4.length(), DELTA, "ERROR: length() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that adding vectors works correctly
		assertEquals(v1Opposite, v1.add(v2), "ERROR: Vector + Vector does not work correctly");

		// =============== Boundary Values Tests ==================
		// TC11: Test that adding a vector to its opposite throws an exception
		assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite),
				"ERROR: Vector + -itself does not throw an exception");
	}

	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	void testSubtractVector() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that subtracting vectors works correctly
		assertEquals(new Vector(3, 6, 9), v1.subtract(v2), "ERROR: Vector - Vector does not work correctly");

		// =============== Boundary Values Tests ==================
		// TC11: Test that subtracting a vector from itself throws an exception
		assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
				"ERROR: Vector - itself does not throw an exception");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that scaling a vector works correctly
		assertEquals(new Vector(5, 0, 0), v5.scale(5), "ERROR: scale() wrong Vector");

		// =============== Boundary Values Tests ==================
		// TC11: Test that scaling a vector by zero throws an exception
		assertThrows(IllegalArgumentException.class, () -> v1.scale(0),
				"ERROR: scale() did not throw exception when multiplied by 0");
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that dotProduct returns the correct value for general case
		assertEquals(-28, v1.dotProduct(v2), DELTA, "ERROR: dotProduct() wrong value");

		// Test that dotProduct returns the correct value for acute angle
		assertEquals(11, v1.dotProduct(v4) , DELTA, "dotProduct wrong value for acute angle");

		// Test that dotProduct returns the correct value for obtuse angle
		assertEquals(0, v1.dotProduct(v6) + 3, DELTA, "dotProduct wrong value for obtuse angle");

		// =============== Boundary Values Tests ==================
		// TC11: Test that dotProduct returns zero for orthogonal vectors
		assertEquals(0, v1.dotProduct(v3), DELTA, "ERROR: dotProduct() for orthogonal vectors is not zero");

		//  Test that dotProduct returns the correct value for unit vector
		assertEquals(1, v1.dotProduct(v5), DELTA, "ERROR: dotProduct() wrong for unit size vector");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		// Perform cross product operation
		Vector vr = v1.crossProduct(v3);

		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that the result length of cross product is correct
		assertEquals(vr.length(), v1.length() * v3.length(), DELTA, "ERROR: crossProduct() wrong result length");

		//  Test that the result of cross product is orthogonal to the first
		// operand
		assertEquals(0, vr.dotProduct(v1), "ERROR: crossProduct() result is not orthogonal to its 1st operand");

		//  Test that the result of cross product is orthogonal to the second
		// operand
		assertEquals(0, vr.dotProduct(v3), "ERROR: crossProduct() result is not orthogonal to its 2nd operand");

		// =============== Boundary Values Tests ==================
		// TC11: Test that cross product of parallel vectors throws an exception
		assertThrows(Exception.class, () -> v1.crossProduct(v2),
				"ERROR: crossProduct() for parallel vectors does not throw an exception");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		// Normalize vector
		Vector u = v1.normalize();
		boolean isSameDirection = true;
		if (v1.dotProduct(u) < 0) {
			isSameDirection = false;
		}

		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that the normalized vector is a unit vector
		assertEquals(1, u.length(), DELTA, "ERROR: the normalized vector is not a unit vector");

		// =============== Boundary Values Tests ==================
		// TC11: Test that the normalized vector is parallel to the original one
		assertThrows(Exception.class, () -> v1.crossProduct(u),
				"ERROR: the normalized vector is not parallel to the original one");

		// TC12: Test that the normalized vector is not opposite to the original one
		assertTrue(isSameDirection, "ERROR: the normalized vector is opposite to the original one");
	}
}
