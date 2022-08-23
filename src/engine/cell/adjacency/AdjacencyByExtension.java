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
		super(new Coord[0]);
		this.relativeCoordsPerState = new HashMap<Coord, Integer>();
	}

	public void addState(Coord coord, int state)
	{
		if(Adjacency.adjacency.contains(coord))
		{
			this.relativeCoordsPerState.put(coord, state);
			super.addToAdjacency(coord);
		}
	}

	public Set<Entry<Coord, Integer>> entrySet()
	{
		return this.relativeCoordsPerState.entrySet();
	}
}
