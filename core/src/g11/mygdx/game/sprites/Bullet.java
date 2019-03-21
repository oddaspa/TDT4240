package g11.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import g11.mygdx.game.BattleSheep;

public class Bullet {
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bound;
    private Texture texture;
    private static final int SPEED = 100;

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public Rectangle getBound() {
        return bound;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public Texture getTexture() {

        return texture;
    }

    public Bullet(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(SPEED, 0, 0);
        texture = new Texture("bullet.png");
        bound = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        System.out.println("shots fired!");
    }

    public void update(float dt) {
        if (position.y > BattleSheep.HEIGHT - 65) {
            position.y = BattleSheep.HEIGHT - 65;
        }
        if (position.y > 0 && position.y <= BattleSheep.HEIGHT) {

            velocity.scl(dt);
            position.add(velocity.x, velocity.y, 0);

            velocity.scl(1 / dt);
            bound.setPosition(position.x, position.y);
        }
        if(position.x > 480){
            position.y=1200;
        }
    }
}
