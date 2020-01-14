package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class BeginScene implements Initializable {


    public Button multiButton;
    public TextField nameHolder;
    public Button startButton;
    public AnchorPane lanScene;
    @FXML private ChoiceBox<String> language;
    private Socket socket;
    private DataOutputStream toServer;
    private DataInputStream fromServer;

    private ResourceBundle bundle = ResourceBundle.getBundle("sample.view.lang", new Locale("en"));

    @FXML
    private void toLanGame(ActionEvent actionEvent) throws IOException {
        //socket = new Socket("10.247.194.193",8000);
        socket = new Socket("10.247.194.193",8000);
        toServer = new DataOutputStream(socket.getOutputStream());
        fromServer = new DataInputStream(socket.getInputStream());
        multiButton.setOpacity(0);
        nameHolder.setOpacity(1);
        nameHolder.setPromptText(bundle.getString("Please_enter_name"));
    }

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
    private void setLanguageOnButton(){
        multiButton.setText(bundle.getString("multiplayer"));
        nameHolder.setPromptText(bundle.getString("Please_enter_name"));
        startButton.setText(bundle.getString("start"));
    }

    public void nameInput(ActionEvent actionEvent) throws IOException {
        String dataToSend = nameHolder.getText().trim();
        toServer.writeUTF(dataToSend);
        toServer.flush();
        int numOfPlayers = fromServer.readInt();
        System.out.println(numOfPlayers);
        boolean isStart = fromServer.readBoolean();
        if (isStart){
            startButton.setDisable(false);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLanguage();
        startButton.setDisable(true);
    }

    public void startGame(ActionEvent actionEvent) throws IOException {
        loadToNextScene();
    }
    private void loadToNextScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/LanPregame.fxml"));
        Parent gameScene =(AnchorPane) loader.load();

        LANPregame lanPregame = loader.getController();
        lanPregame.setGameLanguage(bundle);

        //lanPregame.setListOfPlayers(listOfPlayers);

        Scene nextScene = new Scene(gameScene);

        Stage nextStage = (Stage) lanScene.getScene().getWindow();
        nextStage.setScene(nextScene);
        nextStage.setTitle("HELLO GAMERS");
        nextStage.show();
    }
}






