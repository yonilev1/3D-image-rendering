package renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * DOF (Depth of Field) class represents the depth of field effect in the
 * camera. It is characterized by the aperture size and the focal distance.
 */
public class DOF {

	/**
     * random number.
     */
    private Random rand = new Random();

    /**
     * Radius of the camera's aperture.
     */
    private double aperture;

    /**
     * Distance to the focal plane.
     */
    private double focalDistance;
    
    private int numRays;

    /**
     * Default constructor.
     */
    public DOF() {
    }

    /**
     * Gets the focal distance.
     *
     * @return the focal distance
     */
    public double getFocalDistance() {
        return focalDistance;
    }
    
    /**
     * Gets the number of rays.
     *
     * @return the number of rays
     */
    public int getNumRays() {
        return numRays;
    }

    /**
     * Gets the aperture size.
     *
     * @return the aperture size
     */
    public double getAperture() {
        return aperture;
    }

    /**
     * Sets the focal distance.
     *
     * @param focalDistance the focal distance to set
     */
    public void setFocalDistance(double focalDistance) {
        this.focalDistance = focalDistance;
    }

    /**
     * Sets the aperture size.
     *
     * @param aperture the aperture size to set
     */
    public void setAperture(double aperture) {
        this.aperture = aperture;
    }
    
    /**
     * Sets the number of rays.
     *
     * @param numRays the number of rays to set
     */
    public void setNumRays(int numRays) {
        this.numRays = numRays;
    }

    /**
     * Constructs a list of rays for depth of field effect using a grid pattern with jitter.
     *
     * @param pij        the point on the view plane
     * @param thisCamera the camera object
     * @return the list of rays for depth of field effect
     */
    public List<Ray> constructRayWithDOF(Point pij, Camera thisCamera) {
        List<Ray> rays = new ArrayList<>();
        
        
        // Calculate the direction from the camera to the point on the view plane
        Vector Vij = pij.subtract(thisCamera.getCameraLocation()).normalize();
        
        // Calculate the distance to the focal plane
        double DF = (thisCamera.getVto().scale(focalDistance)).length();
        double dij = DF / (thisCamera.getVto().dotProduct(Vij));
        
        // Calculate the focal point using the direction vector and the distance to the focal plane
        Point focalPoint = thisCamera.getCameraLocation().add(Vij.scale(dij));
        
        if (thisCamera.getAdaptive() == true) {
        	Vector dir = focalPoint.subtract(pij).normalize();
        	rays.add(new Ray(pij,dir));
        	return rays;
        }
        
        int gridSize = (int) Math.sqrt(numRays);
        if (gridSize * gridSize < numRays) {
            gridSize++;
        }
        double gridSpacing = aperture * 2 / gridSize;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (rays.size() >= numRays) break;

                double apertureX = -aperture + gridSpacing * (i + 0.5);
                double apertureY = -aperture + gridSpacing * (j + 0.5);

                // Add random jitter to the grid points
                double jitterX = (rand.nextDouble() - 0.5) * gridSpacing;
                double jitterY = (rand.nextDouble() - 0.5) * gridSpacing;

                Point jitteredAperturePoint = thisCamera.getCameraLocation().add(thisCamera.getVright().scale(apertureX + jitterX))
                        .add(thisCamera.getVup().scale(apertureY + jitterY));
                Vector rayDirection = focalPoint.subtract(jitteredAperturePoint).normalize();
                rays.add(new Ray(jitteredAperturePoint, rayDirection));
            }
        }

        return rays;
    }
}
