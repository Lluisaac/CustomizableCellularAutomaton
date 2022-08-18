package engine.cell.transition;

import engine.grid.Coord;
import engine.grid.Grid;

public class TransitionByDefault extends Transition
{

	public TransitionByDefault(int originalState, int resultingState)
	{
		this.originalState = originalState;
		this.resultingState = resultingState;
	}

	public boolean isTransitionAdmissible(Coord coord, Grid grid)
	{
		return true;
	}
}
