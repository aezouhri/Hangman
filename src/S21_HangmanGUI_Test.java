import static javax.swing.WindowConstants.EXIT_ON_CLOSE;




public class S21_HangmanGUI_Test {

    /**
     *
     * Creating an object to call the different method in S21_HangmanGUI and create a window.
     */

    public static void main(String[] args) {
        S21_HangmanGUI Play = new S21_HangmanGUI();

        Play.setTitle("Hangman game");
        Play.setSize(1000, 500);
        Play.setVisible(true);
        Play.setDefaultCloseOperation(EXIT_ON_CLOSE);


    }
}
