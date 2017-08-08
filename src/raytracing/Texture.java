/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author zikmundt
 */
public class Texture {
    BufferedImage bitmap;
    
    public Texture(String image) throws IOException {
        bitmap = ImageIO.read(new File(image));
    }
    
    Shade getColor(Point [] triangle, Point uv){
        Point point = triangle[0].mul(uv.x).add(triangle[1].mul(uv.y)).add(triangle[2].mul(1-uv.x-uv.y));
        int x = (int)(point.x*bitmap.getWidth());
        int y = (int)(point.y*bitmap.getHeight());
        
        return new Shade(bitmap.getRGB(x, y));
    }
    
    Shade getColor(Point point){
        int x = (int)(point.x*bitmap.getWidth());
        int y = (int)(point.y*bitmap.getHeight());
        
        return new Shade(bitmap.getRGB(x, y));
    }
    
    Vector getBump(Point point){
        double center, c1;
        double diffx, diffy;
        int x = (int)(point.x*bitmap.getWidth());
        int y = (int)(point.y*bitmap.getHeight());
        
        center = new Shade(bitmap.getRGB(x, y)).r;
        if(x > 0) {
            diffx = center - new Shade(bitmap.getRGB(x-1, y)).r;
        } else {
            diffx = new Shade(bitmap.getRGB(x+1, y)).r - center;
        }
        if(y > 0) {
            diffy = center - new Shade(bitmap.getRGB(x, y-1)).r;
        } else {
            diffy = new Shade(bitmap.getRGB(x, y+1)).r - center;
        }
        
        return new Vector(diffx, diffy, 0);
    }
}
