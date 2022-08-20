package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

import engine.cell.Cell;
import engine.cell.adjacency.Adjacency;
import engine.cell.transition.Transition;
import engine.grid.Coord;
import engine.grid.Grid;
import renderer.Camera;
import renderer.Renderer;

public class Game extends BasicGame
{

	private static final int TICK_LENGTH = 1000;

	private static Game singleton;
	private GameContainer container;
	
	public final JSONObject json;

	private int overflow;
	private Renderer renderer;

	private List<Cell> toUpdate;
	private Set<Cell> nextToUpdate;
	private List<Cell> cleanUp;

	private Grid grid;

	private boolean isPaused;

	private int speedNotch;

	private int selectedState;

	public Game(JSONObject json)
	{		
		super("Customizable Cellular Automaton");
		
		this.json = json;

		this.overflow = 0;

		this.grid = new Grid();

		this.isPaused = true;

		this.speedNotch = 1;

		this.selectedState = -1;
	}

	public static Game setGame(JSONObject json)
	{
		Game.singleton = new Game(json);

		return Game.singleton;
	}

	public static Game getGame()
	{
		return Game.singleton;
	}

	@Override
	public void init(GameContainer container) throws SlickException
	{
		Cell.init();
		Adjacency.init();
		Transition.init();

		this.container = container;

		this.renderer = new Renderer();

		this.toUpdate = new ArrayList<Cell>();
		this.nextToUpdate = new HashSet<Cell>();
		this.cleanUp = new ArrayList<Cell>();

		container.getInput().addMouseListener(new MouseListener() {

			@Override
			public void setInput(Input arg0)
			{
			}

			@Override
			public boolean isAcceptingInput()
			{
				return true;
			}

			@Override
			public void inputStarted()
			{
			}

			@Override
			public void inputEnded()
			{
			}

			@Override
			public void mouseWheelMoved(int change)
			{
				if(change < 0)
				{
					renderer.getCamera().unzoom();
				}
				else
				{
					renderer.getCamera().zoom();
				}
			}

			@Override
			public void mouseReleased(int button, int x, int y)
			{
				if(button == Input.MOUSE_LEFT_BUTTON)
				{
					try
					{
						click(x, y);
					}
					catch(SlickException e)
					{
						e.printStackTrace();
					}
				}
			}

			@Override
			public void mousePressed(int arg0, int arg1, int arg2)
			{
			}

			@Override
			public void mouseMoved(int arg0, int arg1, int arg2, int arg3)
			{
			}

			@Override
			public void mouseDragged(int arg0, int arg1, int arg2, int arg3)
			{
			}

			@Override
			public void mouseClicked(int arg0, int arg1, int arg2, int arg3)
			{
			}
		});

		container.getInput().addKeyListener(new KeyListener() {

			@Override
			public void setInput(Input arg0)
			{
			}

			@Override
			public boolean isAcceptingInput()
			{
				return true;
			}

			@Override
			public void inputStarted()
			{
			}

			@Override
			public void inputEnded()
			{
			}

			@Override
			public void keyReleased(int key, char c)
			{
				Camera cam = renderer.getCamera();

				switch(key)
				{
					case Input.KEY_W:
					case Input.KEY_UP:
					case Input.KEY_S:
					case Input.KEY_DOWN:
						cam.stopMovingVertical();
						break;
					case Input.KEY_A:
					case Input.KEY_LEFT:
					case Input.KEY_D:
					case Input.KEY_RIGHT:
						cam.stopMovingHorizontal();
						break;
				}
			}

			@Override
			public void keyPressed(int key, char c)
			{
				Camera cam = renderer.getCamera();

				switch(key)
				{
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
					case Input.KEY_Q:
						switchSelectedState();
						break;
					case Input.KEY_C:
						cam.center();
						break;
					case Input.KEY_SPACE:
						pause();
						break;
					case Input.KEY_ADD:
						speedUp();
						break;
					case Input.KEY_SUBTRACT:
						speedDown();
						break;
					case Input.KEY_ESCAPE:
						Game.getGame().exit();
						break;
				}
			}
		});
	}

