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
        Shade shade = new Shade();
        for (Light light : s.lights){
            Ray shadowRay = new Ray(point, light.getDirection(point).mul(-1));
            shadowRay.direction.normalize();
            normal.normalize();

            Hit hit = s.hitObject(shadowRay);
            if(hit != null && light instanceof Light.Spherical){
                Point lp = ((Light.Spherical)light).position;
                Point hit_lp = point.sub(lp);
                if(hit_lp.dot(hit_lp) < hit.distance*hit.distance)
                    hit = null;
            }
            if(hit == null){
                // not in shadow
                shade.add(object.shade.mul(light.color).mul(light.brightness*Math.max(0,shadowRay.direction.dot(normal))));
            } 
        }
        shade.div(s.lights.size());
        shade.norm();
        return shade;
    }
}
