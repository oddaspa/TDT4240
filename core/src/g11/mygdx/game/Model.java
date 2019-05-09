package g11.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import g11.mygdx.game.states.ConfirmationState;
import g11.mygdx.game.states.InGameState;
import g11.mygdx.game.states.LoadingState;
import g11.mygdx.game.states.MenuState;
import g11.mygdx.game.states.PlaceAnimalState;

public class Model {
    public String currentMode;
    private MenuState menu;
    private LoadingState loading;
    private PlaceAnimalState placeAnimal;
    private InGameState inGameState;
    private ConfirmationState confirmationState;
    private String previousState;
    private String nextState;
    private PlayServices action;


    // MAKE ALL THE STATES

    public Model(PlayServices actionResolver){
        this.action = actionResolver;
        this.menu = new MenuState(actionResolver);
        this.loading = new LoadingState();
        this.placeAnimal = new PlaceAnimalState(actionResolver);
        this.inGameState = null;
        this.previousState = "menuState";
        this.nextState = "";
        this.currentMode = "loadingState";
        this.confirmationState = new ConfirmationState(this.previousState, menu);
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
            if(this.inGameState == null){
                this.inGameState = new InGameState(action);
            }
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

        if(currentMode.equals("loadingState")) {
            return this.loading.serveData();
        }

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
