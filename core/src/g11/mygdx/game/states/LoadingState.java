package g11.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import g11.mygdx.game.BattleSheep;
import g11.mygdx.game.sprites.Chicken;

public class LoadingState implements IState {
    private Array<Sprite> loadingSprites;
    private Array<String> loadingMessages;
    private Chicken chicken;
    private long startTime;
    public LoadingState(){
        startTime = System.currentTimeMillis();

        this.loadingSprites = new Array<Sprite>();
        this.loadingMessages = new Array<String>();
        loadData();
    }
    @Override
    public String parseInput(float[] data) {
        if(((System.currentTimeMillis() - startTime) / 1000) > 3){
            return "menuState";
        }

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

