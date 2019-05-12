package g11.mygdx.game;

public interface PlayServices
{
    public void signIn();
    public void signOut();
    public boolean isSignedIn();
    public void onQuickMatchClicked();
    public void onStartMatchClicked();
    public void onDoneClicked();
    public void writeData(byte[] str);
    public void onFinishClicked();
    public String[] retrieveData();
    public String getmDisplayName();
    public void writeBoard(byte[] str);
    public boolean getIsDoingTurn();
    public boolean getHasOpponent();
    public String getOpponentName();
    public boolean isPlayer2();
    public int getTurnCounter();
    public void askForRematch();
    public boolean getRematchStatus();
}

