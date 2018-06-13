

package ObjetosJuego;

/**
 *
 * @author Agustina Francalancia
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import Graficos.Assets;
import Math.Vector2D;
import estados.EstadoJuego;

public class Meteoros extends ObjetoMovil{

	private Tamanios tamaños;

    public Meteoros(Vector2D posicion, Vector2D velocidad, double maxVel, BufferedImage textura, EstadoJuego estadoJuego, Tamanios tamaños) {
        super(posicion, velocidad, maxVel, textura, estadoJuego);
        this.tamaños = tamaños;
        this.velocidad = velocidad.scale(maxVel);
    }

	
    @Override
    public void actualizar() {
  
		posicion = posicion.add(velocidad);
		
		if(posicion.getX() > Constantes.WIDTH)
			posicion.setX(-width);
		if(posicion.getY() > Constantes.HEIGHT)
			posicion.setY(-height);
		
		if(posicion.getX() < -width)
			posicion.setX(Constantes.WIDTH);
		if(posicion.getY() < -height)
			posicion.setY(Constantes.HEIGHT);
		
		angulo += Constantes.DELTAANGLE/2;
    }
    
        
    @Override
    public void Destroy(){
        
        estadoJuego.divisionMeteoro(this);
        estadoJuego.addPuntaje(Constantes.METEOR_SCORE, posicion);
        super.Destroy();
    }

    @Override
    public void dibujar(Graphics g) {
		
		Graphics2D g2D = (Graphics2D)g;
		
		at = AffineTransform.getTranslateInstance(posicion.getX(), posicion.getY());
		
		at.rotate(angulo, width/2, height/2);
		
		g2D.drawImage(textura, at, null);
	}

	public Tamanios getTamanios(){
		return tamaños;
	}
}

