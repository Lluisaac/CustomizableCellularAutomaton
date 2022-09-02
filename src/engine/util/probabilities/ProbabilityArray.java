package engine.util.probabilities;

import java.util.Random;

import org.json.JSONArray;

public class ProbabilityArray
{
	private static Random rand = new Random();

	private int defaultState;
	private Probability[] probabilities;

	public ProbabilityArray(int defaultState, Probability... probabilities)
	{
		this.defaultState = defaultState;
		this.probabilities = probabilities;
	}

	public static ProbabilityArray importJSONProbabilityArray(int defaultState, JSONArray jsonArray)
	{
		Probability[] probabilities = new Probability[jsonArray.length()];

		for(int i = 0; i < jsonArray.length(); i++)
		{
			probabilities[i] = Probability.importJSONProbability(jsonArray.getJSONObject(i));
		}

		return new ProbabilityArray(defaultState, probabilities);
	}

	public int getRandomState()
	{
		double roll = rand.nextDouble();
		int index = -1;
		double total = 0;

		while(total < roll)
		{
			index++;

			if(index == this.probabilities.length)
			{
				return this.defaultState;
			}

			total += this.probabilities[index].probability;
		}

		return this.probabilities[index].resultingState;
	}

	@Override
	public String toString()
	{
		String str = "";

		for(Probability probability : probabilities)
		{
			str += probability + "\n";
		}

		return str;
	}
}
