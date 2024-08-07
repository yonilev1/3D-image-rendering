package unittests.renderer;

import org.junit.jupiter.api.Test;
import static java.awt.Color.*;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;
import java.util.Random;


class projectTest {

	/** Scene for the tests */
	private final Scene scene = new Scene("Test scene");
	/** Camera builder for the tests with triangles */
	@Test
	public void SnowMan() {
		
		/** Camera builder for the tests with triangles */
		//scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.1));

		final Material sphereMaterial = new Material().setKD(0.05).setKS(0.25).setShininess(1000);
		final Material Material =new Material().setKD(0.05).setKS(0.005).setShininess(70);

		final Color sphereColor = new Color(BLACK).reduce(2);
		final Color woodColor = new Color(139,69,19).reduce(1);
		final Color forestColor = new Color(34,139,34).reduce(1);
		
		
		// Define the eight vertices of the cube
		Point v0 = new Point(-10, 5, 195);
		Point v1 = new Point(10, 5, 195);
		Point v2 = new Point(10, 5, 175);
		Point v3 = new Point(-10, 5, 175);
		
		// Apex of the pyramid
		Point apex = new Point(0, 100, 162.5);

		scene.geometries.add(
				// Sides of the pyramid
				new Triangle(v0, v1, apex).setEmission(new Color(orange)) 
						.setMaterial(Material),
				new Triangle(v1, v2, apex).setEmission(new Color(orange)) 
						.setMaterial(Material),
				new Triangle(v2, v3, apex).setEmission(new Color(orange)) 
						.setMaterial(Material),
				new Triangle(v3, v0, apex).setEmission(new Color(orange)) 
						.setMaterial(Material));
		
		
		 // Define constants for bounds of the trees
        int xMin = -600, xMax = 600;
        int yMin = -3000, yMax =-200;
        int zMin = 100, zMax = 200; 
        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            // Generate random positions within bounds
        	int x=0;
        	
             x = rand.nextInt(xMax - xMin + 1) + xMin;                 
            int y = rand.nextInt(yMax - yMin + 1) + yMin;
            int z = rand.nextInt(zMax - zMin + 1) + zMin;
            
           

            Point v0Tree1 = new Point(x , y - 100, z + 80);
            Point v1Tree1 = new Point(x , y , z + 80);
            Point v2Tree1 = new Point(x-100, y - 100, z + 80);
            Point v3Tree1 = new Point(x-100, y , z + 80);
            Point apexTree1 = new Point((x-50), y - 50, z + 250);
            
            // Define the base and apex points for the tree                          
            Point v0Tree = new Point(x , y - 100, z);
            Point v1Tree = new Point(x , y , z);
            Point v2Tree = new Point(x-100, y - 100, z);
            Point v3Tree = new Point(x-100, y , z);
            Point apexTree = new Point((x-50), y - 50, z + 150);
            
            Point Tree = new Point(x - 30, y - 100, z);
            Point Tree1 = new Point(x -30, y , z);
            Point Tree2 = new Point(x -90, y - 100, z);
            Point Tree3 = new Point(x - 90, y , z);
            Point v4Tree = new Point(x -30, y - 100, 0);
            Point v5Tree = new Point(x -30, y , 0);
            Point v6Tree = new Point(x - 90, y , 0);
            Point v7Tree = new Point(x - 90, y - 100,0);

            // Add the tree to the scene
            scene.geometries.add(
                // Sides of the pyramid
                new Triangle(v0Tree, v1Tree, apexTree).setEmission(forestColor)
                        .setMaterial(Material),
                new Triangle(v1Tree, v3Tree, apexTree).setEmission(forestColor)
                        .setMaterial(Material),
                new Triangle(v2Tree, v3Tree, apexTree).setEmission(forestColor)
                        .setMaterial(Material),
                new Triangle(v2Tree, v0Tree, apexTree).setEmission(forestColor)
                        .setMaterial(Material),

                new Triangle(v0Tree1, v1Tree1, apexTree1).setEmission(forestColor)
                        .setMaterial(Material),
                new Triangle(v1Tree1, v3Tree1, apexTree1).setEmission(forestColor)
                        .setMaterial(Material),
                new Triangle(v2Tree1, v3Tree1, apexTree1).setEmission(forestColor)
                        .setMaterial(Material),
                new Triangle(v2Tree1, v0Tree1, apexTree1).setEmission(forestColor)
                        .setMaterial(Material)
            );

            scene.geometries.add(
                // Top face 
                new Polygon(v4Tree, v5Tree, v6Tree, v7Tree).setEmission(woodColor)
                        .setMaterial(new Material().setKD(0.25).setKS(0.5).setShininess(70)),
                // Right face 
                new Polygon(Tree3, Tree2, v7Tree, v6Tree).setEmission(woodColor)
                        .setMaterial(new Material().setKD(0.25).setKS(0.5).setShininess(70)),
                // Front face 
                new Polygon(Tree1, v5Tree, v6Tree, Tree3).setEmission(woodColor)
                        .setMaterial(new Material().setKD(0.25).setKS(0.5).setShininess(70)),
                // Back face
                new Polygon(Tree, v4Tree, v7Tree, Tree2).setEmission(woodColor)
                        .setMaterial(new Material().setKD(0.25).setKS(0.5).setShininess(70)),
                // Bottom face
                new Polygon(Tree, Tree1, Tree3, Tree2).setEmission(woodColor)
                        .setMaterial(new Material().setKD(0.25).setKS(0.5).setShininess(70)),
                // Left face 
                new Polygon(Tree, Tree1, v5Tree, v4Tree).setEmission(woodColor)
                        .setMaterial(new Material().setKD(0.25).setKS(0.5).setShininess(70))
            );
		
        }
		
		
		scene.geometries.add(
				new Plane(new Point(0,0,0), new Vector(0, 0, 1)).setMaterial(new Material().setKD(0.1))
						 .setEmission(new Color(220,220,220)),	
				new Plane(new Point(0,-5000,0), new Vector(0, -1, 0))
				     .setMaterial(new Material().setKD(0.00001).setKR(1).setKT(0))
				     .setEmission(new Color(blue).reduce(7)),
			    
				// snowman body
				new Sphere(new Point(0, 0, 70), 70d).setEmission(new Color(215, 215, 215))
						.setMaterial(sphereMaterial),
				// snowman head
				new Sphere(new Point(0, 0, 180), 45d).setEmission(new Color(215, 215, 215)).setMaterial(sphereMaterial),
				// moon
			    // new Sphere(new Point(1400, -5000 , 1400), 270d).setEmission(new Color(white)).setMaterial(sphereMaterial1),
				// Front Sphere
			    new Sphere(new Point(-22.5, 39, 200), 7d).setEmission(sphereColor).setMaterial(sphereMaterial),
				// Down Sphere
				new Sphere(new Point(22.5, 39, 200), 7d).setEmission(sphereColor).setMaterial(sphereMaterial),
				new Sphere(new Point(0, 55, 120), 7d).setEmission(new Color(red).reduce(4)).setMaterial(sphereMaterial),
				new Sphere(new Point(0, 60, 100), 7d).setEmission(new Color(red).reduce(4)).setMaterial(sphereMaterial),
		        new Sphere(new Point(0, 70, 80), 7d).setEmission(new Color(red).reduce(4)).setMaterial(sphereMaterial));
		
		
  for(int i = 0; i <= 1000; i ++) {
		     
		           int randI = rand.nextInt(2000) - 1000; // Generates a random number between -1000 and 999
		           int randJ = rand.nextInt(3000) - 1500; // Generates a random number between -1500 and 1499
		           int randK = rand.nextInt(600) ; // Generates a random number between 0 and 600
		           int size = rand.nextInt(6);

		           scene.geometries.add(new Sphere(new Point( randI,  randJ, randK), size)
		                    .setEmission(new Color(white))
		                  .setMaterial(sphereMaterial));
		        }
	for(int i = 0; i <= 300; i ++) {
	     
        int randI = rand.nextInt(2000) - 1000; // Generates a random number between -700 and 699
        int randJ = rand.nextInt(6000) - 3000; // Generates a random number between -6000 and 2999
        int randK = rand.nextInt(6)-20 ; // Generates a random number between 0 and 600
        int size = rand.nextInt(30)+15;

        scene.geometries.add(new Sphere(new Point( randI,  randJ, randK ), size)
                 .setEmission(new Color(250,250,250))
               .setMaterial(sphereMaterial));
	}
     	scene.lights.add(new SpotLight(new Color(400, 400, 400), new Vector(0, -1, 0), new Point(0, 250, 300)).setKL(4E-5)
			.setKQ(2E-7));
	    int resolution = 1000;
		Camera.Builder cameraBuilder = Camera.getBuilder().setRayTracer(new SimpleRayTracer(scene)) //
				.setVpSize(180, 180);
		

		cameraBuilder.setView(new Point(0, 1300, 150), new Point(0, 0, 100))
				     .setVpDistance(250) //
				     .setImageWriter(new ImageWriter("SnowMan", resolution, resolution)) //
				     .setDOF(15, 1250 ,3)//
			         .build() //
				     .renderImage().writeToImage();
	
	}
	
}


