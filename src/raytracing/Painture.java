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
    double scale = 0.25;
    BufferedImage buffer = null;
    
    void createImage(){
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        
        Sphere sphere = new Sphere(new Point(), 60, new Shade(1,0,0));
        
        for(int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                Shade shade = new Shade();
                
                for(int row = 0; row < 8; row++){
                    for(int col = 0; col < 8; col++){
                        Ray ray = new Ray(new Point(scale*(x-width/2+(col+0.5)/8), 
                                scale*(y-height/2+(row+0.5)/8), 100), 
                                new Vector(0,0,-1));
                        if(sphere.hit(ray) != 0.0){
                            shade.add(sphere.shade);
                        }
                    }
                }
                buffer.setRGB(x, y, shade.div(64).toRGB());
            }
        }
    }
    
    void saveFile(String filename) throws IOException{
        File file = new File(filename);
        ImageIO.write(buffer, "PNG", file);
    }
}
