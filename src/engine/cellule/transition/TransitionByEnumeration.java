package engine.cellule.transition;

import java.util.HashMap;
import java.util.Map;

import engine.cellule.adjacence.Adjacency;
import engine.cellule.adjacence.AdjacencyByEnumeration;
import engine.grille.Coord;
import engine.grille.Grid;
import engine.util.Pair;

public class TransitionByEnumeration extends Transition {

	private AdjacencyByEnumeration enumeration;

	public TransitionByEnumeration(int originalState, AdjacencyByEnumeration enumeration, int resultingState) {
		this.originalState = originalState;
		this.enumeration = enumeration;
		this.resultingState = resultingState;
	}

	@Override
	public boolean isTransitionAdmissible(Coord coord, Grid grid) {
		boolean result = false;

		Map<Integer, Integer> values = buildValuesMap(coord, grid);

		for (Pair pair : this.enumeration.getPairs()) {
			if ((values.containsKey(pair.first) && values.get(pair.first) == pair.second)
					|| (!values.containsKey(pair.first) && pair.second == 0)) {
				result = true;
			}
		}

		return result;
	}

	private Map<Integer, Integer> buildValuesMap(Coord coord, Grid grid) {
		Map<Integer, Integer> values = new HashMap<Integer, Integer>();

		for (Coord adj : Adjacency.getAdjacency()) {
			Coord relativeCoord = coord.plus(adj);
			addValue(values, grid.getCell(relativeCoord).getState());
		}

		return values;
	}

	private void addValue(Map<Integer, Integer> valuesMap, int value) {
		if (!valuesMap.containsKey(value)) {
			valuesMap.put(value, 0);
		}

		valuesMap.replace(value, valuesMap.get(value) + 1);
	}

}
