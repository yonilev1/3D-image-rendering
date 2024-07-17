package renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//import renderer.Camera;

//import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class DOF {
	private double aperture;   // Radius of the camera's aperture
    private double focalDistance ;  // Distance to the focal plane
    
	
	 public double getFocalDistance() {
	        return focalDistance;
	    }

	    public double getAperture() {
	        return aperture;
	    }
	    
	    public DOF() {
	    }
	    
	    public void setFocalDistance(double fd) {
	    	this.focalDistance = fd;
	    }
	    public void setAperture(double aperture) {
	    	this.aperture = aperture;
	    }
	    
	    public List<Ray> constructRayWithDOF(Point pij ,Camera thisCamera){
	    List<Ray> rays = new ArrayList<>();
		Point focalPoint = pij.add(pij.subtract(thisCamera.getCameraLocation()).normalize().scale(focalDistance));
	    Random rand = new Random();
	    int numRays = 10; // Number of rays to sample within the aperture for DOF effect
	    for (int k = 0; k < numRays; k++) {
	    	double angle = 2 * Math.PI * rand.nextDouble();
	        double radius = aperture * Math.sqrt(rand.nextDouble());
	        double apertureX = radius * Math.cos(angle);
	        double apertureY = radius * Math.sin(angle);

	        Point randomAperturePoint = thisCamera.getCameraLocation().add(thisCamera.getVright().scale(apertureX)).add(thisCamera.getVup().scale(apertureY));
	        Vector rayDirection = focalPoint.subtract(randomAperturePoint).normalize();
	        rays.add(new Ray(randomAperturePoint, rayDirection));
	        }

	   return rays;
	   }
	
}
