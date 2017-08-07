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
        shadows = false;
        lights.clear();
        lights.add(new Light.Distant(new Normal(-5,-10,0)));
        lights.add(new Light.Distant(new Normal(5,-10,0)));
        //lights.add(new Light.Spherical(new Point(-150,500,-200),new Shade(0xffffff),2));
        //lights.add(new Light.Spherical(new Point(-150,500,20),new Shade(0xffffFF),2));
        
        // a mirror triangle
        objects.add(new Triangle(new Point(5, -1, -20), 
                new Point(-5, 15, -19.5), 
                new Point(-18.0, 2.0, -19.8), 
                new Shade(0x80b300),GeometricObject.MaterialType.Reflection));
        
        // an object from file
        
        try {
            TriangleMesh cube2 = TriangleMesh.loadGeoFile("cube3.geo", new Shade(0xff0000));
            if(cube2 != null){
                objects.add(cube2);
                cube2.type = GeometricObject.MaterialType.Texture;
                cube2.ior = 1.9;
                cube2.transform(new TransformMatrix().setPosition(new Point(0,3,0)).setScale(2).setRotation(200, 0, 0));
                for(Triangle t : cube2.triangles){
                    t.vertNormals = null;
                }
                cube2.setTexture(new Texture("texture.png"));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        try {
            TriangleMesh cube = TriangleMesh.loadGeoFile("cow.geo", new Shade(0xffffff));
            if(cube != null){
                //objects.add(cube);
                cube.type = GeometricObject.MaterialType.Diffuse;
                cube.ior = 1.9;
                cube.transform(new TransformMatrix().setScale(2).setRotation(-15));
                //objects.add(new BoundingBox(cube.vertices, cube.shade));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        Triangle triangle = new Triangle(
                new Point(2,0,0), 
                new Point(0,2,-2), 
                new Point(0,0,-2),
                new Shade(1,0,0));
        
        //triangle.transform(new TransformMatrix().setHeightScale(2).setPosition(new Point(-3,0,3)));
        
        objects.add(triangle);
        
        // zoom
        try{
            Sphere sphere = new Sphere(new Point(-10,5,0), 3, new Shade(1,0,0), GeometricObject.MaterialType.Texture, 1.7);
            sphere.setTexture(new Texture("globe.png"), new Normal(0,-1,0), new Normal(0,0,1));
            objects.add(sphere);
        }  catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        // floor
        objects.add(new Plane(new Point(0,-1,0), new Normal(0, 1, 0), new Shade(0x929292)));
        
        // right wall
        objects.add(new Plane(new Point(10,0,0), new Normal(-1, 0, 0), new Shade(0xc58a00), GeometricObject.MaterialType.Reflection, 2));
    }
    
    @Override
    Projection getProjection(int height){
        return new Projection.Perspective(height,new Point(-20, 20, 5), new Point(0, 0, 0), 45);
    }
}
