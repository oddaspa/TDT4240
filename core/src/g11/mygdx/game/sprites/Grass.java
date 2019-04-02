package g11.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.concurrent.ThreadLocalRandom;

import g11.mygdx.game.BattleSheep;

public class Grass extends Sprite{
    Sprite animal;
    boolean hasAnimal;
    public Grass (){
        super();
        int rand = ThreadLocalRandom.current().nextInt(1, 4 + 1);
        Texture grass = new Texture("grass-" + rand + ".png");
        this.setTexture(grass);
        float rangeY = (BattleSheep.HEIGHT/4)/8;
        this.setSize(Math.round(rangeY) - 2,Math.round(rangeY) - 2);
        this.animal = new Sprite();
        this.hasAnimal = false;
    }

    public Sprite gotHit(){
        if(hasAnimal){
            Texture tex = new Texture("blood-1.png");
            Sprite blood = new Sprite(tex, (int) this.getWidth(), (int) this.getHeight());
            blood.setPosition(this.getX(), this.getY());
            this.animal.setAlpha(1f);
            return blood;
        } else {
            Texture tex = new Texture("quick_game.png");
            Sprite ground = new Sprite(tex, (int) this.getWidth(), (int) this.getHeight());
            ground.setPosition(this.getX(), this.getY());
            return ground;
        }
    }

    @Override
    public String toString(){
        return ".";
    }


    public void setAnimal(Sprite animal){
        this.hasAnimal = true;
        this.animal = animal;
    }


    public Sprite getAnimal(){
        return this.animal;
    }


}


