package g11.mygdx.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import g11.mygdx.game.BattleSheep;

public class InGameState implements IState {
    private Array<Sprite> opponentBoard;
    private Array<Sprite> myBoard;
    private Array<String> inGameMessages;
    public InGameState(){
        this.myBoard = new Array<Sprite>();
        this.opponentBoard = new Array<Sprite>();
        this.inGameMessages = new Array<String>();
        loadData();

    }


    @Override
    public String parseInput(float[] data) {
        return "inGameStatus";
    }

    @Override
    public Array<String> serveMessages() {
        return this.inGameMessages;
    }

    @Override
    public Array<Sprite> serveData() {
        Array<Sprite> allData = this.myBoard;
        allData.addAll(this.opponentBoard);
        return allData;
    }

    @Override
    public void loadData() {
        placeOpponentBoard();
        placeMyBoard();
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
        for(int i = 0; i <= 8; i++){
            for(int j = 0; j <= 8; j++){
                int n = rand.nextInt(4);
                Sprite s = new Sprite(grassSprites.get(n).getTexture(), BattleSheep.WIDTH/10 - 2,BattleSheep.WIDTH/10 - 2);
                s.setPosition(j*BattleSheep.WIDTH/10 + 1 + BattleSheep.WIDTH/20,i*BattleSheep.WIDTH/10 + 1 + 300);
                this.opponentBoard.add(s);
            }
        }

        Texture b = new Texture("bonde-liten.png");
        Sprite bonde = new Sprite(b,b.getWidth()/2,b.getHeight()/2);
        bonde.setPosition(60,700);
        this.myBoard.add(bonde);
        this.inGameMessages.add("");
        this.inGameMessages.add("Marius");
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
        float rangeY = (BattleSheep.HEIGHT/4)/8;
        //random placement of tiles
        for(int i = 0; i <= 8; i++){
            for(int j = 0; j <=8; j++){
                int n = rand.nextInt(4);
                Sprite s = new Sprite(grassSprites.get(n).getTexture(),Math.round(rangeY) - 2,Math.round(rangeY) - 2);
                s.setPosition(j*rangeY + 1 + BattleSheep.WIDTH/4,i*rangeY + 1 + 50 );
                this.myBoard.add(s);
            }
        }
        Texture b = new Texture("bonde-liten.png");
        Sprite bonde = new Sprite(b,b.getWidth()/2,b.getHeight()/2);
        bonde.setPosition(420,50);
        this.myBoard.add(bonde);
        this.inGameMessages.add("Odd Gunnar");
    }


}
