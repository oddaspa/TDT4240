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
            float[] coordinates = new float[2];
            coordinates[0] = gx;
            coordinates[1] = gy;
            this.channel.update(coordinates);
        }else {
            //no new input
            this.channel.update(null);
        }
        //prints where input starts
        if (Gdx.input.justTouched()){System.out.println("(X: " + gx + ", Y:" + gy + ")");}
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(Array<Sprite> sprites, Array<String> msgs) {
        cam.update();
        sb.begin();
        for (Sprite s : sprites) {
            sb.draw(s.getTexture(), Math.round(s.getX()),Math.round(s.getY()), s.getWidth(), s.getHeight());
        }
        BitmapFont font = new BitmapFont();
        font.getData().setScale(4);
        font.setColor(Color.BLACK);
        if(msgs != null && msgs.size > 2){
            font.draw(sb, msgs.get(0),BattleSheep.WIDTH/5, (float) (BattleSheep.HEIGHT * 0.9));
            font.draw(sb, msgs.get(1),BattleSheep.WIDTH /6, (float) (BattleSheep.HEIGHT * 0.6));
            font.draw(sb, msgs.get(2),(float) ((BattleSheep.WIDTH * 0.35)), BattleSheep.HEIGHT / 16);

        }

        sb.end();
    }

    @Override
    public void resize(int width, int height) {
        // Calculate new Viewport
        gamePort.update(width, height);
    }

    @Override
    public void dispose() {
        sb.dispose();
    }

}
