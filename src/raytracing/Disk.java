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
    float radius;
    float radius2 = 0;

    public Disk(Point _point, Normal _normal, float _radius , Shade _shade) {
        super(_point, _normal, _shade);
        radius = _radius;
    }

    public Disk(Point _point, Normal _normal, float _radius, float _radius2 , Shade _shade) {
        super(_point, _normal, _shade);
        radius = _radius;
        radius2 = _radius2;
    }

    @Override
    double hit(Ray ray) {
        double t = super.hit(ray);
        if(t > 0){
            Point p = ray.origin.add(ray.direction.mul(t));
            Vector v = new Vector(p.sub(point));
            double v2 = v.dot(v);
            if(v2 > radius*radius || v2 < radius2*radius2)
                t = 0;
        }
        return t;
    }
    
}
