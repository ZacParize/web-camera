package com.mycompany.webcam;

/**
 * Dispose webcam device.
 */
public class WebcamDisposeTask extends WebcamTask {

	public WebcamDisposeTask(WebcamDriver driver, WebcamDevice device) {
		super(driver, device);
	}

	public void dispose() throws InterruptedException {
		process();
	}

	@Override
	protected void handle() {
		getDevice().dispose();
	}
}
