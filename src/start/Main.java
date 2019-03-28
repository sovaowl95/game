package start;

import gui.MainFrame;

public class Main {
    private static final boolean FULLSCREEN = false;

    public static void main(String[] args) {
                //todo: check 4:3 16:9 .. etc
        // making a window app would be much challenging as there is getHeight never takes the size of our window originally.
        MainFrame mainFrame;
        if (FULLSCREEN) {
//            mainFrame = new MainFrame();
            mainFrame = new MainFrame(1600, 900, FULLSCREEN);
//            mainFrame = new MainFrame(800, 400, FULLSCREEN);
        }else{
            mainFrame = new MainFrame(1600, 900, FULLSCREEN);
//            mainFrame = new MainFrame(160, 90, FULLSCREEN);
        }
        mainFrame.init();
    }
}
