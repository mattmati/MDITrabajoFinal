
package Graficos;

import Math.Vector2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 *
 * @author Agustina Francalancia
 */
public class Texto {
    
    public static void dibujaTexto(Graphics g, String texto, Vector2D posi, boolean centro, Color color, Font font){
        
        g.setColor(color);
        g.setFont(font);
        Vector2D posicion = new Vector2D(posi.getX(), posi.getY());
        
        if(centro){
            FontMetrics fm = g.getFontMetrics();
            posicion.setX(posicion.getX() - fm.stringWidth(texto) / 2);
            posicion.setY(posicion.getY() - fm.getHeight() / 2);
        }
        g.drawString(texto, (int)posicion.getX(), (int)posicion.getY());
    }
    
}
