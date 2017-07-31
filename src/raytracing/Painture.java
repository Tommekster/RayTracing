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
public class Painture {
    int width = 640;
    int height = 480;
    BufferedImage buffer = null;
    
    void createImage(){
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        
        Sphere sphere = new Sphere(new Point(), 60, new Shade(1,0,0));
        
        for(int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                Ray ray = new Ray(new Point(x-width/2+0.5, y-height/2+0.5, 100), new Vector(0,0,-1));
                
                if(sphere.hit(ray) != 0.0){
                    buffer.setRGB(x, y, sphere.shade.toRGB());
                } else {
                    buffer.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
    }
    
    void saveFile(String filename) throws IOException{
        File file = new File(filename);
        ImageIO.write(buffer, "PNG", file);
    }
}
