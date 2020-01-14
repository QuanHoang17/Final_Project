package sample.controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;
import javafx.util.Duration;
import sample.model.Players;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LANController implements Initializable {
    public Label textLabel;
    public AnchorPane lanScene;
    public Button startButton;
    public GridPane listHostNClients;
    private ArrayList <Players> listOfPlayers = new ArrayList<>();
    private InetAddress ip;
    private ServerSocket sever = new ServerSocket(8000);
    private boolean isButtonClick = false;
    private int numOfClients = 0;
    private ResourceBundle bundle;

    public LANController() throws IOException {
        new Thread(() ->{

            while (true){
                try {
                    Socket socket = sever.accept();
                    numOfClients++;

                    Platform.runLater(() ->{
                        textLabel.setText(numOfClients+ " " + bundle.getString("player_joined"));
                    });

                    new Thread(new playerThread(socket)).start();


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    void setGameLanguage(ResourceBundle bundle){

        this.bundle = bundle;
    }

    void setListOfPlayers(ArrayList<Players> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    private void loadToNextScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/LANPregame.fxml"));
        Parent gameScene =(AnchorPane) loader.load();

        LANPregame lanPregame = loader.getController();

        lanPregame.setListOfPlayers(listOfPlayers);

//        lanPregame.setGameLanguage(bundle);
        Scene nextScene = new Scene(gameScene);

        Stage nextStage = (Stage) lanScene.getScene().getWindow();
        nextStage.setScene(nextScene);
        nextStage.setTitle("HELLO GAMERS");
        nextStage.show();
    }

    public void startGame(ActionEvent actionEvent) throws IOException {
        isButtonClick = true;
        loadToNextScene();

    }

    private void fadeInTransition(){
        FadeTransition sceneFadeIn = new FadeTransition();
        sceneFadeIn.setFromValue(0);
        sceneFadeIn.setToValue(1);
        sceneFadeIn.setDuration(Duration.seconds(1));
        sceneFadeIn.setDelay(Duration.seconds(1));
        sceneFadeIn.setNode(lanScene);
        sceneFadeIn.setOnFinished(e->{

            initializeComponents();

        });
        sceneFadeIn.play();

    }

    private void initializeComponents(){
        startButton.setDisable(true);

        listHostNClients.add(new Label(bundle.getString("host")), 0, 0);

        listHostNClients.add(new Label (listOfPlayers.get(0).getName()), 1, 0);

        textLabel.setText(bundle.getString("waiting"));
        startButton.setText(bundle.getString("start"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lanScene.setOpacity(0);
        fadeInTransition();

    }

    class playerThread implements Runnable{
        Socket socket;
        playerThread(Socket socket){
            this.socket = socket;
        }
        public void run(){
            try {
                DataInputStream fromClients = new DataInputStream(socket.getInputStream());
                DataOutputStream toClients = new DataOutputStream(socket.getOutputStream());

                while (true){
                    toClients.writeInt(numOfClients);

                    String dataReceived = fromClients.readUTF();

                    Platform.runLater(()->{
                        listHostNClients.addColumn(0, new Label(bundle.getString("guest")));
                        listHostNClients.addColumn(1, new Label(dataReceived));
                    });
                    InetAddress inetAddress = socket.getInetAddress();
                    addPlayers(listOfPlayers.size() + 1, dataReceived, inetAddress.getHostAddress());

                    //toClients.writeBoolean(true);
                    System.out.println(listOfPlayers.size());
                    if (listOfPlayers.size() >= 2 && listOfPlayers.size() <= 4 ){

                        startButton.setDisable(false);
                        toClients.writeBoolean(true);

                    }




                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void addPlayers(int id, String name, String ip){
            listOfPlayers.add(new Players(id, name, ip));
        }


    }
}
