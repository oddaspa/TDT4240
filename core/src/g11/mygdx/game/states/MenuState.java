package g11.mygdx.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import g11.mygdx.game.BattleSheep;
import g11.mygdx.game.MyAssetManager;

public class MenuState implements IState {
    // NEEDS TO CONTAIN THE DATA
    private Array<Texture> allAssets;
    private Array<Sprite> menuSprites;
    // MAKE ALL THE STATES

    public MenuState(){
        this.allAssets = new Array<Texture>();
        MyAssetManager.manager.getAll(Texture.class, allAssets);
        this.menuSprites = new Array<Sprite>();
        loadData();
    }
    @Override
    public String parseInput(float[] data){
        if (data == null) {return "menuState";}
        else{
            Sprite button = this.menuSprites.get(1);
            //      Y  >      400      &&  Y      <       570
            if(data[1] > button.getY() && data[1] < button.getY() + button.getHeight()){
                //  X      >   45          &&  X      <       435
                if(data[0] > button.getX() && data[0] < button.getX() + button.getWidth()){
                    return "placeAnimalState";
                }

            }
        }
        return "menuState";
    }
    @Override
    public Array<String> serveMessages(){
        return null;
    }
    @Override
    public Array<Sprite> serveData(){
        return this.menuSprites;
    }

    @Override
    public void loadData(){
        Texture bg = new Texture("background2.jpg");
        Sprite background = new Sprite(bg);
        background.setPosition(0,0);
        background.setSize(BattleSheep.WIDTH,BattleSheep.HEIGHT);

        Texture playBtn = new Texture("quick_game.png");
        Sprite startButton = new Sprite(playBtn);
        startButton.setSize(2 * BattleSheep.WIDTH / 3, BattleSheep.HEIGHT / 4);
        startButton.setPosition(BattleSheep.WIDTH / 6,(BattleSheep.HEIGHT *2 / 7));

        Texture challengeBtn = new Texture("challenge_friend.png");
        Sprite challengeButton = new Sprite(challengeBtn);
        challengeButton.setSize(2 * BattleSheep.WIDTH / 3, BattleSheep.HEIGHT / 4);

        challengeButton.setPosition(BattleSheep.WIDTH / 6,(BattleSheep.HEIGHT / 14));


        this.menuSprites.add(background);
        this.menuSprites.add(startButton);
        this.menuSprites.add(challengeButton);
    }
}
