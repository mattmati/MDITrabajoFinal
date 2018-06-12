package PongFinal;

import java.awt.Color;
import java.awt.Graphics;

public class Raqueta
{

	public int numeroRaqueta;

	public int x, y, ancho = 50, altura = 250;

	public int score;

	public Raqueta(Principal pong, int numeroraqueta)
	{
		this.numeroRaqueta = numeroraqueta;

		if (numeroraqueta == 1)
		{
			this.x = 0;
		}

		if (numeroraqueta == 2)
		{
			this.x = pong.ANCHO - ancho;
		}

		this.y = pong.ALTO / 2 - this.altura / 2;
	}

	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(x, y, ancho, altura);
	}

	public void movimiento(boolean up)
	{
		int velocidad = 15;

		if (up)
		{
			if (y - velocidad > 0)
			{
				y -= velocidad;
			}
			else
			{
				y = 0;
			}
		}
		else
		{
			if (y + altura + velocidad < Principal.principal.ALTO)
			{
				y += velocidad;
			}
			else
			{
				y = Principal.principal.ALTO - altura;
			}
		}
	}

}
