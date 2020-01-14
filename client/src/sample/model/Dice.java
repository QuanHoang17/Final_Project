package sample.model;

import javafx.scene.paint.Color;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Dice
{
    int MAX = 6;
    private int value;
    private ArrayList<Integer> list_roll = new ArrayList<>(){
        {
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
            add(6);
        }
    };

    public int roll() {
        value = (int)(Math.random() * MAX) + 1;
        return value;
    }

    public int roll_order() {
        Collections.shuffle(list_roll);
        int pick = list_roll.get(0);
        value = pick;
        list_roll.remove(0);
        return value;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int val){
        this.value = val;
    }

    public int roll_order(int value){
        list_roll.remove(Integer.valueOf(value));

        Collections.shuffle(list_roll);

        return list_roll.get(0);

    }
    public int roll_order_LAN(int value){
        list_roll.remove(Integer.valueOf(value));
        Collections.shuffle(list_roll);
        return list_roll.get(0);
    }

}