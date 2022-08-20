package engine.cell.transition;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import engine.Game;
import engine.cell.Cell;
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
		JSONObject json = Game.getGame().json.getJSONObject("transition");
		Transition.importJsonTransitionEnumeration(json);
	}

	private static void importJsonTransitionEnumeration(JSONObject json)
	{
		JSONArray arr = json.getJSONArray("enumeration");
		
		for(int i = 0; i < arr.length(); i++)
		{
			JSONObject transition = arr.getJSONObject(i);
			
			JSONArray arrAdjacency = transition.getJSONArray("adjacency");
			
			AdjacencyByEnumeration adjEnum = new AdjacencyByEnumeration();
			
			for(int j = 0; j < arrAdjacency.length(); j++)
			{
				JSONObject adjacency = arrAdjacency.getJSONObject(j);
				
				adjEnum.addStateAndQuantity(adjacency.getInt("state"), adjacency.getInt("quantity"));
			}
			
			Transition.add(new TransitionByEnumeration(transition.getInt("initialState"), transition.getInt("resultingState"), adjEnum));
		}
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
