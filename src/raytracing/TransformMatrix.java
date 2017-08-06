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
    double rotx = 0, roty = 0, rotz = 0; // rotation along axis x,y,z
    Point position = new Point();
    double [] matrix = null;

    public TransformMatrix() {
    }
    
    TransformMatrix setPosition(Point p){
        position.set(p);
        return this;
    }
    
    TransformMatrix setWidthScale(double d){
        width = d;
        matrix = null;
        return this;
    }
    
    TransformMatrix setHeightScale(double d){
        height = d;
        matrix = null;
        return this;
    }
    
    TransformMatrix setDepthScale(double d){
        depth = d;
        matrix = null;
        return this;
    }
    
    TransformMatrix setScale(double d){
        width = d;
        height = d;
        depth = d;
        matrix = null;
        return this;
    }
    
    TransformMatrix setScale(double w, double h, double d){
        width = w;
        height = h;
        depth = d;
        matrix = null;
        return this;
    }
    
    TransformMatrix setRotation(double rx, double ry, double rz){
        rotx = toRadians(rx);
        roty = toRadians(ry);
        rotz = toRadians(rz);
        matrix = null;
        return this;
    }
    
    TransformMatrix setRotation(double ry){
        roty = toRadians(ry);
        matrix = null;
        return this;
    }
    
    double [] getTransformMatrix(){
        if(matrix != null) return matrix;
        matrix = new double [] {
            width, 0, 0, 
            0, height, 0, 
            0, 0, depth
        };
        if(rotz != 0) {
            double cosz = cos(rotz);
            double sinz = sin(rotz);
            matrix = dotMatrices3x3(new double[]{
                cosz, -sinz, 0,
                sinz, cosz, 0,
                0, 0, 1
            }, matrix);
        }
        if(rotx != 0) {
            double cosx = cos(rotx);
            double sinx = sin(rotx);
            matrix = dotMatrices3x3(new double[]{
                1, 0, 0, 
                0, cosx, -sinx, 
                0, sinx, cosx 
            }, matrix);
        }
        if(roty != 0) {
            double cosy = cos(roty);
            double siny = sin(roty);
            matrix = dotMatrices3x3(new double[]{
                cosy, 0, -siny, 
                0, 1, 0,
                siny, 0, cosy 
            }, matrix);
        }
        
        return matrix;
    }
    
    double [] dotMatrices3x3(double [] A, double [] B){
        double [] C = new double [9];
        int i, j, k;
        
        for(i = 0; i < 3; i++){
            for(j = 0; j < 3; j++){
                C[3*i + j] = 0;
                for(k = 0; k < 3; k++){
                    C[3*i + j] += A[3*i + k] * B[3*k + j];
                }
            }
        }
        return C;
    }
    
    Point transform(Point p){
        Point q = new Point();
        double [] m = getTransformMatrix();
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
