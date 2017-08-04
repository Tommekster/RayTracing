/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

import java.io.IOException;

/**
 *
 * @author zikmundt
 */
public class RayTracing {
    public static final double MINIMAL_VALUE = 1E-4;
    public static final double BIAS = 1E-4;
    public static final int MAX_DEPTH = 5;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Painture p = new Painture(new Scene3());
        System.out.println("generating thumbnail...");
        //p.setDimensions(160, 120, 4);
        p.createImage();
       
        MainWindow mw = new MainWindow();
        mw.displayImage(p.buffer);
        mw.setVisible(true);
        
        p.setDimensions(640, 480, 1);
        p.tracer.sampler = new Sampler.Regular(2);
        p.createImage();
        mw.displayImage(p.buffer);
        //p.saveFile("image.png");
        
        p.setDimensions(1280, 960, 0.5);
        p.createImage();
        mw.displayImage(p.buffer);
        
        if(false) {
            System.out.println("generating image...");
            p.setDimensions(1280, 960, 0.5);
            p.tracer.sampler = new Sampler.Jittered(4);
            p.createImage();
            mw.displayImage(p.buffer);

            System.out.println("saving to image.png");
            p.saveFile("image.png");
        }
    }
    
}
