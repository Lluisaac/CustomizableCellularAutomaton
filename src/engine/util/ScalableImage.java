package engine.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ScalableImage extends Image
{

	private float scale = 1f;

	public ScalableImage(String path) throws SlickException
	{
		super(path);
	}

	protected ScalableImage(Image image)
	{
		super(image);
	}

	@Override
	public Image getScaledCopy(float scale)
	{
		ScalableImage scaledCopy = new ScalableImage(super.getScaledCopy(scale));
		scaledCopy.scale = scale;
		return scaledCopy;
	}

	@Override
	public Color getColor(int x, int y)
	{
		return super.getColor(Math.round(x * (1 / this.scale)), Math.round(y * (1 / this.scale)));
	}
}
