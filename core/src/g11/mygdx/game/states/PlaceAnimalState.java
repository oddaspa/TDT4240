package g11.mygdx.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import g11.mygdx.game.BattleSheep;
import g11.mygdx.game.PlayServices;
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
    private PlayButton randButton;
    private Sprite selectedAnimal;
    private Array<Sprite> allData;
    private Random rand = new Random();
    private PlayServices action;

    public PlaceAnimalState(PlayServices actionResolver){
        this.action = actionResolver;
        this.placeAnimalSprites = new Array<Sprite>();
        this.placeAnimalMessages = new Array<String>();
        this.allData = new Array<Sprite>();
        this.selectedAnimal = null;

        Texture homeButtonTexture = new Texture("home.png");
        Sprite homeButtonSprite = new Sprite(homeButtonTexture, BattleSheep.WIDTH / 10, BattleSheep.WIDTH / 10);
        this.homeButton = new HomeButton(homeButtonSprite, (float) BattleSheep.WIDTH / 48, (float) BattleSheep.HEIGHT - BattleSheep.HEIGHT / 80 - homeButtonSprite.getHeight());

        Texture playButtonTexture = new Texture("play.png");
        Sprite playButtonSprite = new Sprite(playButtonTexture, BattleSheep.WIDTH / 2, BattleSheep.WIDTH / 10);
        this.playButton = new PlayButton(playButtonSprite, BattleSheep.WIDTH - BattleSheep.WIDTH / 48 - playButtonSprite.getWidth(), BattleSheep.HEIGHT - BattleSheep.HEIGHT / 80 - homeButtonSprite.getHeight());

        Texture randomizeButtonTexture = new Texture("play.png");
        Sprite randomizeButtonSprite = new Sprite(randomizeButtonTexture, BattleSheep.WIDTH / 2, BattleSheep.WIDTH / 10);
        this.randButton = new PlayButton(randomizeButtonSprite, BattleSheep.WIDTH / 2 - randomizeButtonSprite.getWidth() / 2, BattleSheep.HEIGHT / 80);

        loadData();

        this.allData.addAll(placeAnimalSprites);
        this.allData.add(playButton.getButton());
        this.allData.add(homeButton.getButton());
        this.allData.add(randButton.getButton());

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
        else if ( randButton.isClicked(data[0], data[1]) ){
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
            action.onDoneClicked();
            return "inGameStatus";
        }else{
            this.placeAnimalMessages.removeIndex(0);
            placeAnimalMessages.add("Place all animals to continue..");
            placeAnimalMessages.reverse();
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
        action.writeData(board.getBytes());
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
        if (selectedAnimal.getX() > BattleSheep.WIDTH * 0.76041) {
            if (selectedAnimal instanceof Sheep){
                selectedAnimal.setPosition((float) (BattleSheep.WIDTH * 0.76041),selectedAnimal.getY());
            } else {
                selectedAnimal.setPosition((float) (BattleSheep.WIDTH * 0.85),selectedAnimal.getY());
            }
        } else if (selectedAnimal.getX() < BattleSheep.WIDTH * 0.0771) {
            selectedAnimal.setPosition((float) (BattleSheep.WIDTH * 0.0771),selectedAnimal.getY());
        }
        if (selectedAnimal.getY() > BattleSheep.HEIGHT * 0.75) {
            if (selectedAnimal instanceof Sheep){
                selectedAnimal.setPosition(selectedAnimal.getX(), (float) (BattleSheep.HEIGHT * 0.73125));
            } else {
                selectedAnimal.setPosition(selectedAnimal.getX(), (float) (BattleSheep.HEIGHT * 0.775));
            }
        } else if (selectedAnimal.getY() < BattleSheep.HEIGHT * 0.3275 && selectedAnimal.getY() > BattleSheep.HEIGHT * 0.275) {
            selectedAnimal.setPosition(selectedAnimal.getX(), (float) (BattleSheep.HEIGHT * 0.3275));
        }
        for (int i=0; i<65; i++) {
            Sprite grassCell = this.placeAnimalSprites.get(i);
            int offset = (int)grassCell.getHeight()/2;
            if (selectedAnimal.getX() + offset >= grassCell.getX() && selectedAnimal.getX() + offset <= grassCell.getX() + grassCell.getWidth() + 2) {
                if (selectedAnimal.getY() + BattleSheep.HEIGHT / 80 >= grassCell.getY() && selectedAnimal.getY() + BattleSheep.HEIGHT /100 <= grassCell.getY() + grassCell.getHeight()){

                    if (selectedAnimal instanceof Sheep){
                        selectedAnimal.setPosition(grassCell.getX(), (float) (grassCell.getY() + BattleSheep.HEIGHT * 0.01875));
                    }else if (selectedAnimal instanceof Chicken){
                        selectedAnimal.setPosition(grassCell.getX() + BattleSheep.WIDTH / 96, (float) (grassCell.getY() + BattleSheep.HEIGHT * 0.00375));
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
                //System.out.println(selectedAnimal + " overlapping " + animal);
                selectedAnimal.setPosition(animal.getX(), (float) (BattleSheep.HEIGHT * 0.175));
            }
        }
    }

    public void randomPlacing(){
        boolean allAnimalsPlaced = false;

        while (!allAnimalsPlaced) {
            for (int i = 65; i < this.placeAnimalSprites.size; i++){
                this.selectedAnimal = this.placeAnimalSprites.get(i);
                int x = rand.nextInt((int) (BattleSheep.WIDTH * 0.9375));
                int y = (int) (rand.nextInt((int) (BattleSheep.HEIGHT * 0.525)) + BattleSheep.HEIGHT * 0.33125);
                this.selectedAnimal.setPosition(x,y);
                this.snapOnGrid();
            }
            boolean checkall = true;
            for (int j = 65; j < this.placeAnimalSprites.size; j++){
                if (this.placeAnimalSprites.get(j).getY() < BattleSheep.HEIGHT * 0.27625){
                    checkall = false;
                }
            }
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
        Sprite chicken = new Chicken(BattleSheep.WIDTH / 12,BattleSheep.WIDTH / 12);
        //chicken.setPosition((1 * BattleSheep.WIDTH / 9) + 10,140);
        Sprite chicken2 = new Chicken(BattleSheep.WIDTH / 12,BattleSheep.WIDTH / 12);
        //chicken2.setPosition(2 * (BattleSheep.WIDTH / 10) + 10,140);
        Sprite chicken3 = new Chicken(BattleSheep.WIDTH / 12,BattleSheep.WIDTH / 12);
        //chicken3.setPosition(3 * (BattleSheep.WIDTH / 10) + 10,140);
        Sprite sheep = new Sheep((int) (BattleSheep.WIDTH * 0.21), (int) (BattleSheep.HEIGHT * 0.09375));
        //sheep.setPosition(4 * (BattleSheep.WIDTH / 10), 140);
        Sprite sheep2 = new Sheep((int) (BattleSheep.WIDTH * 0.21), (int) (BattleSheep.HEIGHT * 0.09375));
        //sheep2.setPosition(6 * (BattleSheep.WIDTH / 10) - 10 ,140);
        Sprite sheep3 = new Sheep((int) (BattleSheep.WIDTH * 0.21), (int) (BattleSheep.HEIGHT * 0.09375));
        //sheep3.setPosition(7 * (BattleSheep.WIDTH / 10) + 30 ,140);
        Sprite farmer = new Sprite();
        farmer.setTexture(new Texture("bonde-liten.png"));
        farmer.setPosition(BattleSheep.WIDTH / 96, (float) (BattleSheep.HEIGHT * 0.1875));
        farmer.setSize(BattleSheep.WIDTH / 8,(3 * BattleSheep.HEIGHT) / 20);
        //place grass cells
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Sprite grassCell = new Grass();
                grassCell.setPosition(j * (BattleSheep.WIDTH / 9)+ 1 + BattleSheep.WIDTH / 16, (float) (i * BattleSheep.WIDTH / 9 + 1 + BattleSheep.HEIGHT * 0.325));
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
        setDefaultPosition();
    }
public void setDefaultPosition() {
        //          s , s , s , c , c , c
        int[] x = {
                (int) (BattleSheep.WIDTH * 0.77),
                (int) (BattleSheep.WIDTH * 0.5833),
                (int) (BattleSheep.WIDTH * 0.3958),
                (int) (BattleSheep.WIDTH * 0.3125),
                (int) (BattleSheep.WIDTH * 0.21875),
                (int) (BattleSheep.WIDTH * 0.125)};

        for (int i = 65; i < this.placeAnimalSprites.size; i++) {
            Sprite animal = this.placeAnimalSprites.get(i);
            animal.setPosition(x[placeAnimalSprites.size-i-1], (float) (BattleSheep.HEIGHT * 0.175));
        }
}

}
