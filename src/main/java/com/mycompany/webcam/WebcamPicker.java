package com.mycompany.webcam;

import javax.swing.*;
import java.util.List;


public class WebcamPicker extends JComboBox {

	private static final long serialVersionUID = 1L;

	private static final WebcamPickerCellRenderer RENDERER = new WebcamPickerCellRenderer();

	public WebcamPicker() {
		this(Webcam.getWebcams());
	}

	public WebcamPicker(List<Webcam> webcams) {
		super(new WebcamPickerModel(webcams));
		setRenderer(RENDERER);
	}

	public Webcam getSelectedWebcam() {
		return (Webcam) getSelectedItem();
	}
}
