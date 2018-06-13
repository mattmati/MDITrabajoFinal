

package ObjetosJuego;

/**
 *
 * @author Agustina Francalancia
 */

import Math.Vector2D;
import estados.EstadoJuego;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public abstract class ObjetoMovil extends ObjetoJuego {

    
    protected Vector2D velocidad;
    protected AffineTransform at;                                               // Este objeto nos ayuda a rotar los objetos
    protected double angulo;                                                    // Representa hacia donde apunta
    protected double maxVel;
    protected int width;
    protected int height;
    protected EstadoJuego estadoJuego;

    public ObjetoMovil(Vector2D posicion, Vector2D velocidad, double maxVel, BufferedImage textura, EstadoJuego estadoJuego) {
        super(posicion, textura);
        this.velocidad = velocidad;
        this.maxVel = maxVel;
        this.estadoJuego = estadoJuego;
        width = textura.getWidth();
        height = textura.getHeight();
        angulo = 0;
    } 
    
    protected void collidesWith(){
		
		ArrayList<ObjetoMovil> objetosMoviles = estadoJuego.getObjetosMoviles(); 
		
		for(int i = 0; i < objetosMoviles.size(); i++){
			
			ObjetoMovil m  = objetosMoviles.get(i);
			
			if(m.equals(this))
				continue;
			
			double distance = m.getCenter().substract(getCenter()).getMagnitud();
			
			if(distance < m.width/2 + width/2 && objetosMoviles.contains(this)){
				objectCollision(m, this);
			}
		}
	}
	
	private void objectCollision(ObjetoMovil a, ObjetoMovil b){
            
            if(a instanceof Player && ((Player)a).isSpawning()) {
			return;
            }
            if(b instanceof Player && ((Player)b).isSpawning()) {
			return;
            }
            
		if(!(a instanceof Meteoros && b instanceof Meteoros)){
                        estadoJuego.playExplosion(getCenter());
			a.Destroy();
			b.Destroy();
		}
		
}

       
    protected void Destroy(){
        estadoJuego.getObjetosMoviles().remove(this);                           // Se determina qué objetos se destruyen al colisonar (excepto meteoros)
    }
            
    protected Vector2D getCenter(){
        return new Vector2D(posicion.getX() + width/2, posicion.getY() + height/2);     // Obtenemos el centro de la nave
    }
}
