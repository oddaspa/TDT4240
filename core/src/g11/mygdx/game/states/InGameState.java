package g11.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import g11.mygdx.game.BattleSheep;
import g11.mygdx.game.PlayServices;
import g11.mygdx.game.modules.HomeButton;
import g11.mygdx.game.sprites.Chicken;
import g11.mygdx.game.sprites.Grass;
import g11.mygdx.game.sprites.Sheep;

public class InGameState implements IState {
    private Array<Sprite> opponentBoard;
    private Array<Sprite> myBoard;
    private Array<Sprite> allSprites;
    private Array<String> inGameMessages;
    private Array<Sprite> grassTiles;
    private HomeButton homeButton;
    private double col = 0;
    private double row = 0;
    private float LEFTBORDER = 1 + BattleSheep.WIDTH / 10;
    private float RIGHTBOARDER = 8 * BattleSheep.WIDTH / 10 + 1 + BattleSheep.WIDTH / 10;
    private float TOPBORDER = (float) (8 * BattleSheep.WIDTH / 10 + 1 + BattleSheep.HEIGHT * 0.375);
    private float BOTTOMBORDER = (float) (1 + BattleSheep.HEIGHT * 0.375);
    float[] formerData;
    private PlayServices action;
    boolean opponentBoardPlaced;
    private int turnCounter;
    private String dots;
    private Array<Sprite> myGrass;

    public InGameState(PlayServices actionResolver){
        this.dots = ".";
        this.turnCounter = 0;
        this.opponentBoardPlaced = false;
        this.action = actionResolver;
        this.myBoard = new Array<Sprite>();
        this.opponentBoard = new Array<Sprite>();
        this.grassTiles = new Array<Sprite>();
        this.allSprites = new Array<Sprite>();
        this.inGameMessages = new Array<String>();
        this.myGrass = new Array<Sprite>();
        loadData();
        allSprites = this.grassTiles;
        allSprites.addAll(opponentBoard);
        this.formerData = new float[2];
        Texture homeButtonTexture = new Texture("home.png");
        Sprite homeButtonSprite = new Sprite(homeButtonTexture, BattleSheep.WIDTH / 10, BattleSheep.WIDTH / 10);
        this.homeButton = new HomeButton(homeButtonSprite, (float) BattleSheep.WIDTH / 48, (float) BattleSheep.HEIGHT - BattleSheep.HEIGHT / 80 - homeButtonSprite.getHeight());
    }


