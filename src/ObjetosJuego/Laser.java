
package ObjetosJuego;

/**
 *
 * @author Agustina Francalancia
 */

import Math.Vector2D;
import estados.EstadoJuego;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Laser extends ObjetoMovil {

    public Laser(Vector2D posicion, Vector2D velocidad, double maxVel, double angulo, BufferedImage textura, EstadoJuego estadoJuego) {
        super(posicion, velocidad, maxVel, textura, estadoJuego);
        this.angulo = angulo;
        this.velocidad = velocidad.scale(maxVel);
    }

    @Override
    public void actualizar() {
        posicion = posicion.add(velocidad);
        if (posicion.getX() < 0 || posicion.getX() > Constantes.WIDTH || 
                posicion.getY() < 0 || posicion.getY() > Constantes.HEIGHT){
            Destroy();                                                          // Elimina los lásers así no quedan "en el infinito"
        }
        
        collidesWith();
    }

    @Override
    public void dibujar(Graphics g) {
        
        Graphics2D g2D = (Graphics2D)g;
		
	at = AffineTransform.getTranslateInstance(posicion.getX() - width/2, posicion.getY());
		
	at.rotate(angulo, width/2, 0);
		
        g2D.drawImage(textura, at, null);
    }
    
    @Override
        public Vector2D getCenter(){
        return new Vector2D(posicion.getX() + width/2, posicion.getY() + width/2);     // Obtenemos el centro de la nave
    }

}
