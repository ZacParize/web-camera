package com.mycompany.gui;

import com.mycompany.format.Picture;
import com.mycompany.webcam.Webcam;

import javax.swing.*;
import java.awt.event.ActionListener;

public class WebcamTakePictureButton extends JButton {

    private static final long serialVersionUID = 1L;

    public WebcamTakePictureButton(String caption,String hint,Webcam webcam,Picture picture) {
        super(caption);
        this.setToolTipText(hint);
        ActionListener listener = new WebcamTakePictureListener(webcam,picture);
        this.addActionListener(listener);
    }

}
