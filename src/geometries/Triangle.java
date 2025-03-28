package geometries;

import java.util.*;

import primitives.*;
import primitives.Vector;

/**
 * Represents a triangle in three-dimensional space.
 */
public class Triangle extends Polygon {

	/**
	 * Constructs a triangle with the specified vertices.
	 * 
	 * @param p1 the first vertex of the triangle
	 * @param p2 the second vertex of the triangle
	 * @param p3 the third vertex of the triangle
	 * @throws IllegalArgumentException if the three points are collinear or if any
	 *                                  of the points are null
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
	}

	/**
	 * Finds the intersection points between the given ray and the triangle.
	 * 
	 * @param ray the ray to intersect with the triangle
	 * @return a list of intersection points, or null if there are no intersections
	 */
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		List<Point> intersections = plane.findIntersections(ray);
		// Check if the ray intersects the plane of the triangle
		if (intersections == null)
			return null;

		// Retrieve the vertices of the triangle
		Point p0 = vertices.getFirst();
		Point p1 = vertices.get(1);
		Point p2 = vertices.getLast();

		// Retrieve the direction vector and head point of the ray
		Vector rayDirection = ray.getDirection();
		Point rayPoint = ray.getHead();

		// Calculate vectors representing edges of the triangle
		Vector v1 = p0.subtract(rayPoint);
		Vector v2 = p1.subtract(rayPoint);
		// Calculate normal vectors to the triangle's edges
		Vector n1 = v1.crossProduct(v2).normalize();
		// Calculate dot products between the normal vectors and the ray direction
		double d1 = Util.alignZero(n1.dotProduct(rayDirection));
		// Check if the ray does not intersects the triangle.
		if (d1 == 0)
			return null;

		Vector v3 = p2.subtract(rayPoint);
		Vector n2 = v2.crossProduct(v3).normalize();
		double d2 = Util.alignZero(n2.dotProduct(rayDirection));
		// Check if the ray does not intersects the triangle
		if (d1 * d2 <= 0)
			return null;

		Vector n3 = v3.crossProduct(v1).normalize();
		double d3 = Util.alignZero(n3.dotProduct(rayDirection));
		// Check if the ray does not intersects the triangle
		if (d1 * d3 <= 0)
			return null;

		return List.of(new GeoPoint(this, intersections.getFirst()));
	}

}
