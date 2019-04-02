package g11.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import g11.mygdx.game.states.InGameState;
import g11.mygdx.game.states.MenuState;
import g11.mygdx.game.states.PlaceAnimalState;

public class Model {
    private String currentMode;
    private MenuState menu;
    private PlaceAnimalState placeAnimal;
    private InGameState inGameState;
    // MAKE ALL THE STATES

    public Model(){
        this.menu = new MenuState();
        this.placeAnimal = new PlaceAnimalState();
        this.inGameState = new InGameState();
        this.currentMode = "menuState";

    }

    public void parseInput(float[] data){
        if(currentMode.equals("menuState")){
            this.currentMode = this.menu.parseInput(data);
        }
        if(currentMode.equals("placeAnimalState")){
            this.currentMode = this.placeAnimal.parseInput(data);
        }
        if(currentMode.equals("inGameStatus")){
            this.currentMode = this.inGameState.parseInput(data);
        }
        if(currentMode.equals("confirmationState")) {
            // TODO make confirmation state
        }
    }

    public Array<Sprite> serveData(){
        if(this.currentMode.equals("menuState")){
            return this.menu.serveData();
        }
        if(currentMode.equals("inGameStatus")){
            return this.inGameState.serveData();
        }
        else{
            return this.placeAnimal.serveData();
        }
    }

    public Array<String> serveMessages(){
        if(currentMode.equals("inGameStatus")){
            return this.inGameState.serveMessages();
        }
        if(this.currentMode.equals("placeAnimalState")){
            return this.placeAnimal.serveMessages();
        }
        else{
            return null;
        }
    }

}
