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
public class BoundingBox extends GeometricObject{

    Point min, max, mid;

    public BoundingBox(Point [] points, Shade _shade) {
        shade = (_shade != null)?new Shade(_shade):null;
        min = new Point(points[0]);
        max = new Point(points[0]);
        
        for(Point p : points) {
            if(p.x < min.x) min.x = p.x;
            if(p.y < min.y) min.y = p.y;
            if(p.z < min.z) min.z = p.z;
            if(p.x > max.x) max.x = p.x;
            if(p.y > max.y) max.y = p.y;
            if(p.z > max.z) max.z = p.z;
        }
        
        Point mid = new Point(min);
        mid.add(max);
        this.mid = mid.mul(0.5);
    }
    
    
    
    @Override
    double hit(Ray ray) {
        double tmin = (min.x - ray.origin.x) / ray.direction.x; 
        double tmax = (max.x - ray.origin.x) / ray.direction.x; 

        if (tmin > tmax) {
            double t = tmin;
            tmin = tmax;
            tmax = t;
        } 

        double tymin = (min.y - ray.origin.y) / ray.direction.y; 
        double tymax = (max.y - ray.origin.y) / ray.direction.y; 

        if (tymin > tymax) {
            double t = tymin;
            tymin = tymax;
            tymax = t;
        } 

        if ((tmin > tymax) || (tymin > tmax)) 
            return 0; 

        if (tymin > tmin) 
            tmin = tymin; 

        if (tymax < tmax) 
            tmax = tymax; 

        double tzmin = (min.z - ray.origin.z) / ray.direction.z; 
        double tzmax = (max.z - ray.origin.z) / ray.direction.z; 

        if (tzmin > tzmax) {
            double t = tzmin;
            tzmin = tzmax;
            tzmax = t;
        } 

        if ((tmin > tzmax) || (tzmin > tmax)) 
            return 0; 

        if (tzmin > tmin) 
            tmin = tzmin; 

//        if (tzmax < tmax) 
//            tmax = tzmax; 

        return retVal(tmin);
    }

    @Override
    Normal getPointNormal(Point p) {
        return new Normal(p.sub(mid));
    }
    
}
