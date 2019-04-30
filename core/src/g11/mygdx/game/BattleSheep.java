package g11.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.golfgl.gdxgamesvcs.IGameServiceClient;
import de.golfgl.gdxgamesvcs.IGameServiceListener;
import de.golfgl.gdxgamesvcs.NoGameServiceClient;

public class BattleSheep extends ApplicationAdapter implements IGameServiceListener {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE = "BattleSheep";
	private SpriteBatch batch;
	private Controller controller;
	private View view;
	private Model model;
	public static float delta;

    public IGameServiceClient gsClient;


    @Override
	public void create () {
		while(!MyAssetManager.manager.update()){
			System.out.println("Loading assets.. " + MyAssetManager.manager.getProgress() * 100 + "%");
		}
		batch = new SpriteBatch();
		Gdx.gl.glClearColor(194/255f, 225/255f, 157/255f, 1);
		delta = Gdx.graphics.getDeltaTime();
		model = new Model();
		view = new View(batch);
		controller = new Controller(view, model);
		view.addObserver(controller);


		// ..google play service client initialization..

        if (gsClient == null)

            // if no client is registered, register the required client
            gsClient = new NoGameServiceClient();

        // for getting callbacks from the client
        gsClient.setListener(this);

        // prints session status, true or false.
        System.out.println(gsClient.isSessionActive());

        // establish a connection to the game service without error messages or login screens
        gsClient.resumeSession();
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		view.update(delta + 1);
	}

    @Override
    public void pause() {
        super.pause();

        gsClient.pauseSession();
    }

    @Override
    public void resume() {
        super.resume();

        gsClient.resumeSession();
    }

    @Override
    public void gsOnSessionActive() {

    }

    @Override
    public void gsOnSessionInactive() {

    }

    @Override
    public void gsShowErrorToUser(GsErrorType et, String msg, Throwable t) {

    }
}
