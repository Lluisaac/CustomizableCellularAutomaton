package engine.cell.transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import engine.grid.Coord;
import engine.grid.Grid;

public class TransitionByDefault extends Transition
{
	
	public TransitionByDefault(int originalState, int resultingState)
	{
		this(originalState, resultingState, 1);
	}
	
	public TransitionByDefault(int originalState, int resultingState, double probability)
	{
		this.originalState = originalState;
		this.resultingState = resultingState;
		this.probability = probability;
	}
	
	public boolean isTransitionAdmissible(Coord coord, Grid grid)
	{
		return super.getRandomChance();
	}
	
	public static void importJsonTransitionDefault(JSONObject transition)
	{
		JSONArray arr = transition.getJSONArray("default");
		
		for(int i = 0; i < arr.length(); i++)
		{
			JSONObject defaultTrans = arr.getJSONObject(i);
			
			try
			{
				Transition.add(new TransitionByDefault(defaultTrans.getInt("initialState"), defaultTrans.getInt("resultingState"), defaultTrans.getDouble("probability")));
			}
			catch (JSONException e)
			{
				Transition.add(new TransitionByDefault(defaultTrans.getInt("initialState"), defaultTrans.getInt("resultingState")));
			}
		}
	}
}
