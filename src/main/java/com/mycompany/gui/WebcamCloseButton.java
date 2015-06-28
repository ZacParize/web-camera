package com.mycompany.gui;

import com.mycompany.webcam.Webcam;

import javax.swing.*;
import java.awt.event.ActionListener;

public class WebcamCloseButton extends JButton{

    private static final long serialVersionUID = 1L;

    public WebcamCloseButton(String caption,String hint,Webcam webcam) {
        super(caption);
        this.setToolTipText(hint);
        ActionListener listener = new WebcamCloseListener(webcam);
        this.addActionListener(listener);
    }

}
