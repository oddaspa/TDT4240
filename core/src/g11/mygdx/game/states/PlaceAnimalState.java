package g11.mygdx.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import g11.mygdx.game.BattleSheep;


public class PlaceAnimalState implements IState{
    private Array<Sprite> placeAnimalSprites;
    private Array<String> placeAnimalMessages;

    public PlaceAnimalState(){
        this.placeAnimalSprites = new Array<Sprite>();
        this.placeAnimalMessages = new Array<String>();
        loadData();
    }
    @Override
    public String parseInput(float[] data) {
        if(data[0]>400){
            if(data[1]>600){
                return "inGameStatus";
            }
        }
        else {
            for (int i=64; i<this.placeAnimalSprites.size; i++){
                Sprite animal = this.placeAnimalSprites.get(i);
                if ( animal.getX() + animal.getWidth() > data[0] && data[0] > animal.getX() ){
                    if ( animal.getY() + animal.getHeight() > data[1] && data[1] > animal.getY() ){
                        System.out.println("animal touched");
                        animal.setPosition(data[0] - animal.getWidth()/2,data[1] - animal.getHeight()/2);
                    }
            }
        }
        }
        return "placeAnimalState";
    }

    @Override
    public Array<String> serveMessages() {
        return this.placeAnimalMessages;
    }

    @Override
    public Array<Sprite> serveData() {
        return this.placeAnimalSprites;
    }

    @Override
    public void loadData() {
        Random rand = new Random();
        Texture c = new Texture("chicken-liten.png");
        Texture sp = new Texture("sheep-liten.png");
        Texture g1 = new Texture("grass-1.png");
        Texture g2 = new Texture("grass-2.png");
        Texture g3 = new Texture("grass-3.png");
        Texture g4 = new Texture("grass-4.png");
        Sprite grass1 = new Sprite(g1);
        Sprite grass2 = new Sprite(g2);
        Sprite grass3 = new Sprite(g3);
        Sprite grass4 = new Sprite(g4);
        Sprite chicken = new Sprite(c);
        Sprite chicken2 = new Sprite(c);
        Sprite chicken3 = new Sprite(c);
        Sprite sheep = new Sprite(sp);
        Sprite sheep2 = new Sprite(sp);


        Array<Sprite> grassSprites = new Array<Sprite>();
        grassSprites.add(grass1);
        grassSprites.add(grass2);
        grassSprites.add(grass3);
        grassSprites.add(grass4);
        //place grass cells
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int n = rand.nextInt(4);
                Sprite s = new Sprite(grassSprites.get(n).getTexture(), BattleSheep.WIDTH / 9 - 2, BattleSheep.WIDTH / 9 - 2);
                s.setPosition(j * (BattleSheep.WIDTH / 9)+ 1 + 30, i * BattleSheep.WIDTH / 9 + 1 + 260);
                this.placeAnimalSprites.add(s);
            }
        }
        chicken.setPosition(31, 140);
        chicken.setSize(40,40);
        chicken2.setPosition(31 + chicken.getWidth() + 10 , 140);
        chicken2.setSize(40,40);
        chicken3.setPosition(31 + 2* (chicken.getWidth() + 10) , 140);
        chicken3.setSize(40,40);
        sheep.setPosition(31 + 3* (chicken.getWidth() + 10), 140);
        sheep.setSize(150,100);
        sheep2.setPosition(31 + 3* (chicken.getWidth() + 10) + sheep.getWidth(), 140);
        sheep2.setSize(90,60);
        this.placeAnimalSprites.add(chicken);
        this.placeAnimalSprites.add(chicken2);
        this.placeAnimalSprites.add(chicken3);
        this.placeAnimalSprites.add(sheep);
        this.placeAnimalSprites.add(sheep2);
        this.placeAnimalMessages.add("Place Your Animals");
        this.placeAnimalMessages.add("");
        this.placeAnimalMessages.add("");
    }


}
