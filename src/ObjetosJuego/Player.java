

package ObjetosJuego;

/**
 *
 * @author Agustina Francalancia
 */

import Ejecutable.Ventana;
import Graficos.Assets;
import Input.Teclado;
import Math.Vector2D;
import estados.EstadoJuego;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Player extends ObjetoMovil {
    
    private Vector2D heading;                                                   // Representa hacia donde mira la nave
    private Vector2D acelera;
    private final double ACC = 0.2;
    private final double deltaAngulo = 0.1;
    private boolean acelerando = false;
    private Cronometro fireRate;
    
    private boolean spawning, visible;
    
    private Cronometro spawnTime, flickerTime;
    
    
    public Player(Vector2D posicion, Vector2D velocidad, double maxVel, BufferedImage textura, EstadoJuego estadoJuego) {
        super(posicion, velocidad, maxVel, textura, estadoJuego);
        
        heading = new Vector2D(0, 1);
        acelera = new Vector2D();
        fireRate = new Cronometro();
        spawnTime = new Cronometro();
        flickerTime = new Cronometro();

    }
    

    @Override
    public void actualizar() {
        
        if (!spawnTime.isRunning()){
            spawning = false;
            visible = true;
        }
        
        if(spawning){
            if (!flickerTime.isRunning()){
                flickerTime.run(Constantes.FLICKER_TIME);
                visible = !visible;
            }
        }
        
        
        if (Teclado.SHOOT && !fireRate.isRunning() && !spawning){
                    estadoJuego.getObjetosMoviles().add(0, new Laser(
                    getCenter().add(heading.scale(width)),
                    heading,
                    10,
                    angulo,
                    Assets.violetLaser,
                    estadoJuego
            ));
            fireRate.run(Constantes.FIRERATE);
        }
    
        
        if(Teclado.RIGHT)
            angulo += deltaAngulo;                                               // Rotación 18°
        if(Teclado.LEFT)
            angulo -= deltaAngulo;                                               // Rotación 18°
        
        if (Teclado.UP){
            acelera = heading.scale(ACC);                                       // El vector acelera va a ser un vector que apunta en la dirección "heading" 
            acelerando = true;                                                          // pero que su magnitud es de 0.2          
        } else {
            if (velocidad.getMagnitud() != 0)
                acelera = (velocidad.scale(-1).normalizar()).scale(ACC/2);
            acelerando = false;
        }
            
        
        velocidad = velocidad.add(acelera);
        velocidad.limite(maxVel);
        
        heading = heading.setDireccion(angulo - Math.PI/2);                     // Rotación nave 90°
        
        posicion = posicion.add(velocidad);
        
        if (posicion.getX() > Constantes.WIDTH)                                 // Si la posición del componente en X es mayor a la de la ventana...
            posicion.setX(0);                                                   // ...saldrá por el lado izquierdo (desde el derecho)    
        if (posicion.getY() > Constantes.HEIGHT)                                // Lo mismo con componente Y...
            posicion.setY(0);                                                   // ...saldrá por el lado de arriba
            
        if (posicion.getX() < 0)                                                
            posicion.setX(Constantes.WIDTH);                                       // Posición nueva será el ancho de la ventana
        if (posicion.getY() < 0)
            posicion.setY(Constantes.HEIGHT);     
        
        fireRate.actualizar();
        spawnTime = new Cronometro();
        flickerTime = new Cronometro();
        collidesWith();
    }

    @Override
    public void Destroy(){
        spawning = true;
        spawnTime.run(Constantes.SPAWNING_TIME);
        resetValues();
        estadoJuego.substractLife();
    }
        
    private void resetValues(){
        
        angulo = 0;
        velocidad = new Vector2D();
        posicion = new Vector2D(Constantes.WIDTH/2 - Assets.player.getWidth()/2,
				Constantes.HEIGHT/2 - Assets.player.getHeight()/2);
    }
    
    
    @Override
    public void dibujar(Graphics g) {
        
        if(!visible)
            return;

	Graphics2D g2D = (Graphics2D)g;
		
	AffineTransform at1 = AffineTransform.getTranslateInstance(posicion.getX() + width/2 + 10,
				posicion.getY() + height/2 + 10);
		
	AffineTransform at2 = AffineTransform.getTranslateInstance(posicion.getX() + 5, posicion.getY() + height/2 + 10);
		
	at1.rotate(angulo, -10, -10);
	at2.rotate(angulo, width/2 -5, -10);
		
	if(acelerando) {
		g2D.drawImage(Assets.speed, at1, null);
		g2D.drawImage(Assets.speed, at2, null);
	}

	at = AffineTransform.getTranslateInstance(posicion.getX(), posicion.getY());
		
	at.rotate(angulo, width/2, height/2);
		
        g2D.drawImage(textura, at, null);
    }    
    
    public boolean isSpawning(){
        return spawning;
    }
    
}
