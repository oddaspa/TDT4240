package g11.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Random;

import g11.mygdx.game.BattleSheep;
import g11.mygdx.game.modules.HomeButton;
import g11.mygdx.game.modules.PlayButton;
import g11.mygdx.game.sprites.Chicken;
import g11.mygdx.game.sprites.Grass;
import g11.mygdx.game.sprites.Sheep;


public class PlaceAnimalState implements IState{
    private Array<Sprite> placeAnimalSprites;
    private Array<String> placeAnimalMessages;
    private HomeButton homeButton;
    private PlayButton playButton;
    private Sprite selectedAnimal;
    private Array<Sprite> allData;

    public PlaceAnimalState(){
        this.placeAnimalSprites = new Array<Sprite>();
        this.placeAnimalMessages = new Array<String>();
        this.allData = new Array<Sprite>();
        this.selectedAnimal = null;

        Texture homeButtonTexture = new Texture("home.png");
        Sprite homeButtonSprite = new Sprite(homeButtonTexture, homeButtonTexture.getWidth() / 6, homeButtonTexture.getHeight() / 6);
        this.homeButton = new HomeButton(homeButtonSprite, (float) 10, (float) BattleSheep.HEIGHT - 10 - homeButtonSprite.getHeight());

        Texture playButtonTexture = new Texture("play.png");
        Sprite playButtonSprite = new Sprite(playButtonTexture, playButtonTexture.getWidth() / 2, homeButtonTexture.getHeight() / 6);
        this.playButton = new PlayButton(playButtonSprite, BattleSheep.WIDTH - 10 - playButtonSprite.getWidth(), BattleSheep.HEIGHT - 10 - playButtonSprite.getHeight());


        loadData();

        this.allData.addAll(placeAnimalSprites);
        this.allData.add(playButton.getButton());
        this.allData.add(homeButton.getButton());

    }

    @Override
    public String parseInput(float[] data) {
        if (data == null) {
            if (this.selectedAnimal != null) {
                this.snapOnGrid(); //runs only when an animal is released
                selectedAnimal = null;
            }
        } else if (this.homeButton.isClicked(data[0], data[1])) {
            return "confirmationState";
        } else if (this.playButton.isClicked(data[0], data[1])) {
            return this.goToGame();
        }
        else if (data[0]<55 && data[1]<55){
            this.randomPlacing();

        }
        else {
            boolean touched = false;
            Sprite newSelectedAnimal = null;
            for (int i=65; i<this.placeAnimalSprites.size; i++){
                Sprite animal = this.placeAnimalSprites.get(i);
                //check if input is on top of animal
                if ( this.touches(animal,data) ){
                    touched = true;
                    newSelectedAnimal = animal;
                    break;
                }
            }
            if (!touched) {
                selectedAnimal = null;
                return "placeAnimalState";
            }
            //if new animal was selected and we have no current moving animal
            if (newSelectedAnimal != null && selectedAnimal == null){
                this.selectedAnimal = newSelectedAnimal;
            }

            //if an animal is selected
            if (selectedAnimal != null){
                selectedAnimal.setPosition(data[0] - selectedAnimal.getWidth()/2,data[1] - selectedAnimal.getHeight()/2);
            }
        }

        return "placeAnimalState";
    }

    public String goToGame(){
        boolean allAnimalsPlaced = true;
        for (int i = 65; i < this.placeAnimalSprites.size; i++){
            if (this.placeAnimalSprites.get(i).getY() < 221){
                allAnimalsPlaced = false;
            }
        }

        if (allAnimalsPlaced){
            this.turnBoardToFile();
            return "inGameStatus";
        }else{
            this.placeAnimalMessages.removeIndex(2);
            placeAnimalMessages.add("Place all animals to continue");
            return "placeAnimalState";
        }
    }

    public void turnBoardToFile(){
        String flippedBoard = "";
        for (int i = 63; i >= 0; i--){
            Sprite grass = this.placeAnimalSprites.get(i);
            String symbol = ".";
            for (int j = 65; j < this.placeAnimalSprites.size; j++){
                Sprite animal = this.placeAnimalSprites.get(j);
                if (animal.getBoundingRectangle().overlaps(grass.getBoundingRectangle())){
                    symbol = animal.toString();
                }
            }
            flippedBoard += symbol;
        }
        String board = "";
        for (int i=0; i<8; i++){
            String boardRow = flippedBoard.substring(i*8,((i+1)*8));
            byte [] boardRowAsByteArray = boardRow.getBytes();

            byte [] result = new byte [boardRowAsByteArray.length];

            // Store result in reverse order into the
            // result byte[]
            for (int j = 0; j<boardRowAsByteArray.length; j++)
                result[j] = boardRowAsByteArray[boardRowAsByteArray.length-j-1];
            boardRow = new String(result);
            board += boardRow + "\n";
        }
        FileHandle handle = Gdx.files.local("myBoard.txt");
        String text = handle.readString();
        String fileRowArray[] = text.split("\\r?\\n");
        String newFileText = fileRowArray[0] + "\n" + board;
        handle.writeString(newFileText, false);
    }


    public boolean touches(Sprite animal, float[] input) {
        boolean touches = false;
        if ( animal.getX() + animal.getWidth() > input[0] && input[0] > animal.getX() ){
            if ( animal.getY() + animal.getHeight() > input[1] && input[1] > animal.getY() ){
                touches = true;
            }}
        return touches;
    }

