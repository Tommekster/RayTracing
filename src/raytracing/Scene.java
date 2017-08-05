/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

import java.util.ArrayList;

/**
 *
 * @author zikmundt
 */
public abstract class Scene {
    ArrayList<GeometricObject> objects = new ArrayList<>();
    ArrayList<Light> lights = new ArrayList<>();
    Shade background = new Shade();
    {
        lights.add(new Light.Distant());
    }
    boolean shadows = true;
    
    Hit hitObject(Ray ray){
        return hitObject(ray,false);
    }
    
    Hit hitObject(Ray ray, boolean shadowRay){
        GeometricObject hitObj = null;
        Triangle hitTriangle = null, triangle = null;
        double dist = 0;
        for(GeometricObject obj : objects){
            double t;
            if(obj instanceof TriangleMesh) {
                Object [] tHit = ((TriangleMesh)obj).hitTriangle(ray);
                t = (Double) tHit[0];
                triangle = (Triangle) tHit[1];
            }
            t = obj.hit(ray);
            if(t > RayTracing.MINIMAL_VALUE){
                if(hitObj == null  || (dist > t)){
                    hitObj = obj;
                    dist = t;
                    hitTriangle = triangle;
                }
            }
        }
        
        return (hitObj == null)?null:new Hit(hitObj,ray,dist,(hitObj instanceof TriangleMesh)?hitTriangle:null);
    }
    
    Projection getProjection(int height){
        return new Projection.Perspective(height,new Point(-50, 100, 300), new Point(0, 0, 0), 45);
    }
}
