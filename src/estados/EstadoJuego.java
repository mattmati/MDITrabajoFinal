

package estados;

import Graficos.Assets;
import Math.Vector2D;
import ObjetosJuego.Player;
import java.awt.Graphics;


public class EstadoJuego {
    
    private Player player;
    
    public EstadoJuego(){
        
        player = new Player(new Vector2D(100, 500), new Vector2D(), 5, Assets.player);
        
    }
    
    public void actualizar(){
        player.actualizar();;
        
    }
    
    public void dibujar(Graphics g){
        
        player.dibujar(g);
    
    }
    
}
