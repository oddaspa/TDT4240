package g11.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Sheep extends Sprite{
    private Rectangle bounds;

    public Sheep (int startX, int startY){
        this.setPosition(startX,startY);
        Texture sheep = new Texture("sheep-liten.png");
        this.setTexture(sheep);
        bounds = new Rectangle(startX, startY, sheep.getWidth(), sheep.getHeight());
    }

    @Override
    public String toString(){
        return "s";
    }


}
