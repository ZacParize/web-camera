package com.mycompany;

/*public class App {

      public static void main(String[] args) throws IOException {

          // get default webcam and open it
          Webcam webcam = Webcam.getDefault();
          webcam.setViewSize(new Dimension(640,480));
          webcam.open();

          // get image
          BufferedImage image = webcam.getImage();

          // save image to PNG file
          ImageIO.write(image, "PNG", new File("test.png"));
      }

}*/

import com.mycompany.format.Picture;
import com.mycompany.gui.WebcamCloseButton;
import com.mycompany.gui.WebcamTakePictureButton;
import com.mycompany.server.GlobalListener;
import com.mycompany.webcam.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class App extends JFrame implements Runnable, WebcamListener, WindowListener, Thread.UncaughtExceptionHandler, ItemListener, WebcamDiscoveryListener {

    private static final long serialVersionUID = 1L;

    private Webcam webcam = null;
    private WebcamPanel panel = null;
    private WebcamPicker picker = null;
    private String ip;
    private int port;

    @Override
    public void run() {

        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        this.port = 9911;

        //Установка Java Look and Feel (по умолчанию)
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        Webcam.addDiscoveryListener(this);

        setTitle("WebCam PDV");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        addWindowListener(this);

        picker = new WebcamPicker();
        picker.addItemListener(this);

        webcam = picker.getSelectedWebcam();

        if (webcam == null) {
            System.out.println("No webcams found...");
            System.exit(1);
        }

        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.addWebcamListener(this);

        panel = new WebcamPanel(webcam, false);
        panel.setFPSDisplayed(true);
        panel.setImageSizeDisplayed(true);

        Picture picture = new Picture(new File("test.jpg"));
        WebcamCloseButton closeButton = new WebcamCloseButton("Exit","Let's close app",webcam);
        closeButton.setVerticalTextPosition(AbstractButton.CENTER);
        closeButton.setHorizontalTextPosition(AbstractButton.RIGHT);
        //closeButton.setPreferredSize(new Dimension(40,20));
        WebcamTakePictureButton button = new WebcamTakePictureButton("Take a picture","Let's take a picture",webcam,picture);
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.RIGHT);
        add(picker, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1,3));
        add(bottomPanel, BorderLayout.SOUTH);
        JPanel displayNetInfo = new JPanel();
        displayNetInfo.setLayout(new GridLayout(2, 1));
        displayNetInfo.add(new JLabel(String.format("ip : %s",this.ip)));
        displayNetInfo.add(new JLabel(String.format("port : %d",this.port)));
        bottomPanel.add(displayNetInfo,BorderLayout.CENTER);
        bottomPanel.add(button,BorderLayout.CENTER);
        bottomPanel.add(closeButton,BorderLayout.CENTER);

        //add(button, BoxLayout.);
        //add(closeButton, BorderLayout.SOUTH, BorderLayout.EAST);

        pack();
        setVisible(true);

        Thread t = new Thread() {

            @Override
            public void run() {
                panel.start();
            }
        };
        t.setName("example-starter");
        t.setDaemon(true);
        t.setUncaughtExceptionHandler(this);
        t.start();
        new GlobalListener(this.ip,this.port,System.getProperty("user.name"),1000);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new App());
    }

    @Override
    public void webcamOpen(WebcamEvent we) {
        System.out.println("webcam open");
    }

    @Override
    public void webcamClosed(WebcamEvent we) {
        System.out.println("webcam closed");
    }

    @Override
    public void webcamDisposed(WebcamEvent we) {
        System.out.println("webcam disposed");
    }

    @Override
    public void webcamImageObtained(WebcamEvent we) {
        // do nothing
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        webcam.close();
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.println("webcam viewer resumed");
        panel.resume();
    }

    @Override
    public void windowIconified(WindowEvent e) {
        System.out.println("webcam viewer paused");
        panel.pause();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println(String.format("Exception in thread %s", t.getName()));
        e.printStackTrace();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() != webcam) {
            if (webcam != null) {

                panel.stop();

                remove(panel);

                webcam.removeWebcamListener(this);
                webcam.close();

                webcam = (Webcam) e.getItem();
                webcam.setViewSize(WebcamResolution.VGA.getSize());
                webcam.addWebcamListener(this);

                System.out.println("selected " + webcam.getName());

                panel = new WebcamPanel(webcam, false);
                panel.setFPSDisplayed(true);

                add(panel, BorderLayout.CENTER);
                pack();

                Thread t = new Thread() {

                    @Override
                    public void run() {
                        panel.start();
                    }
                };
                t.setName("example-stoper");
                t.setDaemon(true);
                t.setUncaughtExceptionHandler(this);
                t.start();
            }
        }
    }

    @Override
    public void webcamFound(WebcamDiscoveryEvent event) {
        if (picker != null) {
            picker.addItem(event.getWebcam());
        }
    }

    @Override
    public void webcamGone(WebcamDiscoveryEvent event) {
        if (picker != null) {
            picker.removeItem(event.getWebcam());
        }
    }
}
