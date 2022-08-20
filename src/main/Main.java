package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONObject;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import engine.Game;
import renderer.Renderer;

public class Main
{

	public static void main(String[] args) throws SlickException, IOException
	{
		if (args.length == 1)
		{
			String jsonString = Files.readString(Path.of(args[0]));
			JSONObject json = new JSONObject(jsonString);
			new AppGameContainer(Game.setGame(json), Renderer.GAME_WIDTH, Renderer.GAME_HEIGHT, true).start();
		}
		else
		{
			System.out.println("Please put a JSON file path as argument and nothing else");
		}
	}
}
