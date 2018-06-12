

package Math;


public class Vector2D {
    
    private double x;
    private double y;
    
    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public Vector2D(){
        
// Inicialización en 0, y no simplemente darle coordenadas específicas
        x = 0;  
        y = 0;
    }
    
    public Vector2D add(Vector2D v){
        return new Vector2D(x + v.getX(), y + v.getY());
    }
    
    public Vector2D scale(double valor){
        return new Vector2D(x * valor, y * valor);                              // Para modificar la magnitud del vector "heading" (dirección)
    }
    
    public void limite(double valor){                                           // Límite de velocidad en eje x y en eje y
        if (x > valor)
            x = valor;
        if (x < -valor)
            x = -valor;
        
        if (y > valor)
            y = valor;
        if (y < -valor)
            y = -valor;
    }
    
    
    public Vector2D normalizar(){
        return new Vector2D(x / getMagnitud(), y / getMagnitud());
    }
    
    public double getMagnitud(){
        return Math.sqrt(x * x + y * y);
    }
    
    public Vector2D setDireccion(double angulo){
        return new Vector2D(Math.cos(angulo) * getMagnitud(), Math.sin(angulo) * getMagnitud());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
}
