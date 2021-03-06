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
public class Scene1 extends Scene{

    public Scene1() {
        background = new Shade(0,1,1);
        lights.clear();
        //lights.add(new Light.Spherical(new Point(0,500,20),new Shade(0xff0000),3));
        //lights.add(new Light.Spherical(new Point(150,500,20),new Shade(0x00ff00),3));
        //lights.add(new Light.Spherical(new Point(-150,500,20),new Shade(0x0000ff),3));
        //lights.add(new Light.Distant(new Normal(10,-100,0)));
        lights.add(new Light.Spherical(new Point(150,500,200),new Shade(0xff0000),2));
        lights.add(new Light.Spherical(new Point(-150,500,20),new Shade(0x00ffFF),2));
        
        // a metal planet
        objects.add(new Sphere(new Point(0,0,-200), 75, new Shade(1,0,0), GeometricObject.MaterialType.Reflection));
        objects.add(new Disk(new Point(0,0,-200), new Normal(2, 5, 1), 110, 90, new Shade(0xaa3939)));
        
        // a glass sphere
        objects.add(new Sphere(new Point(), 50, new Shade(1,1,0)));
        
        // color spheres
        objects.add(new Sphere(new Point(-200,0,0), 50, new Shade(0,1,0)));
        objects.add(new Sphere(new Point(200,0,0), 50, new Shade(0,0,1)));
        objects.add(new Sphere(new Point(-60,30,80), 60, new Shade(1,1,0), GeometricObject.MaterialType.ReflectionAndRefraction, 1.4));
        
        // a triangle
        objects.add(new Triangle(new Point(-200, -100, -550), 
                new Point(50, 150, -600), 
                new Point(-250, 250, -550), 
                new Shade(0x80b300),GeometricObject.MaterialType.Reflection));
        
        //objects.add(new Cylinder(new Point(150, 0, 120), new Point(180, 100, 80), 50, new Shade(0xffb300), GeometricObject.MaterialType.ReflectionAndRefraction, 1.4));
        objects.add(new Cylinder(new Point(-150, 300, -100), new Point(180, 150, -90), 25, new Shade(0xffb300), GeometricObject.MaterialType.Reflection));
        objects.add(new Cylinder.Finite(new Point(125, -100, 100), new Point(180, 90, 165), 5, new Shade(0xffb300)));
        objects.add(new Cylinder.Finite(new Point(150, -100, 120), new Point(150, 50, 120), 50, new Shade(0xffb300), GeometricObject.MaterialType.ReflectionAndRefraction, 1.4));
        
        // floor
        //objects.add(new Plane(new Point(0,-100,0), new Normal(0, 1, 0), new Shade(0x921400)));
        objects.add(new Plane(new Point(0,-100,0), new Normal(0, 1, 0), new Shade(0x929292)));
        
        // left wall
        //objects.add(new Plane(new Point(-300,0,0), new Normal(1, 0, 0), new Shade(0xc51b00)));
        
        // right wall
        objects.add(new Plane(new Point(300,0,0), new Normal(-1, 0, 0.1), new Shade(0xc58a00)));
        
        // back, fron walls
        //objects.add(new Plane(new Point(0, 0, 1000), new Normal(0,0,1), new Shade(0x80b300))); // green one
        //objects.add(new Plane(new Point(0, 0, -1000), new Normal(0,0,1), new Shade(0xffb300))); // orange
    }
    
}
