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
                {
                    Ray reflectedRay = getReflectedRay();
                    Hit hit = s.hitObject(reflectedRay);
                    if(hit != null) return hit.getShade(s, depth+1).mul(0.8);
                }
                break;
                
            case ReflectionAndRefraction:
                {
                    Ray refractedRay = getRefractedRay();
                    Hit hit = s.hitObject(refractedRay);
                    if(hit != null) return hit.getShade(s, depth+1).mul(0.99);
                }
                break;
        }
        return new Shade(s.background);
    }

    Ray getReflectedRay(){
        return getReflectedRay(normal);
    }

    Ray getReflectedRay(Normal normal){
        return new Ray(point.add(normal.mul(RayTracing.BIAS)),
                new Vector(ray.direction.sub(normal.mul(2*ray.direction.dot(normal)))));
    }

    Ray getRefractedRay(){
        double cosi = Math.min(1, Math.max(-1, ray.direction.dot(normal)));
        Vector Nrefr;
        double eta;
        
        if(cosi < 0) { // from outside to inside
            cosi = -cosi;
            Nrefr = normal;
            eta = 1/object.ior; // Snell law: n1/n2
        } else { // from inside to outside
            Nrefr = normal.mul(-1); // reverse normal
            eta = object.ior;
        }
        // k = 1 - n^2(1-cosi^2)
        double k = 1 - eta*eta * (1 - cosi*cosi);
        
        if(k < 0) { // total internal reflection
            return null; 
        } else {
            // return: eta * I + (eta * NdotI - sqrtf(k)) * Nrefr
            return new Ray(point.add(normal.mul(-RayTracing.BIAS)), 
                    new Vector(ray.direction.mul(eta).add(Nrefr.mul(eta * cosi - Math.sqrt(k)))));
        }
    }
}
