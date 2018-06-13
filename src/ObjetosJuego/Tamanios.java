

package ObjetosJuego;

/**
 *
 * @author Agustina Francalancia
 */

import Graficos.Assets;
import java.awt.image.BufferedImage;


public enum Tamanios {
    
    GRANDE(2, Assets.medios), MEDIO(2, Assets.chicos), CHICO(2, Assets.minimos), MINIMO(0, null);      // Divisiones de meteoros
    
    public int cantidad;
    
    public BufferedImage[] texturas;
    
    private Tamanios (int cantidad, BufferedImage[] texturas){
        this.cantidad = cantidad;
        this.texturas = texturas;
    }
    
}
