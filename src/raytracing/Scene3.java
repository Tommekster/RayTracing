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
public class Scene3 extends Scene {
    public Scene3() {
        background = new Shade(0,1,1);
        lights.clear();
        lights.add(new Light.Spherical(new Point(150,500,-200),new Shade(0xffffff),2));
        lights.add(new Light.Spherical(new Point(-150,500,20),new Shade(0xffffFF),2));
        
        // a mirror triangle
        objects.add(new Triangle(new Point(5, -1, -20), 
                new Point(-5, 15, -19.5), 
                new Point(-18.0, 2.0, -19.8), 
                new Shade(0x80b300),GeometricObject.MaterialType.Reflection));
        
        // an object from file
        try {
            TriangleMesh cube = TriangleMesh.loadGeoFile("cow.geo", new Shade(0xffffff));
            if(cube != null){
                objects.add(cube);
                cube.type = GeometricObject.MaterialType.Diffuse;
                cube.ior = 1.9;
                //objects.add(new BoundingBox(cube.vertices, cube.shade));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        // zoom
        //objects.add(new Sphere(new Point(-25,50,150), 10, new Shade(1,0,0), GeometricObject.MaterialType.ReflectionAndRefraction, 1.7));
        
        // floor
        objects.add(new Plane(new Point(0,-1,0), new Normal(0, 1, 0), new Shade(0x929292)));
        
        // right wall
        objects.add(new Plane(new Point(10,0,0), new Normal(-1, 0, 0.1), new Shade(0xc58a00), GeometricObject.MaterialType.Glossy, 2));
    }
    
    @Override
    Projection getProjection(int height){
        return new Projection.Perspective(height,new Point(-13, 14, 15), new Point(0, 0, 0), 45);
    }
}
