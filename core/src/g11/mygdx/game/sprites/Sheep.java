package g11.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import g11.mygdx.game.BattleSheep;

public class Sheep extends Sprite{
    int hitpoints;
    public Sheep (int width, int height){
        Texture sheep = new Texture("sheep-liten.png");
        this.setTexture(sheep);
        this.setSize(width, height);
        this.hitpoints = 4;
    }

    public void gotHit(){
        hitpoints--;
        System.out.println("sheep got hit");

        if(hitpoints == 0){
            System.out.println("sheep dead");
            dead();
        }

    }
    public void dead(){
        this.setSize(BattleSheep.WIDTH / 10*2 - 2, BattleSheep.WIDTH / 10*2 - 2);
    }
    @Override
    public String toString(){
        return "s";
    }


}
