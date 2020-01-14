package sample.model;

import java.io.Serializable;

public class Players implements Serializable {
    private int id = 0;
    private int scores = 0;
    private String name = "";
    private int order = -1;
    private int current_max_home = 6;

    public String getIpAddress() {
        return ipAddress;
    }

    private String ipAddress;

    public int getId() {
        return id;
    }
    public int getScores() {
        return scores;
    }
    public String getName() {
        return name;
    }
    public int getOrder() {
        return order;
    }
    public int getCurrent_max_home() {
        return current_max_home;
    }
    public void setScores(int scores) {
        this.scores = scores;
    }
    public void setCurrent_max_home(int current_max_home) {
        this.current_max_home = current_max_home;
    }
    public void setOrder(int order){
        this.order = order;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void addScores(int scores) {
        this.scores = this.scores + scores;
    }

    public Players(int id){
        this.id = id;
    }
    public Players(int id, String name){
        this.id = id;
        this.name = name;
    }
    public Players(int id, String name, String ipAddress){
        this.id = id;
        this.name = name;
        this.ipAddress = ipAddress;
    }

}
