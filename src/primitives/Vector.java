package primitives;

/**
 * Represents a vector in three-dimensional space.
 */
public class Vector extends Point {

	/**
	 * Constructs a new vector with the specified coordinates.
	 *
	 * @param x The x-coordinate of the vector.
	 * @param y The y-coordinate of the vector.
	 * @param z The z-coordinate of the vector.
	 * @throws IllegalArgumentException if the vector has zero length.
	 */
	public Vector(double x, double y, double z) {
		super(x, y, z);
		if (xyz.equals(Double3.ZERO)) {
			throw new IllegalArgumentException("Invalid parameter: Vector cannot have zero length3");
		}
	}

	/**
	 * Constructs a new vector with the specified coordinates as a Double3 object.
	 *
	 * @param xyz The coordinates of the vector as a Double3 object.
	 * @throws IllegalArgumentException if the vector has zero length.
	 */
	public Vector(Double3 xyz) {
		super(xyz);
		if (xyz.equals(Double3.ZERO)) {
			throw new IllegalArgumentException("Invalid parameter: Vector cannot have zero length");
		}
	}

	/**
	 * Checks if this vector is equal to another object.
	 *
	 * @param obj The object to compare to.
	 * @return true if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return obj instanceof Vector other && super.equals(other);
	}

	/**
	 * Returns a string representation of this vector.
	 *
	 * @return A string representation of this vector.
	 */
	@Override
	public String toString() {
		return "->" + super.toString();
	}

	/**
	 * Calculates the square of the length of this vector.
	 *
	 * @return The square of the length of this vector.
	 */
	public double lengthSquared() {
		return dotProduct(this);
	}

	/**
	 * Calculates the length of this vector.
	 *
	 * @return The length of this vector.
	 */
	public double length() {
		return Math.sqrt(lengthSquared());
	}

	/**
	 * Adds another vector to this vector, returning a new vector.
	 *
	 * @param v The vector to add.
	 * @return The resulting vector after adding the other vector.
	 */
	public Vector add(Vector v) {
		return new Vector(this.xyz.add(v.xyz));
	}

	/**
	 * Scales this vector by a scalar value, returning a new vector.
	 *
	 * @param scalar The scalar value to scale by.
	 * @return The resulting scaled vector.
	 */
	public Vector scale(double scalar) {
		return new Vector(xyz.scale(scalar));
	}

	/**
	 * Calculates the dot product of this vector with another vector.
	 *
	 * @param v The other vector.
	 * @return The dot product of this vector with the other vector.
	 */
	public double dotProduct(Vector v) {
		return xyz.d1 * v.xyz.d1 + xyz.d2 * v.xyz.d2 + xyz.d3 * v.xyz.d3;
	}

	/**
	 * Calculates the cross product of this vector with another vector.
	 *
	 * @param v The other vector.
	 * @return The cross product of this vector with the other vector.
	 */
	public Vector crossProduct(Vector v) {
		return new Vector(xyz.d2 * v.xyz.d3 - xyz.d3 * v.xyz.d2, xyz.d3 * v.xyz.d1 - xyz.d1 * v.xyz.d3,
				xyz.d1 * v.xyz.d2 - xyz.d2 * v.xyz.d1);
	}

	public double getX() {
		return this.xyz.d1;
	}

	public double getY() {
		return this.xyz.d2;
	}

	public double getZ() {
		return this.xyz.d3;
	}

	/**
	 * Normalizes this vector, returning a new vector with length 1.
	 *
	 * @return The normalized vector.
	 */
	public Vector normalize() {
		return scale(1 / length());
	}
}
