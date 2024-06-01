package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;

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
     * @throws IllegalArgumentException if the three points are collinear or if any of the points are null
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }
    
    /**
     * Finds the intersection points between a given ray and the Triangle.
     * 
     * @param ray the ray to intersect with the Triangle
     * @return a list of intersection points, or null if there are no intersections
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        // Implementation goes here
        return null; // Returning null for now as a placeholder
    }

}
