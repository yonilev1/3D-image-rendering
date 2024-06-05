package primitives;

import static primitives.Util.*;

/**
 * Represents a ray in three-dimensional space.
 */
public class Ray {

	/** The starting point (head) of the ray. */
	private final Point head;

	/** The direction vector of the ray. */
	private final Vector direction;

	/**
	 * Constructs a new ray with the specified starting point and direction.
	 *
	 * @param head      The starting point (head) of the ray.
	 * @param direction The direction vector of the ray.
	 */
	public Ray(Point head, Vector direction) {
		this.head = head;
		this.direction = direction.normalize();
	}

	/**
	 * Checks if this ray is equal to another object.
	 *
	 * @param obj The object to compare to.
	 * @return true if the objects are equal, false otherwise.
	 */
	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		return obj instanceof Ray other && //
				this.head.equals(other.head) && //
				this.direction.equals(other.direction);
	}

	/**
	 * Returns a string representation of this ray.
	 *
	 * @return A string representation of this ray.
	 */
	@Override
	public String toString() {
		return "Ray:" + head + direction;
	}

	/**
	 * Returns the head point of the vector.
	 * 
	 * @return the head point of the vector
	 */
	public Point getHead() {
		return this.head;
	}

	/**
	 * Returns the direction of the vector.
	 * 
	 * @return the direction of the vector
	 */
	public Vector getDirection() {
		return this.direction;
	}

	/**
	 * Calculates a point on the ray at a distance t from the ray's head.
	 *
	 * @param t the distance from the head of the ray
	 * @return the point on the ray at distance t, or the head if t is zero
	 */
	public Point getPoint(double t) {
		return isZero(t) ? head : head.add(direction.scale(t));
	}
}
