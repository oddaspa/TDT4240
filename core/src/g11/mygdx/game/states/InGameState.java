package g11.mygdx.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import g11.mygdx.game.BattleSheep;

public class InGameState implements IState {
    private Array<Sprite> opponentBoard;
    private Array<Sprite> myBoard;
    public InGameState(){
        this.myBoard = new Array<Sprite>();
        this.opponentBoard = new Array<Sprite>();

    }


    @Override
    public String parseInput(float[] data) {
        return null;
    }

    @Override
    public Array<String> serveMessages() {
        return null;
    }

    @Override
    public Array<Sprite> serveData() {
        return null;
    }

    @Override
    public void loadData() {

    }

    private void placeOpponentBoard(){
        Random rand = new Random();
        Texture g1 = new Texture("grass-1.png");
        Texture g2 = new Texture("grass-2.png");
        Texture g3 = new Texture("grass-3.png");
        Texture g4 = new Texture("grass-4.png");
        Sprite grass1 = new Sprite(g1);
        Sprite grass2 = new Sprite(g2);
        Sprite grass3 = new Sprite(g3);
        Sprite grass4 = new Sprite(g4);

        Array<Sprite> grassSprites = new Array<Sprite>();
        grassSprites.add(grass1);
        grassSprites.add(grass2);
        grassSprites.add(grass3);
        grassSprites.add(grass4);
        //random placement of tiles
        for(int i = 0; i <= 3; i++){
            for(int j = 0; j <= 7; j++){
                int n = rand.nextInt(4);
                Sprite s = new Sprite(grassSprites.get(n).getTexture(), BattleSheep.WIDTH/6 - 2,BattleSheep.WIDTH/6 - 2);
                s.setPosition(j*BattleSheep.WIDTH/6 + 1,i*BattleSheep.WIDTH/6 + 1 + 400);
                this.opponentBoard.add(s);
            }
        }
    }

    private void placeMyBoard(){
        Random rand = new Random();
        Texture g1 = new Texture("grass-1.png");
        Texture g2 = new Texture("grass-2.png");
        Texture g3 = new Texture("grass-3.png");
        Texture g4 = new Texture("grass-4.png");
        Sprite grass1 = new Sprite(g1);
        Sprite grass2 = new Sprite(g2);
        Sprite grass3 = new Sprite(g3);
        Sprite grass4 = new Sprite(g4);

        Array<Sprite> grassSprites = new Array<Sprite>();
        grassSprites.add(grass1);
        grassSprites.add(grass2);
        grassSprites.add(grass3);
        grassSprites.add(grass4);
        //random placement of tiles
        for(int i = 0; i <= 3; i++){
            for(int j = 0; j <=7; j++){
                int n = rand.nextInt(4);
                Sprite s = new Sprite(grassSprites.get(n).getTexture(),BattleSheep.WIDTH/6 - 2,BattleSheep.WIDTH/6 - 2);
                s.setPosition(j*BattleSheep.WIDTH/6 + 1,i*BattleSheep.WIDTH/6 + 1);
                this.myBoard.add(s);
            }
        }
    }


}
