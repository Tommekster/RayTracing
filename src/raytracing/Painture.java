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
public class Painture {
    int width = 640;
    int height = 480;
    /*int width = 1024;
    int height = 768;*/
    double scale = 2;
    BufferedImage buffer = null;
    Scene scene;
    Sampler sampler = new Sampler.Regular(4);
    Projection projection = new Projection.Perspective(height,new Point(-100, 200, 300), new Point(0, 0, 0), 45);
    //Projection projection = new Projection.Orthogonal();
    Tracer tracer;

    public Painture(Scene s) {
        scene = s;
        tracer = new Tracer(scene, sampler, projection, width, height, scale);
    }
    
    
    
    void createImage(){
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        
        
        Sphere sphere = new Sphere(new Point(), 60, new Shade(1,0,0));
        
        for(int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                buffer.setRGB(x, height-y-1 /* reverse coord*/, tracer.trace(x, y));
            }
        }
    }
    
    void saveFile(String filename) throws IOException{
        File file = new File(filename);
        ImageIO.write(buffer, "PNG", file);
    }
}