    public void snapOnGrid() {
        if (selectedAnimal.getX() > 365) {
            if (selectedAnimal instanceof Sheep){
                selectedAnimal.setPosition(365,selectedAnimal.getY());
            } else {
                selectedAnimal.setPosition(408,selectedAnimal.getY());
            }
        } else if (selectedAnimal.getX() < 37) {
            selectedAnimal.setPosition(37,selectedAnimal.getY());
        }
        if (selectedAnimal.getY() > 620) {
            if (selectedAnimal instanceof Sheep){
                selectedAnimal.setPosition(selectedAnimal.getX(),585);
            } else {
                selectedAnimal.setPosition(selectedAnimal.getX(),650);
            }
        } else if (selectedAnimal.getY() < 262 && selectedAnimal.getY() > 220) {
            selectedAnimal.setPosition(selectedAnimal.getX(),262);
        }
        for (int i=0; i<65; i++) {
            Sprite grassCell = this.placeAnimalSprites.get(i);
            int offset = (int)grassCell.getHeight()/2;
            if (selectedAnimal.getX() + offset >= grassCell.getX() && selectedAnimal.getX() + offset <= grassCell.getX() + grassCell.getWidth() + 2) {
                if (selectedAnimal.getY() + 10 >= grassCell.getY() && selectedAnimal.getY() + 8 <= grassCell.getY() + grassCell.getHeight()){

                    if (selectedAnimal instanceof Sheep){
                        selectedAnimal.setPosition(grassCell.getX(), grassCell.getY() + 15);
                    }else if (selectedAnimal instanceof Chicken){
                        selectedAnimal.setPosition(grassCell.getX() + 5, grassCell.getY() + 3);
                    }else {
                        selectedAnimal.setPosition(grassCell.getX(), grassCell.getY());
                    }
                    this.checkValidPosition();
                }
            }
        }
    }

    public void checkValidPosition(){
        for (int i=65; i<this.placeAnimalSprites.size; i++){
            Sprite animal = this.placeAnimalSprites.get(i);
            if ( selectedAnimal != animal && selectedAnimal.getBoundingRectangle().overlaps(animal.getBoundingRectangle()) ){
                System.out.println(selectedAnimal + " overlapping " + animal);
                selectedAnimal.setPosition(animal.getX(),140);
            }
        }
    }

    public void randomPlacing(){
        Random rand = new Random();
        boolean allAnimalsPlaced = false;

        while (!allAnimalsPlaced) {
            for (int i = 65; i < this.placeAnimalSprites.size; i++){
                this.selectedAnimal = this.placeAnimalSprites.get(i);
                int x = rand.nextInt(450);
                int y = rand.nextInt(420) + 265;
                this.selectedAnimal.setPosition(x,y);
                this.snapOnGrid();
            }
            boolean checkall = true;
            int misses = 0;
            for (int j = 65; j < this.placeAnimalSprites.size; j++){
                if (this.placeAnimalSprites.get(j).getY() < 221){
                    checkall = false;
                    misses++;

                }
            }
            System.out.println("miss count: "+misses);
            allAnimalsPlaced = checkall;
        }
    }

    @Override
    public Array<String> serveMessages() {
        return this.placeAnimalMessages;
    }

    @Override
    public Array<Sprite> serveData() {

        allData.add(this.homeButton.getButton());
        return allData;
    }

    @Override
    public void loadData() {
        Sprite chicken = new Chicken(40,40);
        chicken.setPosition((1 * BattleSheep.WIDTH / 9) + 10,140);
        Sprite chicken2 = new Chicken(40,40);
        chicken2.setPosition(2 * (BattleSheep.WIDTH / 10) + 10,140);
        Sprite chicken3 = new Chicken(40,40);
        chicken3.setPosition(3 * (BattleSheep.WIDTH / 10) + 10,140);
        Sprite sheep = new Sheep(101,75);
        sheep.setPosition(4 * (BattleSheep.WIDTH / 10), 140);
        Sprite sheep2 = new Sheep(101,75);
        sheep2.setPosition(6 * (BattleSheep.WIDTH / 10) - 10 ,140);
        Sprite sheep3 = new Sheep(101,75);
        sheep3.setPosition(7 * (BattleSheep.WIDTH / 10) + 30 ,140);
        Sprite farmer = new Sprite();
        farmer.setTexture(new Texture("bonde-liten.png"));
        farmer.setPosition(5,150);
        farmer.setSize(60,120);
        //place grass cells
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Sprite grassCell = new Grass();
                grassCell.setPosition(j * (BattleSheep.WIDTH / 9)+ 1 + 30,i * BattleSheep.WIDTH / 9 + 1 + 260);
                grassCell.setSize( BattleSheep.WIDTH / 9 - 2, BattleSheep.WIDTH / 9 - 2);
                this.placeAnimalSprites.add(grassCell);
            }
        }

        this.placeAnimalSprites.add(farmer);

        this.placeAnimalSprites.add(chicken);
        this.placeAnimalSprites.add(chicken2);
        this.placeAnimalSprites.add(chicken3);
        this.placeAnimalSprites.add(sheep);
        this.placeAnimalSprites.add(sheep2);
        this.placeAnimalSprites.add(sheep3);
        this.placeAnimalMessages.add("     Place Your Animals");
        this.placeAnimalMessages.add("");
        this.placeAnimalMessages.add("");
    }


}
