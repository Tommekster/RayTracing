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
public abstract class Projection {
    Point eye;
    Point lookat;
    double distance;
    Vector u,v,w;
    
    abstract Ray createRay(Point origin);
    final void computeVectors(){
        w = new Vector(eye.sub(lookat));
        w.normalize();
        
        Vector up = new Vector(0.0001234, 1, 0.1355);
        
        u = up.cross(w);
        u.normalize();
        
        v = w.cross(u);
        v.normalize();
    }
    
    static class Orthogonal extends Projection{
        @Override
        Ray createRay(Point origin) {
            origin.z = 100;
            Ray ray = new Ray(origin, new Vector(0, 0, -1));
            
            return ray;
        }
    }
    
    static class Perspective extends Projection{

        public Perspective(int height, Point _eye, Point _lookat, double FOV) {
            eye = new Point(_eye);
            lookat = new Point(_lookat);
            // tan(theta) = view_height/(2distance);
            double dist = (height/2.0)/Math.tan(Math.toRadians(FOV));
            distance = dist;
            
            computeVectors();
        }
        
        @Override
        Ray createRay(Point point) {
            Ray ray = new Ray(eye, new Vector(u.mul(point.x).add(v.mul(point.y).sub(w.mul(-distance)))));
            ray.direction.normalize();
            
            return ray;
        }
    }
}
