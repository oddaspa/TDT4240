package g11.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import g11.mygdx.game.BattleSheep;

public class Sheep extends Sprite{
    private Rectangle bounds;

    public Sheep (int width, int height){
        Texture sheep = new Texture("sheep-liten.png");
        this.setTexture(sheep);
        this.setSize(width, height);
    }

    public void gotHit(){
        System.out.println("sheep got hit");
        this.setSize(BattleSheep.WIDTH / 10 - 2, BattleSheep.WIDTH / 10 - 2);
    }

    @Override
    public String toString(){
        return "s";
    }


}
