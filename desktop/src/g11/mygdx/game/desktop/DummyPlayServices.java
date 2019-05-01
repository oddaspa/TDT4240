package g11.mygdx.game.desktop;

import g11.mygdx.game.PlayServices;

public class DummyPlayServices implements PlayServices {
    @Override
    public void signIn() {

    }

    @Override
    public void signOut() {

    }

    @Override
    public void rateGame() {

    }

    @Override
    public void unlockAchievement(String str) {

    }

    @Override
    public void submitScore(int highScore) {

    }

    @Override
    public void submitLevel(int highLevel) {

    }

    @Override
    public void showAchievement() {

    }

    @Override
    public void showScore() {

    }

    @Override
    public void showLevel() {

    }

    @Override
    public boolean isSignedIn() {
        return false;
    }
}