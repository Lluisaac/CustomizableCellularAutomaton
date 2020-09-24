package engine.grid;

import engine.util.Pair;

public class Coord {
	public int x;
	public int y;

	public Coord() {
		this(0, 0);
	}

	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coord(Coord copy) {
		this.x = copy.x;
		this.y = copy.y;
	}

	public Coord(Pair coord) {
		this.x = coord.first;
		this.y = coord.second;
	}

	public Coord plus(Coord other) {
		Coord result = new Coord(other);

		result.x += this.x;
		result.y += this.y;

		return result;
	}

	public Coord plus(int x, int y) {
		return this.plus(new Coord(x, y));
	}

	public Coord reverse() {
		return new Coord(-this.x, -this.y);
	}

	@Override
	public String toString() {
		return "[" + this.x + ", " + y + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(Coord.class) && ((Coord) obj).x == this.x && ((Coord) obj).y == y) {
			return true;
		} else {
			return false;
		}
	}
}
