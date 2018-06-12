package PongFinal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Principal implements ActionListener, KeyListener{

	public static Principal principal;

	public int ANCHO = 700, ALTO = 700;

	public Tablero tablero;

	public Raqueta jugador1;

	public Raqueta jugador2;

	public Pelota pelota;

	public boolean bot = false, seleccioneDificultad;

	public boolean teclaW, teclaS, arriba, abajo;

	public int estado = 0, limite = 7, ganar; //0 = Menu, 1 = Pausa, 2 = Jugar, 3 = Afuera

	public int botDificultad, botMovimiento, botRefrescar = 0;

	public Random random;

	public JFrame jframe;

	public Principal()
	{
		Timer timer = new Timer(20, this);
		random = new Random();

		jframe = new JFrame("Pong");

		tablero = new Tablero();

		jframe.setSize(ANCHO + 15, ALTO + 35);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(tablero);
		jframe.addKeyListener(this);

		timer.start();
	}

	public void dibujar()
	{
		estado = 2;
		jugador1 = new Raqueta(this, 1);
		jugador2 = new Raqueta(this, 2);
		pelota = new Pelota(this);
	}

	public void actualizar()
	{
		if (jugador1.score >= limite)
		{
			ganar = 1;
			estado = 3;
		}

		if (jugador2.score >= limite)
		{
			estado = 3;
			ganar = 2;
		}

		if (teclaW)
		{
			jugador1.movimiento(true);
		}
		if (teclaS)
		{
			jugador1.movimiento(false);
		}

		if (!bot)
		{
			if (arriba)
			{
				jugador2.movimiento(true);
			}
			if (abajo)
			{
				jugador2.movimiento(false);
			}
		}
		else
		{
			if (botRefrescar > 0)
			{
				botRefrescar--;

				if (botRefrescar == 0)
				{
					botMovimiento = 0;
				}
			}

			if (botMovimiento < 10)
			{
				if (jugador2.y + jugador2.altura / 2 < pelota.y)
				{
					jugador2.movimiento(false);
					botMovimiento++;
				}

				if (jugador2.y + jugador2.altura / 2 > pelota.y)
				{
					jugador2.movimiento(true);
					botMovimiento++;
				}

				if (botDificultad == 0)
				{
					botRefrescar = 20;
				}
				if (botDificultad == 1)
				{
					botRefrescar = 15;
				}
				if (botDificultad == 2)
				{
					botRefrescar = 10;
				}
			}
		}

		pelota.update(jugador1, jugador2);
	}

	public void tablro(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, ANCHO, ALTO);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (estado == 0)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Century Gothic", 1, 50));

			g.drawString("PONG", ANCHO / 2 - 75, 50);

			if (!seleccioneDificultad)
			{
				g.setFont(new Font("Century Gothic", 1, 30));

				g.drawString("Espacio para comenzar", ANCHO / 2 - 150, ALTO / 2 - 25);
				g.drawString("SHIFT para elegir la dificultad", ANCHO / 2 - 200, ALTO / 2 + 25);
				g.drawString("<< Puntaje Limite: " + limite + " >>", ANCHO / 2 - 150, ALTO / 2 + 75);
			}
		}

		if (seleccioneDificultad)
		{
			String string = botDificultad == 0 ? "Facil" : (botDificultad == 1 ? "Medio" : "Dificil");

			g.setFont(new Font("Century Gothic", 1, 30));

			g.drawString("<< Dificultad del BOT " + string + " >>", ANCHO / 2 - 180, ALTO / 2 - 25);
			g.drawString("Espacio para comenzar", ANCHO / 2 - 150, ALTO / 2 + 25);
		}

		if (estado == 1)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Century Gothic", 1, 50));
			g.drawString("EN PAUSA", ANCHO / 2 - 103, ALTO / 2 - 25);
		}

		if (estado == 1 || estado == 2)
		{
			g.setColor(Color.WHITE);

			g.setStroke(new BasicStroke(5f));

			g.drawLine(ANCHO / 2, 0, ANCHO / 2, ALTO);

			g.setStroke(new BasicStroke(2f));

			g.drawOval(ANCHO / 2 - 150, ALTO / 2 - 150, 300, 300);

			g.setFont(new Font("Century Gothic", 1, 50));

			g.drawString(String.valueOf(jugador1.score), ANCHO / 2 - 90, 50);
			g.drawString(String.valueOf(jugador2.score), ANCHO / 2 + 65, 50);

			jugador1.render(g);
			jugador2.render(g);
			pelota.render(g);
		}

		if (estado == 3)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Century Gothic", 1, 50));

			g.drawString("PONG", ANCHO / 2 - 75, 50);

			if (bot && ganar == 2)
			{
				g.drawString("GANO EL BOT!", ANCHO / 2 - 170, 200);
			}
			else
			{
				g.drawString("JUGADOR " + ganar + " GANO!", ANCHO / 2 - 165, 200);
			}

			g.setFont(new Font("Century Gothic", 1, 30));

			g.drawString("Espacio para jugar nuevamente", ANCHO / 2 - 185, ALTO / 2 - 25);
			g.drawString("Presione ESC para salir", ANCHO / 2 - 140, ALTO / 2 + 25);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (estado == 2)
		{
			actualizar();
		}

		tablero.repaint();
	}

	public static void main(String[] args)
	{
		principal = new Principal();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W)
		{
			teclaW = true;
		}
		else if (id == KeyEvent.VK_S)
		{
			teclaS = true;
		}
		else if (id == KeyEvent.VK_UP)
		{
			arriba = true;
		}
		else if (id == KeyEvent.VK_DOWN)
		{
			abajo = true;
		}
		else if (id == KeyEvent.VK_RIGHT)
		{
			if (seleccioneDificultad)
			{
				if (botDificultad < 2)
				{
					botDificultad++;
				}
				else
				{
					botDificultad = 0;
				}
			}
			else if (estado == 0)
			{
				limite++;
			}
		}
		else if (id == KeyEvent.VK_LEFT)
		{
			if (seleccioneDificultad)
			{
				if (botDificultad > 0)
				{
					botDificultad--;
				}
				else
				{
					botDificultad = 2;
				}
			}
			else if (estado == 0 && limite > 1)
			{
				limite--;
			}
		}
		else if (id == KeyEvent.VK_ESCAPE && (estado == 2 || estado == 3))
		{
			estado = 0;
		}
		else if (id == KeyEvent.VK_SHIFT && estado == 0)
		{
			bot = true;
			seleccioneDificultad = true;
		}
		else if (id == KeyEvent.VK_SPACE)
		{
			if (estado == 0 || estado == 3)
			{
				if (!seleccioneDificultad)
				{
					bot = false;
				}
				else
				{
					seleccioneDificultad = false;
				}

				dibujar();
			}
			else if (estado == 1)
			{
				estado = 2;
			}
			else if (estado == 2)
			{
				estado = 1;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W)
		{
			teclaW = false;
		}
		else if (id == KeyEvent.VK_S)
		{
			teclaS = false;
		}
		else if (id == KeyEvent.VK_UP)
		{
			arriba = false;
		}
		else if (id == KeyEvent.VK_DOWN)
		{
			abajo = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}
}
