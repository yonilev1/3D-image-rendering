package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.*;
import primitives.*;
import static primitives.Util.*;
import scene.Scene;

/**
 * A simple implementation of the RayTracerBase class. This class provides basic
 * ray tracing functionality for a 3D scene.
 */
public class SimpleRayTracer extends RayTracerBase {

	/**
	 * Constructs a new SimpleRayTracer with the specified scene.
	 *
	 * @param scene The scene to be rendered.
	 */
	public SimpleRayTracer(Scene scene) {
		super(scene);
	}

	/**
	 * Traces a ray through the scene, computes the color at the intersection point
	 * (if any), and returns the background color of the scene if no intersection is
	 * found.
	 *
	 * @param ray The ray to trace through the scene.
	 * @return The color at the intersection point or the background color if no
	 *         intersection is found.
	 */
	@Override
	public Color traceRay(Ray ray) {
		// Find intersections of the ray with the scene geometries
		var intersections = scene.geometries.findGeoIntersectionsHelper(ray);

		// If no intersections found, return the background color of the scene
		return intersections == null ? scene.background //
				: calcColor(ray.findClosestGeoPoint(intersections), ray);
	}

	/**
	 * Private method to calculate the color at a given point in the scene. For now,
	 * returns the ambient light intensity of the scene combined with local effects.
	 *
	 * @param intersection The intersection point to calculate the color for.
	 * @param ray          The ray that intersects with the point.
	 * @return The color at the specified intersection point.
	 */
	private Color calcColor(GeoPoint intersection, Ray ray) {
		return scene.ambientLight.getIntensity().add(calcLocalEffects(intersection, ray));
	}

	/**
	 * Calculates the local effects (diffuse and specular) at the intersection
	 * point.
	 *
	 * @param gp  The intersection point.
	 * @param ray The ray that intersects with the point.
	 * @return The color resulting from the local effects at the intersection point.
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray) {
		Vector v = ray.getDirection();
		Vector n = gp.geometry.getNormal(gp.point);
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0)
			return Color.BLACK;

		Color color = gp.geometry.getEmission();
		Material material = gp.geometry.getMaterial();

		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(gp.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) {
				Color iL = lightSource.getIntensity(gp.point);
				color = color.add(iL.scale(calcDiffusive(material, nl) //
						.add(calcSpecular(material, n, l, nl, v))));
			}
		}
		return color;
	}

	/**
	 * Calculates the diffuse reflection component at the intersection point.
	 *
	 * @param material The material properties of the intersected geometry.
	 * @param nl       The dot product of the normal and light direction vectors.
	 * @return The diffuse reflection component.
	 */
	private Double3 calcDiffusive(Material material, double nl) {
		return material.kD.scale(nl > 0 ? nl : -nl);
	}

	/**
	 * Calculates the specular reflection component at the intersection point.
	 *
	 * @param material The material properties of the intersected geometry.
	 * @param n        The normal vector at the intersection point.
	 * @param l        The light direction vector.
	 * @param nl       The dot product of the normal and light direction vectors.
	 * @param v        The view direction vector.
	 * @return The specular reflection component.
	 */
	private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
		Vector r = l.subtract(n.scale(2 * nl));
		double rv = alignZero(-r.dotProduct(v));

		return rv <= 0 ? Double3.ZERO : material.kS.scale(Math.pow(rv, material.shininess));
	}
}
