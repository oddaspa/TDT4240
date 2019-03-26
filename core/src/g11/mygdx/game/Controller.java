package g11.mygdx.game;

/* COORDINATES INTERACTIONS BETWEEN THE VIEW AND THE MODEL */

import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Controller extends InputListener {
    private View theView;
    private Model theModel;


    public Controller(View theView, Model theModel){
        this.theView = theView;
        this.theModel = theModel;
    }

    public void update(float[] coordinates) {
        theModel.parseInput(coordinates);
        theView.render(theModel.serveData());
    }
}
