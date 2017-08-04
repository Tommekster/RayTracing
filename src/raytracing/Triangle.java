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
public class Triangle extends Plane {
    Point a,b,c;
    Vector ba, cb, ac;
    boolean singleSide = false;
    
    public Triangle(Point a, Point b, Point c, Shade _shade) {
        super(a, new Normal(b.sub(a).cross(c.sub(a))), _shade);
        this.a = a;
        this.b = b;
        this.c = c;
        ba = b.sub(a);
        cb = c.sub(b);
        ac = a.sub(c);
    }
    
    public Triangle(Point a, Point b, Point c, Shade _shade, MaterialType _type) {
        this(a,b,c,_shade);
        type = _type;
        if(_type == MaterialType.ReflectionAndRefraction) ior = 1.2;
    }

    @Override
    double hit(Ray ray) {
        double t = super.hit(ray);
        if(t > 0){
            // 1. find P
            Point p = ray.origin.add(ray.direction.mul(t));
            
            // 2. is P inside triangle?
            if(normal.dot(ba.cross(p.sub(a))) < 0) return 0; // p is on the right side of (b-a)
            if(normal.dot(cb.cross(p.sub(b))) < 0) return 0; // p is on the right side of (b-a)
            if(normal.dot(ac.cross(p.sub(c))) < 0) return 0; // p is on the right side of (b-a)
            
            if(singleSide && normal.dot(ray.origin) > 0) return 0; // looking from opposite side
        }
        return t;
    }
    
    
}
