package engine.cell.transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import engine.cell.Cell;
import engine.cell.adjacency.Adjacency;
import engine.cell.adjacency.AdjacencyByEnumeration;
import engine.grid.Coord;
import engine.grid.Grid;
import engine.util.Pair;

public class TransitionByEnumeration extends Transition
{

	private AdjacencyByEnumeration[] enumeration;
	private int[] stateArray;

	public TransitionByEnumeration(int originalState, int resultingState, AdjacencyByEnumeration... enumeration)
	{
		this(originalState, resultingState, 1, enumeration);
	}

	public TransitionByEnumeration(int originalState, int resultingState, double probability, AdjacencyByEnumeration... enumeration)
	{
		this.originalState = originalState;
		this.resultingState = resultingState;
		this.probability = probability;
		
		this.enumeration = enumeration;
		
		this.stateArray = new int[Cell.getNumberOfStates()];
	}

	@Override
	public boolean isTransitionAdmissible(Coord coord, Grid grid)
	{
		for(AdjacencyByEnumeration adj : this.enumeration)
		{
			buildAdjQuantitiesArray(coord, grid, adj);

			for(Pair pair : adj.getPairs())
			{
				if(this.stateArray[pair.first] != pair.second)
				{
					return false;
				}
			}
		}

		return super.getRandomChance();
	}

	private void buildAdjQuantitiesArray(Coord coord, Grid grid, Adjacency adj)
	{
		this.clearStateArray();

		for(Coord relativeCoord : adj.getAdjacency())
		{
			Coord absoluteCoord = coord.plus(relativeCoord);
			Cell cell = grid.getCell(absoluteCoord);

			if(cell == null)
			{
				this.stateArray[0]++;
			}
			else
			{
				this.stateArray[cell.getState()]++;
			}
		}
	}

	private void clearStateArray()
	{
		for(int i = 0; i < stateArray.length; i++)
		{
			stateArray[i] = 0;
		}
	}
	
	public static void importJsonTransitionEnumeration(JSONObject transition)
	{
		JSONArray arr = transition.getJSONArray("enumeration");
		
		for(int i = 0; i < arr.length(); i++)
		{
			JSONObject enumeration = arr.getJSONObject(i);
			
			TransitionByEnumeration.importEnumeration(enumeration);
		}
	}

	private static void importEnumeration(JSONObject enumeration)
	{
		JSONArray stateQuantities = enumeration.getJSONArray("stateQuantities");
		
		AdjacencyByEnumeration adjEnum = new AdjacencyByEnumeration(TransitionByEnumeration.importAdjacencySubset(enumeration));
		
		for(int j = 0; j < stateQuantities.length(); j++)
		{
			JSONObject adjacency = stateQuantities.getJSONObject(j);
			
			adjEnum.addStateAndQuantity(adjacency.getInt("state"), adjacency.getInt("quantity"));
		}
		
		Transition.add(new TransitionByEnumeration(enumeration.getInt("initialState"), enumeration.getInt("resultingState"), adjEnum));
	}

	private static Coord[] importAdjacencySubset(JSONObject enumeration)
	{		
		try 
		{
			JSONArray adjSubset = enumeration.getJSONArray("adjacencySubset");
			Coord[] adjacencySubset = new Coord[adjSubset.length()];
			
			for(int j = 0; j < adjSubset.length(); j++)
			{
				JSONObject jsonCoords = adjSubset.getJSONObject(j);
				adjacencySubset[j] = new Coord(jsonCoords.getInt("x"), jsonCoords.getInt("y"));
			}
			
			return adjacencySubset;
		}
		catch (JSONException e)
		{				
			return Adjacency.getBasicAdjacency().toArray(new Coord[0]);
		}
	}

}
