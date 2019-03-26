package g11.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import g11.mygdx.game.sprites.Drone;
import g11.mygdx.game.sprites.Helicopter;

public class Model {
    // NEEDS TO CONTAIN THE DATA
    private Array<Texture> allAssets;
    private Array<Sprite> allSprites;
    private Array<Sprite> menuSprites;
    private Array<Sprite> playSprites;
    private HashMap<String, Array<Sprite>> internalData;
    private int currentMode;
    // MAKE ALL THE STATES

    public Model(){
        this.currentMode = 1;
        this.allAssets = new Array<Texture>();
        MyAssetManager.manager.getAll(Texture.class, allAssets);
        this.allSprites = new Array<Sprite>();
        this.menuSprites = new Array<Sprite>();
        this.playSprites = new Array<Sprite>();
        this.internalData = new HashMap<String, Array<Sprite>>();
        loadAllSprites();

    }

    public void parseInput(float[] data){
        if(currentMode == 1){
            Sprite button = this.internalData.get("menu").get(1);
            //      Y  >      400      &&  Y      <       570
            if(data[1] > button.getY() && data[1] < button.getY() + button.getHeight()){
                //  X      >   45          &&  X      <       435
                if(data[0] > button.getX() && data[0] < button.getX() + button.getWidth()){
                    System.out.println("Pressed Start Button");
                    this.currentMode = 0;
                }
            }
        }
        if(currentMode == 0){
            Array<Sprite> currentSprites = servePlaySprites();
            for(Sprite s : currentSprites){
                if (s instanceof Helicopter) {
                    float x = data[0];
                    ((Helicopter) s).update(BattleSheep.delta);
                    ((Helicopter) s).jump(x);
                }
            }
            // LOGIC FOR PLAY STATE
        }
    }

    // Pass relevant sprites
    public Array<Sprite> serveData(){
        if(this.currentMode == 1){
            System.out.println("Serving menu state");
            return serveMenuSprites();
        } else
            System.out.println("Serving play state");
            return servePlaySprites();
    }

    // NB TextureAtlas might solve a lot of problems
    private void loadAllSprites(){
        Texture bg = new Texture("background.jpg");
        Texture sB = new Texture("StartButton.png");
        Texture heliText = new Texture("heli13.png");
        Texture droneText = new Texture("droneCycle.png");
        Helicopter heli = new Helicopter(heliText);
        Drone drone = new Drone(200, droneText);
        Sprite background = new Sprite(bg, 0,0, BattleSheep.WIDTH, BattleSheep.HEIGHT);
        Sprite startButton = new Sprite(sB, (BattleSheep.WIDTH / 2) - (sB.getWidth() / 2), BattleSheep.HEIGHT / 2, sB.getWidth(),sB.getHeight());

        // Had to use set position for some reason..
        startButton.setPosition((BattleSheep.WIDTH / 2) - (sB.getWidth() / 2), BattleSheep.HEIGHT / 2);
        this.playSprites.add(heli);
        this.playSprites.add(drone);
        this.menuSprites.add(background);
        this.menuSprites.add(startButton);
        this.internalData.put("menu", menuSprites);
        this.internalData.put("play", playSprites);

    }

    public Array<Sprite> serveMenuSprites(){
        System.out.println(this.internalData.get("menu"));
        return this.internalData.get("menu");
    }

    public Array<Sprite> servePlaySprites(){
        return this.internalData.get("play");
    }

    // PROVIDES ACCESS TO THE DATA




}
