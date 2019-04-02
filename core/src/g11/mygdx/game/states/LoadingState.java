package g11.mygdx.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import g11.mygdx.game.BattleSheep;
import g11.mygdx.game.sprites.Animation;
import g11.mygdx.game.sprites.Chicken;

public class LoadingState implements IState {
    private Array<Sprite> loadingSprites;
    private Array<String> loadingMessages;
    private Chicken chicken;

    public LoadingState(){
        this.loadingSprites = new Array<Sprite>();
        this.loadingMessages = new Array<String>();
        loadData();
    }
    @Override
    public String parseInput(float[] data) {
        return "loadingState";
    }

    @Override
    public Array<String> serveMessages() {
        return this.loadingMessages;
    }

    @Override
    public Array<Sprite> serveData() {
        this.chicken.update(BattleSheep.delta/4);
        return this.loadingSprites;
    }

    @Override
    public void loadData() {
        chicken = new Chicken(300,300);
        chicken.setPosition(BattleSheep.WIDTH/2 - chicken.getWidth()/2, BattleSheep.HEIGHT/2 - chicken.getHeight()/2);
        this.loadingSprites.add(chicken);


    }




}

