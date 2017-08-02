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
    double distance;
    
    Hit(GeometricObject o, Ray ray, double t){
        object = o;
        point = ray.origin.add(ray.direction.mul(t));
        normal = o.getPointNormal(point);
        distance = t;
    }
    
    Shade getShade(Scene s){
        Ray shadowRay = new Ray(point, s.light.getDirection(point).mul(-1));
        
        Hit hit = s.hitObject(shadowRay);
        if(hit == null){
            // not in shadow
            return object.shade.mul(s.light.brightness);
        } else {
            return (hit.object == object)?new Shade(1,1,1) : new Shade();
        }
    }
    
    Shade getShade(Scene s, int x, int y){
        Ray shadowRay = new Ray(point, s.light.getDirection(point).mul(-1));
        shadowRay.direction.normalize();
        normal.normalize();
        /*if(x == 640/2 && y == 480/2){
            System.out.println(""+shadowRay.direction.x+","+shadowRay.direction.y+","+shadowRay.direction.z+",");
            return new Shade(1,1,1);
        }*/
        
        Hit hit = s.hitObject(shadowRay);
        if(hit != null && s.light instanceof Light.Spherical){
            Point lp = ((Light.Spherical)s.light).position;
            Point hit_lp = point.sub(lp);
            if(hit_lp.dot(hit_lp) < hit.distance*hit.distance)
                hit = null;
        }
        if(hit == null){
            // not in shadow
            double prod = shadowRay.direction.dot(normal);
            if(prod > 0) return new Shade(prod, prod, prod);
                //return object.shade.mul(prod);
            else return new Shade(-prod, 0, 0);
            //return object.shade/*.mul(s.light.color)*/.mul(s.light.brightness*Math.max(0,shadowRay.direction.dot(normal)));
        } else {
            return (hit.object == object)?new Shade(1,1,1) : new Shade();
        }
    }
}
