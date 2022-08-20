package engine.cell.transition;

import java.util.Map.Entry;

import engine.cell.adjacency.AdjacencyByExtension;
import engine.grid.Coord;
import engine.grid.Grid;

public class TransitionByExtension extends Transition
{

	private AdjacencyByExtension[] adjacentCells;

	public TransitionByExtension(int originalState, int resultingState, AdjacencyByExtension... adjacentCells)
	{
		this.originalState = originalState;
		this.adjacentCells = adjacentCells;
		this.resultingState = resultingState;
	}

	public boolean isTransitionAdmissible(Coord coord, Grid grid)
	{
		for(AdjacencyByExtension adj : this.adjacentCells)
		{
			for(Entry<Coord, Integer> entry : adj.entrySet())
			{
				Coord relativeCoord = coord.plus(entry.getKey());
				if(grid.getCell(relativeCoord).getState() != entry.getValue())
				{
					return false;
				}
			}
		}

		return true;
	}
}
