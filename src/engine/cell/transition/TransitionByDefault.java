package engine.cell.transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import engine.cell.transition.probabilistic.ProbabilisticTransition;
import engine.grid.Coord;
import engine.grid.Grid;
import engine.util.probabilities.ProbabilityArray;

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

			try
			{
				Transition.add(new TransitionByDefault(defaultTrans.getInt("initialState"), defaultTrans.getInt("resultingState")));
			}
			catch(JSONException e)
			{
				TransitionByDefault trans = new TransitionByDefault(defaultTrans.getInt("initialState"), defaultTrans.getInt("initialState"));
				ProbabilityArray array = ProbabilityArray.importJSONProbabilityArray(defaultTrans.getInt("initialState"), defaultTrans.getJSONArray("probabilities"));

				Transition.add(new ProbabilisticTransition<TransitionByDefault>(trans, array));
			}
		}
	}
}
