

package Input;

/**
 *
 * @author Agustina Francalancia
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Teclado implements KeyListener{
    
    private boolean [] teclas = new boolean[256];                               // Tamaño grande así es más fácil comprobar el estado de las teclas que se utilizan
    
    public static boolean UP, LEFT, RIGHT, SHOOT;                               // Se determinan las teclas para jugar con las flechas de dirección
            
    public Teclado(){
        
        UP = false;
        LEFT = false;
        RIGHT = false;
        SHOOT = false;
    }

    public void actualizar(){
        
        UP = teclas[KeyEvent.VK_UP];
        LEFT = teclas[KeyEvent.VK_LEFT];
        RIGHT = teclas[KeyEvent.VK_RIGHT];
        SHOOT = teclas[KeyEvent.VK_SPACE];
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {                                        // Cada vez que se presione una tecla se llama a este método 
        teclas[e.getKeyCode()] = true;                                                                                //y la información de qué tecla fue presionada se guarda en → KeyEvent        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        teclas[e.getKeyCode()] = false;

    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
}
