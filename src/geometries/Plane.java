package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.*;

/**
 * Represents a plane in three-dimensional space.
 */
public class Plane extends Geometry {

	/** A point on the plane. */
	private final Point pointOnPlane;

	/** The normal vector to the plane. */
	private final Vector normalVector;

	/**
	 * Constructs a plane passing through three points.
	 *
	 * @param p1 The first point.
	 * @param p2 The second point.
	 * @param p3 The third point.
	 */
	public Plane(Point p1, Point p2, Point p3) {
		Vector v1 = p2.subtract(p1);
		Vector v2 = p3.subtract(p1);
		this.pointOnPlane = p1;
		this.normalVector = v1.crossProduct(v2).normalize();
	}

	/**
	 * Constructs a plane with the specified point on the plane and normal vector.
	 *
	 * @param pointOnPlane The point on the plane.
	 * @param normalVector The normal vector to the plane.
	 */
	public Plane(Point pointOnPlane, Vector normalVector) {
		this.pointOnPlane = pointOnPlane;
		this.normalVector = normalVector.normalize();
	}

	/**
	 * Returns the normal vector to the plane.
	 * 
	 * @param pointOnSurface (unused) A point on the surface of the plane.
	 * @return The normal vector to the plane.
	 */
	@Override
	public Vector getNormal(Point pointOnSurface) {
		return normalVector;
	}

	/**
	 * Returns the normal vector to the plane.
	 * 
	 * @return The normal vector to the plane.
	 */
	public Vector getNormal() {
		return normalVector;
	}

	/**
	 * Finds the intersection points between the given ray and the plane.
	 * 
	 * @param ray the ray to intersect with the plane
	 * @return a list of intersection points, or null if there are no intersections
	 */
	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

		Point rayHead = ray.getHead(); // Origin of the ray
		if (rayHead.equals(pointOnPlane))
			return null;

		double s = normalVector.dotProduct(ray.getDirection());
		if (isZero(s))
			return null;

		// Calculate the parameter t for the ray-plane intersection
		double t = alignZero(normalVector.dotProduct(pointOnPlane.subtract(rayHead)) / s);
		// If t is not positive, there is no intersection
		// If t is positive, the intersection point is in the ray's direction
		return t <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
	}

}
