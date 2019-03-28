package g11.mygdx.game;

/* GENERIC SCREEN THAT TAKES DATA FROM MODEL AND PUSHES TO THE SCREEN */


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class View extends Screen {
    float gx;
    float gy;
    int currentScreen;
    private SpriteBatch sb;
    private Controller channel;


    View(SpriteBatch batch) {
        super(batch);
        this.channel = null;
        this.sb = batch;
        this.currentScreen = 1;
    }


    public void addObserver(Controller c){
        this.channel = c;
    }
    // METHODS TO GET THE DATA

    public void handleInput() {
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            gx = touchPos.x;
            gy = BattleSheep.HEIGHT - touchPos.y;
            System.out.println("(X: " + gx + ", Y:" + gy + ")");
        }
        float[] coordinates = new float[2];
        coordinates[0] = gx;
        coordinates[1] = gy;
        this.channel.update(coordinates);
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(Array<Sprite> sprites, Array<String> msgs) {
        sb.begin();
        for (Sprite s : sprites) {
            sb.draw(s.getTexture(), Math.round(s.getX()),Math.round(s.getY()), s.getWidth(), s.getHeight());
        }
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2);
        font.setColor(Color.BLACK);
        if(msgs != null){
            font.draw(sb, msgs.get(0),BattleSheep.WIDTH/6, 720);
            font.draw(sb, msgs.get(1),400, 790);
            font.draw(sb, msgs.get(2),10, 40);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        sb.dispose();
    }

}
