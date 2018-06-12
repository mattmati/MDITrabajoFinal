

package Graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Loader {
    
    public static BufferedImage ImageLoader (String path){                      // BufferedImage: forma en la que Java guarda imagenes en la memoria / path → ruta de imagen
                  
        try {
            return ImageIO.read(Loader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
}
}
