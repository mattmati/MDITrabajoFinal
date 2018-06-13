

package Graficos;

import Math.Vector2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Agustina Francalancia
 */
public class Animacion {
    
    private BufferedImage[] frames;                                             // Nro total de fotogramas
    private int velocidad;                                                      // Velocidad en el cual se cambia de un frame al siguiente
    private boolean running;                                                    // Animacion corre o no (false = termina animación)
    private int index;                                                          // Indice fotograma actual
    private Vector2D posicion;                                                  // Representa posición inicial
    private long time, lastTime;                                                // Controla tiempo actual
    
    public Animacion(BufferedImage[] frames, int velocidad, Vector2D posicion){
        
        this.frames = frames;
        this.velocidad = velocidad;
        this.posicion = posicion;
        index = 0;
        running = true;
        time = 0;
        lastTime = System.currentTimeMillis();
        
    }
    
    public void actualizar(){
        
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        
        if (time > velocidad){                                                  // Si tiempo es mayor a velocidad, está listo para cambiar de fotograma
            time = 0;
            index++;
            if(index >= frames.length){                                         // Si indice se sale del tamaño de fotograma...
                running = false;                                                // ...termina animación
            }
        } 
    }

    public boolean isRunning() {
        return running;
    }

    public Vector2D getPosicion() {
        return posicion;
    }
    
    public BufferedImage getCurrentFrame(){                                     // Obtiene fotograma actual en arreglo de fotogramas
        return frames[index];
    }
}
