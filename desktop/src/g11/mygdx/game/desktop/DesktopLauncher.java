package g11.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import g11.mygdx.game.BattleSheep;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = BattleSheep.WIDTH;
		config.height = BattleSheep.HEIGHT;
		config.title = BattleSheep.TITLE;


		new LwjglApplication(new BattleSheep(), config);
	}
}
