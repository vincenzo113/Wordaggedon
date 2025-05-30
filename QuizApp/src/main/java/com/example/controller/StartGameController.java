package com.example.controller;


import javafx.scene.control.Label;

public class StartGameController {


    public static void aggiornaLabel(Label label, String username){
        String [] fields = label.getText().split("[,]");
        fields[1] = username ;
        label.setText(String.join(", ",fields));
    }

}
