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
public class Scene4 extends Scene{

    public Scene4() {
        background = new Shade(0,1,1);
        lights.clear();
        lights.add(new Light.Spherical(new Point(-150,100,200),new Shade(0xffffff),1));
        //lights.add(new Light.Spherical(new Point(-150,500,20),new Shade(0xffffFF),2));
        lights.add(new Light.Ambient(new Shade(0xffffFF)));
        
        // a glossy ball
        Sphere ball = new Sphere(new Point(100,0,-30), 75, new Shade(1,0,0), GeometricObject.MaterialType.Phong);
        ball.phong_n = 20;
        ball.Ks = 0.5;
        objects.add(ball);
        
        
        // floor
        objects.add(new Plane(new Point(0,-300,0), new Normal(0, 1, 0), new Shade(0x929292)));
        
        // right wall
        objects.add(new Plane(new Point(300,0,0), new Normal(-1, 0, 0.1), new Shade(0xc58a00)));
    }
    
}
