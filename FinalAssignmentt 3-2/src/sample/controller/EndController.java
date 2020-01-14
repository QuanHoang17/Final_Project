package sample.controller;


import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import sample.model.Players;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

public class EndController implements Initializable {
    public Button quitButton;
    public VBox subFunctions;
    public Button playAgainButton;
    public Button backToGameButton;
    public Button backToMenuButton;
    public Label textMsg;

    private ResourceBundle bundle = ResourceBundle.getBundle("sample.view.lang", new Locale("en"));

    public GridPane scoreBoard;
    @FXML
    private AnchorPane endScene;

    private ArrayList <Players> listOfPlayer = new ArrayList<>();

    void setBundle(ResourceBundle bundle){
        this.bundle = bundle;
    }

    void setListOfPlayer(ArrayList<Players> listOfPlayer) {
        this.listOfPlayer = listOfPlayer;
    }

    //Hue: create a ranking board based on the number of players and their scores in descending order.
    private void initializeScoreBoard(){
        scoreBoard.setOpacity(1);
        scoreBoard.add (new Label(bundle.getString("rank")), 0, 0);
        scoreBoard.add (new Label(bundle.getString("player")), 1, 0);
        scoreBoard.add (new Label(bundle.getString("score")), 2, 0);

        listOfPlayer.sort(Comparator.comparing(Players::getScores).reversed());

        for (Players players : listOfPlayer) {
            scoreBoard.addColumn(0, new Label(Integer.toString(listOfPlayer.indexOf(players) + 1)));
            scoreBoard.addColumn(1, new Label(players.getName()));
            scoreBoard.addColumn(2, new Label(Integer.toString(players.getScores())));
        }

    }

    //Hue: set language on buttons before scene is completely loaded.
    private void setNodeOnLanguage() {
        textMsg.setText(bundle.getString("game_finished"));
        textMsg.setAlignment(Pos.CENTER);

        quitButton.setText(bundle.getString("quit"));
        quitButton.setAlignment(Pos.CENTER);

        playAgainButton.setText(bundle.getString("play_again"));
        playAgainButton.setAlignment(Pos.CENTER);

        //back to game button
        backToMenuButton.setText(bundle.getString("back_to_menu"));
        backToMenuButton.setAlignment(Pos.CENTER);

        backToGameButton.setText(bundle.getString("back_to_game"));
        backToGameButton.setAlignment(Pos.CENTER);
    }



}
