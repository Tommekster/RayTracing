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
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author zikmundt
 */
public class Painture {
    static final int SAMPLES = 8;
    
    int width = 640;
    int height = 480;
    double scale = 2.5;
    BufferedImage buffer = null;
    Scene scene;
    Random random = new Random();

    public Painture(Scene s) {
        scene = s;
    }
    
    
    
    void createImage(){
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        
        
        Sphere sphere = new Sphere(new Point(), 60, new Shade(1,0,0));
        
        for(int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                trace(x, y);
            }
        }
    }

    void trace(int x, int y) {
        Shade shade = new Shade();
        for(int row = 0; row < SAMPLES; row++){
            for(int col = 0; col < SAMPLES; col++){
                Ray ray = new Ray(new Point(scale*(x-width/2+(col+random.nextFloat())/SAMPLES),
                        scale*(y-height/2+(row+random.nextFloat())/SAMPLES), 100),
                        new Vector(0,0,-1));
                GeometricObject hitobj = scene.hitObject(ray);
                if(hitobj != null){
                    shade.add(hitobj.shade);
                }
            }
        }
        buffer.setRGB(x, y, shade.div(SAMPLES*SAMPLES).toRGB());
    }
    
    void saveFile(String filename) throws IOException{
        File file = new File(filename);
        ImageIO.write(buffer, "PNG", file);
    }
}
