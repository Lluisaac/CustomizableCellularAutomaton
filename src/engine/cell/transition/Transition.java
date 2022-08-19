package engine.cell.transition;

import java.util.ArrayList;
import java.util.List;

import engine.Game;
import engine.cell.Cell;
import engine.cell.adjacency.AdjacencyByEnumeration;
import engine.grid.Coord;
import engine.grid.Grid;
import engine.util.Pair;

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

		AdjacencyByEnumeration adj = new AdjacencyByEnumeration();
		adj.addStateAndQuantity(1, 3);
		Transition.add(new TransitionByEnumeration(0, 1, adj));

		adj = new AdjacencyByEnumeration();
		adj.addStateAndQuantity(1, 0);
		Transition.add(new TransitionByEnumeration(1, 0, adj));

		adj = new AdjacencyByEnumeration();
		adj.addStateAndQuantity(1, 1);
		Transition.add(new TransitionByEnumeration(1, 0, adj));

		adj = new AdjacencyByEnumeration();
		adj.addStateAndQuantity(1, 4);
		Transition.add(new TransitionByEnumeration(1, 0, adj));

		adj = new AdjacencyByEnumeration();
		adj.addStateAndQuantity(1, 5);
		Transition.add(new TransitionByEnumeration(1, 0, adj));

		adj = new AdjacencyByEnumeration();
		adj.addStateAndQuantity(1, 6);
		Transition.add(new TransitionByEnumeration(1, 0, adj));

		adj = new AdjacencyByEnumeration();
		adj.addStateAndQuantity(1, 7);
		Transition.add(new TransitionByEnumeration(1, 0, adj));

		adj = new AdjacencyByEnumeration();
		adj.addStateAndQuantity(1, 8);
		Transition.add(new TransitionByEnumeration(1, 0, adj));
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

	public abstract boolean isTransitionAdmissible(Coord coord, Grid grille);
}
