package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.mygdx.game.ParadiseGame;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		new LwjglApplication(new ParadiseGame(), "Paradise Core", 1200, 640);
	}
}
