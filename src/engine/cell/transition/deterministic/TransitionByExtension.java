package engine.cell.transition.deterministic;

import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import engine.cell.adjacency.AdjacencyByExtension;
import engine.grid.Coord;
import engine.grid.Grid;

public class TransitionByExtension extends Transition
{

	private AdjacencyByExtension[] adjacentCells;

	public TransitionByExtension(int originalState, int resultingState, AdjacencyByExtension... adjacentCells)
	{
		super(originalState, resultingState);

		this.adjacentCells = adjacentCells;
	}

	public boolean isTransitionAdmissible(Coord coord, Grid grid)
	{
		for(AdjacencyByExtension adj : this.adjacentCells)
		{
			for(Entry<Coord, Integer> entry : adj.entrySet())
			{
				Coord relativeCoord = coord.plus(entry.getKey());
				if(grid.getCell(relativeCoord) != null)
				{
					if(grid.getCell(relativeCoord).getState() != entry.getValue())
					{
						return false;
					}
				}
				else if(entry.getValue() != 0)
				{
					return false;
				}
			}
		}

		return true;
	}

	public static void importJsonTransitionExtension(JSONObject transition)
	{
		JSONArray arr = transition.getJSONArray("extension");

		for(int i = 0; i < arr.length(); i++)
		{
			JSONObject extension = arr.getJSONObject(i);

			TransitionByExtension.importExtension(extension);
		}
	}

	private static void importExtension(JSONObject extension)
	{
		JSONArray statePositions = extension.getJSONArray("statePositions");

		AdjacencyByExtension adj = new AdjacencyByExtension();

		for(int j = 0; j < statePositions.length(); j++)
		{
			JSONObject adjacency = statePositions.getJSONObject(j);

			adj.addState(new Coord(adjacency.getInt("x"), adjacency.getInt("x")), adjacency.getInt("state"));
		}

		Transition.add(new TransitionByExtension(extension.getInt("initialState"), extension.getInt("resultingState"), adj));
	}
}
