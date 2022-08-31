package engine.cell.transition.probabilistic;

import engine.cell.transition.Transition;
import engine.util.probabilistic.ProbabilityArray;

public abstract class ProbabilisticTransition extends Transition
{
	protected ProbabilityArray probabilities;

	public ProbabilisticTransition(int originalState, ProbabilityArray probabilities)
	{
		super(originalState, originalState);
		this.probabilities = probabilities;
	}
}
