package sample.controller;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.model.Dice;
import sample.model.Horse;
import sample.model.Players;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML private AnchorPane gameScene;

    @FXML private Button quit;
    @FXML private Button stopButton;
    @FXML private Button rollDices;

    @FXML public Label currentTurn;
    @FXML public Label nextTurn;
    @FXML private Label player1_name;
    @FXML private Label player2_name;
    @FXML private Label player3_name;
    @FXML private Label player4_name;

    @FXML private ImageView di1;
    @FXML private ImageView di2;
    @FXML public ImageView player1_1;
    @FXML public ImageView player1_2;
    @FXML public ImageView player1_3;
    @FXML public ImageView player1_4;
    @FXML public ImageView player2_1;
    @FXML public ImageView player2_2;
    @FXML public ImageView player2_3;
    @FXML public ImageView player2_4;
    @FXML public ImageView player3_1;
    @FXML public ImageView player3_2;
    @FXML public ImageView player3_3;
    @FXML public ImageView player3_4;
    @FXML public ImageView player4_1;
    @FXML public ImageView player4_2;
    @FXML public ImageView player4_3;
    @FXML public ImageView player4_4;
    @FXML public ImageView predict;

    private final byte MOVE1 = 43;
    private final double MOVE2 = 64.5;
    private final byte MOVE3 = 36;

    private ResourceBundle bundle;
    private ArrayList <Players> listOfPlayers = new ArrayList<>();
    private ArrayList<Horse> listOfHorses = new ArrayList<>();
    private ArrayList<ImageView> activeHorses = new ArrayList<>();
    private ArrayList<Label> listOfScores = new ArrayList<>();

    private byte player_order = -1;
    private byte internal_turn = 0;
    private Dice dice1 = new Dice();
    private Dice dice2 = new Dice();
    private boolean has_kick = false;
    private boolean is_dice_match = false;
    private int selected_dice_value = 0;
    private byte current_dice = 0;

    // Arraylist methods
    private byte searchHorseIndex(ImageView image_id) {
        for (byte i = 0; i < listOfHorses.size(); i++) {
            if (listOfHorses.get(i).getImage_id() == image_id) {
                return i;
            }
        }
        return -1;
    }

    private byte searchPlayerIndex(int player_id) {
        for (byte i = 0; i < listOfPlayers.size(); i++) {
            if (listOfPlayers.get(i).getId() == player_id) {
                return i;
            }
        }
        return -1;
    }

    // Initialization
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameScene.setOpacity(0);
        sceneFadeIn();
    }

    private void sceneFadeIn() {
        FadeTransition sceneFadeIn = new FadeTransition();
        sceneFadeIn.setFromValue(0);
        sceneFadeIn.setToValue(1);
        sceneFadeIn.setDuration(Duration.seconds(1));
        sceneFadeIn.setDelay(Duration.seconds(1));
        sceneFadeIn.setNode(gameScene);
        sceneFadeIn.setOnFinished(e-> {
            setNodeLanguage();
            for (Players player : listOfPlayers) {
                if (player.getId() == 1) {
                    player1_name.setText(player.getName());
                    listOfHorses.add(new Horse(player1_1, 1, player1_1.getLayoutX(), player1_1.getLayoutY(), 215, 41, 279.5, 41));
                    listOfHorses.add(new Horse(player1_2, 1, player1_2.getLayoutX(), player1_2.getLayoutY(), 215, 41, 279.5, 41));
                    listOfHorses.add(new Horse(player1_3, 1, player1_3.getLayoutX(), player1_3.getLayoutY(), 215, 41, 279.5, 41));
                    listOfHorses.add(new Horse(player1_4, 1, player1_4.getLayoutX(), player1_4.getLayoutY(), 215, 41, 279.5, 41));
                } else if (player.getId() == 2) {
                    player2_name.setText(player.getName());
                    listOfHorses.add(new Horse(player2_1, 2, player2_1.getLayoutX(), player2_1.getLayoutY(), 559, 256, 559, 320.5));
                    listOfHorses.add(new Horse(player2_2, 2, player2_2.getLayoutX(), player2_2.getLayoutY(), 559, 256, 559, 320.5));
                    listOfHorses.add(new Horse(player2_3, 2, player2_3.getLayoutX(), player2_3.getLayoutY(), 559, 256, 559, 320.5));
                    listOfHorses.add(new Horse(player2_4, 2, player2_4.getLayoutX(), player2_4.getLayoutY(), 559, 256, 559, 320.5));
                } else if (player.getId() == 3) {
                    player3_name.setText(player.getName());
                    listOfHorses.add(new Horse(player3_1, 3, player3_1.getLayoutX(), player3_1.getLayoutY(), 0, 385, 0, 320.5));
                    listOfHorses.add(new Horse(player3_2, 3, player3_2.getLayoutX(), player3_2.getLayoutY(), 0, 385, 0, 320.5));
                    listOfHorses.add(new Horse(player3_3, 3, player3_3.getLayoutX(), player3_3.getLayoutY(), 0, 385, 0, 320.5));
                    listOfHorses.add(new Horse(player3_4, 3, player3_4.getLayoutX(), player3_4.getLayoutY(), 0, 385, 0, 320.5));
                } else {
                    player4_name.setText(player.getName());
                    listOfHorses.add(new Horse(player4_1, 4, player4_1.getLayoutX(), player4_1.getLayoutY(), 344, 600, 279.5, 600));
                    listOfHorses.add(new Horse(player4_2, 4, player4_2.getLayoutX(), player4_2.getLayoutY(), 344, 600, 279.5, 600));
                    listOfHorses.add(new Horse(player4_3, 4, player4_3.getLayoutX(), player4_3.getLayoutY(), 344, 600, 279.5, 600));
                    listOfHorses.add(new Horse(player4_4, 4, player4_4.getLayoutX(), player4_4.getLayoutY(), 344, 600, 279.5, 600));
                }
            }
            for (Horse horse : listOfHorses) {
                horse.getImage_id().setDisable(true);
                horse.getImage_id().setOpacity(1);
            }
        });
        sceneFadeIn.play();
    }

    void setBundle(ResourceBundle bundle){
        this.bundle = bundle;
    }

    void setListOfPlayers(ArrayList<Players> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    private void setNodeLanguage() {
        rollDices.setText(bundle.getString("roll"));
        quit.setText(bundle.getString("quit"));
        stopButton.setText(bundle.getString("stop"));
        currentTurn.setText(bundle.getString("current_turn"));
        nextTurn.setText(bundle.getString("next_turn") + listOfPlayers.get(player_order + 1).getName());
    }

    // Dice control
    public void randomDices(ActionEvent event) {
        di1.setOpacity(1);
        di2.setOpacity(1);
        rollDices.setDisable(false);
        di1.setImage(new Image("file:src/sample/view/Facedice/" + dice1.roll() + ".png"));
        di2.setImage(new Image("file:src/sample/view/Facedice/" + dice2.roll() + ".png"));
        // Determining player order
        if (player_order == listOfPlayers.size() - 1 && !is_dice_match) {
            player_order = 0;
        } else if (!is_dice_match) {
            player_order++;
        }
        internal_turn = 0;
        is_dice_match = dice1.getValue() == dice2.getValue();

        // Displaying player turn
        if (player_order == listOfPlayers.size() - 1 && !is_dice_match) {
            nextTurn.setText(bundle.getString("next_turn") + listOfPlayers.get(0).getName());
        } else if (!is_dice_match) {
            nextTurn.setText(bundle.getString("next_turn") + listOfPlayers.get(player_order + 1).getName());
        }
        currentTurn.setText(bundle.getString("current_turn") + listOfPlayers.get(player_order).getName());

        // Player turn and dice rolling control
        for (Horse horse : listOfHorses) {
            if (horse.getPlayer_id() != listOfPlayers.get(player_order).getId()) {
                horse.getImage_id().setDisable(true);
            } else {
                horse.getImage_id().setDisable(false);
                if (predictMovement(horse, dice1.getValue()) || predictMovement(horse, dice2.getValue())) {
                    rollDices.setDisable(true);
                }
            }
        }

        rollDices.setDisable(true);
    }

    private void setDiceValueEqual () {
        if (selected_dice_value == dice1.getValue()) {
            dice1.setValue(dice2.getValue());
        } else {
            dice2.setValue(dice1.getValue());
        }
    }

    // Game control

    @FXML void exitGame(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML void stopGame(ActionEvent actionEvent) {
        fadeOutTransition();
    }

    // Scene transition
    private void fadeOutTransition() {
        FadeTransition gameSceneFadeOut = new FadeTransition();
        gameSceneFadeOut.setNode(gameScene);
        gameSceneFadeOut.setDuration(Duration.seconds(1));
        gameSceneFadeOut.setFromValue(1);
        gameSceneFadeOut.setToValue(0);
        gameSceneFadeOut.setOnFinished((ActionEvent e) ->{
            try {
                toEndScene();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        gameSceneFadeOut.play();
    }

    private void toEndScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/EndScene.fxml"));
        Parent endScene = (AnchorPane) loader.load();

        EndController endController = loader.getController();
        endController.setBundle(bundle);
        endController.setListOfPlayer(listOfPlayers);

        Scene nextScene = new Scene(endScene);

        Stage nextStage = (Stage) gameScene.getScene().getWindow();
        nextStage.setScene(nextScene);
        nextStage.show();
    }

    // Movement control
    private void movementDecision(Horse horse) {
        if (horse.getImage_id().getLayoutX() == 215 && horse.getImage_id().getLayoutY() == 41) {
            horse.setMoveX(0);
            horse.setMoveY(MOVE1);
        } else if (horse.getImage_id().getLayoutX() == 215 && horse.getImage_id().getLayoutY() == 256) {
            horse.setMoveX(-1 * MOVE1);
            horse.setMoveY(0);
        } else if (horse.getImage_id().getLayoutX() == 0 && horse.getImage_id().getLayoutY() == 256) {
            horse.setMoveX(0);
            horse.setMoveY(MOVE2);
        } else if (horse.getImage_id().getLayoutX() == 0 && horse.getImage_id().getLayoutY() == 385) {
            horse.setMoveX(MOVE1);
            horse.setMoveY(0);
        } else if (horse.getImage_id().getLayoutX() == 215 && horse.getImage_id().getLayoutY() == 385) {
            horse.setMoveX(0);
            horse.setMoveY(MOVE1);
        } else if (horse.getImage_id().getLayoutX() == 215 && horse.getImage_id().getLayoutY() == 600) {
            horse.setMoveX(MOVE2);
            horse.setMoveY(0);
        } else if (horse.getImage_id().getLayoutX() == 344 && horse.getImage_id().getLayoutY() == 600) {
            horse.setMoveX(0);
            horse.setMoveY(-1 * MOVE1);
        } else if (horse.getImage_id().getLayoutX() == 344 && horse.getImage_id().getLayoutY() == 385) {
            horse.setMoveX(MOVE1);
            horse.setMoveY(0);
        } else if (horse.getImage_id().getLayoutX() == 559 && horse.getImage_id().getLayoutY() == 385) {
            horse.setMoveX(0);
            horse.setMoveY(-1 * MOVE2);
        } else if (horse.getImage_id().getLayoutX() == 559 && horse.getImage_id().getLayoutY() == 256) {
            horse.setMoveX(-1 * MOVE1);
            horse.setMoveY(0);
        } else if (horse.getImage_id().getLayoutX() == 344 && horse.getImage_id().getLayoutY() == 256) {
            horse.setMoveX(0);
            horse.setMoveY(-1 * MOVE1);
        } else if (horse.getImage_id().getLayoutX() == 344 && horse.getImage_id().getLayoutY() == 41) {
            horse.setMoveX(-1 * MOVE2);
            horse.setMoveY(0);
        } else if (horse.getImage_id().getLayoutX() == horse.getHomeX() && horse.getImage_id().getLayoutY() == horse.getHomeY()) {
            if (horse.getPlayer_id() == 1) {
                horse.setMoveX(0);
                horse.setMoveY(MOVE3);
            } else if (horse.getPlayer_id() == 2) {
                horse.setMoveX(-1 * MOVE3);
                horse.setMoveY(0);
            } else if (horse.getPlayer_id() == 3) {
                horse.setMoveX(MOVE3);
                horse.setMoveY(0);
            } else if (horse.getPlayer_id() == 4) {
                horse.setMoveX(0);
                horse.setMoveY(-1 * MOVE3);
            }
            horse.setHome_value(selected_dice_value);
        }
    }

    private boolean predictMovement (Horse currenthorse, int dice_value) {
        boolean predict_move = false;
        predict.setLayoutX(currenthorse.getImage_id().getLayoutX());
        predict.setLayoutY(currenthorse.getImage_id().getLayoutY());
        if (dice_value == 6 && currenthorse.getImage_id().getLayoutX() == currenthorse.getInitialX() && currenthorse.getImage_id().getLayoutY() == currenthorse.getInitialY()) {
            boolean can_out = true;
            for (ImageView activehorse : activeHorses) {
                if (activehorse.getLayoutX() == currenthorse.getOutX() && activehorse.getLayoutY() == currenthorse.getOutY()) {
                    if (activehorse.getId().charAt(6) == currenthorse.getImage_id().getId().charAt(6)) {
                        can_out = false;
                    }
                    break;
                }
            }
            if (can_out) {
                predict_move = true;
            }
        } else if (dice_value == currenthorse.getHome_value() + 1 && currenthorse.getHome_value() != 0) {
            boolean can_move = true;
            for (ImageView activehorse : activeHorses) {
                if (currenthorse.getImage_id() != activehorse && currenthorse.getImage_id().getId().charAt(6) == activehorse.getId().charAt(6)) {
                    byte temp_index = searchHorseIndex(activehorse);
                    if (currenthorse.getHome_value() + 1 == listOfHorses.get(temp_index).getHome_value()) {
                        can_move = false;
                    }
                }
            }
            if (can_move) {
                predict_move = true;
            }
        } else if (currenthorse.getImage_id().getLayoutX() != currenthorse.getInitialX() && currenthorse.getImage_id().getLayoutY() != currenthorse.getInitialY() && currenthorse.getHome_value() == 0) {
            Horse horse = new Horse();
            horse.setImage_id(predict);
            horse.setMoveX(currenthorse.getMoveX());
            horse.setMoveY(currenthorse.getMoveY());
            horse.setHomeX(currenthorse.getHomeX());
            horse.setHomeY(currenthorse.getHomeY());
            horse.setPlayer_id(currenthorse.getPlayer_id());
            one: for (current_dice = 1; current_dice <= dice_value; current_dice++) {
                // Movement direction decision
                movementDecision(horse);
                // Start moving
                horse.getImage_id().setLayoutX(horse.getImage_id().getLayoutX() + horse.getMoveX());
                horse.getImage_id().setLayoutY(horse.getImage_id().getLayoutY() + horse.getMoveY());
                // Home node collision
                if (current_dice < dice_value && horse.getImage_id().getLayoutX() == currenthorse.getHomeX() && horse.getImage_id().getLayoutY() == currenthorse.getHomeY()) {
                    predict_move = false;
                    break;
                }
                // Horse collision
                for (ImageView activehorse : activeHorses) {
                    if (!currenthorse.getImage_id().getId().equals(activehorse.getId())) {
                        if (horse.getImage_id().getLayoutX() == activehorse.getLayoutX() && horse.getImage_id().getLayoutY() == activehorse.getLayoutY()) {
                            if (current_dice < dice_value || horse.getPlayer_id() == activehorse.getId().charAt(6) - 48) {
                                predict_move = false;
                                break one;
                            } else {
                                predict_move = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (current_dice - 1 == dice_value) {
                predict_move = true;
            }
        }
        current_dice = 0;
        predict.setLayoutX(0);
        predict.setLayoutY(0);
        return predict_move;
    }

    private void disableHorses() {
        for (Horse horse : listOfHorses) {
            horse.getImage_id().setDisable(true);
        }
    }

    private void enableHorses() {
        for (Horse horse : listOfHorses) {
            if (horse.getPlayer_id() == listOfPlayers.get(player_order).getId()) {
                horse.getImage_id().setDisable(false);
            }
        }
    }

    private void kickAction(int kicker_index, int kick_index) {
        listOfHorses.get(kick_index).getImage_id().setLayoutX(listOfHorses.get(kick_index).getInitialX());
        listOfHorses.get(kick_index).getImage_id().setLayoutY(listOfHorses.get(kick_index).getInitialY());
        listOfPlayers.get(searchPlayerIndex(listOfHorses.get(kick_index).getPlayer_id())).addScores(-2);
        listOfPlayers.get(searchPlayerIndex(listOfHorses.get(kicker_index).getPlayer_id())).addScores(2);
    }

    private void moveOut(Horse horse) {
        boolean can_out = true;
        for (ImageView activehorse : activeHorses) {
            // Checking for collision
            if (activehorse.getLayoutX() == horse.getOutX() && activehorse.getLayoutY() == horse.getOutY()) {
                if (activehorse.getId().charAt(6) == horse.getImage_id().getId().charAt(6)) {
                    can_out = false;
                } else {
                    byte temp_index = searchHorseIndex(activehorse);
                    activeHorses.remove(activehorse);
                    kickAction(listOfHorses.indexOf(horse), temp_index);
                }
                break;
            }
        }
        if (can_out) {
            horse.getImage_id().setLayoutX(horse.getOutX());
            horse.getImage_id().setLayoutY(horse.getOutY());
            horse.getImage_id().setDisable(true);
            activeHorses.add(horse.getImage_id());
            internal_turn++;
            setDiceValueEqual();
        }
    }

    private void moveHome(Horse horse) {
        boolean can_move = true;
        for (ImageView activehorse : activeHorses) {
            // Checking for collision
            if (horse.getImage_id() != activehorse && horse.getImage_id().getId().charAt(6) == activehorse.getId().charAt(6)) {
                byte temp_index = searchHorseIndex(activehorse);
                if (horse.getHome_value() + 1 == listOfHorses.get(temp_index).getHome_value()) {
                    can_move = false;
                }
            }
        }
        if (can_move) {
            horse.getImage_id().setLayoutX(horse.getImage_id().getLayoutX() + horse.getMoveX());
            horse.getImage_id().setLayoutY(horse.getImage_id().getLayoutY() + horse.getMoveY());
            horse.getImage_id().setDisable(true);
            internal_turn++;
            setDiceValueEqual();
            // Add score
            listOfPlayers.get(player_order).addScores(1);

            horse.setHome_value(selected_dice_value);
            if (horse.getHome_value() == listOfPlayers.get(player_order).getCurrent_max_home()) {
                listOfPlayers.get(player_order).setCurrent_max_home(listOfPlayers.get(player_order).getCurrent_max_home() - 1);
            }
        }
    }

    private void moveNormal (Horse horse) {
        disableHorses();
        boolean rollDice_status = rollDices.isDisable();
        rollDices.setDisable(true);
        has_kick = false;
        current_dice = 0;
        double tempX = horse.getImage_id().getLayoutX();
        double tempY = horse.getImage_id().getLayoutY();
        double tempmoveX = horse.getMoveX();
        double tempmoveY = horse.getMoveY();
        Timeline movement = new Timeline();
        movement.setCycleCount(selected_dice_value);
        EventHandler<ActionEvent> move = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                current_dice++;
                // Movement direction decision
                movementDecision(horse);
                // Start moving
                horse.getImage_id().setLayoutX(horse.getImage_id().getLayoutX() + horse.getMoveX());
                horse.getImage_id().setLayoutY(horse.getImage_id().getLayoutY() + horse.getMoveY());
                // Home node collision
                if (current_dice < selected_dice_value && horse.getImage_id().getLayoutX() == horse.getHomeX() && horse.getImage_id().getLayoutY() == horse.getHomeY()) {
                    horse.getImage_id().setLayoutX(tempX);
                    horse.getImage_id().setLayoutY(tempY);
                    horse.setMoveX(tempmoveX);
                    horse.setMoveY(tempmoveY);
                    current_dice = 0;
                    enableHorses();
                    if (!rollDice_status) {
                        rollDices.setDisable(false);
                    }
                    movement.stop();
                }
                // Horse collision
                for (ImageView activehorse : activeHorses) {
                    if (horse.getImage_id() != activehorse) {
                        if (horse.getImage_id().getLayoutX() == activehorse.getLayoutX() && horse.getImage_id().getLayoutY() == activehorse.getLayoutY()) {
                            if (current_dice < selected_dice_value || horse.getImage_id().getId().charAt(6) == activehorse.getId().charAt(6)) {
                                horse.getImage_id().setLayoutX(tempX);
                                horse.getImage_id().setLayoutY(tempY);
                                horse.setMoveX(tempmoveX);
                                horse.setMoveY(tempmoveY);
                                horse.setHome_value(0);
                                current_dice = 0;
                                enableHorses();
                                if (!rollDice_status) {
                                    rollDices.setDisable(false);
                                }
                                movement.stop();
                            } else {
                                has_kick = true;
                                byte temp_index = searchHorseIndex(activehorse);
                                kickAction(listOfHorses.indexOf(horse), temp_index);
                                activeHorses.remove(activehorse);
                                break;
                            }
                        }
                    }
                }
            }
        };
        movement.getKeyFrames().add(new KeyFrame(Duration.millis(500), move));
        movement.setOnFinished(e -> {
            current_dice = 0;
            internal_turn++;
            setDiceValueEqual();
            // Add score to the player when move from home node to home run position
            if (horse.getHome_value() != 0) {
                listOfPlayers.get(player_order).addScores(horse.getHome_value());
                if (horse.getHome_value() == listOfPlayers.get(player_order).getCurrent_max_home()) {
                    listOfPlayers.get(player_order).setCurrent_max_home(listOfPlayers.get(player_order).getCurrent_max_home() - 1);
                }
            }
            // End the game when there are 4 horse at home run position
            if (listOfPlayers.get(player_order).getCurrent_max_home() == 2) {
                fadeOutTransition();
            }
            // Re-enable the horse when there are still turn, disable current horse in some situation
            selected_dice_value = dice1.getValue();
            rollDices.setDisable(false);
            if (internal_turn != 2) {
                enableHorses();
                if (horse.getImage_id().getLayoutX() == horse.getHomeX() && horse.getImage_id().getLayoutY() == horse.getHomeY()) {
                    horse.getImage_id().setDisable(true);
                } else if (has_kick) {
                    horse.getImage_id().setDisable(true);
                } else if (horse.getHome_value() != 0) {
                    horse.getImage_id().setDisable(true);
                }
                for (Horse current_horse : listOfHorses) {
                    if (!current_horse.getImage_id().isDisabled() && predictMovement(current_horse, selected_dice_value)) {
                        rollDices.setDisable(true);
                    }
                }
            }
        });
        movement.playFromStart();
    }

    @FXML void move_horse (MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            selected_dice_value = dice1.getValue();
        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            selected_dice_value = dice2.getValue();
        }
        Horse horse = listOfHorses.get(searchHorseIndex((ImageView) mouseEvent.getSource()));
        if (selected_dice_value == 6 && horse.getImage_id().getLayoutX() == horse.getInitialX() && horse.getImage_id().getLayoutY() == horse.getInitialY()) {
            moveOut(horse);
            rollDices.setDisable(false);
            selected_dice_value = dice1.getValue();
            if (internal_turn == 2) {
                disableHorses();
            } else {
                for (Horse current_horse : listOfHorses) {
                    if (!current_horse.getImage_id().isDisabled() && predictMovement(current_horse, selected_dice_value)) {
                        rollDices.setDisable(true);
                    }
                }
            }
        } else if (selected_dice_value == horse.getHome_value() + 1 && horse.getHome_value() != 0) {
            moveHome(horse);
            // Checking if there are 4 horse at home node
            if (listOfPlayers.get(player_order).getCurrent_max_home() == 2) {
                fadeOutTransition();
            }
            rollDices.setDisable(false);
            // Get remaining dice value
            selected_dice_value = dice1.getValue();
            if (internal_turn == 2) {
                disableHorses();
            } else {
                for (Horse current_horse : listOfHorses) {
                    if (!current_horse.getImage_id().isDisabled() && predictMovement(current_horse, selected_dice_value)) {
                        rollDices.setDisable(true);
                    }
                }
            }
        } else if (horse.getImage_id().getLayoutX() != horse.getInitialX() && horse.getImage_id().getLayoutY() != horse.getInitialY() && horse.getHome_value() == 0) {
            moveNormal(horse);
        }
    }
}
