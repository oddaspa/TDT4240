package g11.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import g11.mygdx.game.states.ConfirmationState;
import g11.mygdx.game.states.InGameState;
import g11.mygdx.game.states.MenuState;
import g11.mygdx.game.states.LoadingState;
import g11.mygdx.game.states.PlaceAnimalState;

public class Model {
    private String currentMode;
    private MenuState menu;
    private LoadingState loading;
    private PlaceAnimalState placeAnimal;
    private InGameState inGameState;
    private ConfirmationState confirmationState;
    private String previousState;
    private String nextState;
    // MAKE ALL THE STATES

    public Model(){
        this.menu = new MenuState();
        this.loading = new LoadingState();
        this.placeAnimal = new PlaceAnimalState();
        this.inGameState = new InGameState();
        this.previousState = "menuState";
        this.nextState = "";
        this.currentMode = "menuState";
        this.confirmationState = new ConfirmationState(this.previousState);


    }

    public void parseInput(float[] data){
        String nextState;

        if(currentMode.equals("menuState")){
            nextState = this.menu.parseInput(data);
            if (!nextState.equals(this.previousState)) {
                this.previousState = this.currentMode;
            }
            this.currentMode = nextState;
            placeAnimal.setDefaultPosition(); //reset animals in placeAnimalState

        }
        if(currentMode.equals("loadingState")){
            this.currentMode = this.loading.parseInput(data);
        }
        if(currentMode.equals("placeAnimalState")){
            nextState = this.placeAnimal.parseInput(data);
            if (!nextState.equals(this.previousState)) {
                this.previousState = this.currentMode;
            }
            this.currentMode = nextState;

        }
        if(currentMode.equals("inGameStatus")){
            nextState = this.inGameState.parseInput(data);
            if (!nextState.equals(this.previousState)) {
                this.previousState = this.currentMode;
            }
            this.currentMode = nextState;
            placeAnimal.setDefaultPosition(); //reset animals in placeAnimalState

        }
        if(currentMode.equals("confirmationState")) {
            nextState = this.confirmationState.parseInput(data);

            if (!nextState.equals(this.previousState)) {
                this.previousState = this.currentMode;
            }
            this.currentMode = nextState;

        }

        if (!this.previousState.equals("confirmationState")) {
            this.confirmationState.setPreviousState(this.previousState);
        }
    }

    public Array<Sprite> serveData(){
        if(this.currentMode.equals("menuState")){
            return this.menu.serveData();
        }

        if(currentMode.equals("loadingState")){
            return this.loading.serveData();

        if (this.currentMode.equals("confirmationState")) {
            return this.confirmationState.serveData();

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
