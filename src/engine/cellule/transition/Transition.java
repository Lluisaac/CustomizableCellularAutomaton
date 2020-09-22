package engine.cellule.transition;

import java.util.ArrayList;
import java.util.List;

import engine.Game;
import engine.cellule.Cell;
import engine.cellule.adjacence.AdjacencyByEnumeration;
import engine.grille.Coord;
import engine.grille.Grid;

public abstract class Transition {

	private static List<Transition> allTransitions;

	protected int originalState;
	protected int resultingState;

	public static void init() {
		Transition.allTransitions = new ArrayList<Transition>();

		AdjacencyByEnumeration adj = new AdjacencyByEnumeration();
		adj.addQuantityForState(1, 0);
		adj.addQuantityForState(1, 1);
		adj.addQuantityForState(1, 4);
		adj.addQuantityForState(1, 5);
		adj.addQuantityForState(1, 6);
		adj.addQuantityForState(1, 7);
		adj.addQuantityForState(1, 8);
		Transition.add(new TransitionByEnumeration(1, adj, 0));
		
		adj = new AdjacencyByEnumeration();
		adj.addQuantityForState(1, 3);
		Transition.add(new TransitionByEnumeration(0, adj, 1));
		
	}

	public static void add(Transition transition) {
		Transition.allTransitions.add(transition);
	}

	public static int getTransitionedState(Coord coord) {
		Grid grid = Game.getGame().getGrid();
		Cell cell = grid.getCell(coord);

		for (Transition transition : Transition.allTransitions) {
			if (transition.originalState == cell.getState()
					&& transition.isTransitionAdmissible(coord, grid)) {
				return transition.resultingState;
			}
		}

		return cell.getState();
	}

	public abstract boolean isTransitionAdmissible(Coord coord, Grid grille);
}
