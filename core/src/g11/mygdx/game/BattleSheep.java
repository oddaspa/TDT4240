package g11.mygdx.game;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class BattleSheep extends ApplicationAdapter {

	public PlayServices localActionActionResolver;
	public static int WIDTH = 480;
	public static int HEIGHT = 800;

	public static final int VIRTUAL_WIDTH = 480;
	public static final int VIRTUAL_HEIGHT = 800;
	public static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH / (float) VIRTUAL_HEIGHT;



	private PlayServices ply;
	public static final String TITLE = "BattleSheep";
	private SpriteBatch batch;
	private Controller controller;
	private View view;
	private Model model;
	public static float delta;



	public BattleSheep(PlayServices anActionResolver){
		this.localActionActionResolver = anActionResolver;

	}

	@Override
	public void create () {
		if(Gdx.app.getType() == Application.ApplicationType.Android) {
			// android specific code
			BattleSheep.WIDTH = Gdx.graphics.getWidth();
			BattleSheep.HEIGHT = Gdx.graphics.getHeight();
		}

		localActionActionResolver.signIn();

		delta = Gdx.graphics.getDeltaTime();
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		batch = new SpriteBatch();
		Gdx.gl.glClearColor(194/255f, 225/255f, 157/255f, 1);
		delta = Gdx.graphics.getDeltaTime();
		model = new Model(this.localActionActionResolver);
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
}
