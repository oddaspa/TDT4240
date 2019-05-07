package g11.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
        private float[] formerData;
        private Sprite signInButton;
        private Sprite signOutButton;
        // MAKE ALL THE STATES

        public MenuState(PlayServices actionResolver){
            this.action = actionResolver;
            this.menuSprites = new Array<Sprite>();
            this.formerData = new float[2];
            loadData();
        }
        @Override
        public String parseInput(float[] data){
            if (action.isSignedIn()){
                if (menuSprites.get(3) == signInButton){
                    menuSprites.set(3,signOutButton);
                }
            }else {
                if (menuSprites.get(3) == signOutButton){
                    menuSprites.set(3,signInButton);
                }
            }
            if (data == null) {return "menuState";}
            if(data == formerData){
                return "menuState";
            }
            formerData = data;
            if (menuSprites.get(3) == signInButton && this.signIn.isClicked(data[0], data[1])) {
                action.signIn();
                return "menuState";
            }

            if (menuSprites.get(3) == signOutButton && this.signOut.isClicked(data[0], data[1])) {
                action.signOut();
                return "menuState";
            }
            if (this.quickGame.isClicked(data[0], data[1])){
                if (action.isSignedIn()) {
                    action.onQuickMatchClicked();
                    return "placeAnimalState";
                }else {
                    Gdx.app.log("------> MenuState", "tried to play while signed out");
                }
            }

            if (this.matchMaking.isClicked(data[0], data[1])){
                if (action.isSignedIn()) {
                    action.onStartMatchClicked();
                    return "placeAnimalState";
                }else {
                    Gdx.app.log("------> MenuState", "tried to play while signed out");
                }
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
            background.setPosition(-15,5);
            background.setSize(BattleSheep.WIDTH,BattleSheep.HEIGHT);
        /*
        Texture playBtn = new Texture("quick_game.png");
        Sprite startButton = new Sprite(playBtn);
        startButton.setSize(2 * BattleSheep.WIDTH / 3, BattleSheep.HEIGHT / 4);
        startButton.setPosition(BattleSheep.WIDTH / 6,(BattleSheep.HEIGHT *2 / 7));
        */
            Texture challengeBtn = new Texture("challenge_friend.png");
            Sprite challengeButton = new Sprite(challengeBtn);
            challengeButton.setSize(2 * BattleSheep.WIDTH / 3, BattleSheep.HEIGHT / 6);
            this.matchMaking = new SignInButton(challengeButton, BattleSheep.WIDTH/2 - (challengeButton.getWidth()/2), BattleSheep.HEIGHT / 8);




            Texture quickTex = new Texture("quick_game.png");
            Sprite quickGameButton = new Sprite(quickTex);
            quickGameButton.setSize(2 * BattleSheep.WIDTH / 3, BattleSheep.HEIGHT / 6);
            this.quickGame = new SignInButton(quickGameButton,BattleSheep.WIDTH/2 - (quickGameButton.getWidth()/2), (BattleSheep.HEIGHT *2 / 6));

            this.menuSprites.add(background);
            this.menuSprites.add(quickGameButton);
            this.menuSprites.add(challengeButton);

            // GPGS STUFF
            signInButton = new Sprite(new Texture("login.png"));
            signInButton.setSize(BattleSheep.WIDTH / 4, BattleSheep.HEIGHT / 12);
            this.signIn = new SignInButton(signInButton,BattleSheep.WIDTH/2 -(signInButton.getWidth()/2),0);

            signOutButton = new Sprite(new Texture("logout.png"));
            signOutButton.setSize(BattleSheep.WIDTH / 4, BattleSheep.HEIGHT / 12);
            this.signOut = new SignInButton(signOutButton,BattleSheep.WIDTH/2 -(signOutButton.getWidth()/2),0);

            this.menuSprites.add(signOutButton);
        }
    }