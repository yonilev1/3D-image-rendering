package primitives;

/**
 * Represents a point in three-dimensional space.
 */
public class Point {
	/** The coordinates of the point. */
	final protected Double3 xyz;

	/** The zero point, representing the origin of the coordinate system. */
	public static Point ZERO = new Point(Double3.ZERO);

	/**
	 * Constructs a new point with the specified coordinates.
	 *
	 * @param x The x-coordinate of the point.
	 * @param y The y-coordinate of the point.
	 * @param z The z-coordinate of the point.
	 */
	public Point(double x, double y, double z) {
		this.xyz = new Double3(x, y, z);
	}

	/**
	 * Constructs a new point with the specified coordinates as a Double3 object.
	 *
	 * @param xyz The coordinates of the point as a Double3 object.
	 */
	public Point(Double3 xyz) {
		this.xyz = xyz;
	}

	/**
	 * Checks if this point is equal to another object.
	 *
	 * @param obj The object to compare to.
	 * @return true if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return obj instanceof Point other && xyz.equals(other.xyz);
	}

	/**
	 * Returns a string representation of this point.
	 *
	 * @return A string representation of this point.
	 */
	@Override
	public String toString() {
		return "" + xyz;
	}

	/**
	 * Adds a vector to this point, returning a new point.
	 *
	 * @param v The vector to add.
	 * @return The resulting point after adding the vector.
	 */
	public Point add(Vector v) {
		return new Point(xyz.add(v.xyz));
	}

	/**
	 * Subtracts another point from this point, returning a vector.
	 *
	 * @param p The point to subtract.
	 * @return The resulting vector after subtracting the other point.
	 */
	public Vector subtract(Point p) {
		return new Vector(xyz.subtract(p.xyz));
	}

	/**
	 * Calculates the Euclidean distance between this point and another point.
	 *
	 * @param p The other point.
	 * @return The Euclidean distance between this point and the other point.
	 */
	public double distance(Point p) {
		return Math.sqrt(distanceSquared(p));
	}

	/**
	 * Calculates the square of the Euclidean distance between this point and
	 * another point.
	 *
	 * @param p The other point.
	 * @return The square of the Euclidean distance between this point and the other
	 *         point.
	 */
	public double distanceSquared(Point p) {
		double deltaX = this.xyz.d1 - p.xyz.d1;
		double deltaY = this.xyz.d2 - p.xyz.d2;
		double deltaZ = this.xyz.d3 - p.xyz.d3;
		return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
	}
}
