package engine;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Button extends Element {
	
	private int x;
	private int y;
	private Image sprite;
	
	public Button(String path, int x, int y) throws SlickException{
		this.x = x;
		this.y = y;
		this.sprite = new Image(path); 
	}

	@Override
	public Image getImage() {
		return this.sprite;
	}

	@Override
	public float getX() {
		return this.x;
	}

	@Override
	public float getY() {
		return this.y;
	}
}
