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
public class Point {
    double x,y,z;
    Point(){
        this(0,0,0);
    }
    Point(double _x, double _y, double _z){
        x = _x;
        y = _y;
        z = _z;
    }
    Point(Point p){
        x = p.x;
        y = p.y;
        z = p.z;
    }
    Point add(Point p){
        return new Point(x + p.x, y + p.y, z + p.z);
    } 
    Point sub(Point p){
        return new Point(x - p.x, y - p.y, z - p.z);
    }
    double dot(Point v){
        return x * v.x + y * v.y + z * v.z;
    }
}
