

package estados;

/**
 *
 * @author Agustina Francalancia
 */

import Graficos.Animacion;
import Graficos.Assets;
import static Graficos.Assets.vida;
import Graficos.Texto;
import Math.Vector2D;
import ObjetosJuego.Constantes;
import ObjetosJuego.Mensaje;
import ObjetosJuego.Meteoros;
import ObjetosJuego.ObjetoMovil;
import ObjetosJuego.Player;
import ObjetosJuego.Tamanios;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class EstadoJuego {
    
public static final Vector2D PLAYER_START_POSITION = new Vector2D(Constantes.WIDTH/2 - Assets.player.getWidth()/2,
Constantes.HEIGHT/2 - Assets.player.getHeight()/2);

    private Player player;
    private ArrayList<ObjetoMovil> objetosMoviles = new ArrayList<ObjetoMovil>();
    private ArrayList<Animacion> explosiones = new ArrayList<Animacion>();
    private ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
    
    
    private int puntaje = 0;
    private int vidas = 3;
    
    private int meteoros;
    private int oleadas = 1;
    
    public EstadoJuego(){
        
       player = new Player(new Vector2D(Constantes.WIDTH/2 - Assets.player.getWidth()/2,
				Constantes.HEIGHT/2 - Assets.player.getHeight()/2), new Vector2D(), Constantes.PLAYER_MAX_VEL, Assets.player, this);
                    objetosMoviles.add(player);
        
        meteoros = 1;
        
        startWave();
    }
    
    
    public void addPuntaje(int valor, Vector2D posicion){
        puntaje += valor;
	mensajes.add(new Mensaje(posicion, true, "+" + valor + " score", Color.WHITE, false, Assets.fontMed, this));    }
    
    public void divisionMeteoro(Meteoros meteoro){
        
        Tamanios tamanios = meteoro.getTamanios();
        BufferedImage[] texturas = tamanios.texturas;
        
        Tamanios newTamanio = null;
        
        switch(tamanios){                                                        // Determinamos tamaños de acuerdo al tamaño
            case GRANDE:                                                            // inicial con respecto a la colisión
                newTamanio = Tamanios.MEDIO;
                break;
            case MEDIO:
                newTamanio = Tamanios.CHICO;
                break;
            case CHICO:
                newTamanio = Tamanios.MINIMO;
                break;
            default:
                return;
        }
        
        for(int i = 0; i < tamanios.cantidad; i++){
            objetosMoviles.add(new Meteoros(
					meteoro.getPosicion(),
					new Vector2D(0, 1).setDireccion(Math.random()*Math.PI*2),
					Constantes.METEORO_VEL*Math.random() + 1,
					texturas[(int)(Math.random()*texturas.length)],
					this,
					newTamanio
					));
            
        }
        
    }
    
    private void startWave(){
            mensajes.add(new Mensaje(new Vector2D(Constantes.WIDTH/2, Constantes.HEIGHT/2), false,
"WAVE " + oleadas, Color.WHITE, true, Assets.fontBig, this));
        
        double x, y;
        
        for (int i = 0; i < meteoros; i++){
            
            x = i % 2 == 0 ? Math.random()*Constantes.WIDTH : 0;                // Si x es igual al contador i, y es par, utilizamos el "módulo" (%), el cual va a devolver 0
                                                                                    // Se pregunta si es verdadero, x es igual a un número aleatorio entre 0 y el ancho de la ventana
                                                                                    // Si no, será 0 (en caso de que sea impar)
            y = i % 2 == 0 ? 0 : Math.random()*Constantes.HEIGHT;               // Si es impar, devuelve 0 y en ese caso si es par la posición de y será 0
                                                                                    // de lo contrario de toma un número aleatorio entre 0 y la altura de la ventana
            BufferedImage textura = Assets.grandes[(int)(Math.random()*Assets.grandes.length)];        // Altura texturas
			objetosMoviles.add(new Meteoros(
					new Vector2D(x, y),
					new Vector2D(0, 1).setDireccion(Math.random()*Math.PI*2),
					Constantes.METEORO_VEL*Math.random() + 1,
					textura,
					this,
					Tamanios.GRANDE
					));
			
		}
		meteoros ++;
}

        
    public void playExplosion(Vector2D posicion){
        explosiones.add(new Animacion(
                Assets.expl,
                50,
                posicion.substract(new Vector2D(Assets.expl[0].getWidth()/2, Assets.expl[0].getHeight()/2))
        ));
    }

        
    public void actualizar(){
        
        for (int i = 0; i < objetosMoviles.size(); i++)
            objetosMoviles.get(i).actualizar();
        
        for (int i = 0; i < explosiones.size(); i++){
            Animacion anim = explosiones.get(i);
            anim.actualizar();
            if(!anim.isRunning()){
                explosiones.remove(i);
            }
        }
        
        for(int i = 0; i < objetosMoviles.size(); i++)
			if(objetosMoviles.get(i) instanceof Meteoros)           
                            return;	
        startWave();
        
    }

    public void dibujar(Graphics g){
        
        Graphics2D g2D = (Graphics2D)g;
        
        // Para que no pierda calidad nuestra nave y no se vea pixelada (Antialiasing):
        
        g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
		for(int i = 0; i < mensajes.size(); i++)
                mensajes.get(i).draw(g2D);
        
        for (int i = 0; i < objetosMoviles.size(); i++)
        objetosMoviles.get(i).dibujar(g);
        
        
        for (int i = 0; i < explosiones.size(); i++){
            Animacion anim = explosiones.get(i);
            g2D.drawImage(anim.getCurrentFrame(), (int)anim.getPosicion().getX(), (int)anim.getPosicion().getY(), null);    
    }
        dibujaPuntaje(g);
        dibujaVidas(g);

}
    
    
    private void dibujaPuntaje(Graphics g){
        Vector2D scorePos = new Vector2D(850, 25);
        String scoreToString = Integer.toString(puntaje);
        
        for(int i = 0; i < scoreToString.length(); i++){
            g.drawImage(Assets.numeros[Integer.parseInt(scoreToString.substring(i, i + 1))],
					(int)scorePos.getX(), (int)scorePos.getY(), null);
            scorePos.setX(scorePos.getX() + 20);
        }
    }
    
    private void dibujaVidas(Graphics g){
		
		Vector2D lifePosition = new Vector2D(25, 25);
		
		g.drawImage(Assets.vida, (int)lifePosition.getX(), (int)lifePosition.getY(), null);
		
		g.drawImage(Assets.numeros[10], (int)lifePosition.getX() + 40,
				(int)lifePosition.getY() + 5, null);
		
		String livesToString = Integer.toString(vidas);
		
		Vector2D pos = new Vector2D(lifePosition.getX(), lifePosition.getY());
		
		for(int i = 0; i < livesToString.length(); i ++)
		{
			int number = Integer.parseInt(livesToString.substring(i, i+1));
			
			if(number <= 0)
				break;
			g.drawImage(Assets.numeros[number],
					(int)pos.getX() + 60, (int)pos.getY() + 5, null);
			pos.setX(pos.getX() + 20);
		}
		
}

    public ArrayList<ObjetoMovil> getObjetosMoviles() {
        return objetosMoviles;
    }

    public ArrayList<Mensaje> getMensajes() {
        return mensajes;
    }
    
    public Player getPlayer() {
	return player;
}
    public void substractLife(){
        vidas--;
    }
   
}
