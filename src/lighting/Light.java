package lighting;

import primitives.*;

/**
 * The Light abstract class represents a light source in the scene. It
 * encapsulates the intensity (color) of the light.
 */
abstract class Light {

    /** The intensity (color) of the light. */
    protected final Color intensity;

    /**
     * Constructor for the Light class. Initializes the light with the given
     * intensity.
     * 
     * @param intensity The intensity (color) of the light.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns the intensity (color) of the light.
     * 
     * @return The intensity of the light.
     */
    public Color getIntensity() {
        return this.intensity;
    }

}
