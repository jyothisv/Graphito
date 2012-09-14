package graphito.graph.layout;

public class Vector2D {
    double x, y;


    public Vector2D()
    {
        x = 0;
        y = 0;
    }
    
    public Vector2D(double u, double v)
    {
        x = u;
        y = v;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public double mod()
    {
        return Math.sqrt(x*x + y*y);
    }

    // For speedup, a function which directly returns the square of the magnitude.
    public double mod2()
    {
        return x*x + y*y;
    }


    // normal vector
    public Vector2D norm()
    {
        double root = this.mod();
        x/=root;
        y/=root;
        // return new Vector2D(x/root, y/root);
        return this;
    }

    public void norm(Vector2D res)
    {
        double root = this.mod();
        res.x = x/root;
        res.y = y/root;
    }



    public Vector2D add(Vector2D that)
    {
        x += that.x;
        y += that.y;
        return this;
    }


    public void add(Vector2D that, Vector2D res)
    {
        res.x = x + that.x;
        res.y = y + that.y;
    }


    
    public Vector2D minus(Vector2D that)
    {
        x -= that.x;
        y -= that.y;
        return this;
    }

    public void minus(Vector2D that, Vector2D res)
    {
        res.x = x - that.x;
        res.y = y - that.y;
    }


    
    public Vector2D div(double divisor)
    {
        x /= divisor;
        y /= divisor;
        return this;
    }

    public void div(double divisor, Vector2D res)
    {
        res.x = x / divisor;
        res.y = y / divisor;
    }


    public Vector2D mult(double m)
    {
        x *= m;
        y *= m;
        return this;
    }

    public void mult(double m, Vector2D res)
    {
        res.x = x * m;
        res.y = y * m;
    }

    public Vector2D neg()
    {
        x = -x;
        y = -y;
        return this;
    }

    public void neg(Vector2D res)
    {
        res.x = -x;
        res.y = -y;
    }
    

    public Vector2D zero()
    {
        x = 0;
        y = 0;
        return this;
    }



    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }

    
}
    
