package com.mycompany.gui;

import com.mycompany.format.Picture;
import com.mycompany.format.PictureInfo;
import com.mycompany.webcam.Webcam;

import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by pdv on 02.03.2015.
 */

public class WebcamTakePictureListener implements ActionListener {

    private static final long serialVersionUID = 1L;

    private Webcam webcam;

    private Picture picture;

    public Webcam getWebcam() {
        return webcam;
    }

    public void setWebcam(Webcam webcam) {
        this.webcam = webcam;
    }

    public PictureInfo getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public WebcamTakePictureListener(Webcam webcam,Picture picture) {
        this.setWebcam(webcam);
        this.setPicture(picture);
    }

    public void actionPerformed(ActionEvent e) {
        if (this.webcam != null) {
            try {
                String name = String.format(this.getPicture().getFile().getName(),this.getPicture().getFormat(),System.currentTimeMillis());
                ImageIO.write(webcam.getImage(),this.getPicture().getFormat(), new File(name));
            } catch (IOException t) {
                t.printStackTrace();
            }
        }
    }

}
