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
    Shade background = new Shade(0,1,1);
    
    GeometricObject hitObject(Ray ray){
        GeometricObject hit = null;
        double dist = 0;
        for(GeometricObject obj : objects){
            double h = obj.hit(ray);
            if(h > 0){
                if(hit == null  || (dist > h)){
                    hit = obj;
                    dist = h;
                }
            }
        }
        
        return hit;
    }
}
