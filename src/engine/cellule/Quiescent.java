package engine.cellule;

import engine.grille.Coord;

public class Quiescent extends Cell {
	
	private static Quiescent singleton;

	private Quiescent(Coord coord) {
		super(coord);
	}
	
	public static Quiescent getQuiescent() {
		if (Quiescent.singleton == null) {
			Quiescent.singleton = new Quiescent(null);
		}
		
		return Quiescent.singleton;
	}
	
	@Override
	public int getState() {
		return 0;
	}

}
