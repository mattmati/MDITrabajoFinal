

package ObjetosJuego;

import Graficos.Assets;
import Input.Teclado;
import Math.Vector2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Player extends ObjetoMovil {
    
    private Vector2D heading;                                                   // Representa hacia donde mira la nave
    private Vector2D acelera;
    private final double ACC = 0.2;
    private final double deltaAngulo = 0.1;
    
    
    public Player(Vector2D posicion, Vector2D velocidad, double maxVel, BufferedImage textura) {
        super(posicion, velocidad, maxVel, textura);
        heading = new Vector2D(0, 1);
        acelera = new Vector2D();
    }

    @Override
    public void actualizar() {
        
        if(Teclado.RIGHT)
            angulo += deltaAngulo;                                               // Rotación 18°
        if(Teclado.LEFT)
            angulo -= deltaAngulo;                                               // Rotación 18°
        
        if (Teclado.UP){
            acelera = heading.scale(ACC);                                       // El vector acelera va a ser un vector que apunta en la dirección "heading" 
                                                                                        // pero que su magnitud es de 0.2          
        } else {
            if (velocidad.getMagnitud() != 0)
                acelera = (velocidad.scale(-1).normalizar()).scale(ACC / 2);
        }
            
        
        velocidad = velocidad.add(acelera);
        velocidad.limite(maxVel);
        
        heading = heading.setDireccion(angulo - Math.PI/2);                     // Rotación nave 90°
        
        posicion = posicion.add(velocidad);

    }

    @Override
    public void dibujar(Graphics g) {
        
        Graphics2D g2D = (Graphics2D) g;
        at = AffineTransform.getTranslateInstance(posicion.getX(), posicion.getY());
        at.rotate(angulo, Assets.player.getWidth() / 2, Assets.player.getHeight() / 2);
        
        g2D.drawImage(Assets.player, at, null);
    }    
}
