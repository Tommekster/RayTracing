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
public class Disk extends Plane{
    double radius;
    double radius2 = 0;
    double radius_2;
    double radius2_2 = 0;

    public Disk(Point _point, Normal _normal, double _radius , Shade _shade) {
        super(_point, _normal, _shade);
        radius = _radius;
        radius_2 = radius*radius;
    }

    public Disk(Point _point, Normal _normal, double _radius, double _radius2 , Shade _shade) {
        this(_point, _normal, _radius, _shade);
        radius2 = _radius2;
        radius2_2 = radius2*radius2;
    }

    @Override
    double hit(Ray ray) {
        double t = super.hit(ray);
        if(t > 0){
            Point p = ray.origin.add(ray.direction.mul(t));
            Vector v = new Vector(p.sub(point));
            double v2 = v.dot(v);
            if(v2 > radius_2 || v2 < radius2_2)
                t = 0;
        }
        return t;
    }
    
}
