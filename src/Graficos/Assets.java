

package Graficos;

/**
 *
 * @author Agustina Francalancia
 */

import java.awt.Font;
import java.awt.image.BufferedImage;


public class Assets {
    
    // Naves
    public static BufferedImage player;
    
    // Efectos
    public static BufferedImage speed;
    
    // Lasers
    public static BufferedImage blueLaser, greenLaser, magentaLaser, violetLaser;
    
    // Meteoros
    public static BufferedImage[] grandes = new BufferedImage[4];
    public static BufferedImage[] medios = new BufferedImage[2];
    public static BufferedImage[] chicos = new BufferedImage[2];
    public static BufferedImage[] minimos = new BufferedImage[2];

    // Explosiones
    public static BufferedImage[] expl = new BufferedImage[9];
    
//    // UFO
//    public static BufferedImage UFO;
    
    // Números
    public static BufferedImage[] numeros = new BufferedImage[11];
    
    
    // Vida
    public static BufferedImage vida;
    
    
    //Fuente
    public static Font fontBig;
    public static Font fontMed;
    
    
//-----------------------------------------------------------------------------    
    
    // Inicialización
    
    public static void init(){
        
        // Naves
        player = Loader.ImageLoader("/Naves/player.png");
        
        
        // Efectos
        speed = Loader.ImageLoader("/Efectos/greenfire.png");
        
        
        // Lasers
        blueLaser = Loader.ImageLoader("/Lasers/bluelaser.png");
        greenLaser = Loader.ImageLoader("/Lasers/greenlaser.png");
        magentaLaser = Loader.ImageLoader("/Lasers/magentalaser.png");
        violetLaser = Loader.ImageLoader("/Lasers/violetlaser.png");
        
        
        // Meteoros
        for (int i = 0; i < grandes.length; i++)
            grandes[i] = Loader.ImageLoader("/Meteoros/grande" + (i+1) + ".png");
        
        for (int i = 0; i < medios.length; i++)
            medios[i] = Loader.ImageLoader("/Meteoros/medio" + (i+1) + ".png");
        
        for (int i = 0; i < chicos.length; i++)
            chicos[i] = Loader.ImageLoader("/Meteoros/chico" + (i+1) + ".png");
        
        for (int i = 0; i < minimos.length; i++)
            minimos[i] = Loader.ImageLoader("/Meteoros/minimo" + (i+1) + ".png");
        
        // Explosiones
        for (int i = 0; i < expl.length; i++)
            expl[i] = Loader.ImageLoader("/Explosiones/" + i + ".png");
        
//        // UFO
//        UFO = Loader.ImageLoader("/UFO/ufoBlue.png");

        
        // Números
        for(int i = 0; i < numeros.length; i++)
            numeros[i] = Loader.ImageLoader("/Numeros/" + i + ".png");

        // Vida
        vida = Loader.ImageLoader("/Vida/vida.png");
        
        
        // Font
        fontBig = Loader.loadFont("/Fuentes/FutureFont.ttf", 42);
        fontMed = Loader.loadFont("/Fuentes/FutureFont.ttf", 20);
        
        
    }
}
