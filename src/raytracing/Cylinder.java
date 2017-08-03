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
public class Cylinder extends GeometricObject{
    Normal normal;
    double radius;
    Point point;
    
    Cylinder(Point a, Point b, double _radius, Shade _shade){
        normal = new Normal(b.sub(a));
        point = a;
        radius = _radius;
        shade = _shade;        
    }
    Cylinder(Point a, Point b, double _radius, Shade _shade, MaterialType _type){
        this(a,b,_radius,_shade);
        type = _type;
        if(type == MaterialType.ReflectionAndRefraction) ior = 1.2;
    }
    Cylinder(Point a, Point b, double _radius, Shade _shade, MaterialType _type, double _ior){
        this(a,b,_radius,_shade);
        type = _type;
        ior = _ior;
    }

    @Override
    double hit(Ray ray) {
        // condition 
        Vector op = ray.origin.sub(point);
        Vector A = op.sub(normal.mul(op.dot(normal)));
        Vector B = ray.direction.sub(normal.mul(ray.direction.dot(normal)));
        
        double a = B.dot(B);
        double b = 2*A.dot(B);
        double c = A.dot(A) - radius*radius;
        
        /*double a = ray.origin.dot(ray.origin) - ray.origin.z*ray.origin.z;
        double b = 2*(ray.direction.dot(ray.origin) - ray.direction.z*ray.origin.z);
        double c = ray.direction.dot(ray.direction) - ray.direction.z*ray.direction.z - radius*radius;*/
        /*double a = ray.direction.x*ray.direction.x + ray.direction.z*ray.direction.z;
        double b = 2*(ray.origin.x*ray.direction.x + ray.origin.z*ray.direction.z);
        double c = ray.origin.x*ray.origin.x + ray.origin.z*ray.origin.z - radius*radius;*/
        
        double discriminant = b*b -4*a*c;
        if(discriminant < 0) return 0;
        else {
            //System.out.println(""+a+" "+b+" "+c);
            return retVal((-b - Math.sqrt(discriminant))/2/a);
        }
    }

    @Override
    Normal getPointNormal(Point p) {
        Vector po = p.sub(point);
        Normal pointNormal = new Normal(po.sub(normal.mul(po.dot(normal))));
        return pointNormal;
    }
}
