/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

/**
 *
 * @author zikmundt
 */
public class Sphere extends GeometricObject{
    Point center;
    double radius;
    double radius2;
    
    Normal top = null, right = null, front = null;
    Texture texture = null;

    public Sphere(Point _center, double _radius, Shade _shade) {
        this(_center, _radius, _shade, null, 0);
    }

    Sphere(Point _center, double _radius, Shade _shade, MaterialType _type) {
        this(_center, _radius, _shade, _type, 0);
    }

    Sphere(Point _center, double _radius, Shade _shade, MaterialType _type, double _ior) {
        super(_shade, _type, _ior);
        center = new Point(_center);
        radius = (_radius > 0)?_radius:1;
        radius2 = radius*radius;
    }

    @Override
    double hit(Ray ray) {
        // condition (p-c)*(p-c) = r^2
        double a = ray.direction.dot(ray.direction);
        double b = 2*ray.origin.sub(center).dot(ray.direction);
        double c = ray.origin.sub(center).dot(ray.origin.sub(center))-radius2;
        
        double discriminant = b*b -4*a*c;
        if(discriminant < 0) return 0;
        else {
            return retVal((-b - Math.sqrt(discriminant))/2/a);
        }
    }

    @Override
    Normal getPointNormal(Point p) {
        return new Normal(p.sub(center));
    }
    
    @Override
    Shade getTexture(Point uv){
        if(texture == null){
            return (Math.sin(uv.x*100)*Math.sin(uv.y*50) > 0)?shade:shade.mul(0.5);
            //return (Math.sin(uv.x)*Math.sin(uv.y) > 0)?new Shade(1,1,1):new Shade();
            //return (Math.sin(uv.x*10)*Math.sin(uv.y*10) > 0)?new Shade(1,1,1):new Shade();
        }
        return texture.getColor(uv);
    }
    
    @Override
    Point getUVcoordinates(Point p){
        if(top == null || right == null || front == null){
            top = new Normal(0,1,0);
            right = new Normal(1,0,0);
            front = new Normal(0,0,1);
        }
        Point pc = p.sub(center);
	double pc_top = pc.dot(top);

        double u = Math.acos(pc_top/radius)/2/Math.PI;
	double r2 = pc.dot(right);
	double v;
	if (r2 == 0) 
		v = (pc.dot(front) > 0) ? Math.PI : 0;
	else
		v = Math.atan(pc.dot(front)/r2) + ((r2 < 0)?Math.PI:0) + Math.PI/2;
	v /= Math.PI*2;
        
        return new Point(v,u,1);
    }
    
    void setTexture(Texture texture, Normal top, Normal right){
        this.texture = texture;
        if(top == null || right == null){
            this.top = new Normal(0,1,0);
            this.right = new Normal(1,0,0);
            front = new Normal(0,0,1);
        } else {
            this.top = top;
            this.right = new Normal(right.sub(top.mul(top.dot(right))));
            front = new Normal(top.cross(this.right));
        }
    }
}
