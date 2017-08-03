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
    double radius2;
    Point point;
    
    Cylinder(Point a, Point b, double _radius, Shade _shade){
        normal = new Normal(b.sub(a));
        point = a;
        radius = _radius;
        radius2 = radius*radius;
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
    
    static class Finite extends Cylinder{
        Disk top;
        Disk bottom;
        //Point midpoint;
        double height;
        
        public Finite(Point a, Point b, double _radius, Shade _shade) {
            super(a, b, (_radius > 0)?_radius:10, _shade);
            top = new Disk(b,normal, _radius, _shade);
            bottom = new Disk(a,new Normal(normal.mul(-1)), _radius, _shade);
            //midpoint = a.add(b).mul(2);
            height = b.sub(a).getMagnitude();
        }
        public Finite(Point a, Point b, double _radius, Shade _shade,MaterialType _type) {
            this(a, b, (_radius > 0)?_radius:10, _shade);
            type = _type;
            if(type == MaterialType.ReflectionAndRefraction) ior = 1.2;
        }
        public Finite(Point a, Point b, double _radius, Shade _shade,MaterialType _type, double _ior) {
            this(a, b, (_radius > 0)?_radius:10, _shade);
            type = _type;
            ior = _ior;
        }
        

        @Override
        double hit(Ray ray) {
            double t = super.hit(ray);
            if(t == 0) return 0;
            
            double position = ray.origin.add(ray.direction.mul(t)).sub(bottom.point).dot(normal);
            if(position < -RayTracing.BIAS) { // point is probably outside the cylinder
                return bottom.hit(ray);
            }
            if(position > (height+RayTracing.BIAS)) {
                return top.hit(ray);
            }
            
            return t;
        }
        
        @Override
        Normal getPointNormal(Point p) {
            double position = p.sub(bottom.point).dot(normal);
            
            if(position < RayTracing.BIAS) { // point is probably outside the cylinder
                return bottom.normal;
            }
            if(position > (height-RayTracing.BIAS)) {
                return top.normal;
            }
            
            return super.getPointNormal(p);
        }
    }
}
