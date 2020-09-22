package engine.cellule;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import engine.Element;
import engine.Game;
import engine.cellule.adjacence.Adjacency;
import engine.cellule.transition.Transition;
import engine.grille.Coord;
import renderer.CellImage;

public class Cell extends Element {
	public static final int LENGTH = 32;
	public static final int HEIGHT = 32;

	private Coord coord;

	private static List<Image> stateImagesList;

	private int state;
	private int nextState;

	public Cell(Coord coord) {
		this.coord = coord;

		this.state = 0;
		this.nextState = 0;
	}

	public static void init() throws SlickException {
		Cell.stateImagesList = new ArrayList<Image>();

		Cell.stateImagesList.add(new CellImage(0, 0, 0));
		Cell.stateImagesList.add(new CellImage(255, 255, 255));
	}

	@Override
	public Image getImage() throws SlickException {
		return Cell.stateImagesList.get(this.state);
	}

	@Override
	public float getX() {
		return this.coord.x * Cell.HEIGHT;
	}

	@Override
	public float getY() {
		return this.coord.y * Cell.LENGTH;
	}

	@Override
	public void click() {
		if (this.state == 0) {
			this.setState(1);
		} else {
			this.setState(0);
		}
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		if (state != this.state) {
			this.state = state;
			this.nextState = state;

			Game.getGame().addToUpdate(this);

			for (Coord adj : Adjacency.getAdjacency()) {
				Coord relativeCoord = coord.plus(adj.reverse());
				Cell cell = Game.getGame().getGrid().getCell(relativeCoord);
					
				Game.getGame().addToUpdate(cell);
			}
		}
	}

	public void preUpdate() {
		this.nextState = Transition.getTransitionedState(this.coord);
	}

	public void postUpdate() {
		this.setState(this.nextState);
	}

	public Coord getCoord() {
		return this.coord;
	}

}
