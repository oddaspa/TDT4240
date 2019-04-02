package g11.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


import java.util.Random;

public class Grass extends Sprite{

    public Grass (){
        super();
        Random rand = new Random();
        int grassType = rand.nextInt(4) + 1;
        String path = "grass-" + grassType + ".png";
        Texture grass = new Texture(path);
        this.setTexture(grass);

    }

    @Override
    public String toString(){
        return ".";
    }




}