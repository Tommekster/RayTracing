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
public class Triangle extends Plane {
    Point b,c;
    double area;
    Vector ba, cb, ac;
    boolean singleSide = false;
    static Shade [] colors = {new Shade(0xff0000), new Shade(0x00ff00), new Shade(0x0000ff)};
    Normal [] vertNormals = null;
    Point [] uvs = null;
    Texture texture;
    
    public Triangle(Point a, Point b, Point c, Shade _shade) {
        super(a, new Normal(b.sub(a).cross(c.sub(a))), _shade);
        //this.a = a;
        this.b = b;
        this.c = c;
        computeEdges();
    }

    final void computeEdges() {
        ba = b.sub(point);
        cb = c.sub(b);
        ac = point.sub(c);
        area = ba.cross(c.sub(point)).getMagnitude() / 2;
    }
    
    Triangle(Point a, Point b, Point c, Shade _shade, MaterialType _type) {
        this(a,b,c,_shade);
        type = _type;
        if(_type == MaterialType.ReflectionAndRefraction) ior = 1.2;
    }

    @Override
    double hit(Ray ray) {
        double t = super.hit(ray);
        if(t > 0){
            // 1. find P
            Point p = ray.origin.add(ray.direction.mul(t));
            
            // 2. is P inside triangle?
            if(normal.dot(ba.cross(p.sub(point))) < 0) return 0; // p is on the right side of (b-a)
            if(normal.dot(cb.cross(p.sub(b))) < 0) return 0; // p is on the right side of (b-a)
            if(normal.dot(ac.cross(p.sub(c))) < 0) return 0; // p is on the right side of (b-a)
            
            if(singleSide && normal.dot(ray.origin) > 0) return 0; // looking from opposite side
        }
        return t;
    }
    
    Point getUVcoordinates(Point p){
        double u = cb.cross(p.sub(b)).getMagnitude()/2/area;
        double v = ac.cross(p.sub(c)).getMagnitude()/2/area;
        
        return new Point(u,v,1);
    }
    
    @Override
    Shade getTexture(Point uv){
        if(texture == null || uvs == null){
            Shade tex = colors[0].mul(uv.x);
            tex.add(colors[1].mul(uv.y));
            tex.add(colors[2].mul(1 - uv.x - uv.y));
            return tex;
        }
        return texture.getColor(uvs, uv);
    }
    
    @Override
    Normal getPointNormal(Point p) {
        if(vertNormals == null) return super.getPointNormal(p);
        Point uv = getUVcoordinates(p);
        return new Normal(vertNormals[2].mul(1 - uv.x - uv.y)
                .add(vertNormals[0].mul(uv.x))
                .add(vertNormals[1].mul(uv.y)));
    }
    
    Triangle transform(TransformMatrix m){
        point = m.transform(point);
        b = m.transform(b);
        c = m.transform(c);
        
        computeEdges();
        
        normal = new Normal(b.sub(point).cross(c.sub(point)));
        
        return this;
    }
    
    public String toString(){
        return point.toString() + " " + b.toString() + " " + c.toString();
    }
}
