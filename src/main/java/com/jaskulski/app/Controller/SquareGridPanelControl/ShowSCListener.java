package com.jaskulski.app.Controller.SquareGridPanelControl;

import com.jaskulski.app.Controller.StartingConditionsHandling.StartingConditionsSerializer;
import com.jaskulski.app.Data.StartingConditions;
import com.jaskulski.app.UI.SquareGridUI.ShowSCFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowSCListener implements ActionListener {

    public StartingConditions startingConditions;

    public void actionPerformed(ActionEvent actionEvent) {
        StartingConditionsSerializer startingConditionsSerializer = new StartingConditionsSerializer();
        startingConditions = startingConditionsSerializer.deserializeSCFromFixedPath();
        ShowSCFrame showSCFrame = new ShowSCFrame(startingConditions);
    }
}