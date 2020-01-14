package sample.controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.scene.media.MediaPlayer;

import javafx.stage.Stage;
import javafx.util.Duration;
import sample.model.Players;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class BeginController {
    public AnchorPane beginScene;
    public MediaPlayer music;
    @FXML private TextField hostNameHolder;
    @FXML private Button soundToggle;
    @FXML private Button start;
    @FXML private Button quit;
    @FXML private ChoiceBox<String> language;
    @FXML private Label label;

    @FXML
    private TextField player1_name_input;
    @FXML
    private TextField player2_name_input;
    @FXML
    private TextField player3_name_input;
    @FXML
    private TextField player4_name_input;

    @FXML
    private Button multiplayer;

    private ArrayList<TextField> player_names = new ArrayList<>();

    //Hue: set default bundle in english
    private ResourceBundle bundle = ResourceBundle.getBundle("sample.view.lang", new Locale("en"));
    private ArrayList <Players> listOfPlayers = new ArrayList<>();
    private ArrayList <Players> oldPlayerList = new ArrayList<>();


    //Hue: Initialization
    private void sceneFadeIn() {
        FadeTransition sceneFadeIn = new FadeTransition();
        sceneFadeIn.setFromValue(0);
        sceneFadeIn.setToValue(1);
        sceneFadeIn.setDuration(Duration.seconds(1));
        sceneFadeIn.setDelay(Duration.seconds(1));
        sceneFadeIn.setNode(beginScene);
        sceneFadeIn.setOnFinished(e->{
            setPlayer();
            setLanguage();
        });
        sceneFadeIn.play();
    }

    //Hue
    public void initialize() throws IOException {
        music.setCycleCount(MediaPlayer.INDEFINITE);
        beginScene.setOpacity(0);
        sceneFadeIn();
    }

    //Hue
    private void setPlayer() {
        player1_name_input.setPromptText(bundle.getString("player1"));
        player2_name_input.setPromptText(bundle.getString("player2"));
        player3_name_input.setPromptText(bundle.getString("player3"));
        player4_name_input.setPromptText(bundle.getString("player4"));
        hostNameHolder.setPromptText(bundle.getString("host_name"));

        player_names.add(player1_name_input);
        player_names.add(player2_name_input);
        player_names.add(player3_name_input);
        player_names.add(player4_name_input);

        if (!oldPlayerList.isEmpty()) {
            for (byte i = 0; i < 4; i++) {
                if (searchIndex(i + 1) != -1) {
                    player_names.get(i).setText(oldPlayerList.get(searchIndex(i + 1)).getName());
                }
            }
        }
    }

    //Hue
    private void setLanguage() {
        //setLanguageOnButton();
        language.getSelectionModel().selectedItemProperty().addListener((observable, value, newValue) -> {
            if (newValue.equals("Vietnamese")){
                Locale locale = new Locale("vi");
                bundle = ResourceBundle.getBundle("sample.view.lang", locale);
                setLanguageOnButton();
            } else {
                bundle = ResourceBundle.getBundle("sample.view.lang", new Locale("en"));
                setLanguageOnButton();
            }
        });
    }

    //Hue
    private void setLanguageOnButton() {
        start.setText(bundle.getString("start"));
        quit.setText(bundle.getString("quit"));
        soundToggle.setText(bundle.getString("sound_on"));
        label.setText(bundle.getString("label"));
        player1_name_input.setPromptText(bundle.getString("player1"));
        player2_name_input.setPromptText(bundle.getString("player2"));
        player3_name_input.setPromptText(bundle.getString("player3"));
        player4_name_input.setPromptText(bundle.getString("player4"));
        hostNameHolder.setPromptText(bundle.getString("host_name"));
        multiplayer.setText(bundle.getString("multiplayer"));
    }

    //Hue: Button control
    @FXML void toggleSound(ActionEvent event) {
        if (music.getStatus()== MediaPlayer.Status.PAUSED) {
            soundToggle.setText(bundle.getString("sound_on"));
            music.play();
        } else if (music.getStatus() == MediaPlayer.Status.PLAYING) {
            soundToggle.setText(bundle.getString("sound_off"));
            music.pause();
        }
    }

    //Hue
    @FXML void exitApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }


}