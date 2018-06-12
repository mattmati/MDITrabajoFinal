package PongFinal;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Pelota
{

	public int x, y, anchura = 25, longitud = 25;

	public int movimientoX, movimientoY;

	public Random random;

	private Principal pong;

	public int cantidadPuntos;

	public Pelota(Principal pong)
	{
		this.pong = pong;

		this.random = new Random();

		error();
	}

	public void update(Raqueta raqueta1, Raqueta raqueta2)
	{
		int velocd = 5;

		this.x += movimientoX * velocd;
		this.y += movimientoY * velocd;

		if (this.y + longitud - movimientoY > pong.ALTO || this.y + movimientoY < 0)
		{
			if (this.movimientoY < 0)
			{
				this.y = 0;
				this.movimientoY = random.nextInt(4);

				if (movimientoY == 0)
				{
					movimientoY = 1;
				}
			}
			else
			{
				this.movimientoY = -random.nextInt(4);
				this.y = pong.ALTO - longitud;

				if (movimientoY == 0)
				{
					movimientoY = -1;
				}
			}
		}

		if (colision(raqueta1) == 1)
		{
			this.movimientoX = 1 + (cantidadPuntos / 5);
			this.movimientoY = -2 + random.nextInt(4);

			if (movimientoY == 0)
			{
				movimientoY = 1;
			}

			cantidadPuntos++;
		}
		else if (colision(raqueta2) == 1)
		{
			this.movimientoX = -1 - (cantidadPuntos / 5);
			this.movimientoY = -2 + random.nextInt(4);

			if (movimientoY == 0)
			{
				movimientoY = 1;
			}

			cantidadPuntos++;
		}

		if (colision(raqueta1) == 2)
		{
			raqueta2.score++;
			error();
		}
		else if (colision(raqueta2) == 2)
		{
			raqueta1.score++;
			error();
		}
	}

	public void error()
	{
		this.cantidadPuntos = 0;
		this.x = pong.ANCHO / 2 - this.anchura / 2;
		this.y = pong.ALTO / 2 - this.longitud / 2;

		this.movimientoY = -2 + random.nextInt(4);

		if (movimientoY == 0)
		{
			movimientoY = 1;
		}

		if (random.nextBoolean())
		{
			movimientoX = 1;
		}
		else
		{
			movimientoX = -1;
		}
	}

	public int colision(Raqueta raqueTa)
	{
		if (this.x < raqueTa.x + raqueTa.ancho && this.x + anchura > raqueTa.x && this.y < raqueTa.y + raqueTa.altura && this.y + longitud > raqueTa.y)
		{
			return 1; //bounce
		}
		else if ((raqueTa.x > x && raqueTa.numeroRaqueta == 1) || (raqueTa.x < x - anchura && raqueTa.numeroRaqueta == 2))
		{
			return 2; //score
		}

		return 0; //nothing
	}

	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillOval(x, y, anchura, longitud);
	}

}
