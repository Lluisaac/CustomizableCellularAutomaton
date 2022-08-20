package engine.cell.transition;

import engine.cell.Cell;
import engine.cell.adjacency.Adjacency;
import engine.cell.adjacency.AdjacencyByEnumeration;
import engine.grid.Coord;
import engine.grid.Grid;
import engine.util.Pair;

public class TransitionByEnumeration extends Transition
{

	private AdjacencyByEnumeration[] enumeration;
	private int[] stateArray;

	public TransitionByEnumeration(int originalState, int resultingState, AdjacencyByEnumeration... enumeration)
	{
		this.originalState = originalState;
		this.resultingState = resultingState;
		this.enumeration = enumeration;
		
		this.stateArray = new int[Cell.getNumberOfStates()];
	}

	@Override
	public boolean isTransitionAdmissible(Coord coord, Grid grid)
	{
		for(AdjacencyByEnumeration adj : this.enumeration)
		{
			buildAdjQuantitiesArray(coord, grid, adj);

			for(Pair pair : adj.getPairs())
			{
				if(this.stateArray[pair.first] != pair.second)
				{
					return false;
				}
			}
		}

		return true;
	}

	private void buildAdjQuantitiesArray(Coord coord, Grid grid, Adjacency adj)
	{
		this.clearStateArray();

		for(Coord relativeCoord : adj.getAdjacency())
		{
			Coord absoluteCoord = coord.plus(relativeCoord);
			Cell cell = grid.getCell(absoluteCoord);

			if(cell == null)
			{
				this.stateArray[0]++;
			}
			else
			{
				this.stateArray[cell.getState()]++;
			}
		}
	}

	private void clearStateArray()
	{
		for(int i = 0; i < stateArray.length; i++)
		{
			stateArray[i] = 0;
		}
	}

}
