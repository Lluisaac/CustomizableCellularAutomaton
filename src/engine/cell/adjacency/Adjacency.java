package engine.cell.adjacency;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import engine.Game;
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
		
		Adjacency.importJsonAdjacency();
		
		System.out.println(adjacency);
	}

	private static void importJsonAdjacency()
	{
		JSONArray arr = Game.getGame().json.getJSONArray("adjacency");
		
		for(int i = 0; i < arr.length(); i++)
		{
			JSONObject coords = arr.getJSONObject(i);
			Adjacency.adjacency.add(new Coord(coords.getInt("x"), coords.getInt("y")));
		}
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