	protected void click(int x, int y) throws SlickException
	{
		List<Element> possible = new ArrayList<Element>();
		for(Element element : this.renderer.getMenu().getElements())
		{
			if(element.containsCoordinates(x, y))
			{
				possible.add(element);
			}
		}

		if(possible.size() > 0)
		{
			possible.get(possible.size() - 1).click();
		}
		else
		{
			this.grid.click(this.selectedState, x, y);
		}
	}

	public void pause()
	{
		this.isPaused = !this.isPaused;

		if(!this.isPaused && this.speedNotch == 0)
		{
			this.speedNotch++;
		}
	}

	public void speedUp()
	{
		this.speedNotch++;

		if(this.speedNotch > 4)
		{
			this.speedNotch = 4;
		}
	}

	public void speedDown()
	{
		this.speedNotch--;

		if(this.speedNotch <= 0)
		{
			this.speedNotch = 0;
			if(!this.isPaused)
			{
				this.isPaused = true;
			}
		}
	}

	public void switchSelectedState()
	{
		this.selectedState++;

		if(this.selectedState >= Cell.getNumberOfStates())
		{
			this.selectedState = -1;
		}
	}

	public void exit()
	{
		this.container.exit();
	}

	public int getTickLength()
	{
		switch(this.speedNotch)
		{
			case 2:
				return Game.TICK_LENGTH / 2;
			case 3:
				return Game.TICK_LENGTH / 4;
			case 4:
				return Game.TICK_LENGTH / 10;
			default:
				return Game.TICK_LENGTH;
		}
	}

	public Grid getGrid()
	{
		return this.grid;
	}

	public Renderer getRenderer()
	{
		return this.renderer;
	}

	public boolean isPaused()
	{
		return this.isPaused;
	}

	public int getSpeedNotch()
	{
		return this.speedNotch;
	}

	public int getSelectedState()
	{
		return selectedState;
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException
	{
		this.renderer.render(g, this.grid);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException
	{
		this.renderer.getCamera().update(delta);
		this.overflow += delta;
		int ticksToDo = (this.overflow / this.getTickLength());
		this.overflow %= this.getTickLength();

		for(int i = 0; i < ticksToDo; i++)
		{
			if(!this.isPaused)
			{
				this.updateTick();
			}
		}
	}

	private void updateTick()
	{
		for(Cell cell : this.toUpdate)
		{
			cell.preUpdate();
		}

		for(Cell cell : this.toUpdate)
		{
			cell.postUpdate();
		}

		cleanLists();
	}

	private void cleanLists()
	{
		for(int i = 0; i < this.cleanUp.size(); i++)
		{
			Cell cell = this.cleanUp.get(i);

			if(cell.isFullyQuiescent() && cell.getState() == 0)
			{
				this.grid.remove(cell);
				this.cleanUp.remove(cell);
				this.nextToUpdate.remove(cell);
				i--;
			}
		}

		this.toUpdate.clear();
		this.toUpdate.addAll(this.nextToUpdate);
		this.nextToUpdate.clear();
	}

	public void addToUpdate(Cell toUpdate)
	{
		this.nextToUpdate.add(toUpdate);

		for(Coord adj : Adjacency.getBasicAdjacency())
		{
			Coord relativeCoord = toUpdate.getCoord().plus(adj.reverse());
			Cell cell = Game.getGame().getGrid().getCell(relativeCoord);

			if(cell == null)
			{
				cell = new Cell(relativeCoord);
				Game.getGame().getGrid().add(cell);
			}
		}
	}

	public void addToUpdateUrgently(Cell cell)
	{
		if(!this.toUpdate.contains(cell))
		{
			this.toUpdate.add(cell);
		}

		for(Coord adj : Adjacency.getBasicAdjacency())
		{
			Coord relativeCoord = cell.getCoord().plus(adj.reverse());
			Cell adjacentCell = Game.getGame().getGrid().getCell(relativeCoord);

			if(adjacentCell == null)
			{
				cell = new Cell(relativeCoord);
				Game.getGame().getGrid().add(cell);
			}

			this.toUpdate.add(adjacentCell);
		}
	}

	public void addToCleanUp(Cell cell)
	{
		if(!this.cleanUp.contains(cell))
		{
			this.cleanUp.add(cell);
		}
	}

	public void removeFromCleanUp(Cell cell)
	{
		this.cleanUp.remove(cell);
	}
}