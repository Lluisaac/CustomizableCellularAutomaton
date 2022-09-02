package engine.util.probabilities;

import org.json.JSONObject;

public class Probability
{
	public int resultingState;
	public double probability;

	public Probability(int resultingState, double probability)
	{
		this.resultingState = resultingState;
		this.probability = probability;
	}

	public static Probability importJSONProbability(JSONObject probaObject)
	{
		return new Probability(probaObject.getInt("resultingState"), probaObject.getDouble("probability"));
	}

	@Override
	public String toString()
	{
		return "[state: " + this.resultingState + ", proba: " + this.probability + "]";
	}
}
