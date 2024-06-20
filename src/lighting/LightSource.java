package lighting;

import primitives.*;

/**
 * The LightSource interface represents a light source in the scene.
 * Implementing classes must provide methods to calculate the intensity of the light
 * at a given point and the direction of the light from the source to the point.
 */
public interface LightSource {

    /**
     * Calculates the intensity (color) of the light at the specified point.
     *
     * @param p The point at which to calculate the light intensity.
     * @return The intensity of the light at the specified point.
     */
    public Color getIntensity(Point p);

    /**
     * Calculates the direction of the light from the source to the specified point.
     *
     * @param p The point to which the light direction is calculated.
     * @return The direction of the light as a vector.
     */
    public Vector getL(Point p);
}
