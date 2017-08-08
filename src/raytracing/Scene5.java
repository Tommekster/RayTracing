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
public class Scene5 extends Scene {
    public Scene5() {
        background = new Shade(0,1,1);
        //shadows = false;
        indirectSamples = 50;
        
        lights.clear();
        Light light = new Light.Distant(new Normal(5,-10,-0.001));
        //light = new Light.Distant(new Normal(1, -5, -5));
        //Light light = new Light.Ambient(new Shade(0xffffff));
        lights.add(light);
        //lights.add(new Light.Distant(new Normal(5,-10,0)));
        //lights.add(new Light.Spherical(new Point(-150,500,-200),new Shade(0xffffff),2));
        //lights.add(new Light.Spherical(new Point(-150,500,20),new Shade(0xffffFF),2));
        
        // a mirror triangle
        Triangle triangle = new Triangle(new Point(5, 0, -4), 
                new Point(0, 5, -4), 
                new Point(-5, 0.1, -4), 
                new Shade(0x80b300),GeometricObject.MaterialType.Diffuse);
        //objects.add(triangle);
        //triangle.Ki = 0.2;
        
        // a ball
        Sphere ball = new Sphere(new Point(0,2,0), 1, new Shade(1,0,0),GeometricObject.MaterialType.ReflectionAndRefraction, 1.5);
        objects.add(ball);
        Sphere ball2 = new Sphere(new Point(1.5,2,-2), 1, new Shade(0,1,0));
        objects.add(ball2);
        ball2.phong_n = 100;
        //ball2.Ki = 1;
        
        // floor
        Plane floor = new Plane(new Point(0,0,0), new Normal(0, 1, 0), new Shade(0x929292), GeometricObject.MaterialType.Glossy,2);
        objects.add(floor);
        floor.phong_n = 1000;
        floor.Ks = 0;
        //floor.Ki = 0.2;
        
        try{
            //floor.setTexture(new Texture("texture2.png"), new Point(1,0,0), 10, 10);
            floor.setBumpTexture(new Texture("bump3.png"), new Point(1,0,0), 10, 10, 1);
        }  catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        // right wall
        objects.add(new Plane(new Point(10,0,0), new Normal(-1, 0, 0), new Shade(0xc58a00), GeometricObject.MaterialType.Diffuse, 2));
    }
    
    @Override
    Projection getProjection(int height){
        return new Projection.Perspective(height,new Point(-1, 5, 5), new Point(0, 0, 0), 45);
    }
}
