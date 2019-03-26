package g11.mygdx.game.states;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public interface IState {

    String parseInput(float[] data);

    Array<String> serveMessages();

    Array<Sprite> serveData();

    void loadData();
}
