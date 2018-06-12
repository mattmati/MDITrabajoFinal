

package ObjetosJuego;

import Math.Vector2D;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public abstract class ObjetoMovil extends ObjetoJuego {
    
    protected Vector2D velocidad;
    protected AffineTransform at;                                               // Este objeto nos ayuda a rotar los objetos
    protected double angulo;                                                    // Representa hacia donde apunta
    protected double maxVel;

    public ObjetoMovil(Vector2D posicion, Vector2D velocidad, double maxVel, BufferedImage textura) {
        super(posicion, textura);
        this.velocidad = velocidad;
        this.maxVel = maxVel;
        angulo = 0;
    } 
}