    @Override
    public String parseInput(float[] data) {
        if(data == formerData){
            return "inGameStatus";
        }
        if (data == null) {return "inGameStatus";}
        else if (this.homeButton.isClicked(data[0], data[1])) {
            return "confirmationState";
        }

        formerData = data;
        double[] coordinates = parsePosition(data);
        if(coordinates[1] != -1){
            if(this.opponentBoard != null){
                makeMove((int) coordinates[0], (int) coordinates[1]);
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
        if(!this.opponentBoardPlaced){
            if (action.getHasOpponent() || action.isPlayer2()){
                Gdx.app.log("ServeData", "Placed new board!");
                placeOpponentBoard();
            }else if(this.opponentBoard.isEmpty()){
                placeOpponentBoard();
            }
        }
        if(turnCounter != action.getTurnCounter()){
            Gdx.app.log("XXXXXXX>>>> updating myBoard!", "TurnCounter: " + turnCounter);
            updateMyBoard();
            turnCounter = action.getTurnCounter();
        }
        if(!action.getHasOpponent() && !action.isPlayer2()){
            this.inGameMessages.clear();
            if(dots.length() >= 3){
                dots = "";
            }
            dots += ".";
            this.inGameMessages.add("Searching for opponent" + dots);
            this.inGameMessages.add("");
            this.inGameMessages.add(action.getmDisplayName());
        }

        Array<Sprite> allData = new Array<Sprite>();
        allData.addAll(this.myBoard);
        allData.addAll(this.grassTiles);
        allData.addAll(this.opponentBoard);
        allData.add(this.homeButton.getButton());
        return allData;
    }


    @Override
    public void loadData() {
        placeOpponentBoard();
        placeMyBoard();
    }

    private double[] parsePosition(float[] data){
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

    private void writeFile(String[] file, int col, int row, char item){
        String wordsArray[] = file;
        String returnString = new String();
        int i = 8;
        for(String s : wordsArray){
            if(i == row){
                char[] ch = s.toCharArray();
                ch[col-1] = item;
                s = String.valueOf(ch);
            }
            returnString += s + "Q";
            i--;
        }
        action.writeData(returnString.getBytes());

    }

    private void updateMyBoard(){
        String[] fromFile = action.retrieveData()[0].split("Q");
        int row = 0;
        int col = 0;

        for (String s : fromFile) {
            for (char c : s.toCharArray()) {
                if(c == 'X'){
                    Sprite sprite = this.myGrass.get(col + row * 8);
                    if(((Grass)sprite).isHit()){
                        continue;
                    }
                    Gdx.app.log(">>>>>>>in Update ", "Col: " + String.valueOf(col) + ", Row: " + String.valueOf(row));
                    Sprite newSprite = ((Grass) sprite).gotHit();
                    if (newSprite == null) {
                        continue;
                    }
                    this.myBoard.add(newSprite);
                }
                col++;
            }
            row++;
            col = 0;
        }
    }


    private void placeMyBoard(){
        String formerRow = "........";
        char formerLetter = '.';
        //RESET
        this.myBoard = new Array<Sprite>();
        String[] fromFile = action.retrieveData()[0].split("Q");
        float rangeY = (BattleSheep.HEIGHT / 4) / 8;
        int i = 8;
        int j = 0;
        int pos = -1;
        for (String s : fromFile) {
            for (char c : s.toCharArray()) {
                pos++;
                // Grass underneath
                Grass grass = new Grass();
                grass.setPosition(j * rangeY + 1 + BattleSheep.WIDTH / 4, i * rangeY + 1 + BattleSheep.HEIGHT / 16);
                this.myBoard.add(grass);
                this.myGrass.add(grass);
                if (c == 'c') {
                    // Chicken
                    Texture tex = new Texture("chicken-liten.png");
                    Sprite chicken = new Sprite(tex, Math.round(rangeY) - 2, Math.round(rangeY) - 2);
                    chicken.setPosition(j * rangeY + 1 + BattleSheep.WIDTH / 4, i * rangeY + 1 + BattleSheep.HEIGHT / 16);
                    this.myBoard.add(chicken);
                    grass.setAnimal(chicken);
                }
                if (c == 's' && formerRow.toCharArray()[j] == 's') {
                    if(formerLetter == 's') {
                        formerLetter = '.';
                        Texture tex = new Texture("sheep-liten.png");
                        Sprite sheep = new Sprite(tex, Math.round(rangeY * 2) - 2, Math.round(rangeY * 2) - 2);
                        sheep.setPosition((j - 1)  * rangeY + 1 + BattleSheep.WIDTH / 4, i * rangeY + 1 + BattleSheep.HEIGHT / 16);
                        this.myBoard.add(sheep);
                        grass.setAnimal(sheep);
                        ((Grass) this.myGrass.get(pos - 1)).setAnimal(sheep);
                        ((Grass) this.myGrass.get(pos - 8)).setAnimal(sheep);
                        ((Grass) this.myGrass.get(pos - 9)).setAnimal(sheep);
                    } else {
                        formerLetter = 's';
                    }
                }
                j++;
            }
            j = 0;
            i--;
            formerRow = s;
        }
        Texture b = new Texture("bonde-liten.png");
        Sprite bonde = new Sprite(b, b.getWidth() / 2, b.getHeight() / 2);
        bonde.setPosition((float) (BattleSheep.WIDTH * 0.875), BattleSheep.HEIGHT / 16);
        this.myBoard.add(bonde);
    }

    //TODO: USE CORRECT SPRITES
    private void placeOpponentBoard(){
        String formerRow = "........";
        char formerLetter = '.';
        if(action.retrieveData()[1] == null){
            Gdx.app.log("placeOpponentBoard()", "No board");
            return;
        }
        this.opponentBoard = new Array<Sprite>();
        String fromFile = action.retrieveData()[1];
        String[] strings = fromFile.split("Q");
        if(action.getHasOpponent() || action.isPlayer2()) {
            this.inGameMessages.clear();
            this.inGameMessages.add(action.getOpponentName());
            this.inGameMessages.add("");
            this.inGameMessages.add(action.getmDisplayName());
            this.opponentBoardPlaced = true;
        }

        int i = 8;
        int j = 0;
        placeOpponentGrass();
        for(String s : strings) {
            for (char c : s.toCharArray()) {
                if (c == 'c') {
                    // Chicken
                    Chicken chicken = new Chicken(0, 0);
                    chicken.setPosition(j * (BattleSheep.WIDTH / 10) + 1 + (BattleSheep.WIDTH / 10), (float) ((i - 1) * (BattleSheep.WIDTH / 10) + 1 + BattleSheep.HEIGHT * 0.375));
                    this.opponentBoard.add(chicken);
                    ((Grass) getGrassTile(j + 1,i)).setAnimal(chicken);
                }
                if (c == 's' && formerRow.toCharArray()[j] == 's') {
                    if (formerLetter == 's') {
                        formerLetter = '.';
                        Sheep sheep = new Sheep(0, 0);
                        sheep.setPosition((j - 1) * (BattleSheep.WIDTH / 10) + 1 + (BattleSheep.WIDTH / 10), (float) ((i - 1) * BattleSheep.WIDTH / 10 + 1 + BattleSheep.HEIGHT * 0.375));
                        this.opponentBoard.add(sheep);
                        ((Grass) getGrassTile(j+1,i)).setAnimal(sheep);
                        ((Grass) getGrassTile(j,i)).setAnimal(sheep);
                        ((Grass) getGrassTile(j,i+1)).setAnimal(sheep);
                        ((Grass) getGrassTile(j+1,i+1)).setAnimal(sheep);

                    }else {
                        formerLetter = 's';
                    }
                }
                j++;
            }
            formerRow = s;
            j = 0;
            i--;
        }
        Texture b = new Texture("bonde-liten.png");
        Sprite bonde = new Sprite(b,b.getWidth()/2,b.getHeight()/2);
        bonde.setPosition(BattleSheep.WIDTH / 8, (float) (BattleSheep.HEIGHT * 0.875));
        this.opponentBoard.add(bonde);
    }
    private void placeOpponentGrass(){
        this.grassTiles = new Array<Sprite>();
        for (int i = 0; i<8; i++) {
            for (int j = 0; j <8; j++) {
                // Grass underneath
                Grass grass = new Grass();
                grass.setSize(BattleSheep.WIDTH / 10 - 2, BattleSheep.WIDTH / 10 - 2);
                grass.setPosition(j * BattleSheep.WIDTH / 10 + 1 + BattleSheep.WIDTH / 10, (float) (i * BattleSheep.WIDTH / 10 + 1 + BattleSheep.HEIGHT * 0.375));
                this.grassTiles.add(grass);
            }
        }
    }

    private Sprite getGrassTile(int col, int row){
        float[] tilePos = new float[2];
        for(Sprite g : this.grassTiles){
            tilePos[0] = g.getX() + g.getWidth()/2;
            tilePos[1] = g.getY() + g.getHeight()/2;
            double[] position = parsePosition(tilePos);
            if(position[0] == (double) col &&  position[1] == (double) row){
                return g;
            }
        }
        return null;
    }

    private void makeMove(int col, int row){
        if(!action.getIsDoingTurn()){
            this.inGameMessages.set(1, "It is not your turn!" );
            return;
        }else {
            String[] data = action.retrieveData();
            this.inGameMessages.set(1, "");
            float[] spritePosition = new float[2];
            float spriteX;
            float spriteY;
            double[] spriteCoordinates;
            for (Sprite c : this.grassTiles) {
                spriteX = c.getX() + c.getWidth() / 2;
                spriteY = c.getY() + c.getHeight() / 2;
                spritePosition[0] = spriteX;
                spritePosition[1] = spriteY;
                spriteCoordinates = parsePosition(spritePosition);
                if (col == spriteCoordinates[0] && row == spriteCoordinates[1]) {
                    if (c instanceof Grass) {
                        Sprite newSprite = ((Grass) c).gotHit();
                        if (newSprite == null) {
                            continue;
                        }
                        this.opponentBoard.add(newSprite);
                        if (((Grass) c).hasAnimal()) {
                            Sprite animal = ((Grass) c).getAnimal();
                            if (animal instanceof Chicken) {
                                ((Chicken) animal).gotHit();
                            }
                            if (animal instanceof Sheep) {
                                ((Sheep) animal).gotHit();
                            }
                        }
                    }
                }
            }
            writeFile(data[1].split("Q"), col, row, 'X');
            action.onDoneClicked();
            if(gameFinished(data)){
                System.out.println("You won!");
            }
        }


    }
    //TODO: c == 'x' || c == 'b'
    private boolean gameFinished(String[] data) {
        String[] fromFile = data[0].split("Q");
        for (String s : fromFile) {
            for (char c : s.toCharArray()) {
                if (c == 'c' || c == 's') {
                    return false;
                }
            }
        }
        fromFile = data[1].split("Q");
        for (String s : fromFile) {
            for (char c : s.toCharArray()) {
                if (c == 'c' || c == 's') {
                    return false;
                }
            }
        }
        action.onFinishClicked();
        return true;
    }

}
