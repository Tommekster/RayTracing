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
    abstract Ray createRay(Point origin);
    
    static class Orthogonal extends Projection{
        @Override
        Ray createRay(Point origin) {
            origin.z = 100;
            Ray ray = new Ray(origin, new Vector(0, 0, -1));
            
            return ray;
        }
    }
}
