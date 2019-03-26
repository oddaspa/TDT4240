
/*
package g11.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

import g11.mygdx.game.BattleSheep;

public class MenuScreen extends Screen{
    private Texture background;
    private Texture playBtn;
    private HashMap<String, int[]> data;
    int[] coordinates;

    public MenuScreen(SpriteBatch sb) {
        super(sb);
        background = new Texture("background.jpg");
        playBtn = new Texture("StartButton.png");
        coordinates[0] = 0;
        coordinates[1] = 0;
        this.data.put("background", coordinates);


    }

    @Override
    public int[] handleInput() {
        if (Gdx.input.justTouched()){
            gsm.set(new PlayScreen(gsm));

        }
    }

    @Override
    public void update(float dt) {
        handleInput();

    }
    public void getData(){
        return
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0,BattleSheep.WIDTH,BattleSheep.HEIGHT);
        sb.draw(playBtn,(BattleSheep.WIDTH / 2) - (playBtn.getWidth() / 2),(BattleSheep.HEIGHT / 2));
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }
}
*/