package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

import engine.cellule.Cell;
import engine.cellule.adjacence.Adjacency;
import engine.cellule.transition.Transition;
import engine.grille.Coord;
import engine.grille.Grid;
import renderer.Camera;
import renderer.Renderer;

public class Game extends BasicGame {

	public static final int TICK_LENGTH = 1000;

	private static Game singleton;
	private GameContainer container;

	private int overflow;
	private Renderer renderer;

	private List<Cell> toUpdate;
	private Set<Cell> nextToUpdate;

	private Grid grid;

	private boolean isPaused;

	public Game() {
		super("Customizable Cellular Automaton");

		this.overflow = 0;

		this.grid = new Grid();

		this.isPaused = true;
	}

	public static Game getGame() {
		if (Game.singleton == null) {
			Game.singleton = new Game();
		}

		return Game.singleton;
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		Cell.init();
		Adjacency.init();
		Transition.init();

		this.container = container;
		
		this.renderer = new Renderer();

		this.toUpdate = new ArrayList<Cell>();
		this.nextToUpdate = new HashSet<Cell>();

		container.getInput().addMouseListener(new MouseListener() {

			@Override
			public void setInput(Input arg0) {
			}

			@Override
			public boolean isAcceptingInput() {
				return true;
			}

			@Override
			public void inputStarted() {
			}

			@Override
			public void inputEnded() {
			}

			@Override
			public void mouseWheelMoved(int change) {
				if (change < 0) {
					renderer.getCamera().unzoom();
				} else {
					renderer.getCamera().zoom();
				}
			}

			@Override
			public void mouseReleased(int button, int x, int y) {
				if (button == Input.MOUSE_LEFT_BUTTON) {
					try {
						grid.click(x, y);
					} catch (SlickException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void mousePressed(int arg0, int arg1, int arg2) {
			}

			@Override
			public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
			}
		});

		container.getInput().addKeyListener(new KeyListener() {

			@Override
			public void setInput(Input arg0) {
			}

			@Override
			public boolean isAcceptingInput() {
				return true;
			}

			@Override
			public void inputStarted() {
			}

			@Override
			public void inputEnded() {
			}

			@Override
			public void keyReleased(int key, char c) {
				Camera cam = renderer.getCamera();

				switch (key) {
				case Input.KEY_P:
					isPaused = !isPaused;
					break;
				case Input.KEY_W:
				case Input.KEY_UP:
					cam.stopMovingUp();
					break;
				case Input.KEY_S:
				case Input.KEY_DOWN:
					cam.stopMovingDown();
					break;
				case Input.KEY_A:
				case Input.KEY_LEFT:
					cam.stopMovingLeft();
					break;
				case Input.KEY_D:
				case Input.KEY_RIGHT:
					cam.stopMovingRight();
					break;
				}
			}

			@Override
			public void keyPressed(int key, char c) {
				Camera cam = renderer.getCamera();

				switch (key) {
				case Input.KEY_W:
				case Input.KEY_UP:
					cam.movingUp();
					break;
				case Input.KEY_S:
				case Input.KEY_DOWN:
					cam.movingDown();
					break;
				case Input.KEY_A:
				case Input.KEY_LEFT:
					cam.movingLeft();
					break;
				case Input.KEY_D:
				case Input.KEY_RIGHT:
					cam.movingRight();
					break;
				case Input.KEY_C:
					cam.center();
					break;
				case Input.KEY_ESCAPE:
					Game.getGame().exit();
					break;
				}
			}
		});
	}
	
	public void exit() {
		this.container.exit();
	}

	public Grid getGrid() {
		return this.grid;
	}
	
	public Renderer getRenderer() {
		return this.renderer;
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		this.renderer.render(g, this.grid);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		this.renderer.getCamera().update(delta);
		this.overflow += delta;
		int ticksToDo = (this.overflow / Game.TICK_LENGTH);
		this.overflow %= Game.TICK_LENGTH;

		for (int i = 0; i < ticksToDo; i++) {
			if (this.isPaused) {
				this.updateTick();
			}
		}
	}

	private void updateTick() {
		for (Cell cell : this.toUpdate) {
			cell.preUpdate();
		}

		for (Cell cell : this.toUpdate) {
			cell.postUpdate();
		}

		this.toUpdate.clear();
		this.toUpdate.addAll(this.nextToUpdate);
		this.nextToUpdate.clear();
	}

	public void addToUpdate(Cell cellule) {
		this.nextToUpdate.add(cellule);
		
		for (Coord adj : Adjacency.getAdjacency()) {
			Coord relativeCoord = cellule.getCoord().plus(adj.reverse());
			Cell cell = Game.getGame().getGrid().getCell(relativeCoord);
			
			if (cell == null) {
				cell = new Cell(relativeCoord);
				Game.getGame().getGrid().add(cell);
			}
		}
	}
}