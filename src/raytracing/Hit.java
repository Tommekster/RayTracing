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
public class Hit {
    GeometricObject object;
    Point point;
    Normal normal;
    
    Hit(GeometricObject o, Ray ray, double t){
        object = o;
        point = ray.origin.add(ray.direction.mul(t));
        normal = o.getPointNormal(point);
    }
    
    Shade getShade(Scene s){
        Ray shadowRay = new Ray(point, s.light.getDirection(point).mul(-1));
        
        if(s.hitObject(shadowRay) == null){
            // not in shadow
            return object.shade.mul(s.light.brightness);
        } else {
            return new Shade();
        }
    }
}
