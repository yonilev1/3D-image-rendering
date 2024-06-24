package primitives;

/**
 * The Material class represents the material properties of a geometric object.
 * It includes properties such as diffuse reflection, specular reflection, and shininess.
 */
public class Material {

    /** The diffuse reflection coefficient. */
    public Double3 kD = Double3.ZERO;

    /** The specular reflection coefficient. */
    public Double3 kS = Double3.ZERO;

    /** The shininess coefficient. */
    public int shininess = 0;

    /**
     * Sets the diffuse reflection coefficient.
     *
     * @param kd The diffuse reflection coefficient as a Double3.
     * @return The Material object itself for method chaining.
     */
    public Material setKD(Double3 kd) {
        this.kD = kd;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient.
     *
     * @param x The diffuse reflection coefficient as a double.
     * @return The Material object itself for method chaining.
     */
    public Material setKD(double x) {
        this.kD = new Double3(x);
        return this;
    }

    /**
     * Sets the specular reflection coefficient.
     *
     * @param ks The specular reflection coefficient as a Double3.
     * @return The Material object itself for method chaining.
     */
    public Material setKS(Double3 ks) {
        this.kS = ks;
        return this;
    }

    /**
     * Sets the specular reflection coefficient.
     *
     * @param x The specular reflection coefficient as a double.
     * @return The Material object itself for method chaining.
     */
    public Material setKS(double x) {
        this.kS = new Double3(x);
        return this;
    }

    /**
     * Sets the shininess coefficient.
     *
     * @param shininess The shininess coefficient.
     * @return The Material object itself for method chaining.
     */
    public Material setShininess(int shininess) {
        this.shininess = shininess;
        return this;
    }

}
