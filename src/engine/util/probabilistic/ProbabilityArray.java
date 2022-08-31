package engine.util.probabilistic;

import java.util.Random;

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
	
	public int getRandomState()
	{
		double roll = rand.nextDouble();
		int index = -1;
		double total = 0;
		
		while (total < roll)
		{
			index++;
			
			if (index == this.probabilities.length)
			{
				return this.defaultState;
			}
			
			total += this.probabilities[index].probability;
		}
		
		return this.probabilities[index].resultingState;
	}
}
