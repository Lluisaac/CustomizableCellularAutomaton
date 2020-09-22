package engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Element {
	
	public abstract Image getImage() throws SlickException;
	public abstract float getX();
	public abstract float getY();
	public abstract void click();
	
	public boolean containsCoordinates(float x, float y) throws SlickException {
		boolean isBefore = (x < this.getX()) || (y < this.getY());
		boolean isAfter = (x > (this.getX() + this.getImage().getWidth()))
				|| (y > (this.getY() + this.getImage().getHeight()));

		return !isBefore && !isAfter && !this.inTransparency(x, y);
	}
	
	public boolean inTransparency(float x, float y) throws SlickException {
		int spriteX = (int) (x - this.getX());
		int spriteY = (int) (y - this.getY());

		Image sprite = this.getImage();
		Color pixelColor = sprite.getColor(spriteX, spriteY);

		return pixelColor.getAlpha() == 0;
	}
}
