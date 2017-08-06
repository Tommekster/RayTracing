/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

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
}
