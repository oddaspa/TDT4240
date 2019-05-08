package g11.mygdx.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.InvitationsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.TurnBasedMultiplayerClient;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchUpdateCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AndroidApplication implements  GoogleApiClient.OnConnectionFailedListener, PlayServices {

    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;
    private static final int RC_SELECT_PLAYERS = 10000;
    private final static int RC_LOOK_AT_MATCHES = 10001;
    private GoogleSignInAccount account= null;

    // Client used to interact with the TurnBasedMultiplayer system.
    private TurnBasedMultiplayerClient mTurnBasedMultiplayerClient = null;
    // Client used to interact with the Invitation system.
    private InvitationsClient mInvitationsClient = null;

    public TurnBasedMatch mMatch = null;
    public SkeletonTurn mTurnData;
    private Player mPlayer;
    // Should I be showing the turn API?
    public boolean isDoingTurn = false;
    public boolean hasOpponent = false;
    AlertDialog.Builder alertDialogBuilder;



    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;
        initialize(new BattleSheep(this), config);
        alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).requestEmail().build();
        //nå har jeg bare lagt inn en fragmentActivity.... denne klassen skulle egentlig extendet fragmentActivity
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(new FragmentActivity(),this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
    }


    @Override
    public void signIn() {
        if (account == null){

            Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(intent,REQ_CODE);
        }else {
            Gdx.app.log("------> signIn()","already signed in to: "+account.getEmail());
        }

    }

    @Override
    public void signOut() {
        if (account != null) {
            googleApiClient.connect();
            googleApiClient.registerConnectionCallbacks(new ConnectionCallbacks() {
                @Override
                public void onConnected(@Nullable Bundle bundle) {
                    if(googleApiClient.isConnected()) {
                        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                if (status.isSuccess()) {
                                    if (account != null ){
                                        showDialog("signed out","signed out from: "+account.getEmail());
                                        Gdx.app.log("------> signOut()","Signed out from account: "+account.getEmail());
                                        account = null;
                                    }
                                }
                            }
                        });
                    }
                }
                @Override
                public void onConnectionSuspended(int i) {
                    Gdx.app.log("------> signOut()", "Google API Client Connection Suspended");
                }
            });

        }else {
            Gdx.app.log("------> signOut()", "already signed out");
        }
    }


    public void showDialog(String title, String message) {
        // set title
        alertDialogBuilder.setTitle(title);
        // set message and positive button
        alertDialogBuilder.setMessage(message)
                .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void handleResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            account = result.getSignInAccount();
            Gdx.app.log("------> signIn()","signed in to account: "+ account.getEmail() );
            mTurnBasedMultiplayerClient = Games.getTurnBasedMultiplayerClient(this, account);
            mInvitationsClient = Games.getInvitationsClient(this, account);
            Games.getPlayersClient(this, account)
                    .getCurrentPlayer()
                    .addOnSuccessListener(
                            new OnSuccessListener<Player>() {
                                @Override
                                public void onSuccess(Player player) {
                                    mPlayer = player;
                                    Gdx.app.log("------> signIn()", "getCurrentPlayer success. PlayerName: "+ mPlayer.getDisplayName() + " PlayerID: "+ mPlayer.getPlayerId());

                                }
                            }
                    );
            // Likewise, we are registering the optional MatchUpdateListener, which
            // will replace notifications you would get otherwise. You do *NOT* have
            // to register a MatchUpdateListener.
            mTurnBasedMultiplayerClient.registerTurnBasedMatchUpdateCallback(mMatchUpdateCallback);
            /*
            mTurnBasedMultiplayerClient.getInboxIntent()
                    .addOnSuccessListener(new OnSuccessListener<Intent>() {
                        @Override
                        public void onSuccess(Intent intent) {
                            Gdx.app.log("------> handleResult()", "success getInboxIntent()");
                            startActivityForResult(intent, RC_LOOK_AT_MATCHES);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Gdx.app.log("------> handleResult()","error getInboxIntent()");
                        }
                    });
            */
            // Retrieve the TurnBasedMatch from the connectionHint
            GamesClient gamesClient = Games.getGamesClient(this, account);
            gamesClient.getActivationHint()
                    .addOnSuccessListener(new OnSuccessListener<Bundle>() {
                        @Override
                        public void onSuccess(Bundle hint) {
                            if (hint != null) {
                                Invitation inv = hint.getParcelable(Multiplayer.EXTRA_INVITATION);
                                Gdx.app.log("-----> getActivationHint()", "invited by: "+inv.getInviter().getDisplayName());
                                TurnBasedMatch match = hint.getParcelable(Multiplayer.EXTRA_TURN_BASED_MATCH);
                                Gdx.app.log("------> signIn()","received non-null hint");
                                if (match != null) {
                                    mMatch = match;
                                    updateMatch();
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Gdx.app.log("signIn()","There was a problem getting the activation hint!");
                        }
                    });

        }else{Gdx.app.log("------> handleResult()", "not succsess");}
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        } else if (requestCode == RC_LOOK_AT_MATCHES) {
            // Returning from the 'Select Match' dialog
            Gdx.app.log("------> onActivityResult()", "RC_LOOK_AT_MATCHES");
            if (resultCode != Activity.RESULT_OK) {
                Gdx.app.log("------> RC_LOOK_AT_MATCHES",
                        "User cancelled returning from the 'Select Match' dialog.");
                return;
            }

            TurnBasedMatch match = data
                    .getParcelableExtra(Multiplayer.EXTRA_TURN_BASED_MATCH);

            if (match != null) {
                mMatch = match;
                updateMatch();
            }

            Gdx.app.log("------> onActivityResult()", "Match = " + match);
        }

        else if (requestCode == RC_SELECT_PLAYERS) {
            // Returning from 'Select players to Invite' dialog

            if (resultCode != Activity.RESULT_OK) {
                // user canceled
                Gdx.app.log("------> RC_SELECT_PLAYERS","User cancelled returning from 'Select players to Invite' dialog");
                return;
            }

            // get the invitee list
            ArrayList<String> invitees = data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);
            Gdx.app.log("------> onActivityResult()", "invitee = " + invitees.get(0));
            // get automatch criteria
            Bundle autoMatchCriteria;

            int minAutoMatchPlayers = data.getIntExtra(Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
            int maxAutoMatchPlayers = data.getIntExtra(Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);

            if (minAutoMatchPlayers > 0) {
                autoMatchCriteria = RoomConfig.createAutoMatchCriteria(minAutoMatchPlayers, maxAutoMatchPlayers, 0);
            } else {
                autoMatchCriteria = null;
            }

            TurnBasedMatchConfig tbmc = TurnBasedMatchConfig.builder()
                    .addInvitedPlayers(invitees)
                    .setAutoMatchCriteria(autoMatchCriteria).build();

            // Start the match
            mTurnBasedMultiplayerClient.createMatch(tbmc)
                    .addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
                        @Override
                        public void onSuccess(TurnBasedMatch turnBasedMatch) {
                            Gdx.app.log("------> RC_SELECT_PLAYERS","Starting match...");
                            onInitiateMatch(turnBasedMatch);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Gdx.app.log("------> RC_SELECT_PLAYERS","There was a problem creating a match!");
                        }
                    });
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Gdx.app.log("------> onConnectionFailed()",connectionResult.getErrorMessage());
    }


    @Override
    public String getOpponentName(){
        return mMatch.getDescriptionParticipant().getDisplayName();
    }


    @Override
    public boolean isSignedIn() {
        return account != null;
    }

    @Override
    public boolean getIsDoingTurn(){
        return isDoingTurn;
    }

    @Override
    public boolean getHasOpponent(){
        return hasOpponent;
    }
    // Open the create-game UI. You will get back an onActivityResult
    // and figure out what to do.
    @Override
    public void onStartMatchClicked() {
        mTurnBasedMultiplayerClient.getSelectOpponentsIntent(1, 1, true)
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_SELECT_PLAYERS);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Gdx.app.log("------> onStartMatchClicked()","error_get_select_opponents");
                    }
                });
    }

    @Override
    // Create a one-on-one automatch game.
    public void onQuickMatchClicked() {

        Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(1, 1, 0);

        TurnBasedMatchConfig turnBasedMatchConfig = TurnBasedMatchConfig.builder()
                .setAutoMatchCriteria(autoMatchCriteria).build();

        // Start the match
        mTurnBasedMultiplayerClient.createMatch(turnBasedMatchConfig)
                .addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
                    @Override
                    public void onSuccess(TurnBasedMatch turnBasedMatch) {
                        onInitiateMatch(turnBasedMatch);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Gdx.app.log("onQuickMatchClicked()","There was a problem creating a match!");
                    }
                });
    }


    private void onDisconnected() {
        Gdx.app.log("------> onDissconnected()", "onDisconnected()");

        mTurnBasedMultiplayerClient = null;
        mInvitationsClient = null;
    }


    private void onInitiateMatch(TurnBasedMatch match) {
        mMatch = match;
        Gdx.app.log("------> onInitiateMatch()","matchID: "+mMatch.getMatchId());
        if (match.getData() != null) {
            mTurnData = new SkeletonTurn();
            // This is a game that has already started, so I'll just start
            Gdx.app.log("------> onInitiateMatch()","This is a game that has already started, so I'll just start");
            try {
                byte[] data = mMatch.getData();
                mTurnData.data.put("p_1", new String(Arrays.copyOfRange(data, 16, 88)));
                mTurnData.data.put("p_2", new String(Arrays.copyOfRange(data, 97, 169)));
                /*
                if (mMatch.getParticipantId(mPlayer.getPlayerId()).equals("p_1")) {
                    preexistingData.data.put("p_1", new String(Arrays.copyOfRange(data, 15, 88)));
                    preexistingData.data.put("p_2", new String(Arrays.copyOfRange(data, 108, 185)));
                } else if(mMatch.getParticipantId(mPlayer.getPlayerId()).equals("p_2")) {
                    preexistingData.data.put("p_2", new String(Arrays.copyOfRange(data, 15, 88)));
                    preexistingData.data.put("p_1", new String(Arrays.copyOfRange(data, 108, 185)));
                }
                */
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mTurnData.persist();
            updateMatch();
            return;
        }
        startMatch();
    }

    // Finish the game. Sometimes, this is your only choice.
    @Override
    public void onFinishClicked() {
        mTurnBasedMultiplayerClient.finishMatch(mMatch.getMatchId())
                .addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
                    @Override
                    public void onSuccess(TurnBasedMatch turnBasedMatch) {
                        onUpdateMatch(turnBasedMatch);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Gdx.app.log("------> onFinnishedClicked()","There was a problem finishing the match!");
                    }
                });

        isDoingTurn = false;

    }

    // Upload your new gamestate, then take a turn, and pass it on to the next
    // player.
    @Override
    public void onDoneClicked() {

        String nextParticipantId = getNextParticipantId();
        Gdx.app.log("-------> onDoneClicked()","next player ID: "+nextParticipantId);

        // Create the next turn
        mTurnData.turnCounter += 1;
        //mTurnData.data = new String(mMatch.getData());
        mTurnBasedMultiplayerClient.takeTurn(mMatch.getMatchId(),
                mTurnData.persist(), nextParticipantId)
                .addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
                    @Override
                    public void onSuccess(TurnBasedMatch turnBasedMatch) {
                        Gdx.app.log("onDoneClicked()","matchID: "+mMatch.getMatchId());
                        isDoingTurn = false;
                        //onUpdateMatch(turnBasedMatch);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Gdx.app.log("------> onDoneCklicked()","There was a problem taking a turn!");
                    }
                });

        //mTurnData = null;
    }

    @Override
    public void writeBoard(byte[] str) {
        String[] data = retrieveData();
        Gdx.app.log("-----> writeBoard()", "player_ID: "+mMatch.getParticipantId(mPlayer.getPlayerId()) + " is writing data");
        if (mMatch.getParticipantId(mPlayer.getPlayerId()).equals("p_1")) {
            mTurnData = mTurnData.unpersist(str, data[1].getBytes());
        }else if (mMatch.getParticipantId(mPlayer.getPlayerId()).equals("p_2")){
            Gdx.app.log("-----> WriteBoard()", "Writing for real");
            mTurnData = mTurnData.unpersist(data[1].getBytes(), str);
        }
            Gdx.app.log("retrieveData in WritingBoard", "retrieveData[0]: " + data[0] + "retrieveData[1]: " + data[1]);
            Gdx.app.log("WriteBoard", "Writing for real");
            mTurnData.persist();
    }


    @Override
    public void writeData(byte[] str) {
        Gdx.app.log("-----> writeData()", "player_ID: " + mMatch.getParticipantId(mPlayer.getPlayerId()) + " is writing data");
        if (mMatch.getParticipantId(mPlayer.getPlayerId()).equals("p_1")) {
            mTurnData = mTurnData.unpersist(retrieveData()[0].getBytes(),str);
        }else if (mMatch.getParticipantId(mPlayer.getPlayerId()).equals("p_2")){
            Gdx.app.log("WriteData", "Writing for real");
            mTurnData = mTurnData.unpersist(str, retrieveData()[0].getBytes());
        }
        //mTurnData = mTurnData.unpersist(str);
        //onInitiateMatch(mMatch);
        try{
            Gdx.app.log("-----> writeData()",new String((String) mTurnData.data.get("p_1")));
        }catch (JSONException e) {}

    }
    @Override
    public String[] retrieveData(){
        String[] returnData = new String[2];
        try {
            if(mTurnData == null){
                returnData[0] = null;
                returnData[1] = null;
                return returnData;
            }
            if (mMatch.getParticipantId(mPlayer.getPlayerId()).equals("p_1")) {
                returnData[0] = mTurnData.data.get("p_1").toString();
                returnData[1] = mTurnData.data.get("p_2").toString();
            }
            if (mMatch.getParticipantId(mPlayer.getPlayerId()).equals("p_2")) {
                returnData[0] = mTurnData.data.get("p_2").toString();
                returnData[1] = mTurnData.data.get("p_1").toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnData;
    }
    @Override
    public String getmDisplayName(){
        return mPlayer.getDisplayName();
    }

    public void onUpdateMatch(TurnBasedMatch match) {
        if (match.canRematch()) {
            askForRematch();
        }
        //isDoingTurn = (match.getTurnStatus() == TurnBasedMatch.MATCH_TURN_STATUS_MY_TURN);
        Gdx.app.log("------> onUpdateMatch()" ,"your turn: "+isDoingTurn);
        mMatch = match;
        if (isDoingTurn) {
            updateMatch();
            return;
        }
    }


    // If you choose to rematch, then call it and wait for a response.
    public void rematch() {
        //showSpinner();
        mTurnBasedMultiplayerClient.rematch(mMatch.getMatchId())
                .addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
                    @Override
                    public void onSuccess(TurnBasedMatch turnBasedMatch) {
                        onInitiateMatch(turnBasedMatch);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Gdx.app.log("------> rematch()","There was a problem starting a rematch!");
                    }
                });
        mMatch = null;
        isDoingTurn = false;
    }

    /**
     * Get the next participant. In this function, we assume that we are
     * round-robin, with all known players going before all automatch players.
     * This is not a requirement; players can go in any order. However, you can
     * take turns in any order.
     *
     * @return participantId of next player, or null if automatching
     */
    public String getNextParticipantId() {

        String myParticipantId = mMatch.getParticipantId(mPlayer.getPlayerId());

        ArrayList<String> participantIds = mMatch.getParticipantIds();

        int desiredIndex = -1;

        for (int i = 0; i < participantIds.size(); i++) {
            if (participantIds.get(i).equals(myParticipantId)) {
                desiredIndex = i + 1;
            }
        }

        if (desiredIndex < participantIds.size()) {
            return participantIds.get(desiredIndex);
        }

        if (mMatch.getAvailableAutoMatchSlots() <= 0) {
            // You've run out of automatch slots, so we start over.
            return participantIds.get(0);
        } else {
            // You have not yet fully automatched, so null will find a new
            // person to play against.
            return null;
        }
    }


    // This is only called on success, so we should have a
    // valid match object. We're taking this opportunity to setup the
    // game, saving our initial state. Calling takeTurn() will
    // callback to OnTurnBasedMatchUpdated(), which will show the game
    // UI.
    public void startMatch() {
        if(mTurnData == null) {
            Gdx.app.log("startMatch", "We started the match");
            mTurnData = new SkeletonTurn();
            // Some basic turn data
            try {
                mTurnData.data.put("p_1", "........Q........Q........Q........Q........Q........Q........Q........Q");
                mTurnData.data.put("p_2", "........Q........Q........Q........Q........Q........Q........Q........Q");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        String myParticipantId = mMatch.getParticipantId(mPlayer.getPlayerId());
        //String myOpponent = mMatch.getParticipantIds().get(1);
        //Gdx.app.log("------> startMatch()", "myParticipantID: "+myParticipantId+ " my opponentID: "+myOpponent);

        mTurnBasedMultiplayerClient.takeTurn(mMatch.getMatchId(),
                mTurnData.persist(), myParticipantId)
                .addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
                    @Override
                    public void onSuccess(TurnBasedMatch turnBasedMatch) {
                        mMatch = turnBasedMatch;
                        updateMatch();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Gdx.app.log("------> startMatch()","There was a problem taking a turn!");
                    }
                });
    }


    // This is the main function that gets called when players choose a match
    // from the inbox, or else create a match and want to start it.
    public void updateMatch() {
        int status = mMatch.getStatus();
        int turnStatus = mMatch.getTurnStatus();
        Gdx.app.log("------> updateMatch()","status: "+status+" turnStatus: "+turnStatus);
        switch (status) {
            case TurnBasedMatch.MATCH_STATUS_CANCELED:
                Gdx.app.log("------> Canceled!", "This game was canceled!");
                return;
            case TurnBasedMatch.MATCH_STATUS_EXPIRED:
                Gdx.app.log("------> Expired!", "This game is expired.  So sad!");
                return;
            case TurnBasedMatch.MATCH_STATUS_AUTO_MATCHING:
                Gdx.app.log("------> Waiting for auto-match...",
                        "We're still waiting for an automatch partner.");
                return;
            case TurnBasedMatch.MATCH_STATUS_COMPLETE:
                if (turnStatus == TurnBasedMatch.MATCH_TURN_STATUS_COMPLETE) {
                    Gdx.app.log("------> Complete!",
                            "This game is over; someone finished it, and so did you!  " +
                                    "There is nothing to be done.");
                    break;
                }

                // Note that in this state, you must still call "Finish" yourself,
                // so we allow this to continue.
                Gdx.app.log("------> Complete!",
                        "This game is over; someone finished it!  You can only finish it now.");
        }

        // OK, it's active. Check on turn status.
        switch (turnStatus) {
            case TurnBasedMatch.MATCH_TURN_STATUS_MY_TURN:
                String[] gameData = retrieveData();
                Gdx.app.log("GameData:", "gameData[0]: " + gameData[0] + ", gameData[1]:" + gameData[1]);
                if(mMatch.getParticipantId(mPlayer.getPlayerId()).equals("p_1")) {
                    mTurnData = SkeletonTurn.unpersist(gameData[0].getBytes(), gameData[1].getBytes());
                }
                if(mMatch.getParticipantId(mPlayer.getPlayerId()).equals("p_2")) {
                    mTurnData = SkeletonTurn.unpersist(gameData[1].getBytes(), gameData[0].getBytes());
                }
                //Gdx.app.log("------> updateMatch()","?? opponent ID = "+mMatch.getDescriptionParticipant().getDisplayName());
                isDoingTurn = true;
                return;

            case TurnBasedMatch.MATCH_TURN_STATUS_THEIR_TURN:
                // Should return results.
                isDoingTurn = false;
                Gdx.app.log("------> Alas...", "It's not your turn.");
                break;

            case TurnBasedMatch.MATCH_TURN_STATUS_INVITED:
                Gdx.app.log("------> Good inititative!",
                        "Still waiting for invitations.\n\nBe patient!");
        }

        mTurnData = null;

    }

    // Rematch dialog
    public void askForRematch() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage("Do you want a rematch?");

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Sure, rematch!",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                rematch();
                            }
                        })
                .setNegativeButton("No.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

        alertDialogBuilder.show();
    }


    private TurnBasedMatchUpdateCallback mMatchUpdateCallback = new TurnBasedMatchUpdateCallback() {
        @Override
        public void onTurnBasedMatchReceived(@NonNull TurnBasedMatch turnBasedMatch) {
            Gdx.app.log("---------> TurnBasedMatchUpdateCallback: ",new String(turnBasedMatch.getData()));
            isDoingTurn = true;
            mTurnData = new SkeletonTurn();

            try {
                byte[] data = turnBasedMatch.getData();
                mTurnData.data.put("p_1", new String(Arrays.copyOfRange(data, 16, 88)));
                mTurnData.data.put("p_2", new String(Arrays.copyOfRange(data, 97, 169)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onUpdateMatch(turnBasedMatch);
            hasOpponent = true;
            Gdx.app.log("---------> TurnBasedMatchUpdateCallback", "opponent did a move!");
        }

        @Override
        public void onTurnBasedMatchRemoved(@NonNull String matchId) {
            Toast.makeText(MainActivity.this, "A match was removed.", Toast.LENGTH_SHORT).show();
        }
    };


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

}
