package g11.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import g11.mygdx.game.BattleSheep;

public class MenuState extends State{
    private Texture background;
    private Texture playBtn;
    private Texture challengeBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("background2.jpg");
        playBtn = new Texture("quick_game.png");
        challengeBtn = new Texture("challenge_friend.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));

        }
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0,BattleSheep.WIDTH,BattleSheep.HEIGHT);
        sb.draw(playBtn,(BattleSheep.WIDTH / 2) - (playBtn.getWidth() / 2),(BattleSheep.HEIGHT *2 / 7));
        sb.draw(challengeBtn,(BattleSheep.WIDTH / 2) - (playBtn.getWidth() / 2),(BattleSheep.HEIGHT / 14));
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }
}
