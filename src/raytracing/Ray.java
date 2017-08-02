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
public class Ray {
    Point origin;
    Vector direction;
    
    Ray(Point _origin, Vector _direction){
        origin = new Point(_origin);
        direction = new Vector(_direction);
        direction.normalize();
    }
}
