package engine.cell.adjacency;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import engine.grid.Coord;

public class AdjacencyByExtension extends Adjacency
{

	private Map<Coord, Integer> relativeCoordsPerState;

	public AdjacencyByExtension()
	{
		this(Adjacency.getBasicAdjacency().toArray(new Coord[0]));
	}

	public AdjacencyByExtension(Coord... cell)
	{
		super(cell);
		this.relativeCoordsPerState = new HashMap<Coord, Integer>();
	}

	public void addState(Coord coord, int state)
	{
		if(Adjacency.adjacency.contains(coord))
		{
			this.relativeCoordsPerState.put(coord, state);
		}
	}

	public Set<Entry<Coord, Integer>> entrySet()
	{
		return this.relativeCoordsPerState.entrySet();
	}
}
