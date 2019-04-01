package g11.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Helicopter extends Sprite {
    public Helicopter(Texture heli){
        super(heli);
    }

    @Override
    public String toString(){
        return "This is helicopter class.";
    }


    public void gotHit(){
        Texture tex = new Texture("quick_game.png");
        this.setTexture(tex);
    }

    public void getGrid(){

    }

}
