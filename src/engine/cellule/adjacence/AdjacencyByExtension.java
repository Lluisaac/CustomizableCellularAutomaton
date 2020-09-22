package engine.cellule.adjacence;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import engine.grille.Coord;

public class AdjacencyByExtension extends Adjacency {

	private Map<Coord, Integer> relativeCoordsPerState;

	public AdjacencyByExtension() {
		this.relativeCoordsPerState = new HashMap<Coord, Integer>();
	}

	public void addState(Coord coord, int state) {
		if (Adjacency.adjacency.contains(coord)) {
			this.relativeCoordsPerState.put(coord, state);
		}
	}

	public Set<Entry<Coord, Integer>> entrySet() {
		return this.relativeCoordsPerState.entrySet();
	}
}
