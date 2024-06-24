package geometries;

import java.util.List;
import primitives.*;

/**
 * Abstract class representing an intersectable geometry object.
 */
public abstract class Intersectable {

    /**
     * Finds the intersection points between a given ray and the geometry.
     *
     * @param ray the ray to intersect with the geometry
     * @return a list of intersection points, or null if there are no intersections
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Represents a point of intersection between a ray and a geometry.
     */
    public static class GeoPoint {
        /**
         * The geometry object at the intersection point.
         */
        public Geometry geometry;
        
        /**
         * The point of intersection.
         */
        public Point point;

        /**
         * Constructs a GeoPoint with the specified geometry and point.
         *
         * @param geometry the geometry object at the intersection
         * @param point    the point of intersection
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * Checks if this GeoPoint is equal to another object.
         *
         * @param obj The object to compare to.
         * @return true if the objects are equal, false otherwise.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            return obj instanceof GeoPoint other && geometry.equals(other.geometry) //
                    && point.equals(other.point);
        }

        /**
         * Returns a string representation of this GeoPoint.
         *
         * @return A string representation of this GeoPoint.
         */
        @Override
        public String toString() {
            return "(" + geometry + ", " + point + ")";
        }
    }

    /**
     * Finds the intersection points between a given ray and the geometry, including
     * additional information.
     *
     * @param ray the ray to intersect with the geometry
     * @return a list of GeoPoints, or null if there are no intersections
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * Helper method to find the intersection points between a given ray and the
     * geometry. This method is intended to be implemented by subclasses.
     *
     * @param ray the ray to intersect with the geometry
     * @return a list of GeoPoints, or null if there are no intersections
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}
