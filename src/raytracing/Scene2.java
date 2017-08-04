/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

/**
 *
 * @author zikmundt
 */
public class Scene2 extends Scene{

    public Scene2() {
        background = new Shade(0,1,1);
        lights.clear();
        lights.add(new Light.Spherical(new Point(150,500,200),new Shade(0xffffff),2));
        lights.add(new Light.Spherical(new Point(-150,500,20),new Shade(0xffffFF),2));
        
        // a glossy ball
        objects.add(new Sphere(new Point(100,0,-30), 75, new Shade(1,0,0), GeometricObject.MaterialType.Glossy, 2));
        
        // a mirror triangle
        objects.add(new Triangle(new Point(-200, -100, -550), 
                new Point(50, 150, -600), 
                new Point(-250, 250, -550), 
                new Shade(0x80b300),GeometricObject.MaterialType.Reflection));
        
        // L object
        TriangleMesh lObj = TriangleMesh.generateLObject(new Shade(1,0,0));
        lObj.type = GeometricObject.MaterialType.Texture;
        
        objects.add(lObj);
        Point [] points = {
                new Point(-50,-50,50),
                new Point(50,-50,50),
                new Point(50,-50,-50),
                new Point(-50,-50,-50),
                new Point(-50,50,-50),
                new Point(-50,50,50)
        };
        Shade yellow = new Shade(1,1,0);
        for(Point point : points){
            objects.add(new Sphere(point, 5, yellow));
        }
        
        // floor
        objects.add(new Plane(new Point(0,-300,0), new Normal(0, 1, 0), new Shade(0x929292)));
        
        // right wall
        objects.add(new Plane(new Point(300,0,0), new Normal(-1, 0, 0.1), new Shade(0xc58a00)));
    }
    
}
