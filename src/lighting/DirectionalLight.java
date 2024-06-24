package lighting;

import primitives.*;

/**
 * The DirectionalLight class represents a directional light source in the
 * scene. A directional light source has a specific direction and its intensity
 * does not diminish with distance.
 */
public class DirectionalLight extends Light implements LightSource {

	/** vector of directional light */
	private Vector direction;

	/**
	 * Constructs a DirectionalLight with the specified intensity and direction.
	 *
	 * @param intensity The intensity (color) of the directional light.
	 * @param direction The direction of the directional light.
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalize();
	}

	/**
	 * Returns the intensity (color) of the light at the specified point. For a
	 * directional light, the intensity is the same everywhere.
	 *
	 * @param p The point at which to calculate the light intensity.
	 * @return The intensity of the light.
	 */
	@Override
	public Color getIntensity(Point p) {
		return getIntensity();
	}

	/**
	 * Returns the direction of the light from the light source to the specified
	 * point. For a directional light, the direction is the same everywhere.
	 *
	 * @param p The point to which the light direction is calculated.
	 * @return The direction of the light as a vector.
	 */
	@Override
	public Vector getL(Point p) {
		return direction;
	}
}
