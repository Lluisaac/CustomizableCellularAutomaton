package engine.cell.transition;

import org.json.JSONArray;
import org.json.JSONObject;

import engine.grid.Coord;
import engine.grid.Grid;

public class TransitionByDefault extends Transition
{

	public TransitionByDefault(int originalState, int resultingState)
	{
		super(originalState, resultingState);
	}

	public boolean isTransitionAdmissible(Coord coord, Grid grid)
	{
		return true;
	}

	public static void importJsonTransitionDefault(JSONObject transition)
	{
		JSONArray arr = transition.getJSONArray("default");

		for(int i = 0; i < arr.length(); i++)
		{
			JSONObject defaultTrans = arr.getJSONObject(i);

			Transition.add(new TransitionByDefault(defaultTrans.getInt("initialState"), defaultTrans.getInt("resultingState")));

		}
	}
}
