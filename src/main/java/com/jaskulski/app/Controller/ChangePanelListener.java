package com.jaskulski.app.Controller;

import com.jaskulski.app.UI.UILauncherFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePanelListener implements ActionListener {

    UILauncherFrame UILauncher;
    JPanel newPanel;

    public ChangePanelListener (UILauncherFrame UILauncher1, JPanel newPanel1){
        this.UILauncher = UILauncher1;
        this.newPanel = newPanel1;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        UILauncher.changePanel(newPanel);
    }
}