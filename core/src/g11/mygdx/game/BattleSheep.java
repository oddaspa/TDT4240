package g11.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BattleSheep extends Game {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	private PlayServices ply;
	public static final String TITLE = "BattleSheep";
	private SpriteBatch batch;
	private Controller controller;
	private View view;
	private Model model;
	public static float delta;


    @Override
	public void create () {

		batch = new SpriteBatch();
		Gdx.gl.glClearColor(194/255f, 225/255f, 157/255f, 1);
		delta = Gdx.graphics.getDeltaTime();
		model = new Model();
		view = new View(batch);
		controller = new Controller(view, model);
		view.addObserver(controller);


	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		view.update(delta + 1);
	}

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    public BattleSheep(PlayServices ply){
        this.ply = ply;
    }
}
