package com.jaskulski.app.UI.CalculatedSquaresUI;

import com.jaskulski.app.UI.UIParameters;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SingleCalculatedSquare extends JPanel {
    private JLabel lblIndex;
    private JLabel lblOrdX;
    private JLabel lblOrdY;
    private JLabel lblOrdH;

    private JLabel lblIndexText;
    private JLabel lblOrdXText;
    private JLabel lblOrdYText;
    private JLabel lblOrdHText;

    GridBagLayout GBL;
    GridBagConstraints GBC;

    public SingleCalculatedSquare(int index1, double x1, double y1, double h1){
        GBL = new GridBagLayout();
        GBC = new GridBagConstraints();
        this.setLayout(GBL);

        initiateSingleSquareLabels();

        lblIndexText.setText(String.valueOf(index1));
        lblOrdXText.setText(String.valueOf(x1));
        lblOrdYText.setText(String.valueOf(y1));
        lblOrdHText.setText(String.valueOf(h1));

        setSquareStyle();
    }

    private void initiateSingleSquareLabels(){
        lblIndex = new JLabel("Numer: ");
        lblOrdX = new JLabel("Rz. X: ");
        lblOrdY = new JLabel("Rz. Y: ");
        lblOrdH = new JLabel("Rz. pro: ");

        lblIndexText = new JLabel();
        lblOrdXText = new JLabel();
        lblOrdYText = new JLabel();
        lblOrdHText = new JLabel();

        GBC.anchor = GridBagConstraints.WEST;
    //    GBC.weightx = 1;

        GBC.gridx = 0;
        GBC.gridy = 0;
        this.add(lblIndex, GBC);

        GBC.gridy = 1;
        this.add(lblOrdX, GBC);

        GBC.gridy = 2;
        this.add(lblOrdY, GBC);

        GBC.gridy = 3;
        this.add(lblOrdH, GBC);

        GBC.gridx = 1;
        GBC.gridy = 0;
        this.add(lblIndexText,GBC);

        GBC.gridy = 1;
        this.add(lblOrdXText, GBC);

        GBC.gridy = 2;
        this.add(lblOrdYText, GBC);

        GBC.gridy = 3;
        this.add(lblOrdHText, GBC);
    }

    private void setSquareStyle(){
        /*int x = UIParameters.frameWidth/10;
        int y = UIParameters.frameHeight/10;
        Dimension dimPreferred = new Dimension(x, y);
        this.setPreferredSize(dimPreferred);
        Dimension dimMax = new Dimension(150, 150);
        this.setMaximumSize(dimMax);*/

        this.setBackground(Color.white);
        UIParameters.setFontToAll(this, UIParameters.fontTiny);
        this.setBorder(new LineBorder(Color.black));
    }
}
