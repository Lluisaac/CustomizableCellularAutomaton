package main;

import java.io.File;
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
		File nativePath = new File("natives/");
		System.setProperty("org.lwjgl.librarypath", nativePath.getAbsolutePath());
		
		Path path;
		
		if (args.length == 1)
		{
			path = Path.of(args[0]);
		}
		else
		{
			path = Path.of("rules.json");
		}
		
		try
		{			
			String jsonString = Files.readString(path);
			JSONObject json = new JSONObject(jsonString);
			new AppGameContainer(Game.setGame(json), Renderer.GAME_WIDTH, Renderer.GAME_HEIGHT, true).start();
		}
		catch (IOException e)
		{
			System.out.println("File " + path + " not found. Start argain with correct json rules file as argument.");
		}
	}
}
