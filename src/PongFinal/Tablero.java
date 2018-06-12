package PongFinal;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Tablero extends JPanel
{

	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Principal.principal.tablro((Graphics2D) g);
	}

}
