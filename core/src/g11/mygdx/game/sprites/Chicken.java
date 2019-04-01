package g11.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Chicken extends Sprite{
    private Rectangle bounds;

    public Chicken (int startX, int startY){
        this.setPosition(startX,startY);
        Texture chicken = new Texture("chicken-liten.png");
        this.setTexture(chicken);
        bounds = new Rectangle(startX, startY, chicken.getWidth(), chicken.getHeight());
    }


    @Override
    public String toString(){
        return "c";
    }



}
