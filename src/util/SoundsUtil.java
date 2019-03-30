package util;

import javax.sound.sampled.*;
import java.io.File;

public class SoundsUtil extends Thread {
    private String filename;

    public SoundsUtil(String fileName) {
        this.filename = fileName;
    }

    public void run() {
        try {
            File fileDir = new File("");
            File file = new File(fileDir.getAbsoluteFile() + "/resources/Sounds/" + filename);
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);

            long frameLength = stream.getFrameLength();
            System.out.println("frameLength = " + frameLength);

            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
            Thread.sleep(100);
            while (clip.isRunning()) {
                Thread.sleep(100);
            }
            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
