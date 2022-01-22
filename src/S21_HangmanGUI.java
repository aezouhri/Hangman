import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class S21_HangmanGUI extends JFrame {


    //Text field used to get User Input for letters to guess
    private JTextField UserInput;
    //Password field used to set the word to guess set as final is password is not supposed to be changed anywhere
    private JPasswordField WordToGuess;
    //Text area used to Print the game's information such as Correct guesses or Incorrect guesses
    private JTextArea PrintingInfo1;
    private JTextArea PrintingInfo2;
    private JTextArea PrintingInfo3;

    //Number of Incorrect guesses left
    int NumberOfIncorrectGuess = 6;
    //Number of correct guesses made
    int NumberOfCorrectGuess = 0;
    //Sets the game on
    boolean GameOn;
    //Used to set when the game is over
    boolean GameOver = false;
    //Temporary UserInput
    String user_input;
    //List used to store characters of the password in a string format
    String[] password_list;


    //List of all the wrong letters guessed so far by the user
    ArrayList<String> LetterWrong = new ArrayList<String>();
    //List of all the right letters guessed so far by the user
    ArrayList<String> LetterRight = new ArrayList<String>();

    //Graphics object, used to call other object and draw on the window
    Graphics GraphUsed;
    //Character list of the password
    char[] wordToGuessPassword;
    //Password without multiple occurrence letters
    String StringPwd = new String("");
    //Size of the password without multiple occurrence letters
    int SizeOfPwd = 0;
    Font GameText = new Font("Arial", Font.PLAIN, 12);
    //Default Constructor for for the game

    /**
     * Public constructor for the Hangman game, sets default values and components size.
     */

    public S21_HangmanGUI() {

        //Sets the type of layout for the window created
        setLayout(null);
        //Sets a default value for the UserInput and sets the size of the Text field
        UserInput = new JTextField(null, 10);
        //Sets sets the size of the Password Field
        WordToGuess = new JPasswordField(10);
        //Sets a default value for the Text Area
        PrintingInfo1 = new JTextArea(" ");
        //Sets a default value for the Text Area
        PrintingInfo2 = new JTextArea("Please enter below the word to be guessed");
        //Sets a default value for the Text Area
        PrintingInfo3 = new JTextArea("Please enter above a letter to guess.");

        //Adding all the elements to the window
        add(WordToGuess);
        add(UserInput);
        add(PrintingInfo1);
        add(PrintingInfo2);
        add(PrintingInfo3);


        //Adds a handler to detect when a key is being enter in TextField and Password field
        KeyHandler handler = new KeyHandler();
        UserInput.addKeyListener(handler);
        WordToGuess.addKeyListener(handler);


        //Set bounds and size for all components with text on the screen
        UserInput.setBounds(500, 250, 60, 25);
        WordToGuess.setBounds(500, 30, 60, 25);


        PrintingInfo1.setBounds(400, 300, 550, 20);
        PrintingInfo2.setBounds(410, 10, 550, 20);
        PrintingInfo3.setBounds(410, 280, 550, 20);

        PrintingInfo1.setFont(GameText);
        PrintingInfo1.setColumns(50);
        //Setting the color of the info text to the same color as the background of the window.
        PrintingInfo1.setBackground(Color.getHSBColor(264, 0, 0.935f));
        //Is used to prevent the user from deleting the text displayed
        PrintingInfo1.setEditable(false);

        PrintingInfo2.setFont(GameText);
        PrintingInfo2.setColumns(50);
        //Setting the color of the info text to the same color as the background of the window.
        PrintingInfo2.setBackground(Color.getHSBColor(264, 0, 0.935f));
        //Is used to prevent the user from deleting the text displayed
        PrintingInfo2.setEditable(false);

        PrintingInfo3.setFont(GameText);
        PrintingInfo3.setColumns(50);
        //Setting the color of the info text to the same color as the background of the window.
        PrintingInfo3.setBackground(Color.getHSBColor(264, 0, 0.935f));
        //Is used to prevent the user from deleting the text displayed
        PrintingInfo3.setEditable(false);


    }


    /**
     * The following serie of "if" and "else" statement are used to first figure out whether or not
     * the game is still okay to run by checking if the player still has enough attempts or
     * if the player has finished the game already.
     * <p>
     * First we see if the letter entered by the player is part of the word to be guessed.
     * If the letter entered is part of the word to guess, first see if the letter has already been guessed.
     * Otherwise we add it to the list of letter correctly guessed. And draw the word in the corresponding spot.
     * <p>
     * However if the letter entered was not part of the word, we first check if it has already wrongly been guesses.
     * Otherwise then we add it to the list of wrong guessed letter
     * and draw a piece of the HangMan
     */

    @Override
    public void paint(Graphics Graph) {

        //To make sure the components in the subclass are properly rendered.
        super.paint(Graph);

        GraphUsed = Graph;


        //Creating multiple Graphics objects for different purposes
        Graphics2D Graph2D = (Graphics2D) Graph;
        Graphics2D StickMan = (Graphics2D) Graph;
        Graphics2D LettersToDisplay = (Graphics2D) Graph;

        Graph2D.setFont(GameText);

        wordToGuessPassword = WordToGuess.getPassword();

        password_list = new String[wordToGuessPassword.length];

        PrintingInfo1.setFont(GameText);
        PrintingInfo1.setColumns(50);
        //Setting the color of the info text to the same color as the background of the window.
        PrintingInfo1.setBackground(Color.getHSBColor(264, 0, 0.935f));
        //Is used to prevent the user from deleting the text displayed
        PrintingInfo1.setEditable(false);


        LettersToDisplay.drawString("Correct Letters Guessed: ", 50, 50);
        LettersToDisplay.drawString("Incorrect Letters Guessed: ", 700, 50);

        //Setting the thickness of the drawings and the blank areas
        BasicStroke Support = new BasicStroke(2.5f);
        BasicStroke Spots = new BasicStroke(1.5f);

        Graph2D.setStroke(Support);
        //Draw the hanging place
        //Whole thing needs to be shifted to the right of the game window
        // left top right bottom
        Graph2D.drawLine(450, 275, 600, 275);
        Graph2D.drawLine(550, 275, 550, 115);
        Graph2D.drawLine(480, 115, 550, 115);
        Graph2D.drawLine(480, 115, 480, 155);


        //Creates the necessary number of spots for letter to be guessed and ignores spaces
        for (int count = 0; count < wordToGuessPassword.length; count++) {
            if (!Character.toString(wordToGuessPassword[count]).equals(" ")) {
                Graphics2D Blanks = (Graphics2D) Graph;
                Blanks.setStroke(Spots);
                Blanks.drawLine(30 + count * 60, 450, 75 + count * 60, 450);


            }
        }


//Represents the total number of correct guesses including multiple appearance letter.
        SizeOfPwd = StringPwd.length();

        if (GameOn && !GameOver) {
            //stores the password in a string array
            for (int i = 0; i < password_list.length; i++) {
                password_list[i] = Character.toString(wordToGuessPassword[i]);
                password_list[i] = password_list[i].toUpperCase();
            }

            for (int i = 0; i < password_list.length; i++) {


                if (!StringPwd.contains(password_list[i]) && !password_list[i].equals(" ")) {
                    StringPwd += password_list[i];

                }
            }

            System.out.println(StringPwd);
            for (int i = 0; i < password_list.length; i++) {
                System.out.println(password_list[i]);
            }

            StringPwd = StringPwd.toUpperCase();

            //Locking the word to guess area, to prevent it from being changed during the game.
            WordToGuess.setEditable(false);

            user_input = UserInput.getText();
            //To make sure an underscored correct letter isn't missed

            user_input = user_input.toUpperCase();


            if (NumberOfIncorrectGuess > 0 && NumberOfCorrectGuess < SizeOfPwd) {

                if (StringPwd.contains(user_input)&& !user_input.equals("")) {


                    if (LetterRight.contains(user_input)) {
                        PrintingInfo1.setText("It seems like you have already tried this letter. Please choose another one.");
                    } else {
                        LetterRight.add(user_input);
                        NumberOfCorrectGuess++;
                        PrintingInfo1.setText("Correct Guess!");
                    }
                } else {
                    if (LetterWrong.contains(user_input)) {
                        PrintingInfo1.setText("It seems like you have already tried this letter. Please choose another one.");

                    } else {
                        LetterWrong.add(user_input);
                        NumberOfIncorrectGuess--;
                        PrintingInfo1.setText("Incorrect guess, you have " + NumberOfIncorrectGuess + " guesses left.");
                    }
                }
                //draws the list of correct letters guessed by the player.
                if (!LetterRight.isEmpty()) {
                    LettersToDisplay.setFont(new Font("Georgia", Font.BOLD, 20));
                    LettersToDisplay.drawString(LetterRight.toString(), 60, 100);

                }
                //Draws the list of wrong letters guessed by the player.
                if (!LetterWrong.isEmpty()) {
                    LettersToDisplay.setFont(new Font("Georgia", Font.BOLD, 20));
                    LettersToDisplay.drawString(LetterWrong.toString(), 650, 100);
                }

                //Draws the correct letters guessed in the right blank position
                for (int count = 0; count < password_list.length; count++) {
                    if (LetterRight.contains(password_list[count])) {
                        Graphics2D LettersToDraw = (Graphics2D) Graph;
                        LettersToDraw.setFont(new Font("Georgia", Font.BOLD, 32));
                        LettersToDraw.drawString(password_list[count], 50 + (count * 55), 450);
                    }

                }

                //Drawing the head after first mistake.
                if (NumberOfIncorrectGuess <= 5) {
                    StickMan.drawOval(465, 157, 30, 30);

                }

                //Drawing the body after second mistake.
                if (NumberOfIncorrectGuess <= 4) {
                    StickMan.drawLine(480, 187, 480, 230);
                }

                //Drawing the left arm after the third mistake.
                if (NumberOfIncorrectGuess <= 3) {
                    StickMan.drawLine(460, 210, 480, 201);
                }

                //Drawing the right arm after fourth mistake.
                if (NumberOfIncorrectGuess <= 2) {
                    StickMan.drawLine(480, 201, 500, 210);
                }

                //Drawing the left leg after the fifth mistake.
                if (NumberOfIncorrectGuess <= 1) {
                    StickMan.drawLine(460, 245, 480, 230);
                }

                //Drawing the right leg after the sixth and last allowed mistake.
                if (NumberOfIncorrectGuess < 1) {
                    StickMan.drawLine(480, 230, 500, 245);
                }

                //Used to clear the guessing area after hitting enter.
                UserInput.setText("");

                //Checks if the user has ran out of attempts
                if (NumberOfIncorrectGuess == 0) {
                    GameOn = !GameOn;
                    GameOver = true;
                }

                //When the player has guessed all the letters, displays a message.
                if (NumberOfCorrectGuess == SizeOfPwd) {
                    Font Game_Won = new Font("Times New Roman", Font.PLAIN, 50);
                    PrintingInfo1.setFont(Game_Won);
                    PrintingInfo1.setBounds(410, 140, 300, 250);
                    PrintingInfo1.setText("You won!");
                    UserInput.setEditable(false);
                    GameOn = !GameOn;
                }
            }


        }
        //When the player has reached the allowed number of mistakes it displays a message.
        if (GameOver) {
            Font Game_Over = new Font("Georgia", Font.PLAIN, 50);

            PrintingInfo1.setBounds(410, 140, 300, 250);
            PrintingInfo1.setFont(Game_Over);
            PrintingInfo1.setText("Game Over");
            UserInput.setEditable(false);

        }
    }

    /**
     * This class connects the Player to the game through the keyboard.
     * When the key "Enter" is hit by the player it will run the program, if the game isn't over already.
     */
    private class KeyHandler implements KeyListener {

        @Override
        public void keyPressed(KeyEvent HitaKey) {
            if ("Enter".equals(KeyEvent.getKeyText(HitaKey.getKeyCode()))) {
                GameOn = true;
                if (!GameOver) {
                    repaint();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent ReleasedKey) {
        }

        @Override
        public void keyTyped(KeyEvent TypedKey) {

        }
    }
}
