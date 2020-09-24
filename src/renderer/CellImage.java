package renderer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import engine.cell.Cell;

public class CellImage extends Image {

	public CellImage(int r, int g, int b) throws SlickException {
		super(Cell.LENGTH, Cell.HEIGHT);
		
		this.getGraphics().setColor(new Color(r, g, b));
		
		this.getGraphics().fillRect(1, 1, Cell.LENGTH - 1, Cell.HEIGHT - 1);
		
		this.getGraphics().flush();
	}
	
}
