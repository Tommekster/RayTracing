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
public class Sphere extends GeometricObject{
    Point center;
    double radius;

    public Sphere(Point _center, double _radius, Shade _shade) {
        center = new Point(_center);
        radius = _radius;
        shade = new Shade(_shade);
    }

    Sphere(Point _center, double _radius, Shade _shade, MaterialType _type) {
        center = new Point(_center);
        radius = _radius;
        shade = new Shade(_shade);
        type = _type;
        if(type == MaterialType.ReflectionAndRefraction) ior = 1.2;
    }

    Sphere(Point _center, double _radius, Shade _shade, MaterialType _type, double _ior) {
        center = new Point(_center);
        radius = _radius;
        shade = new Shade(_shade);
        type = _type;
        ior = _ior;
    }

    @Override
    double hit(Ray ray) {
        // condition (p-c)*(p-c) = r^2
        double a = ray.direction.dot(ray.direction);
        double b = 2*ray.origin.sub(center).dot(ray.direction);
        double c = ray.origin.sub(center).dot(ray.origin.sub(center))-radius*radius;
        
        double discriminant = b*b -4*a*c;
        if(discriminant < 0) return 0;
        else {
            return retVal((-b - Math.sqrt(discriminant))/2/a);
        }
    }

    @Override
    Normal getPointNormal(Point p) {
        return new Normal(p.sub(center));
    }
    
}
