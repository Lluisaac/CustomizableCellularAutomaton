package renderer;

import java.awt.Toolkit;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import engine.cell.Cell;
import engine.grid.Grid;

public class Renderer
{

	public static final int GAME_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static final int GAME_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();

	private Camera cam;
	private Menu menu;

	public Renderer() throws SlickException
	{
		this.cam = new Camera();
		this.menu = new Menu();
	}

	public void render(Graphics g, Grid grid) throws SlickException
	{
		this.cam.render(g);		
		this.renderGrid(g, grid);
		this.renderMenu(g);
	}

	private void renderGrid(Graphics g, List<Cell> grid) throws SlickException
	{
		for(Cell cell : grid)
		{
			if (cell.getState() != 0)
			{				
				g.drawImage(cell.getImage(), cell.getX(), cell.getY());
			}
		}

	}

	private void renderMenu(Graphics g) throws SlickException
	{
		g.resetTransform();
		this.menu.render(g);
	}

	public Camera getCamera()
	{
		return this.cam;
	}

	public Menu getMenu()
	{
		return this.menu;
	}
}
