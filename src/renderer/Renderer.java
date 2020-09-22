package renderer;

import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import engine.cellule.Cell;
import engine.grille.Grid;

public class Renderer {

	public static final int GAME_HEIGHT = 1080;
	public static final int GAME_WIDTH = 1920;
	
	private Camera cam;
	
	public Renderer() {
		this.cam = new Camera();
	}

	public void render(Graphics g, Grid grid) throws SlickException {
		this.cam.render(g);
		renderGrid(g, grid);
		renderMenu(g);
	}

	private void renderGrid(Graphics g, List<Cell> grid) throws SlickException {
		for (Cell cell : grid) {
			g.drawImage(cell.getImage(), cell.getX(), cell.getY());
		}

	}

	private void renderMenu(Graphics g) {

	}
	
	public Camera getCamera() {
		return this.cam;
	}
}
