package g11.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class Drone extends Sprite implements AssetModel {
    private static final int GRAVITY = -2;
    private Vector3 position;
    private Vector3 velocity;
    private Animation droneAnimation;
    private int startY;
    private int speed;
    private Rectangle bound;
    private Random rand;
    private Sprite sp;

    public Drone(float x, Texture drone){
        super(drone);
        rand = new Random();
        startY = rand.nextInt(700-100) + 100;
        speed = rand.nextInt(60-20) + 20;
        speed = speed*-1;
        position = new Vector3(x, startY, 0);
        velocity = new Vector3(0, 0, 0);
        sp = new Sprite(drone, drone.getWidth(), drone.getHeight());
        sp.setPosition(x, startY);
        droneAnimation = new Animation(new TextureRegion(drone),2,0.5f);
        bound = new Rectangle(position.x+10, position.y,drone.getWidth()-20,drone.getHeight()-10);
        //System.out.println("DRONE W: " + drone.getWidth() + ", H: " + drone.getHeight());

    }
    @Override
    public String toString(){
        return "This is Drone class.";
    }

    public void update(float dt) {
        droneAnimation.update(dt);
        if (position.y < startY - (this.getHeight()/2)) {
            move();
        } else {
            velocity.add(0, GRAVITY, 0);
        }
        velocity.scl(dt);
        position.add(velocity.x, velocity.y, 0);
        velocity.scl(1/dt);
        bound.setPosition(position.x + this.getWidth()/2, position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    //public TextureRegion getTexture() { return droneAnimation.getFrame(); }


    public void move(){

        velocity.y = 100;
        velocity.x = speed;
    }
    public float getVelocityX(){
        return velocity.x;
    }
    public void dispose(){
        this.dispose();
    }


    public void reposition(float x){
        getPosition().set(x, (rand.nextInt(700-100) + 100),0);
        bound.setPosition(position.x,position.y);

    }


    public Vector3 getVelocity() {
        return velocity;
    }

    public void bounce(Vector3 helicopter, Vector3 velocity){
        velocity.x = helicopter.x;
        velocity.y = helicopter.y;
        velocity.z = helicopter.z;
    }

    public boolean collides(Rectangle player){
        return player.overlaps(bound);
    }
}
