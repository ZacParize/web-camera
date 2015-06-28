package com.mycompany.gui;

import com.mycompany.webcam.Webcam;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WebcamCloseListener implements ActionListener {

    private static final long serialVersionUID = 1L;

    private Webcam webcam;

    public Webcam getWebcam() {
        return webcam;
    }

    public void setWebcam(Webcam webcam) {
        this.webcam = webcam;
    }

    public WebcamCloseListener(Webcam webcam) {
        this.setWebcam(webcam);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.webcam != null) webcam.close();
        System.exit(0);
    }

}
