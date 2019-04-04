package g11.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


import g11.mygdx.game.BattleSheep;

public class Chicken extends Sprite{

    Texture chicken1;
    Texture chicken2;
    boolean flip;
    int stepCount;

    public Chicken (int width, int height){
        super();
        this.chicken1 = new Texture("chicken-ani1.png");
        this.chicken2 = new Texture("chicken-ani2.png");
        this.setTexture(chicken1);
        this.setSize(width, height);
        this.flip = true;
        this.stepCount = 0;
    }

    public void update(float dt) {
        stepCount++;
        if((stepCount % 20) == 0) {
            if (flip) {
                this.setTexture(chicken2);
                flip = false;
            } else {
                this.setTexture(chicken1);
                flip = true;
            }
        }
    }

    public void gotHit(){
        this.setSize(BattleSheep.WIDTH / 10 - 2, BattleSheep.WIDTH / 10 - 2);

    }

    @Override
    public String toString(){
        return "c";
    }



}
