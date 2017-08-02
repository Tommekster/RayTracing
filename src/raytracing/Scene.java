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
    Shade background = new Shade();
    Light light = new Light(new Point(0,10E8,0),1,new Shade(1, 1, 1));
    
    Hit hitObject(Ray ray){
        GeometricObject hitObj = null;
        double dist = 0;
        for(GeometricObject obj : objects){
            double t = obj.hit(ray);
            if(t > 0){
                if(hitObj == null  || (dist > t)){
                    hitObj = obj;
                    dist = t;
                }
            }
        }
        
        return new Hit(hitObj,ray,dist);
    }
}
