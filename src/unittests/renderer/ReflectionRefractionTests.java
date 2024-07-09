/**
 * 
 */
package unittests.renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	/** Scene for the tests */
	private final Scene scene = new Scene("Test scene");
	/** Camera builder for the tests with triangles */
	private final Camera.Builder cameraBuilder = Camera.getBuilder()
			.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)).setRayTracer(new SimpleRayTracer(scene));

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheres() {
		scene.geometries.add(
				new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(100).setKT(0.3)),
				new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED))
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)));
		scene.lights.add(new SpotLight(new Color(1000, 600, 0), new Vector(-1, -1, -2), new Point(-100, -100, 500))
				.setKL(0.0004).setKQ(0.0000006));

		cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(150, 150)
				.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)).build().renderImage().writeToImage();
	}

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheresOnMirrors() {
		scene.geometries.add(
				new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)).setMaterial(
						new Material().setKD(0.25).setKS(0.25).setShininess(20).setKT(new Double3(0.5, 0, 0))),
				new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20))
						.setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000))
						.setEmission(new Color(20, 20, 20)).setMaterial(new Material().setKR(1)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)).setEmission(new Color(20, 20, 20))
						.setMaterial(new Material().setKR(new Double3(0.5, 0, 0.4))));
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Vector(-1, -1, -4), new Point(-750, -750, -150))
				.setKL(0.00001).setKQ(0.000005));

		cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000).setVpSize(2500, 2500)
				.setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500)).build().renderImage()
				.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		scene.geometries.add(
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30).setKT(0.6)));
		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Vector(0, 0, -1), new Point(60, 50, 0)).setKL(4E-5)
				.setKQ(2E-7));

		cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(200, 200)
				.setImageWriter(new ImageWriter("refractionShadow", 600, 600)).build().renderImage().writeToImage();
	}
	
	/** Geometry combination including refraction and reflection */
	@Test
	public void geometryCombinationTest() {
		Camera.Builder camera = Camera.getBuilder().setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)).setRayTracer(new SimpleRayTracer(scene));

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		
		scene.geometries.add( //
				new Sphere(new Point(10,10, -50), 10d).setEmission(new Color(55,90,10))
				.setMaterial(new Material().setKD(0.001).setKS(0.05).setShininess(10).setKR(0.8)),
				new Sphere(new Point(0,0, -50), 10d).setEmission(new Color(0,10,100))
				.setMaterial(new Material().setKD(0.001).setKS(0.05).setShininess(10).setKT(0.6)),
				new Sphere(new Point(-20,-20, -50), 10d).setEmission(new Color(0,190,0)) 
				.setMaterial(new Material().setKD(0.001).setKS(0.05).setShininess(10).setKR(0.6)),
				new Sphere(new Point(-40,-40, -50), 10d).setEmission(new Color(255,190,0))
				.setMaterial(new Material().setKD(0.001).setKS(0.05).setShininess(10).setKT(0.6)),
				new Sphere(new Point(-60,-60, -50), 10d).setEmission(new Color(200,0,100))
				.setMaterial(new Material().setKD(0.001).setKS(0.05).setShininess(10).setKR(0.6)),
				new Sphere(new Point(-80,-80, -50), 10d).setEmission(new Color(55,90,10))
				.setMaterial(new Material().setKD(0.001).setKS(0.05).setShininess(10).setKT(0.6)),
				
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)), //
						
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)), //
						
				new Sphere(new Point(60, 50, -50),30d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30).setKR(0.6)),
		
		        new Sphere(new Point(-35, 20, 10),30d).setEmission(new Color(RED)) //
		          .setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30).setKT(0.7)));
	
		scene.lights.add(new SpotLight(new Color(300, 500, 300), new Vector(0,0,-1), new Point(0, 0, 100)) //
				.setKL(0.000001).setKQ(0.00001));
		
		camera.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(200, 200)
		.setImageWriter(new ImageWriter("GeometryCombination", 600, 600)).build().renderImage()
		.writeToImage();
	}
	/** Geometry combination including refraction and reflection */
	@Test
	public void coloredCubeTest() {
	    Camera.Builder camera1 = Camera.getBuilder()
	    		.setDirection(new Vector(-1, -1, -1), new Vector(-1, -1, 2))
	            .setRayTracer(new SimpleRayTracer(scene));
	    Camera.Builder camera2 = Camera.getBuilder()
	    		.setDirection(new Vector(1, 1, -1), new Vector(1, 1, 2))
	            .setRayTracer(new SimpleRayTracer(scene));
	    Camera.Builder camera3 = Camera.getBuilder()
	    		.setDirection(new Vector(0, 0, -1), new Vector(1, 0, 0))
	            .setRayTracer(new SimpleRayTracer(scene));
	    Camera.Builder camera4 = Camera.getBuilder()
	    		.setDirection(new Vector(-1, 0, -1), new Vector( -1, 0,1 ))
	            .setRayTracer(new SimpleRayTracer(scene));
	    Camera.Builder camera5 = Camera.getBuilder()
	    		.setDirection(new Vector(-1, -1, 0), new Vector( 0, 0,1 ))
	            .setRayTracer(new SimpleRayTracer(scene));
	    

	    scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.1));
	   final Material sphereMaterial =new Material().setKD(0.05).setKS(0.25).setShininess(20)
			                                         .setKR(0.8);
	   final double faceKT = 0.35;
	   final double SRadius = 50d;
	   final Color sphereColor = new Color(BLACK).reduce(2);

	    // Define the eight vertices of the cube
	    Point v0 = new Point(-50, -50, -50);
	    Point v1 = new Point(50, -50, -50);
	    Point v2 = new Point(50, 50, -50);
	    Point v3 = new Point(-50, 50, -50);
	    Point v4 = new Point(-50, -50, 50);
	    Point v5 = new Point(50, -50, 50);
	    Point v6 = new Point(50, 50, 50);
	    Point v7 = new Point(-50, 50, 50);
	    // Apex of the pyramid
	    Point apex = new Point(0, 0, -130);

	    scene.geometries.add(

	            // Sides of the pyramid
	            new Triangle(v0, v1, apex)
	                .setEmission(new Color(0, 255, 0)) // green color
	                .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(70)),
	            
	            new Triangle(v1, v2, apex)
	                .setEmission(new Color(0, 0, 255)) // Blue color
	                .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(70)),
	            
	            new Triangle(v2, v3, apex)
	                .setEmission(new Color(255,  255,0)) //yellow color
	                .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(70)),
	            
	            new Triangle(v3, v0, apex)
	                .setEmission(new Color(255, 0, 0)) // red color
	                .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(70))
	    );

	    scene.geometries.add(
	            //   Top face (red)
	            new Polygon(v4, v5, v6, v7).setEmission(new Color(255, 0, 0))
	                .setMaterial(new Material().setKD(1).setKS(0.5).setKT(faceKT).setShininess(70)),

	            //  Right face (green)
	            new Polygon(v3, v2, v6, v7).setEmission(new Color(0, 255, 0))
	                .setMaterial(new Material().setKD(1).setKS(0.5).setKT(faceKT).setShininess(70)),

	            //   Front face (blue)
	            new Polygon(v1, v5, v6, v2).setEmission(new Color(0, 0, 255))
	                .setMaterial(new Material().setKD(0.5).setKS(0.5).setKT(faceKT).setShininess(70)),

	            //    Back face (yellow)
	            new Polygon(v0, v4, v7, v3).setEmission(new Color(255, 255, 0))
	                .setMaterial(new Material().setKD(0).setKS(0.5).setKT(faceKT).setShininess(70)),

	            //  Bottom face (cyan)
	            new Polygon(v0, v1, v2, v3).setEmission(new Color(0, 255, 255))
	                .setMaterial(new Material().setKD(0).setKS(0.5).setKT(faceKT).setShininess(70)),

	            //  Left face (magenta)
	            new Polygon(v0, v1, v5, v4).setEmission(new Color(255, 0, 255))
	                .setMaterial(new Material().setKD(0).setKS(0.5).setKT(faceKT).setShininess(70)),
	                
	            // plane under the geometries 
	            new Plane(new Point(0, 0, -300), new Vector(0, 0, 1)).setMaterial(new Material().setKD(0.9))
			        .setEmission(new Color(blue).reduce(4)),
			        
	                //inside Sphere
	                new Sphere(new Point(0, 0, 0), 30d).setEmission(new Color(200, 200, 200)).
	                setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)),
	                
	                //Upper Sphere
	                new Sphere(new Point(0, 0, 180), SRadius).setEmission(sphereColor).
	                setMaterial(sphereMaterial),
	                
	                //Right Sphere
	                new Sphere(new Point(0, 170, 0), SRadius).setEmission(sphereColor).
	                setMaterial(sphereMaterial),
	                
	                //Front Sphere
	                new Sphere(new Point(170, 0, 0), SRadius).setEmission(sphereColor).
	                setMaterial(sphereMaterial),
	                
	                //Down Sphere
	                new Sphere(new Point(0, 0, -200), SRadius).setEmission(sphereColor).
	                setMaterial(sphereMaterial),
	                
	                //Left Sphere
	                new Sphere(new Point(0, -170, 0), SRadius).setEmission(sphereColor).
	                setMaterial(sphereMaterial),
	                
	                //Back Sphere
	                new Sphere(new Point(-170, 0, 0), SRadius).setEmission(sphereColor.add()).
	                setMaterial(sphereMaterial)
	                
	    );

	  scene.lights.add(new PointLight(new Color(250, 250, 250), new Point(200, 200, 200))
	            .setKL(0.00001).setKQ(0.00001));
	    
	  scene.lights.add(new PointLight(new Color(155, 155, 155), new Point(-200, -200, -200))
	          .setKL(0.0001).setKQ(0.00001));

	    camera1.setLocation(new Point(500, 500, 500)).setVpDistance(300).setVpSize(200,200)
	           .setImageWriter(new ImageWriter("ColoredCube", 600,600)).build().renderImage()
	           .writeToImage();
	    camera2.setLocation(new Point(-500, -500, 500)).setVpDistance(200).setVpSize(200,200)
               .setImageWriter(new ImageWriter("ColoredCubeBACK", 600,600)).build().renderImage()
               .writeToImage();
	    camera3.setLocation(new Point(0, 0, 500)).setVpDistance(100).setVpSize(200,200)
	           .setImageWriter(new ImageWriter("ColoredCubeDOWN", 600,600)).build().renderImage()
	           .writeToImage();
	    camera4.setLocation(new Point(350, 0, 400)).setVpDistance(300).setVpSize(200,200)
	           .setImageWriter(new ImageWriter("ColoredCubeFRONT", 600,600)).build().renderImage()
	           .writeToImage();
	    camera5.setLocation(new Point(210, 120, -51)).setVpDistance(100).setVpSize(200,200)
	           .setImageWriter(new ImageWriter("ColoredCubeTriangle", 600,600)).build().renderImage()
	           .writeToImage();
	    
	}


	} 
	
	 
