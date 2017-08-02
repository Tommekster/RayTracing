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
        
        objects.add(new Sphere(new Point(0,0,-100), 50, new Shade(1,0,0)));
        objects.add(new Disk(new Point(0,0,-100), new Normal(2, 1, 1), 75, new Shade(0xaa3939)));
        objects.add(new Sphere(new Point(), 50, new Shade(1,1,0)));
        objects.add(new Sphere(new Point(-200,0,0), 50, new Shade(0,1,0)));
        objects.add(new Sphere(new Point(200,0,0), 50, new Shade(0,0,1)));
        //objects.add(new Plane(new Point(0,0,0), new Normal(0, 1, 0.2), new Shade(1,1,0)));
        // podlaha
        objects.add(new Plane(new Point(0,-100,0), new Normal(0, 1, 0), new Shade(0x921400)));
        // leva stena
        objects.add(new Plane(new Point(-300,0,0), new Normal(1, 0, 0), new Shade(0xc51b00)));
        // prava strana
        objects.add(new Plane(new Point(300,0,0), new Normal(-1, 0, 0), new Shade(0xc58a00)));
        
        //objects.add(new Plane(new Point(), new Normal(0, 0, 1), new Shade(0xc58a00)));
        //objects.add
        
        // zadni, predni steny
        objects.add(new Plane(new Point(0, 0, 1000), new Normal(0,0,1), new Shade(0x80b300))); // zelene
        objects.add(new Plane(new Point(0, 0, -1000), new Normal(0,0,1), new Shade(0xffb300))); // oranzove
    }
    
}
