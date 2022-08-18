package engine.cell.transition;

import java.util.ArrayList;
import java.util.List;

import engine.Game;
import engine.cell.Cell;
import engine.cell.adjacency.AdjacencyByEnumeration;
import engine.grid.Coord;
import engine.grid.Grid;

public abstract class Transition
{

	private static List<Transition> allTransitions;

	protected int originalState;
	protected int resultingState;

	public static void init()
	{
		Transition.allTransitions = new ArrayList<Transition>();

		AdjacencyByEnumeration adj = new AdjacencyByEnumeration();
		adj.addQuantityForState(2, 1);

		AdjacencyByEnumeration adj2 = new AdjacencyByEnumeration(new Coord(1, 0), new Coord(-1, 0), new Coord(0, 1), new Coord(0, -1));
		adj2.addQuantityForState(2, 1);
		adj2.addQuantityForState(1, 0);

		Transition.add(new TransitionByEnumeration(0, 2, adj, adj2));

		adj = new AdjacencyByEnumeration();
		adj.addQuantityForState(2, 2);

		adj2 = new AdjacencyByEnumeration(new Coord(1, 0), new Coord(-1, 0), new Coord(0, 1), new Coord(0, -1));
		adj2.addQuantityForState(2, 1);
		adj2.addQuantityForState(1, 0);

		Transition.add(new TransitionByEnumeration(0, 2, adj, adj2));

		Transition.add(new TransitionByDefault(2, 1));

	}

	public static void add(Transition transition)
	{
		Transition.allTransitions.add(transition);
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

	public abstract boolean isTransitionAdmissible(Coord coord, Grid grille);
}
