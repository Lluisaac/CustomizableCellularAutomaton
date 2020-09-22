package engine.cellule.adjacence;

import java.util.ArrayList;
import java.util.List;

import engine.util.Pair;

public class AdjacencyByEnumeration extends Adjacency {

	private List<Pair> quantityForState;

	public AdjacencyByEnumeration() {
		this.quantityForState = new ArrayList<Pair>();
	}

	public void addQuantityForState(int state, int quantity) {
		this.quantityForState.add(new Pair(state, quantity));
	}

	public List<Pair> getPairs() {
		return this.quantityForState;
	}
	
}
