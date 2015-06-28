package com.mycompany.webcam;

public interface WebcamDiscoveryListener {

	void webcamFound(WebcamDiscoveryEvent event);

	void webcamGone(WebcamDiscoveryEvent event);

}
