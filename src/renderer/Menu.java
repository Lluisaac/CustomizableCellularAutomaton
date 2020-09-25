package renderer;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import engine.Element;
import engine.Game;

public class Menu {

	private List<Element> elements;

	private Image paused;
	private Image unpaused;
	private Image center;
	private Image speedUp;
	private Image speedDown;
	private Image speedEmpty;
	private Image speedFull;
	private Image speedFullPaused;

	public Menu() throws SlickException {

		paused = new Image("assets/pause.png").getScaledCopy(0.5f);
		unpaused = new Image("assets/unpause.png").getScaledCopy(0.5f);
		center = new Image("assets/center.png").getScaledCopy(0.5f);
		speedUp = new Image("assets/speedup.png").getScaledCopy(0.5f);
		speedDown = new Image("assets/speeddown.png").getScaledCopy(0.5f);
		speedEmpty = new Image("assets/empty.png").getScaledCopy(0.5f);
		speedFull = new Image("assets/full.png").getScaledCopy(0.5f);
		speedFullPaused = new Image("assets/half.png").getScaledCopy(0.5f);

		this.elements = new ArrayList<Element>();

		this.elements.add(new Element() {

			@Override
			public void click() {
				Game.getGame().pause();
			}

			@Override
			public Image getImage() throws SlickException {
				if (Game.getGame().isPaused()) {
					return paused;
				} else {
					return unpaused;
				}
			}

			@Override
			public float getX() {
				return Renderer.GAME_WIDTH - paused.getWidth() - 20;
			}

			@Override
			public float getY() {
				return Renderer.GAME_HEIGHT - paused.getHeight() - 20;
			}
		});

		this.elements.add(new Element() {

			@Override
			public void click() {
				Game.getGame().getRenderer().getCamera().center();
			}

			@Override
			public Image getImage() throws SlickException {
				return center;
			}

			@Override
			public float getX() {
				return 20;
			}

			@Override
			public float getY() {
				return Renderer.GAME_HEIGHT - center.getHeight() - 20;
			}
		});

		this.elements.add(new Element() {

			@Override
			public void click() {
				Game.getGame().speedUp();
			}

			@Override
			public Image getImage() throws SlickException {
				return speedUp;
			}

			@Override
			public float getX() {
				return Renderer.GAME_WIDTH - paused.getWidth() - speedUp.getWidth() - (2 * 20);
			}

			@Override
			public float getY() {
				return Renderer.GAME_HEIGHT - speedUp.getHeight() - 20;
			}
		});

		this.elements.add(new Element() {

			@Override
			public void click() {

			}

			@Override
			public Image getImage() throws SlickException {
				if (Game.getGame().getSpeedNotch() <= 3) {
					return speedEmpty;
				} else if (Game.getGame().isPaused()) {
					return speedFullPaused;
				} else {
					return speedFull;
				}
			}

			@Override
			public float getX() {
				return Renderer.GAME_WIDTH - paused.getWidth() - speedUp.getWidth() - speedFull.getWidth() - (3 * 20);
			}

			@Override
			public float getY() {
				return Renderer.GAME_HEIGHT - speedFull.getHeight() - (speedFull.getHeight() / 2) - 20;
			}
		});

		this.elements.add(new Element() {

			@Override
			public void click() {

			}

			@Override
			public Image getImage() throws SlickException {
				if (Game.getGame().getSpeedNotch() <= 2) {
					return speedEmpty;
				} else if (Game.getGame().isPaused()) {
					return speedFullPaused;
				} else {
					return speedFull;
				}
			}

			@Override
			public float getX() {
				return Renderer.GAME_WIDTH - paused.getWidth() - speedUp.getWidth() - (2 * speedFull.getWidth()) - (4 * 20);
			}

			@Override
			public float getY() {
				return Renderer.GAME_HEIGHT - speedFull.getHeight() - (speedFull.getHeight() / 2) - 20;
			}
		});

		this.elements.add(new Element() {

			@Override
			public void click() {

			}

			@Override
			public Image getImage() throws SlickException {
				if (Game.getGame().getSpeedNotch() <= 1) {
					return speedEmpty;
				} else if (Game.getGame().isPaused()) {
					return speedFullPaused;
				} else {
					return speedFull;
				}
			}

			@Override
			public float getX() {
				return Renderer.GAME_WIDTH - paused.getWidth() - speedUp.getWidth() - (3 * speedFull.getWidth()) - (5 * 20);
			}

			@Override
			public float getY() {
				return Renderer.GAME_HEIGHT - speedFull.getHeight() - (speedFull.getHeight() / 2) - 20;
			}
		});

		this.elements.add(new Element() {

			@Override
			public void click() {

			}

			@Override
			public Image getImage() throws SlickException {
				if (Game.getGame().getSpeedNotch() <= 0) {
					return speedEmpty;
				} else if (Game.getGame().isPaused()) {
					return speedFullPaused;
				} else {
					return speedFull;
				}
			}

			@Override
			public float getX() {
				return Renderer.GAME_WIDTH - paused.getWidth() - speedUp.getWidth() - (4 * speedFull.getWidth()) - (6 * 20);
			}

			@Override
			public float getY() {
				return Renderer.GAME_HEIGHT - speedFull.getHeight() - (speedFull.getHeight() / 2) - 20;
			}
		});

		this.elements.add(new Element() {

			@Override
			public void click() {
				Game.getGame().speedDown();
			}

			@Override
			public Image getImage() throws SlickException {
				return speedDown;
			}

			@Override
			public float getX() {
				return Renderer.GAME_WIDTH - paused.getWidth() - speedUp.getWidth() - (4 * speedFull.getWidth()) - speedDown.getWidth() - (7 * 20);
			}

			@Override
			public float getY() {
				return Renderer.GAME_HEIGHT - speedDown.getHeight() - 20;
			}
		});
	}

	public void render(Graphics g) throws SlickException {
		for (Element element : elements) {
			g.drawImage(element.getImage(), element.getX(), element.getY());
		}
	}

	public List<Element> getElements() {
		return this.elements;
	}

}
