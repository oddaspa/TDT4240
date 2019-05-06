package g11.mygdx.game;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class BattleSheep extends ApplicationAdapter implements PlayServices{

	public PlayServices localActionActionResolver;
	public static int WIDTH = 480;
	public static int HEIGHT = 800;

	public static final int VIRTUAL_WIDTH = 480;
	public static final int VIRTUAL_HEIGHT = 800;
	public static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH / (float) VIRTUAL_HEIGHT;

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
			BattleSheep.WIDTH = 1080;
			BattleSheep.HEIGHT = 1920;
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

	@Override
	public void signIn() {

	}

	@Override
	public void signOut() {

	}

	@Override
	public void rematch() {

	}

	@Override
	public void onQuickMatchClicked() {

	}

    @Override
    public String retrieveData() {
        return null;
    }

    @Override
    public void writeData(byte[] str) {

    }

	@Override
	public String getmDisplayName() {
		return null;
	}

	@Override
	public void onDoneClicked() {

	}

	@Override
	public void onFinishClicked() {

	}

	@Override
	public void onStartMatchClicked() {

	}

	@Override
	public void rateGame() {

	}

	@Override
	public void unlockAchievement(String str) {

	}

	@Override
	public void submitScore(int highScore) {

	}

	@Override
	public void submitLevel(int highLevel) {

	}

	@Override
	public void showAchievement() {

	}

	@Override
	public void showScore() {

	}

	@Override
	public void showLevel() {

	}

	@Override
	public boolean isSignedIn() {
		return false;
	}
}
