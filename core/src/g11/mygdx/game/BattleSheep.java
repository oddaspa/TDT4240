package g11.mygdx.game;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class BattleSheep extends ApplicationAdapter {

	private Stage stage;
	private Label statusLabel;

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

	public PlayServices getLocalActionActionResolver() {
		return localActionActionResolver;
	}

	public void signIn(){
		this.getLocalActionActionResolver().signIn();
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
		//Gdx.gl.glClearColor(194/255f, 225/255f, 157/255f, 1);
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

	public void updateStatus(String comment){
		statusLabel.setText("Status:"+comment);
	}
}










/* STUFF FROM onCreate()
Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		Image logo = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("libgdx.png")))));
		logo.setPosition(Gdx.graphics.getWidth()/2-logo.getWidth()/2,(float)(Gdx.graphics.getHeight()*0.75));
		stage.addActor(logo);


		statusLabel = new Label("Status: signed out",skin);
		statusLabel.setAlignment(Align.center);
		statusLabel.setWidth(Gdx.graphics.getWidth());
		statusLabel.setWrap(true);
		statusLabel.setPosition(0,Gdx.graphics.getHeight()/2-statusLabel.getHeight()*2);
		stage.addActor(statusLabel);

		TextButton signInButton = new TextButton("Sign In", skin);
		signInButton.setTouchable(Touchable.enabled);
		signInButton.setBounds(0, 0, signInButton.getWidth(), signInButton.getHeight());
		signInButton.setPosition(Gdx.graphics.getWidth() / 3 - signInButton.getWidth() / 2, Gdx.graphics.getHeight() / 4 - signInButton.getHeight() / 2);
		signInButton.setWidth(Gdx.graphics.getWidth() / 12 * 2);
		signInButton.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				updateStatus("clicked Sign In");
				localActionActionResolver.signIn();
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		stage.addActor(signInButton);

		TextButton signOutButton = new TextButton("Sign Out",skin);
		signOutButton.setPosition(Gdx.graphics.getWidth()/3*2-signOutButton.getWidth()/2,Gdx.graphics.getHeight()/4-signOutButton.getHeight()/2);
		signOutButton.setWidth(Gdx.graphics.getWidth()/12*2);
		signOutButton.setTouchable(Touchable.disabled);
		signOutButton.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				updateStatus("clicked Sign out");
				localActionActionResolver.signOut();
			}

			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
		});
		stage.addActor(signOutButton);


 */













/*
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BattleSheep extends Game {

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


    @Override
	public void create () {
		if(Gdx.app.getType() == Application.ApplicationType.Android) {
			// android specific code
			BattleSheep.WIDTH = 1080;
			BattleSheep.HEIGHT = 1920;
		}
		batch = new SpriteBatch();
		//Gdx.gl.glClearColor(194/255f, 225/255f, 157/255f, 1);
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
*/