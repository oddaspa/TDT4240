package g11.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import g11.mygdx.game.BattleSheep;
public class DesktopLauncher {
	public static void main (String[] arg) {
		DummyPlayServices dumdum = new DummyPlayServices();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 480;
		config.height = 800;
		config.title = BattleSheep.TITLE;
		new LwjglApplication(new BattleSheep(dumdum), config);
	}
}
