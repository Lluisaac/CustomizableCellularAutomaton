package engine.cell.transition.deterministic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import engine.Game;
import engine.cell.Cell;
import engine.cell.transition.probabilistic.ProbabilisticTransition;
import engine.grid.Coord;
import engine.grid.Grid;
import engine.util.Pair;
import engine.util.probabilities.Probability;
import engine.util.probabilities.ProbabilityArray;

public abstract class Transition
{

	private static List<Transition> allTransitions;
	private static List<Pair> allSimplifiedTransitions;

	protected int originalState;
	protected int resultingState;

	public static void init()
	{
		Transition.allTransitions = new ArrayList<Transition>();
		Transition.allSimplifiedTransitions = new ArrayList<Pair>();
		
		ProbabilityArray probabilities = new ProbabilityArray(8, new Probability(3, 0.5));
		TransitionByDefault transDefault = new TransitionByDefault(8, 8);
		ProbabilisticTransition<TransitionByDefault> trans = new ProbabilisticTransition<TransitionByDefault>(transDefault, probabilities);
		Transition.add(trans);
		
		Transition.importJsonTransition();
	}

	private static void importJsonTransition()
	{
		JSONObject transition = Game.getGame().json.getJSONObject("transition");
		
		TransitionByEnumeration.importJsonTransitionEnumeration(transition);
		TransitionByExtension.importJsonTransitionExtension(transition);
		TransitionByDefault.importJsonTransitionDefault(transition);
	}

	public static void add(Transition transition)
	{
		Transition.allTransitions.add(transition);
		Transition.allSimplifiedTransitions.add(new Pair(transition.originalState, transition.resultingState));
	}

	public static int getTransitionedState(Coord coord)
	{
		Grid grid = Game.getGame().getGrid();
		Cell cell = grid.getCell(coord);

		for(Transition transition : Transition.allTransitions)
		{
			if(transition.originalState == cell.getState() && transition.isTransitionAdmissible(coord, grid))
			{
				return transition.resultingState;
			}
		}

		return cell.getState();
	}
	
	public static List<Pair> getAllSimplifiedTransitions()
	{
		return Transition.allSimplifiedTransitions;
	}
	
	public Transition(int originalState, int resultingState)
	{
		this.originalState = originalState;
		this.resultingState = resultingState;
	}

	public abstract boolean isTransitionAdmissible(Coord coord, Grid grille);
	
	public int getOriginalState()
	{
		return this.originalState;
	}
}
