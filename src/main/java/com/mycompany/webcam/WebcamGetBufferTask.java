package com.mycompany.webcam;

import com.mycompany.webcam.WebcamDevice.BufferAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;


public class WebcamGetBufferTask extends WebcamTask {

	private static final Logger LOG = LoggerFactory.getLogger(WebcamGetBufferTask.class);

	private volatile ByteBuffer buffer = null;

	public WebcamGetBufferTask(WebcamDriver driver, WebcamDevice device) {
		super(driver, device);
	}

	public ByteBuffer getBuffer() {
		try {
			process();
		} catch (InterruptedException e) {
			LOG.debug("Image buffer request interrupted", e);
			return null;
		}
		return buffer;
	}

	@Override
	protected void handle() {

		WebcamDevice device = getDevice();
		if (!device.isOpen()) {
			return;
		}

		if (!(device instanceof BufferAccess)) {
			return;
		}

		buffer = ((BufferAccess) device).getImageBytes();
	}
}
