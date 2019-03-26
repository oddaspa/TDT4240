package g11.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class MyAssetManager {
    public static final AssetManager manager = new AssetManager();
    public static final String helicopter = "heli13.png";
    public static final String startButton = "StartButton.png";
    public static final String drone = "droneCylce.png";
    public static final String bullet = "bullet.png";
    public static final String background = "background.png";

    public static Array<Texture> allAssets;
    public static void load() {
        manager.load(helicopter, Texture.class);
        manager.load(startButton, Texture.class);
        manager.load(drone, Texture.class);
        manager.load(bullet, Texture.class);
        manager.load(background, Texture.class);
    }


    public static void dispose(){
        manager.dispose();
    }
}
