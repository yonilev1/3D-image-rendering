package renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import renderer.Camera;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class DOF {
	
	public List<Ray>constructRayWithDOF(Point pij,Point p0,double focalDistance, double aperture)
	{
        List<Ray> rays = new ArrayList<>();
		Point focalPoint = pij.add(pij.subtract(p0).normalize().scale(focalDistance));
	    Random rand = new Random();
	    int numRays = 10; // Number of rays to sample within the aperture for DOF effect
	    for (int k = 0; k < numRays; k++) {
	    	double angle = 2 * Math.PI * rand.nextDouble();
	        double radius = aperture * Math.sqrt(rand.nextDouble());
	        double apertureX = radius * Math.cos(angle);
	        double apertureY = radius * Math.sin(angle);

	        Point randomAperturePoint = p0.add(vRight.scale(apertureX)).add(vUp.scale(apertureY));
	        Vector rayDirection = focalPoint.subtract(randomAperturePoint).normalize();
	        rays.add(new Ray(randomAperturePoint, rayDirection));
	        }

	    return rays;
	}
}
