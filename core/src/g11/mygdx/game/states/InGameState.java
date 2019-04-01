package g11.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import g11.mygdx.game.BattleSheep;
import g11.mygdx.game.sprites.Helicopter;

public class InGameState implements IState {
    private Array<Sprite> opponentBoard;
    private Array<Sprite> myBoard;
    private Array<String> inGameMessages;
    private double col = 0;
    private double row = 0;
    private float LEFTBORDER = 1 + BattleSheep.WIDTH / 20;
    private float RIGHTBOARDER = 8 * BattleSheep.WIDTH / 10 + 1 + BattleSheep.WIDTH / 20;
    private float TOPBORDER = 8 * BattleSheep.WIDTH / 10 + 1 + 300;
    private float BOTTOMBORDER = 1 + 300;

    private Array<Integer> enemyAnimals;
    public InGameState(){
        this.myBoard = new Array<Sprite>();
        this.opponentBoard = new Array<Sprite>();
        this.inGameMessages = new Array<String>();
        this.enemyAnimals = new Array<Integer>();
        loadData();
        System.out.println(this.opponentBoard.size);
    }


    @Override
    public String parseInput(float[] data) {
        float[] spritePosition = new float[2];
        float spriteX;
        float spriteY;
        double[] coordinates = parsePosition(data);
        double[] spriteCoordinates;
        if(coordinates[1] != -1){
            if(this.opponentBoard != null){
                for(Sprite c : this.opponentBoard){
                    spriteX = c.getX() + c.getWidth()/2;
                    spriteY = c.getY() + c.getHeight()/2;
                    spritePosition[0] = spriteX;
                    spritePosition[1] = spriteY;
                    spriteCoordinates = parsePosition(spritePosition);
                    if(coordinates[0] == spriteCoordinates[0] && coordinates[1] == spriteCoordinates[1]){
                        //TODO: set sprite to isHit and update texture.
                        if(c instanceof Helicopter){
                            ((Helicopter) c).gotHit();
                        } else{
                            this.opponentBoard.get(this.opponentBoard.indexOf(c, false)).setSize(10,10);
                        }

                    }
                }
            }
        }
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
        placeOpponentBoard("oppBoard.txt");
        placeMyBoard("myBoard.txt");
    }

    public double[] parsePosition(float[] data){
        double[] output = new double[2];
        output[0] = -1;
        output[1] = -1;
        if(data[0] < RIGHTBOARDER && data[0]  > LEFTBORDER){
            float POS_X = data[0] - LEFTBORDER;
            if(data[1] < TOPBORDER && data[1] > BOTTOMBORDER){
                float POS_Y = data[1] - BOTTOMBORDER;
                row = Math.ceil((POS_Y/(TOPBORDER-BOTTOMBORDER))*8);
                col = Math.ceil((POS_X/(RIGHTBOARDER-LEFTBORDER))*8);
                output[0] = col;
                output[1] = row;
            }
        }
        return output;
    }

    public String[] readFile(String file) {
        FileHandle handle = Gdx.files.local(file);
        String text = handle.readString();
        String wordsArray[] = text.split("\\r?\\n");
        return wordsArray;
    }
    public void writeFile(String file, int col, int row, char item){
        FileHandle handle = Gdx.files.local(file);
        String text = handle.readString();
        String wordsArray[] = text.split("\\r?\\n");
        wordsArray[row].toCharArray()[col] = item;
        for(String s : wordsArray){
            handle.writeString(s, false);
        }

    }

