package g11.mygdx.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import g11.mygdx.game.BattleSheep;
import g11.mygdx.game.PlayServices;
import g11.mygdx.game.modules.Button;
import g11.mygdx.game.modules.SignInButton;

public class MenuState implements IState {
    // NEEDS TO CONTAIN THE DATA
    private Array<Sprite> menuSprites;
    private Button signIn;
    private Button signOut;
    private Button quickGame;
    private Button matchMaking;
    private PlayServices action;
    // MAKE ALL THE STATES

    public MenuState(PlayServices actionResolver){
        this.action = actionResolver;
        this.menuSprites = new Array<Sprite>();
        loadData();
    }
    @Override
    public String parseInput(float[] data){
        if (data == null) {return "menuState";}

        if (this.signIn.isClicked(data[0], data[1])) {
            action.signIn();
            return "menuState";
        }

        if (this.signOut.isClicked(data[0], data[1])) {
            action.signOut();
            return "menuState";
        }
        if (this.quickGame.isClicked(data[0], data[1])){
            this.action.onQuickMatchClicked();
            return "placeAnimalState";
        }

        if (this.matchMaking.isClicked(data[0], data[1])){
            action.unlockAchievement("CgkIiJTL5d0KEAIQBA");
            action.onStartMatchClicked();
            return "menuState";
        }

        return "menuState";
    }
    @Override
    public Array<String> serveMessages(){
        return null;
    }
    @Override
    public Array<Sprite> serveData(){
        return this.menuSprites;
    }

    @Override
    public void loadData(){
        Texture bg = new Texture("background2.jpg");
        Sprite background = new Sprite(bg);
        background.setPosition(0,0);
        background.setSize(BattleSheep.WIDTH,BattleSheep.HEIGHT);
        /*
        Texture playBtn = new Texture("quick_game.png");
        Sprite startButton = new Sprite(playBtn);
        startButton.setSize(2 * BattleSheep.WIDTH / 3, BattleSheep.HEIGHT / 4);
        startButton.setPosition(BattleSheep.WIDTH / 6,(BattleSheep.HEIGHT *2 / 7));
        */
        Texture challengeBtn = new Texture("challenge_friend.png");
        Sprite challengeButton = new Sprite(challengeBtn);
        challengeButton.setSize(2 * BattleSheep.WIDTH / 3, BattleSheep.HEIGHT / 4);
        this.matchMaking = new SignInButton(challengeButton, BattleSheep.WIDTH / 6, BattleSheep.HEIGHT / 14);


        // GPGS STUFF
        Texture signInTex = new Texture("sign_in_button1.png");
        Sprite signInButton = new Sprite(signInTex);
        signInButton.setSize(BattleSheep.WIDTH / 4, BattleSheep.HEIGHT / 12);
        this.signIn = new SignInButton(signInButton,0,0);

        Texture signOutTex = new Texture("sign_out_button1.png");
        Sprite signOutButton = new Sprite(signOutTex);
        signOutButton.setSize(BattleSheep.WIDTH / 4, BattleSheep.HEIGHT / 12);
        this.signOut = new SignInButton(signOutButton,BattleSheep.WIDTH - BattleSheep.WIDTH/4,0);

        Texture quickTex = new Texture("quick_game.png");
        Sprite quickGameButton = new Sprite(quickTex);
        quickGameButton.setSize(2 * BattleSheep.WIDTH / 3, BattleSheep.HEIGHT / 4);
        this.quickGame = new SignInButton(quickGameButton,BattleSheep.WIDTH / 6, (BattleSheep.HEIGHT *2 / 7));

        this.menuSprites.add(background);
        this.menuSprites.add(quickGameButton);
        this.menuSprites.add(challengeButton);

        this.menuSprites.add(signInButton);
        this.menuSprites.add(signOutButton);
    }
}
