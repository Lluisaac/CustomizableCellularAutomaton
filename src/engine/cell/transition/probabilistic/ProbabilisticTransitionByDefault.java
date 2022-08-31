package engine.cell.transition.probabilistic;

import engine.grid.Coord;
import engine.grid.Grid;
import engine.util.probabilistic.ProbabilityArray;

public class ProbabilisticTransitionByDefault extends ProbabilisticTransition
{
	
	public ProbabilisticTransitionByDefault(int originalState, ProbabilityArray probabilities)
	{
		super(originalState, probabilities);
	}
	
	public boolean isTransitionAdmissible(Coord coord, Grid grid)
	{
		this.resultingState = this.probabilities.getRandomState();
		
		return this.resultingState != this.originalState;
	}
}
