package engine.cellule.transition;

import java.util.Map.Entry;

import engine.cellule.adjacence.AdjacencyByExtension;
import engine.grille.Coord;
import engine.grille.Grid;

public class TransitionByExtension extends Transition {

	private AdjacencyByExtension adjacentCells;

	public TransitionByExtension(int originalState, AdjacencyByExtension adjacentCells, int resultingState) {
		this.originalState = originalState;
		this.adjacentCells = adjacentCells;
		this.resultingState = resultingState;
	}

	public boolean isTransitionAdmissible(Coord coord, Grid grid) {
		boolean result = true;

		for (Entry<Coord, Integer> entry : this.adjacentCells.entrySet()) {
			Coord relativeCoord = coord.plus(entry.getKey());
			if (grid.getCell(relativeCoord).getState() != entry.getValue()) {
				result &= false;
			}
		}

		return result;
	}
}
