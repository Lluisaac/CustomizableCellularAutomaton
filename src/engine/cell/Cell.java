package engine.cell;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import engine.Element;
import engine.Game;
import engine.cell.adjacency.Adjacency;
import engine.cell.transition.deterministic.Transition;
import engine.grid.Coord;
import engine.util.CellImage;

public class Cell extends Element
{
	public static final int LENGTH = 32;
	public static final int HEIGHT = 32;

	private Coord coord;

	private static List<Image> stateImagesList;
	private static int numberOfStates;

	private int state;
	private int nextState;

	public Cell(Coord coord)
	{
		this.coord = coord;

		this.setState(0);
	}

	public static void init() throws SlickException
	{
		Cell.stateImagesList = new ArrayList<Image>();
		Cell.stateImagesList.add(new CellImage(0, 0, 0));
		
		Cell.importJsonCells();

		Cell.numberOfStates = Cell.stateImagesList.size();
	}

	private static void importJsonCells() throws SlickException
	{
		JSONArray arr = Game.getGame().json.getJSONArray("states");
		
		for(int i = 0; i < arr.length(); i++)
		{
			JSONObject color = arr.getJSONObject(i);
			Cell.stateImagesList.add(new CellImage(color.getInt("red"), color.getInt("green"), color.getInt("blue")));
		}
	}

	public static int getNumberOfStates()
	{
		return Cell.numberOfStates;
	}

	@Override
	public Image getImage() throws SlickException
	{
		return Cell.stateImagesList.get(this.state);
	}

	@Override
	public float getX()
	{
		return this.coord.x * Cell.HEIGHT;
	}

	@Override
	public float getY()
	{
		return this.coord.y * Cell.LENGTH;
	}

	@Override
	public void click()
	{
		if(this.state == Cell.getNumberOfStates() - 1)
		{
			this.setState(0);
		}
		else
		{
			this.setState(this.state + 1);
		}

		Game.getGame().addToUpdateUrgently(this);
	}
	
	public void rightClick()
	{
		if(this.state == 0)
		{
			this.setState(Cell.getNumberOfStates() - 1);
		}
		else
		{
			this.setState(this.state - 1);
		}

		Game.getGame().addToUpdateUrgently(this);
	}

	public void click(int selectedState)
	{
		if(selectedState == -1)
		{
			this.click();
		}
		else if(selectedState == -2)
		{
			this.rightClick();
		}
		else
		{
			this.setState(selectedState);
			Game.getGame().addToUpdateUrgently(this);
		}
	}

	public int getState()
	{
		return this.state;
	}

	public void setState(int state)
	{
		if(state != this.state)
		{
			this.state = state;
			this.nextState = state;

			Game.getGame().addToUpdate(this);

			for(Coord adj : Adjacency.getBasicAdjacency())
			{
				Coord relativeCoord = this.coord.plus(adj.reverse());
				Cell cell = Game.getGame().getGrid().getCell(relativeCoord);

				Game.getGame().addToUpdate(cell);
			}
		}

		if(this.state == 0)
		{
			Game.getGame().addToCleanUp(this);
		}
		else
		{
			Game.getGame().removeFromCleanUp(this);
		}
	}

	public void preUpdate()
	{
		this.nextState = Transition.getTransitionedState(this.coord);
	}

	public void postUpdate()
	{
		this.setState(this.nextState);
	}

	public Coord getCoord()
	{
		return this.coord;
	}

	public boolean isFullyQuiescent()
	{
		for(Coord adj : Adjacency.getBasicAdjacency())
		{
			Coord relativeCoord = this.coord.plus(adj);
			Cell cell = Game.getGame().getGrid().getCell(relativeCoord);

			if(cell != null && cell.getState() != 0)
			{
				return false;
			}
		}
		return true;
	}

	public static List<Image> getStateImages()
	{
		return Cell.stateImagesList;
	}

}