    private void placeMyBoard(String file){
        this.myBoard = new Array<Sprite>();
        String[] fromFile = readFile(file);
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
        this.inGameMessages.add(fromFile[0]);
        int i = 0;
        int j = 0;
        int k = 0;
        for(String s : fromFile){
            if(k==0){
                k++;
                continue;
            }

            for(char c : s.toCharArray()){
                // Grass underneath
                int n = rand.nextInt(4);
                Sprite sprite = new Sprite(grassSprites.get(n).getTexture(),Math.round(rangeY) - 2,Math.round(rangeY) - 2);
                sprite.setPosition(j*rangeY + 1 + BattleSheep.WIDTH/4,i*rangeY + 1 + 50 );
                this.myBoard.add(sprite);
                if(c == 'c' ){
                    // Chicken
                    Texture tex = new Texture("chicken-liten.png");
                    Sprite chicken = new Sprite(tex,Math.round(rangeY) - 2,Math.round(rangeY) - 2);
                    chicken.setPosition(j*rangeY + 1 + BattleSheep.WIDTH/4,i*rangeY + 1 + 50 );
                    this.myBoard.add(chicken);
                }
                if (c == 's'){
                    if(s.charAt(j - 1) == 's'){
                        Texture tex = new Texture("sheep-liten.png");
                        Sprite sheep = new Sprite(tex,Math.round(rangeY*2) - 2,Math.round(rangeY) - 2);
                        sheep.setPosition(j*rangeY + 1 + BattleSheep.WIDTH/4,i*rangeY + 1 + 50 - rangeY );
                        this.myBoard.add(sheep);
                    }
                }
                j++;
            }
            j=0;
            i++;
        }

        Texture b = new Texture("bonde-liten.png");
        Sprite bonde = new Sprite(b,b.getWidth()/2,b.getHeight()/2);
        bonde.setPosition(420,50);
        this.myBoard.add(bonde);
    }

    //TODO: USE CORRECT SPRITES
    private void placeOpponentBoard(String file){
        this.myBoard = new Array<Sprite>();
        String[] fromFile = readFile(file);
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
        this.inGameMessages.add("");
        this.inGameMessages.add(fromFile[0]);
        int i = 0;
        int j = 0;
        int k = 0;
        for(String s : fromFile) {
            if (k == 0) {
                k++;
                continue;
            }

            for (char c : s.toCharArray()) {
                // Grass underneath
                int n = rand.nextInt(4);
                Sprite grass = new Sprite(grassSprites.get(n).getTexture(), BattleSheep.WIDTH / 10 - 2, BattleSheep.WIDTH / 10 - 2);
                grass.setPosition(j * BattleSheep.WIDTH / 10 + 1 + BattleSheep.WIDTH / 20, i * BattleSheep.WIDTH / 10 + 1 + 300);
                this.opponentBoard.add(grass);
                if (c == 'x') {
                    // Chicken
                    Texture tex = new Texture("bullet.png");
                    Sprite chicken = new Sprite(tex, BattleSheep.WIDTH / 10 - 2, BattleSheep.WIDTH / 10 - 2);
                    chicken.setPosition(j * BattleSheep.WIDTH / 10 + 1 + BattleSheep.WIDTH / 20, i * BattleSheep.WIDTH / 10 + 1 + 300);
                    this.opponentBoard.add(chicken);
                    this.enemyAnimals.add(i*7 + j-1);
                }
                if (c == 'b') {
                    Texture tex = new Texture("heli13.png");
                    Helicopter heli = new Helicopter(tex);
                    heli.setSize(BattleSheep.WIDTH / 10 - 2, BattleSheep.WIDTH / 10 - 2);
                    heli.setPosition(j * BattleSheep.WIDTH / 10 + 1 + BattleSheep.WIDTH / 20, i * BattleSheep.WIDTH / 10 + 1 + 300);
                    this.opponentBoard.add(heli);
                    this.enemyAnimals.add(i*7 + j-1);
                    this.enemyAnimals.add(i*7 + j);

                }
                j++;
            }
            j = 0;
            i++;
        }
        Texture b = new Texture("bonde-liten.png");
        Sprite bonde = new Sprite(b,b.getWidth()/2,b.getHeight()/2);
        bonde.setPosition(60,700);
        this.myBoard.add(bonde);


    }
}
