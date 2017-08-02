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
    Ray ray;
    Normal normal;
    double distance;
    
    Hit(GeometricObject o, Ray ray, double t){
        object = o;
        point = ray.origin.add(ray.direction.mul(t));
        normal = o.getPointNormal(point);
        this.ray = ray;
        distance = t;
    }
    
    Shade getShade(Scene s){
        return getShade(s,0);
    }
    
    Shade getShade(Scene s, int depth){
        if(depth > RayTracing.MAX_DEPTH) return new Shade(s.background);
        
        switch(object.type){
            case Diffuse:
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
                
            case Reflection:
                Ray reflectedRay = getReflectedRay();
                Hit hit = s.hitObject(reflectedRay);
                if(hit != null) return hit.getShade(s, depth+1).mul(0.8);
                break;
        }
        return new Shade(s.background);
    }

    Ray getReflectedRay(){
        return new Ray(point.add(normal.mul(RayTracing.BIAS)),new Vector(ray.direction.sub(normal.mul(2*ray.direction.dot(normal)))));
    }
}
