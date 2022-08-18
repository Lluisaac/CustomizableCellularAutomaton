package engine.cell.adjacency;

import java.util.ArrayList;
import java.util.List;

import engine.grid.Coord;
import engine.util.Pair;

public class AdjacencyByEnumeration extends Adjacency
{

	private List<Pair> quantityForState;

	public AdjacencyByEnumeration()
	{
		this(Adjacency.getBasicAdjacency().toArray(new Coord[0]));
	}

	public AdjacencyByEnumeration(Coord... cell)
	{
		super(cell);
		this.quantityForState = new ArrayList<Pair>();
	}

	public void addQuantityForState(int state, int quantity)
	{
		this.quantityForState.add(new Pair(state, quantity));
	}

	public List<Pair> getPairs()
	{
		return this.quantityForState;
	}

}
