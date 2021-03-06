package g11.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import g11.mygdx.game.BattleSheep;
import g11.mygdx.game.PlayServices;
import g11.mygdx.game.modules.Button;
import g11.mygdx.game.modules.HomeButton;
import g11.mygdx.game.modules.SignInButton;

    public class MenuState implements IState {
        // NEEDS TO CONTAIN THE DATA
        private Array<Sprite> menuSprites;
        private Button signIn;
        private Button signOut;
        private Button quickGame;
        //private Button matchMaking;
        private Button soundButton;
        private PlayServices action;
        private float[] formerData;
        private Sprite signInButton;
        private Sprite signOutButton;
        public Music music = Gdx.audio.newMusic(Gdx.files.internal("backgroundMusic.mp3"));
        private static Texture soundTexture = new Texture("sound.png");
        private static Texture soundOffTexture = new Texture("nosound.png");


        // MAKE ALL THE STATES

        public MenuState(PlayServices actionResolver){
            this.action = actionResolver;
            this.menuSprites = new Array<Sprite>();
            this.formerData = new float[2];
            loadData();
            music.setLooping(true);
            music.setVolume(0.6f);
            music.play();

        }
        @Override
        public String parseInput(float[] data){
            if (action.isSignedIn()){
                if (menuSprites.get(2) == signInButton){
                    menuSprites.set(2,signOutButton);
                }
            }else {
                if (menuSprites.get(2) == signOutButton){
                    menuSprites.set(2,signInButton);
                }
            }
            if (data == null) {return "menuState";}
            if(data == formerData){
                return "menuState";
            }
            formerData = data;
            if (this.soundButton.isClicked(data[0],data[1])) {
                if (music.getVolume() == 0.6f){
                    music.setVolume(0f);
                    soundButton.getButton().setTexture(soundOffTexture);
                }else{
                    music.setVolume(0.6f);
                    soundButton.getButton().setTexture(soundTexture);
                }

            }

            if (menuSprites.get(2) == signInButton && this.signIn.isClicked(data[0], data[1])) {
                action.signIn();
                return "menuState";
            }

            if (menuSprites.get(2) == signOutButton && this.signOut.isClicked(data[0], data[1])) {
                action.signOut();
                Gdx.app.log("------> MenuState", "tried to sign out..");
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
            background.setPosition(-20,-50);
            background.setSize(BattleSheep.WIDTH,BattleSheep.HEIGHT);
        /*
        Texture playBtn = new Texture("quick_game.png");
        Sprite startButton = new Sprite(playBtn);
        startButton.setSize(2 * BattleSheep.WIDTH / 3, BattleSheep.HEIGHT / 4);
        startButton.setPosition(BattleSheep.WIDTH / 6,(BattleSheep.HEIGHT *2 / 7));

            Texture challengeBtn = new Texture("challenge_friend.png");
            Sprite challengeButton = new Sprite(challengeBtn);
            challengeButton.setSize(2 * BattleSheep.WIDTH / 3, BattleSheep.HEIGHT / 6);
            this.matchMaking = new SignInButton(challengeButton, BattleSheep.WIDTH/2 - (challengeButton.getWidth()/2), BattleSheep.HEIGHT / 8);
*/



            Texture quickTex = new Texture("quick_game.png");
            Sprite quickGameButton = new Sprite(quickTex);
            quickGameButton.setSize(2 * BattleSheep.WIDTH / 3, BattleSheep.HEIGHT / 6);
            this.quickGame = new SignInButton(quickGameButton,BattleSheep.WIDTH/2 - (quickGameButton.getWidth()/2),(float) (BattleSheep.HEIGHT *2 / 8));

            this.menuSprites.add(background);
            this.menuSprites.add(quickGameButton);

            // GPGS STUFF
            signInButton = new Sprite(new Texture("login.png"));
            signInButton.setSize(BattleSheep.WIDTH / 4, BattleSheep.WIDTH / 8);
            this.signIn = new SignInButton(signInButton,BattleSheep.WIDTH - BattleSheep.WIDTH/15 - signInButton.getWidth(),BattleSheep.HEIGHT - signInButton.getHeight()- 50);

            signOutButton = new Sprite(new Texture("logout.png"));
            signOutButton.setSize(BattleSheep.WIDTH / 4, BattleSheep.WIDTH / 8);
            this.signOut = new SignInButton(signOutButton,BattleSheep.WIDTH - BattleSheep.WIDTH/15 - signOutButton.getWidth() ,BattleSheep.HEIGHT - signOutButton.getHeight()- 50);


            Sprite soundSprite = new Sprite(soundTexture, BattleSheep.WIDTH / 8, BattleSheep.WIDTH / 8);
            this.soundButton = new HomeButton(soundSprite, (float) BattleSheep.WIDTH/15, BattleSheep.HEIGHT - soundSprite.getHeight()- 50);


            this.menuSprites.add(signOutButton);
            this.menuSprites.add(soundButton.getButton());
        }

        public PlayServices getAction(){
            return this.action;
        }
    }

