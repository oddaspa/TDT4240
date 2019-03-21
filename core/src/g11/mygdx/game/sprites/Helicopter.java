package g11.mygdx.game.sprites;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import g11.mygdx.game.BattleSheep;

public class Helicopter {
    private static final int GRAVITY = -7;
    private Vector3 position;
    private Vector3 velocity;
    private Animation heliAnimation;
    private Texture heli;
    private Texture texture;
    private boolean shooting;
    public int health;
    private Rectangle bound;

    public Helicopter(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        heli = new Texture("heli13.png");
        texture = new Texture("helianimation4.png");
        heliAnimation = new Animation(new TextureRegion(texture),4,0.1f);
        bound = new Rectangle(x,y,heli.getWidth(),heli.getHeight());
        shooting = false;
        health = 100;

    }

    public void update(float dt){
        heliAnimation.update(dt);
        if(position.y > BattleSheep.HEIGHT - 65){
            position.y = BattleSheep.HEIGHT - 65;
        }
        if(position.y > 0 && position.y <= BattleSheep.HEIGHT) {
            velocity.add(0, GRAVITY, 0);
        }
        if (position.x > BattleSheep.WIDTH-80){
            position.x = BattleSheep.WIDTH-80;
            velocity.x = 0;
        }
        if (position.x < 80){
            position.x = 80;
            velocity.x = 0;
        }
        velocity.scl(dt);
        position.add(velocity.x, velocity.y, 0);
        if(position.y < 0) {
            position.y = 0;
        }
        velocity.scl(1/dt);
        bound.setPosition(position.x,position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return heliAnimation.getFrame();
    }

    public void jump(float x_position){
        velocity.y = 250;
        velocity.x = x_position - position.x;
    }

    public void move(int keyPress){
        if(keyPress == Input.Keys.UP){
            velocity.y += 17;
        }
        if(keyPress == Input.Keys.DOWN){
            velocity.y -= 10;
        }
        if(keyPress == Input.Keys.LEFT){
            velocity.x -= 10;
        }
        if(keyPress == Input.Keys.RIGHT){
            velocity.x += 10;
        }

    }
    public float getVelocityX(){
        return velocity.x;
    }

    public Vector3 getVelocity(){
        return velocity;
    }

    public void dispose(){
        heli.dispose();
    }

    public Rectangle getBound(){
        return bound;
    }

    public void bounce(Vector3 drone, Vector3 velocity){
        velocity.x = -drone.x;
        velocity.y = -drone.y;
        velocity.z = -drone.z;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public boolean isShooting() {
        return shooting;
    }

    public void shoot(){
        shooting = true;

    }
}
