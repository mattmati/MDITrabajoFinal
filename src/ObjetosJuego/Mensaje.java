

package ObjetosJuego;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Graficos.Texto;
import Math.Vector2D;
import estados.EstadoJuego;

public class Mensaje {
	private EstadoJuego estadoJuego;
	private float alpha;
	private String texto;
	private Vector2D posicion;
	private Color color;
	private boolean centro;
	private boolean fade;
	private Font font;
	private final float deltaAlpha = 0.01f;
	
	public Mensaje(Vector2D posicion, boolean fade, String texto, Color color,
			boolean centro, Font font, EstadoJuego estadoJuego) {
		this.font = font;
		this.estadoJuego = estadoJuego;
		this.texto = texto;
		this.posicion = posicion;
		this.fade = fade;
		this.color = color;
		this.centro = centro;
		
		if(fade)
			alpha = 1;
		else
			alpha = 0;
		
	}
	
	public void draw(Graphics2D g2d) {
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		
		Texto.dibujaTexto(g2d, texto, posicion, centro, color, font);
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		
		posicion.setY(posicion.getY() - 1);
		
		if(fade)
			alpha -= deltaAlpha;
		else
			alpha += deltaAlpha;
		
		if(fade && alpha < 0) {
			estadoJuego.getMensajes().remove(this);
		}
		
		if(!fade && alpha > 1) {
			fade = true;
			alpha = 1;
		}
		
		
	}
	
	
}
