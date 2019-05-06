package g11.mygdx.game;

public interface PlayServices
{
    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievement(String str);
    public void submitScore(int highScore);
    public void submitLevel(int highLevel);
    public void showAchievement();
    public void showScore();
    public void showLevel();
    public boolean isSignedIn();
    public void createMatch();
    public void onStartMatchClicked();
    public void onDoneClicked();
    public void writeData(byte[] string);
    public void onFinishClicked();
    public String retrieveData();
    public String getmDisplayName();
}

