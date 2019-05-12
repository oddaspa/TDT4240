package g11.mygdx.game;

/* COORDINATES INTERACTIONS BETWEEN THE VIEW AND THE MODEL */

import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Controller extends InputListener {
    private View theView;
    private Model theModel;
    private float[] previousCoordinates = new float[2];

    public Controller(View theView, Model theModel){
        this.theView = theView;
        this.theModel = theModel;
        this.previousCoordinates[0] = -1;
        this.previousCoordinates[1] = -1;
    }

    public void update(float[] coordinates) {
        if(theModel.currentMode.equals("placeAnimalState")){
            theModel.parseInput(coordinates);

        }else if(theModel.currentMode.equals("loadingState")) {
            theModel.parseInput(coordinates);
        }else if(theModel.currentMode.equals("menuState")) {
        theModel.parseInput(coordinates);
    }
        else{
            if(coordinates != null){
                if(Math.abs(coordinates[0] - this.previousCoordinates[0]) > 3 && Math.abs(coordinates[1] - this.previousCoordinates[1]) > 3){
                    theModel.parseInput(coordinates);
                    this.previousCoordinates = coordinates;
                }
            }
        }
        theView.render(theModel.serveData(), theModel.serveMessages());
    }
}
