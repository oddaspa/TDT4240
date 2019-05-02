package g11.mygdx.game.modules;

import com.badlogic.gdx.graphics.g2d.Sprite;

import g11.mygdx.game.BattleSheep;

public class YesButton extends Button {

    public YesButton(Sprite buttonSprite, float x, float y) {
        super(buttonSprite, x, y);
        buttonSprite.setSize(2* BattleSheep.WIDTH / 3, BattleSheep.HEIGHT / 6);
    }

}
