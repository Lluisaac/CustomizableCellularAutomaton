package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import engine.Game;
import renderer.Renderer;

public class Main {

	public static void main(String[] args) throws SlickException {
		new AppGameContainer(Game.getGame(), Renderer.GAME_WIDTH, Renderer.GAME_HEIGHT, true).start();
	}
}
