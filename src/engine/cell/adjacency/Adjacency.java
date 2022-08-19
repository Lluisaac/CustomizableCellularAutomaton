package engine.cell.adjacency;

import java.util.ArrayList;
import java.util.List;

import engine.grid.Coord;

public abstract class Adjacency
{

	protected static List<Coord> adjacency;

	private List<Coord> adjacentCells;

	public Adjacency(Coord[] cell)
	{
		this.adjacentCells = new ArrayList<Coord>();

		for(Coord coord : cell)
		{
			if(Adjacency.adjacency.contains(coord))
			{
				this.adjacentCells.add(coord);
			}
		}
	}

	public List<Coord> getAdjacency()
	{
		return this.adjacentCells;
	}

	public static void init()
	{
		Adjacency.adjacency = new ArrayList<Coord>();

		Adjacency.adjacency.add(new Coord(-1, -1));
		Adjacency.adjacency.add(new Coord(-1, 0));
		Adjacency.adjacency.add(new Coord(-1, 1));
		Adjacency.adjacency.add(new Coord(0, -1));
		Adjacency.adjacency.add(new Coord(0, 1));
		Adjacency.adjacency.add(new Coord(1, -1));
		Adjacency.adjacency.add(new Coord(1, 0));
		Adjacency.adjacency.add(new Coord(1, 1));
	}

	public static void setAdjacency(List<Coord> adj)
	{
		Adjacency.adjacency = adj;
	}

	public static List<Coord> getBasicAdjacency()
	{
		return Adjacency.adjacency;
	}
}
