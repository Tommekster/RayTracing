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
    Triangle triangle;
    double distance;
    
    Hit(GeometricObject o, Ray ray, double t){
        object = o;
        point = ray.origin.add(ray.direction.mul(t));
        normal = o.getPointNormal(point);
        this.ray = ray;
        distance = t;
    }
    
    Hit(GeometricObject o, Ray ray, double t, Triangle _triangle){
        object = o;
        point = ray.origin.add(ray.direction.mul(t));
        normal = (_triangle != null)? _triangle.getPointNormal(point):o.getPointNormal(point);
        this.ray = ray;
        distance = t;
        triangle = (o instanceof Triangle)?(Triangle)o:_triangle;
    }
    
    Shade getShade(Scene s){
        return getShade(s,0);
    }
    
    Shade getShade(Scene s, int depth){
        if(depth > RayTracing.MAX_DEPTH) return new Shade(s.background);
        
        switch(object.type){
            case Diffuse:
            case Texture:
                {
                    Shade shade = getDiffuseShade(s,depth);
                    return shade;
                }
                
            case Reflection:
                {
                    Ray reflectedRay = getReflectedRay();
                    Hit hit = s.hitObject(reflectedRay);
                    if(hit != null) return hit.getShade(s, depth+1).mul(0.8);
                }
                break;
                
            case ReflectionAndRefraction:
            case Glossy:
                {
                    Shade shade = new Shade();
                    double kr = getFresnelIndex();
                    
                    //if(kr == 1) return new Shade(1,0,0);
                    //return new Shade(0,0,kr);
                    if(kr < 1) { // it is not internal reflection
                        if(object.type == GeometricObject.MaterialType.Glossy){
                            Shade dShade = getDiffuseShade(s,depth);
                            shade.add(dShade.mul(1-kr));
                        } else {
                            Ray refractedRay = getRefractedRay();
                            Hit refractedHit = s.hitObject(refractedRay);
                            if(refractedHit != null) {
                                shade.add(refractedHit.getShade(s, depth+1).mul(0.99*(1-kr)));
                            }
                        }
                    }
                    Ray reflectedRay = getReflectedRay();
                    Hit reflectedHit = s.hitObject(reflectedRay);
                    if(reflectedHit != null) {
                        shade.add(reflectedHit.getShade(s, depth+1).mul(0.8*kr));
                    } else {
                        shade.add(s.background.mul(kr));
                    }
                    if(object.Ks != 0){ 
                        for(Light light : s.lights){
                            if(!(light instanceof Light.Ambient)) {
                                Ray lightReflected = getReflectedRay(normal,light.getDirection(point).mul(-1)); 
                                double spec = ray.direction.dot(lightReflected.direction);
                                spec = (spec < 0) ? 0 : Math.pow(spec,object.phong_n);
                                shade.add(light.color.mul(light.brightness*object.Ks*spec));
                            }
                        }
                    }
                    shade.norm();
                    return shade;
                }
        }
        return new Shade(s.background);
    }
    
    Shade getDiffuseShade(Scene s, int depth) {
        Point biasPoint = point.add(ray.direction.mul(-RayTracing.BIAS));
        Shade shade = new Shade();
        for (Light light : s.lights){
            Ray shadowRay = new Ray(biasPoint, light.getDirection(point).mul(-1));
            shadowRay.direction.normalize();
            normal.normalize();
            
            // shadow ray
            Hit hit = (s.shadows && !(light instanceof Light.Ambient))?s.hitObject(shadowRay, true):null;
            if(hit != null && light instanceof Light.Spherical){
                Point lp = ((Light.Spherical)light).position;
                Point hit_lp = point.sub(lp);
                if(hit_lp.dot(hit_lp) < hit.distance*hit.distance)
                    hit = null;
            }
            if(hit == null){
                // not in shadow
                double LN = (light instanceof Light.Ambient)?object.Ka:Math.max(0,shadowRay.direction.dot(normal));
                if(object.type == GeometricObject.MaterialType.Texture){
                    Point uv = ((triangle != null)?triangle:object).getUVcoordinates(point);
                    shade.add(((triangle != null)?triangle:object).getTexture(uv).mul(light.color).mul(light.brightness*LN));
                } else {
                    shade.add(object.shade.mul(light.color).mul(light.brightness*object.Kd*LN));
                }
                if(object.Ks != 0 && !(light instanceof Light.Ambient)) {
                    Ray lightReflected = getReflectedRay(normal,light.getDirection(point).mul(-1)); 
                    double spec = ray.direction.dot(lightReflected.direction);
                    spec = (spec < 0) ? 0 : Math.pow(spec,object.phong_n);
                    shade.add(light.color.mul(light.brightness*object.Ks*spec));
                }
            }
        }
        shade.div(s.lights.size());
        
        if(object.Ki != 0 && s.indirectSamples > 0){
            Shade indirectLight = new Shade();
            for(int n = 0; n < s.indirectSamples; n++){
                Ray indirect = new Ray(biasPoint, Normal.getRandom(normal));
                Hit hit = s.hitObject(indirect);
                if(hit != null){
                    indirectLight.add(hit.getShade(s, depth+1).mul(normal.dot(indirect.direction)));
                }
            }
            indirectLight.div(s.indirectSamples);
            shade.add(indirectLight);
        }
                
        shade.norm();
        return shade;
    }

    Ray getReflectedRay(){
        return getReflectedRay(normal);
    }

    Ray getReflectedRay(Normal normal){
        return getReflectedRay(normal,ray.direction);
    }

    Ray getReflectedRay(Normal normal, Vector direction){
        Vector vector = new Vector(direction.sub(normal.mul(2*direction.dot(normal)))); 
        return new Ray(point.add(vector.mul(-RayTracing.BIAS)),
                vector);
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
    
    double getFresnelIndex(){
        double cosi = Math.min(1, Math.max(-1, ray.direction.dot(normal)));
        double etai, etat;
        
        if(cosi < 0) {
            etai = 1;
            etat = object.ior;
            cosi = -cosi;
        } else {
            etai = object.ior;
            etat = 1;
        }
        
        double sint = etai / etat * Math.sqrt(1 - cosi * cosi);
        if(sint >= 1) {
            return 1; // total internal reflection
        } else {
            double cost = Math.sqrt(1 - sint * sint);
            double Rs = (etat * cosi - etai * cost) / (etat * cosi + etai * cost); // parallel
            double Rp = (etai * cosi - etat * cost) / (etai * cosi + etat * cost); // perpendicular
            
            return (Rs*Rs + Rp*Rp) / 2;
        }
    }
}
