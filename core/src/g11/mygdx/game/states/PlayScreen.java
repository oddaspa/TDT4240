/*
package g11.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import g11.mygdx.game.BattleSheep;
import g11.mygdx.game.sprites.Bullet;
import g11.mygdx.game.sprites.Drone;

import g11.mygdx.game.sprites.Helicopter;

public class PlayScreen extends Screen {
    private static final int DRONE_SPACING = 125;
    private static final int DRONE_COUNT = 3;
    private Helicopter heli;
    private Texture bg;
    private Bullet bullet;
    private float x_position;
    private BitmapFont font;
    private Array<Drone> drones;
    private Array<Bullet> bullets;
    private int score;
    private int bullet_count;


    public PlayScreen(State gsm) {
        super(gsm);
        heli = new Helicopter(50, 400);
        drones = new Array<Drone>();
        bullets = new Array<Bullet>();
        bullet_count = 4;

        for(int i =1; i <= 4; i++){
            bullets.add(new Bullet(900,900));
        }

        for(int i =1; i <= DRONE_COUNT; i++){
            drones.add(new Drone(i * (DRONE_SPACING + 44) + 400));
        }

        // zoom
        //cam.setToOrtho(false, MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT / 2);
        bg = new Texture("background.jpg");
        font = new BitmapFont();
        score = 0;



    }

    @Override
    protected int[] handleInput() {
        if(Gdx.input.justTouched()){
            x_position = Gdx.input.getX();
            heli.jump(x_position);

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            heli.shoot();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            heli.move(Input.Keys.UP);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            heli.move(Input.Keys.DOWN);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            heli.move(Input.Keys.LEFT);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            heli.move(Input.Keys.RIGHT);
        }

    }

    @Override
    public void update(float dt) {
        if(heli.health < 1){
            System.out.println("YOU DIED WITH THE SCORE OF: " + score);
            gsm.set(new MenuScreen(gsm));
        }
        handleInput();
        heli.update(dt);
        if (heli.isShooting()){



            bullets.get(bullet_count % 4).setPosition(heli.getPosition().x,heli.getPosition().y);
            heli.setShooting(false);
            bullet_count++;
        }


        for(Drone drone : drones){
            drone.update(dt);
            if(drone.getPosition().x + 88 < 0){
                drone.reposition(drone.getPosition().x +((88 + DRONE_SPACING) * DRONE_COUNT));
            }

            if(drone.collides(heli.getBound())){
                Vector3 heliVel = heli.getVelocity();
                Vector3 droneVel = drone.getVelocity();
                drone.bounce(heliVel,droneVel);
                heli.bounce(droneVel,heliVel);
                heli.health -= 20;
            }
            for(Bullet bullet : bullets) {
                bullet.update(dt);
                if (drone.collides(bullet.getBound())) {
                    score++;
                    Vector3 bulletSpeed = bullet.getVelocity();
                    Vector3 droneVel = drone.getVelocity();
                    drone.reposition(drone.getPosition().x + ((88 + DRONE_SPACING) * DRONE_COUNT));
                    bullet.setPosition(900, 900);
                }
            }
        }


    }

    @Override
    public void render(SpriteBatch sb) {
        // zoom
        //sb.setProjectionMatrix(cam.combined);
        sb.begin();
        //sb.draw(bg, cam.position.x - (cam.viewportWidth / 2) ,0);
        sb.draw(bg, 0 ,0,BattleSheep.WIDTH,BattleSheep.HEIGHT);
        if(heli.getVelocityX() < 0){
            sb.draw(heli.getTexture(), heli.getPosition().x-80, heli.getPosition().y);

        }else {
            sb.draw(heli.getTexture(),heli.getPosition().x+80,heli.getPosition().y,-160,55);
        }
        for(Drone drone: drones) {
            sb.draw(drone.getTexture(), drone.getPosition().x, drone.getPosition().y);
        }
        font.draw(sb, "x:" + heli.getPosition().x + ", y:" + heli.getPosition().y,0,800);
        font.draw(sb,"SCORE: " + score,200,800);
        font.draw(sb, "HEALTH: " + heli.health, 300,800 );
        for(Bullet bullet : bullets) {
            sb.draw(bullet.getTexture(), bullet.getPosition().x, bullet.getPosition().y);
        }

        sb.end();

    }

    @Override
    public void dispose() {
        bg.dispose();
        heli.dispose();
        for(Drone drone: drones){
            drone.dispose();
        }

    }

}
*/