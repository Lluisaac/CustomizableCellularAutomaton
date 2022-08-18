package engine.grid;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import engine.Game;
import engine.cell.Cell;
import engine.util.Pair;

public class Grid extends ArrayList<Cell>
{
	private static final long serialVersionUID = -8281083828318811645L;

	public Cell getCell(Coord coord)
	{
		for(Cell cell : this)
		{
			if(cell.getCoord().equals(coord))
			{
				return cell;
			}
		}

		return null;
	}

	public void click(int selectedState, int x, int y) throws SlickException
	{
		Pair pair = Game.getGame().getRenderer().getCamera().transform(x, y);

		Coord coord = new Coord(pair);
		Cell cell = this.getCell(coord);

		if(cell == null && selectedState != 0)
		{
			cell = new Cell(coord);
			this.add(cell);
		}

		if(cell != null && cell.getState() != selectedState)
		{
			cell.click(selectedState);
		}
	}
}
