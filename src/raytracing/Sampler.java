/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

import java.util.Random;

/**
 *
 * @author zikmundt
 */
public abstract class Sampler {
    int samples;
    abstract Point sample(int row, int col, int x, int y);
    
    static class Regular extends Sampler {
        public Regular() {
            this(8);
        }
        public Regular(int _samples) {
            samples = _samples;
        }

        @Override
        Point sample(int row, int col, int x, int y) {
            return new Point((x+(col+0.5)/samples),
                            (y+(row+0.5)/samples),0);
        }
    }
    
    static class Jittered extends Sampler {
        Random random = new Random();

        @Override
        Point sample(int row, int col, int x, int y) {
            return new Point((x+(col+random.nextFloat())/samples),
                            (y+(row+random.nextFloat())/samples),0);
        }
    }

}
