package primitives;

import static primitives.Util.*;

import java.util.List;

import geometries.Intersectable.GeoPoint;

/**
 * Represents a ray in three-dimensional space.
 */
public class Ray {

	/** The starting point (head) of the ray. */
	private final Point head;

	/** The direction vector of the ray. */
	private final Vector direction;

	/**
	 * Constructs a new ray with the specified starting point and direction. The
	 * direction vector is normalized for the ray (no need to transfer a normalized
	 * argument)
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
	 * Returns the starting point (head) of the ray.
	 *
	 * @return The starting point (head) of the ray.
	 */
	public Point getHead() {
		return this.head;
	}

	/**
	 * Returns the direction vector of the ray.
	 *
	 * @return The direction vector of the ray.
	 */
	public Vector getDirection() {
		return this.direction;
	}

	/**
	 * Calculates a point on the ray at a distance t from the ray's head.
	 *
	 * @param t the distance from the head of the ray.
	 * @return The point on the ray at distance t, or the head if t is zero.
	 */
	public Point getPoint(double t) {
		return isZero(t) ? head : head.add(direction.scale(t));
	}

	/**
	 * Finds the closest point to the ray's head from a list of points.
	 *
	 * @param points The list of points to search from.
	 * @return The closest point to the ray's head from the list, or null if the
	 *         list is empty.
	 */
	public Point findClosestPoint(List<Point> points) {
		return points == null || points.isEmpty() ? null
				: findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
	}

	/**
	 * Finds the closest GeoPoint to the ray's head from a list of GeoPoints.
	 *
	 * @param listOfPoints The list of GeoPoints to search from.
	 * @return The closest GeoPoint to the ray's head from the list, or null if the
	 *         list is empty.
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> listOfPoints) {
		if (listOfPoints == null || listOfPoints.isEmpty())
			return null;

		GeoPoint closestPoint = null; // Start by assuming the first point is closest
		double minDistance = Double.POSITIVE_INFINITY; // Initialize with a large value

		for (GeoPoint point : listOfPoints) {
			double distance = head.distance(point.point);
			if (distance < minDistance) {
				minDistance = distance;
				closestPoint = point;
			}
		}
		return closestPoint;
	}
}
