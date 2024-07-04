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
	 * The maximum recursion level for color calculation.
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 7;

	/**
	 * The minimum attenuation factor for color calculation.
	 */
	private static final double MIN_CALC_COLOR_K = 0.007;

	/**
	 * The initial attenuation factor for color calculation.
	 */
	private static final double INITIAL_K = 1.0;

	/**
	 * Constructs a new SimpleRayTracer with the specified scene.
	 *
	 * @param scene The scene to be rendered.
	 */
	public SimpleRayTracer(Scene scene) {
		super(scene);
	}

	/**
	 * Finds the closest intersection point of a ray with the geometries in the
	 * scene.
	 *
	 * @param ray The ray for which to find the closest intersection.
	 * @return The closest intersection point (GeoPoint) of the ray with the
	 *         geometries, or null if no intersections are found.
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
	}

	/**
	 * Traces a ray in the scene and returns the color of the closest intersection
	 * point.
	 *
	 * @param ray The ray to trace.
	 * @return The color of the closest intersection point, or the background color
	 *         if no intersections are found.
	 */
	@Override
	public Color traceRay(Ray ray) {
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
	}

	/**
	 * Calculates the color at a given point in the scene, taking into account the
	 * ambient light intensity of the scene combined with local effects.
	 *
	 * @param gp  The intersection point to calculate the color for.
	 * @param ray The ray that intersects with the point.
	 * @return The color at the specified intersection point.
	 */
	private Color calcColor(GeoPoint gp, Ray ray) {
		return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K)).add(scene.ambientLight.getIntensity());
	}

	/**
	 * Calculates the color at a given point in the scene, taking into account the
	 * reflection and refraction effects.
	 *
	 * @param gp    The intersection point to calculate the color for.
	 * @param ray   The ray that intersects with the point.
	 * @param level The recursion level.
	 * @param k     The attenuation factor.
	 * @return The color at the specified intersection point.
	 */
	private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(gp, ray, k);
		return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
	}

	/**
	 * Calculates the global effects (reflection and refraction) at a given point in
	 * the scene.
	 *
	 * @param gp    The intersection point to calculate the color for.
	 * @param ray   The ray that intersects with the point.
	 * @param level The recursion level.
	 * @param k     The attenuation factor.
	 * @return The color resulting from the global effects at the specified
	 *         intersection point.
	 */
	private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
		Vector n = gp.geometry.getNormal(gp.point);
		Vector v = ray.getDirection();
		Material material = gp.geometry.getMaterial();
		return calcGlobalEffects(constructRefractedRay(gp, v, n), material.kT, level, k)
				.add(calcGlobalEffects(constructRefractedRay(gp, v, n), material.kR, level, k));
	}

	/**
	 * Recursively calculates the color for the global effects (reflection and
	 * refraction).
	 *
	 * @param ray   The ray to trace.
	 * @param kx    The reflection/refraction coefficient.
	 * @param level The recursion level.
	 * @param k     The attenuation factor.
	 * @return The color resulting from the global effects.
	 */
	private Color calcGlobalEffects(Ray ray, Double3 kx, int level, Double3 k) {
		Double3 kkx = kx.product(k);
		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;
		GeoPoint gp = findClosestIntersection(ray);
		return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx).scale(kx));
	}

	/**
	 * Constructs a refracted ray based on the intersection point and incoming ray.
	 *
	 * @param gp The geometric point of intersection.
	 * @param v  The direction vector of the incoming ray.
	 * @param n  The normal vector at the intersection point.
	 * @return The refracted ray originating from the intersection point.
	 */
	private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
		return new Ray(gp.point, v, n);
	}

	/**
	 * Calculates the transparency factor at a given point in the scene by checking
	 * for obstructions between the light source and the point.
	 *
	 * @param gp The geometric point.
	 * @param ls The light source.
	 * @param l  The light direction vector.
	 * @param n  The normal vector at the intersection point.
	 * @return The transparency factor at the specified intersection point.
	 */
	private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n) {
		Vector lightDir = l.scale(-1);
		Ray lR = new Ray(gp.point, lightDir, n);

		var intersections = scene.geometries.findGeoIntersections(lR);
		if (intersections == null)
			return Double3.ONE;

		Double3 ktr = Double3.ONE;
		double distanceToLight = ls.getDistance(gp.point);
		for (GeoPoint intersectionPoint : intersections) {
			if (alignZero(intersectionPoint.point.distance(gp.point) - distanceToLight) <= 0) {
				ktr = ktr.product(intersectionPoint.geometry.getMaterial().kT);
				if (ktr.equals(Double3.ZERO))
					break;
			}
		}
		return ktr;
	}

	/**
	 * Calculates the local effects (diffuse and specular) at the intersection
	 * point.
	 *
	 * @param gp  The intersection point.
	 * @param ray The ray that intersects with the point.
	 * @param k   The attenuation factor.
	 * @return The color resulting from the local effects at the intersection point.
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
		Vector v = ray.getDirection();
		Vector n = gp.geometry.getNormal(gp.point);
		double nv = alignZero(n.dotProduct(v));
		Color color = gp.geometry.getEmission();
		if (nv == 0)
			return color;

		Material material = gp.geometry.getMaterial();
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(gp.point);
			double nl = alignZero(n.dotProduct(l));
			if ((nl * nv > 0)) {
				Double3 ktr = transparency(gp, lightSource, l, n);
				if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
					Color iL = lightSource.getIntensity(gp.point).scale(ktr);
					color = color.add(iL.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));
				}
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
		double mminusRV = alignZero(-r.dotProduct(v));
		return mminusRV <= 0 ? Double3.ZERO : material.kS.scale(Math.pow(mminusRV, material.shininess));
	}
}
