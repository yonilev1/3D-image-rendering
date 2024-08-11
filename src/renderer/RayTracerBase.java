package renderer;

import java.util.List;

import primitives.*;
import scene.Scene;

/**
 * Abstract base class for ray tracing in a 3D scene. This class is responsible
 * for tracing rays and determining the color at the intersection points.
 */
public abstract class RayTracerBase {

	/** The scene to be rendered */
	protected final Scene scene;

	/**
	 * Constructs a new RayTracerBase with the specified scene.
	 *
	 * @param scene The scene to be rendered.
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Traces a ray and determines the color at the intersection point. This method
	 * must be implemented by subclasses.
	 *
	 * @param ray The ray to trace.
	 * @return The color at the intersection point.
	 */
	public abstract Color traceRay(Ray ray);
	
	public abstract Color average_color_calculator(List<Ray> rays);
}
