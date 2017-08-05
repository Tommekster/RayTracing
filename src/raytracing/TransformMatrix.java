/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.toRadians;

/**
 *
 * @author zikmundt
 */
public class TransformMatrix { // for homogenous points
    double width = 1, height = 1, depth = 1;
    double yaw = 0, roll = 0;
    Point position = new Point();

    public TransformMatrix() {
    }
    
    TransformMatrix setPosition(Point p){
        position.set(p);
        return this;
    }
    
    TransformMatrix setWidth(double d){
        width = d;
        return this;
    }
    
    TransformMatrix setHeight(double d){
        height = d;
        return this;
    }
    
    TransformMatrix setDepth(double d){
        depth = d;
        return this;
    }
    
    TransformMatrix setScale(double d){
        width = d;
        height = d;
        depth = d;
        return this;
    }
    
    TransformMatrix setScale(double w, double h, double d){
        width = w;
        height = h;
        depth = d;
        return this;
    }
    
    TransformMatrix setRotation(double p, double r){
        yaw = toRadians(p);
        roll = toRadians(r);
        return this;
    }
    
    TransformMatrix setYaw(double d){
        yaw = toRadians(d);
        return this;
    }
    
    TransformMatrix setRoll(double d){
        roll = toRadians(d);
        return this;
    }
    
    double [] getMatrix(){
        if(yaw == 0 && roll == 0)
            return new double [] {
                width, 0, 0, 
                0, height, 0, 
                0, 0, depth
            };
        
        if(roll == 0)
            return new double [] {
                width*cos(yaw), -height*sin(yaw), 0, 
                width*sin(yaw), height*cos(yaw), 0, 
                0, 0, depth
            };
        
        return new double [] {
            width*cos(yaw)*cos(roll), -height*sin(yaw), -depth*cos(yaw)*sin(roll), 
            width*sin(yaw)*cos(roll), height*cos(yaw), -depth*sin(yaw)*sin(roll), 
            width*sin(roll), 0, depth*cos(roll)
        };
    }
    
    Point transform(Point p){
        Point q = new Point();
        double [] m = getMatrix();
        /*
        dst.x = src.x * m[0][0] + src.y * m[1][0] + src.z * m[2][0] + m[3][0]; 
        dst.y = src.x * m[0][1] + src.y * m[1][1] + src.z * m[2][1] + m[3][1]; 
        dst.z = src.x * m[0][2] + src.y * m[1][2] + src.z * m[2][2] + m[3][2]; 
        T w = src.x * m[0][3] + src.y * m[1][3] + src.z * m[2][3] + m[3][3]; 
        if (w != 1 && w != 0) { 
            dst.x = x / w; 
            dst.y = y / w; 
            dst.z = z / w; 
        } 
        */
        q.x = p.x * m[0] + p.y * m[3] + p.z * m[6] + position.x;
        q.y = p.x * m[1] + p.y * m[4] + p.z * m[7] + position.y;
        q.z = p.x * m[2] + p.y * m[5] + p.z * m[8] + position.z;
        //double w = p.x * m[0] + p.y * m[3] + p.z * m[6] + position.x;
        
        return q;
    }
    
    Point [] transform(Point [] points){
        Point [] newPoints = new Point[points.length];
        int i = 0;
        for(Point p : points){
            newPoints[i++] = transform(p);
        }
        return newPoints;
    }
}
