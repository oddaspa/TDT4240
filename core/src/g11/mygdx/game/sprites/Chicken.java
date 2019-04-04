package g11.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import g11.mygdx.game.BattleSheep;

public class Chicken extends Sprite{
    public Chicken (int width, int height){
        super();
        Texture chicken = new Texture("chicken-liten.png");
        this.setTexture(chicken);
        this.setSize(width, height);

    }

    public void gotHit(){
        this.setSize(BattleSheep.WIDTH / 10 - 2, BattleSheep.WIDTH / 10 - 2);
    }

    @Override
    public String toString(){
        return "c";
    }



}
