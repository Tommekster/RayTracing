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
        
        objects.add(new Sphere(new Point(), 50, new Shade(1,0,0)));
        objects.add(new Sphere(new Point(-200,0,0), 50, new Shade(0,1,0)));
        objects.add(new Sphere(new Point(200,0,0), 50, new Shade(0,0,1)));
        //objects.add(new Plane(new Point(0,0,0), new Normal(0, 1, 0.2), new Shade(1,1,0)));
    }
    
}
