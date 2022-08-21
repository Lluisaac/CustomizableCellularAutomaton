package engine.cell.transition;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import engine.Game;
import engine.cell.Cell;
import engine.cell.adjacency.Adjacency;
import engine.cell.adjacency.AdjacencyByEnumeration;
import engine.grid.Coord;
import engine.grid.Grid;
import engine.util.Pair;

public abstract class Transition
{

	private static List<Transition> allTransitions;
	private static List<Pair> allSimplifiedTransitions;

	protected int originalState;
	protected int resultingState;

	public static void init()
	{
		Transition.allTransitions = new ArrayList<Transition>();
		Transition.allSimplifiedTransitions = new ArrayList<Pair>();
		
		Transition.importJsonTransition();
	}

	private static void importJsonTransition()
	{
		JSONObject transition = Game.getGame().json.getJSONObject("transition");
		Transition.importJsonTransitionEnumeration(transition);
	}

	private static void importJsonTransitionEnumeration(JSONObject transition)
	{
		JSONArray arr = transition.getJSONArray("enumeration");
		
		for(int i = 0; i < arr.length(); i++)
		{
			JSONObject enumeration = arr.getJSONObject(i);
			
			Transition.importEnumeration(enumeration);
		}
	}

	private static void importEnumeration(JSONObject enumeration)
	{
		JSONArray stateQuantities = enumeration.getJSONArray("stateQuantities");
		
		AdjacencyByEnumeration adjEnum = new AdjacencyByEnumeration(Transition.importAdjacencySubset(enumeration));
		
		for(int j = 0; j < stateQuantities.length(); j++)
		{
			JSONObject adjacency = stateQuantities.getJSONObject(j);
			
			adjEnum.addStateAndQuantity(adjacency.getInt("state"), adjacency.getInt("quantity"));
		}
		
		Transition.add(new TransitionByEnumeration(enumeration.getInt("initialState"), enumeration.getInt("resultingState"), adjEnum));
	}

	private static Coord[] importAdjacencySubset(JSONObject enumeration)
	{
		Coord[] adjacencySubset;
		
		try 
		{
			JSONArray adjSubset = enumeration.getJSONArray("adjacency");
			adjacencySubset = new Coord[adjSubset.length()];
			
			for(int j = 0; j < adjSubset.length(); j++)
			{
				JSONObject jsonCoords = adjSubset.getJSONObject(j);
				adjacencySubset[j] = new Coord(jsonCoords.getInt("x"), jsonCoords.getInt("y"));
			}
			
		}
		catch (JSONException e)
		{				
			adjacencySubset = Adjacency.getBasicAdjacency().toArray(new Coord[0]);
		}
		
		return adjacencySubset;
	}

	public static void add(Transition transition)
	{
		Transition.allTransitions.add(transition);
		Transition.allSimplifiedTransitions.add(new Pair(transition.originalState, transition.resultingState));
	}

	public static int getTransitionedState(Coord coord)
	{
		Grid grid = Game.getGame().getGrid();
		Cell cell = grid.getCell(coord);

		for(Transition transition : Transition.allTransitions)
		{
			if(transition.originalState == cell.getState() && transition.isTransitionAdmissible(coord, grid))
			{
				return transition.resultingState;
			}
		}

		return cell.getState();
	}
	
	public static List<Pair> getAllSimplifiedTransitions()
	{
		return Transition.allSimplifiedTransitions;
	}

	public abstract boolean isTransitionAdmissible(Coord coord, Grid grille);
}
