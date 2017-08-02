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
public class Plane extends GeometricObject{
    Point point;
    Normal normal;
    
    Plane(Point _point, Normal _normal, Shade _shade){
        point = new Point(_point);
        normal = new Normal(_normal);
        shade = new Shade(_shade);
    }

    @Override
    double hit(Ray ray) {
        // condition: (p-a)*n = 0
        double t = point.sub(ray.origin).dot(normal)/(ray.direction.dot(normal));
        
        return retVal(t);
    }
    
    @Override
    Normal getPointNormal(Point p) {
        return normal;
    }
}
