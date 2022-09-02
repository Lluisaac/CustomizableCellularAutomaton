package engine.cell.transition.probabilistic;

import engine.cell.transition.Transition;
import engine.grid.Coord;
import engine.grid.Grid;
import engine.util.probabilities.ProbabilityArray;

public class ProbabilisticTransition<T extends Transition> extends Transition
{
	protected ProbabilityArray probabilities;
	private T transition;

	public ProbabilisticTransition(T transition, ProbabilityArray probabilites)
	{
		super(transition.getOriginalState(), transition.getOriginalState());
		this.transition = transition;
		this.probabilities = probabilites;
	}

	@Override
	public boolean isTransitionAdmissible(Coord coord, Grid grid)
	{
		this.resultingState = this.probabilities.getRandomState();

		return this.resultingState != this.originalState && this.transition.isTransitionAdmissible(coord, grid);
	}
}
