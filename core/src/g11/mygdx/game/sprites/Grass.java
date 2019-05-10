package g11.mygdx.game.sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.concurrent.ThreadLocalRandom;

import g11.mygdx.game.BattleSheep;

public class Grass extends Sprite{
    Sprite animal;
    boolean hasAnimal;
    boolean isHit;
    Music animalHit;


    public Grass (){
        super();
        animalHit = Gdx.audio.newMusic(Gdx.files.internal("buttonClick.mp3"));
        animalHit.setVolume(0.5f);
        int rand = ThreadLocalRandom.current().nextInt(1, 4 + 1);
        Texture grass = new Texture("grass-" + rand + ".png");
        this.setTexture(grass);
        float rangeY = (BattleSheep.HEIGHT/4)/8;
        this.setSize(Math.round(rangeY) - 2,Math.round(rangeY) - 2);
        this.animal = new Sprite();
        this.hasAnimal = false;
        this.isHit = false;
    }

    public Sprite gotHit(){
        if(isHit){
            return null;
        }
        isHit = true;
        if(hasAnimal){
            animalHit.play();
            Texture tex = new Texture("blood-1.png");
            Sprite blood = new Sprite(tex, (int) this.getWidth()/2, (int) this.getHeight()/2);
            blood.setPosition(this.getX()+this.getWidth()/4, this.getY()+this.getHeight()/4);
            this.animal.setAlpha(1f);
            return blood;
        } else {
            Texture tex = new Texture("grass-hit.png");
            Sprite ground = new Sprite(tex, (int) this.getWidth(), (int) this.getHeight());
            ground.setPosition(this.getX(), this.getY());
            return ground;
        }
    }

    @Override
    public String toString(){
        return ".";
    }

    public boolean isHit() {
        return this.isHit;
    }

    public void setAnimal(Sprite animal){
        this.hasAnimal = true;
        this.animal = animal;
    }

    public boolean hasAnimal(){
        return hasAnimal;
    }
    public Sprite getAnimal(){
        return this.animal;
    }


}


