package engine.cell.transition;

import java.util.HashMap;
import java.util.Map;

import engine.cell.Cell;
import engine.cell.adjacency.Adjacency;
import engine.cell.adjacency.AdjacencyByEnumeration;
import engine.grid.Coord;
import engine.grid.Grid;
import engine.util.Pair;

public class TransitionByEnumeration extends Transition
{

	private AdjacencyByEnumeration[] enumeration;

	public TransitionByEnumeration(int originalState, int resultingState, AdjacencyByEnumeration... enumeration)
	{
		this.originalState = originalState;
		this.enumeration = enumeration;
		this.resultingState = resultingState;
	}

	@Override
	public boolean isTransitionAdmissible(Coord coord, Grid grid)
	{
		boolean result = true;

		for(AdjacencyByEnumeration adj : this.enumeration)
		{
			boolean intermediate = true;

			Map<Integer, Integer> values = buildValuesMap(coord, grid, adj);

			for(Pair pair : adj.getPairs())
			{
				if(!((values.containsKey(pair.first) && values.get(pair.first) == pair.second) || (!values.containsKey(pair.first) && pair.second == 0)))
				{
					intermediate = false;
				}
			}

			result &= intermediate;
		}

		return result;
	}

	private Map<Integer, Integer> buildValuesMap(Coord coord, Grid grid, Adjacency adj)
	{
		Map<Integer, Integer> values = new HashMap<Integer, Integer>();

		for(Coord relativeCoord : adj.getAdjacency())
		{
			Coord absoluteCoord = coord.plus(relativeCoord);
			Cell cell = grid.getCell(absoluteCoord);

			if(cell == null)
			{
				addValue(values, 0);
			}
			else
			{
				addValue(values, cell.getState());
			}
		}

		return values;
	}

	private void addValue(Map<Integer, Integer> valuesMap, int value)
	{
		if(!valuesMap.containsKey(value))
		{
			valuesMap.put(value, 0);
		}

		valuesMap.replace(value, valuesMap.get(value) + 1);
	}

}
