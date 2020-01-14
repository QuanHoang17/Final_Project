package sample.model;

import javafx.scene.image.ImageView;

public class Horse  {
    private ImageView image_id;
    private double initialX;
    private double initialY;
    private double outX;
    private double outY;
    private double moveX;
    private double moveY;
    private double homeX;
    private double homeY;
    private int home_value = 0;
    private int player_id;

    public double getInitialX() {
        return initialX;
    }
    public double getInitialY() {
        return initialY;
    }
    public double getOutX() {
        return outX;
    }
    public double getOutY() {
        return outY;
    }
    public double getMoveX() {
        return moveX;
    }
    public double getMoveY() {
        return moveY;
    }
    public double getHomeX() { return homeX; }
    public double getHomeY() { return homeY; }
    public int getHome_value() { return home_value; }
    public int getPlayer_id() {
        return player_id;
    }
    public ImageView getImage_id() { return image_id; }

    public void setImage_id(ImageView image_id) { this.image_id = image_id; }
    public void setInitialX(double initialX) {
        this.initialX = initialX;
    }
    public void setInitialY(double initialY) {
        this.initialY = initialY;
    }
    public void setOutX(double outX) {
        this.outX = outX;
    }
    public void setOutY(double outY) {
        this.outY = outY;
    }
    public void setMoveX(double moveX) {
        this.moveX = moveX;
    }
    public void setMoveY(double moveY) {
        this.moveY = moveY;
    }
    public void setHomeX(double homeX) { this.homeX = homeX; }
    public void setHomeY(double homeY) { this.homeY = homeY; }
    public void setHome_value(int home_value) { this.home_value = home_value; }
    public void setPlayer_id(int id) {
        player_id = id;
    }

    public Horse(ImageView image_id, int id, double iniX, double iniY, double outX, double outY, double homeX, double homeY) {
        this.image_id = image_id;
        player_id = id;
        this.outX = outX;
        this.outY = outY;
        this.homeX = homeX;
        this.homeY = homeY;
        initialX = iniX;
        initialY = iniY;
    }

    public Horse() {}

}
