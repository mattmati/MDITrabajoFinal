

package Graficos;

/**
 *
 * @author Agustina Francalancia
 */

import java.awt.Font;
import java.awt.FontFormatException;
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
    
   public static Font loadFont(String path, int size) {
			try {
				return Font.createFont(Font.TRUETYPE_FONT, 
                                        Loader.class.getResourceAsStream(path)).deriveFont(Font.PLAIN, size);
			} catch (FontFormatException | IOException e) {
				e.printStackTrace();
				return null;
			}
}
}
