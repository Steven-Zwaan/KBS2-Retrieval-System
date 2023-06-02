import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        MainScreen scherm = new MainScreen(); // nieuw scherm aanmaken
        Queue instance = Queue.getInstance(); // instance van queue opslaan zodat we er overal bijkunnen
        instance.setScreen(scherm);
    }
}