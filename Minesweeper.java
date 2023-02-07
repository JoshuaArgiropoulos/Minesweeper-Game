import java.util.Scanner;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

public class Minesweeper {
    private boolean bombs = false;
    private int numOfGuesses = 0;
    private int sizeOfField;
    private int numOfBombs;
    private int[]finalArray;
    private int guessedSafeSlots;
    private int numOfWins = 0;
    private boolean [] userGuessArray;


    //Setter and getter functions for the bomb variable
    public void setIsthereBombs(boolean setBombs){
        bombs = setBombs;
    }
    //returns if there are bombs in the field
    public boolean isBombs() {
        return(bombs);
    }
    //Used to reset the users win streak
    public void userLost(){
        numOfWins = 0;
    }
    //Used to increment the users wins. This is used to display the users new winstreak at the end of the game.
    public void userWon(){
        numOfWins++;
    }
    //Used to display the users winstreak. 
    public int getUserWins(){
        return numOfWins;
    }
    //Used to return the number of unoccupied sqaures the user has guessed. 
    public int getGuessedSafeSlots(){
        return guessedSafeSlots;
    }
    //Used after the user guesses a safe square. Used to calculate the amount of squares the user must guess to win the level
    public void incrementGuessedSafeSlot(){
        guessedSafeSlots++;
    }
    //Returns number of unoccipied areas in the game 
    public int safeSlots(){
        return(sizeOfField *sizeOfField -numOfBombs);
    }
    //Returns the update user array of coords the user has guessed
    public boolean[]getUserGuesses(){
        return(userGuessArray);
    }
    //Updates the user guessing array
    public void setUserGuesses(boolean []setUserGuesses){
        userGuessArray = setUserGuesses;
    }
    //Returns the num of bombs on the field
    public int getBombs(){
        return(numOfBombs);
    }
    //Sets the num of bombs on the field
    public void setBombs(int setBombs){

        numOfBombs = setBombs;
    }
    //Returns the completed map of the field. This includes the locations of the bombs and the proximity unoccupied spaces are to bombs.
    public int[] finalArray(){
        return(finalArray);
    }
    //Sets the final array of the game
    private void setFinalArray(int[] setFinalArray){
        finalArray = setFinalArray;
    }
    //Gets the length of the field
    public int sizeOfField(){
        return(sizeOfField);
    }
    //Sets the length and width of the field (The field is a square)
    public void setSizeOfField(int setSizeOfField){
        sizeOfField = setSizeOfField;
    }
    //gets the number of guesses the user has done in this game
    public int getGuess(){
        return numOfGuesses;
    }
    //Adds 1 when the user guesses a place
    public void userGuessed(){
        numOfGuesses++;
    }

//Used to generate an array with either 0's or -1's. The 0s repersent an unoccupied square and the -1's repersent where a bomb is located. This will be used with createField() to create the field
    public int[] bombCoords(){

    int areaOfField = sizeOfField() * sizeOfField();


    Set<Integer> uniqueBombs = new HashSet<>();
    Random rand = new Random();
    //Generates the coordinates of the bombs in the arena. Ensures the bomb coords are less than the size of the area and are unique numbers
    while (uniqueBombs.size() < getBombs()) {
        int randomNumber = rand.nextInt(areaOfField);
        uniqueBombs.add(randomNumber);
    }
    
    Integer[] bombIndex = new Integer[uniqueBombs.size()];
    uniqueBombs.toArray(bombIndex);
    //Goes through the entire array and sets nums to 0
    int [] intBombIndex = new int[areaOfField];
    for (int i = 0; i < areaOfField; i++) {
        intBombIndex[i] = 0;
    }
    //Goes through the bomb coords array and the output of the first array will be equal to the index of the final array for the bomb location
    for (int i = 0; i<bombIndex.length;i++){
        intBombIndex[bombIndex[i]] = -1;
    }

    //Returns the array of 0's and -1's
    return intBombIndex;
    
}

    //Slightly different than the function above, used to create an array with both bombs (being -1) and proximity numbers. The proximity numbers are in unoccupied squares and are the sum of surrounding bombs
    public void createField(int printOrder[]){
        int proximity;
        int N = sizeOfField();
        //Goes through the array by going row by row
        for (int i = 0; i < N; i++) {
            for (int x = 0; x < N; x++) {
                //Resets proximity after each unoccupied postion 
                proximity = 0;
                //Index is calculated by the (rows * length of the row) + column number
                int index = x + i * N;
                //Sums the surrounding bombs
                if (printOrder[index] == -1) continue;
                if (x > 0 && printOrder[index - 1] == -1) {
                    proximity++;
                }
                if (x < N - 1 && printOrder[index + 1] == -1) {
                    proximity++;
                }
                if (i > 0 && printOrder[index - N] == -1) {
                    proximity++;
                }
                if (i < N - 1 && printOrder[index + N] == -1) {
                    proximity++;
                }
                if (x > 0 && i > 0 && printOrder[index - N - 1] == -1) {
                    proximity++;
                }
                if (x > 0 && i < N - 1 && printOrder[index + N - 1] == -1) {
                    proximity++;
                }
                if (x < N - 1 && i > 0 && printOrder[index - N + 1] == -1) {
                    proximity++;
                }
                if (x < N - 1 && i < N - 1 && printOrder[index + N + 1] == -1) {
                    proximity++;
                }
                printOrder[index] = proximity;
            }
        }
        //Sets the final array into the private variable
        setFinalArray(printOrder);
        
    }

    //Used to print the complete map. Checks if there are no bombs and prints the completed map. The user will have an option to select amount of bombs in a level and must check if there are any bombs
    public void printMap(){
        int N = sizeOfField();
        int [] printOrder = finalArray();
        System.out.println("Here is the completed field.");
        //If there are no bombs, prints a map of 0s
        if (getBombs()==0) {
            for (int i = 0; i < N; i++) {
                for (int x = 0; x < N; x++) {
                    System.out.print("[0]");
                }
                System.out.println();
            }
            //If there are bombs, prints B where there are bombs, and prints proximity to bombs for everywhere else
        } else {
            for (int i = 0; i < N; i++) {
                for (int x = 0; x < N; x++) {
                    if (printOrder[x + i * N] == -1) {
                        System.out.print("[B]");
                    } else {
                        System.out.print("["+printOrder[x + i * N]+"]");
                    }
                }
                System.out.println();
            }
        }
    }
//Prints an empty map with only [ ] to indict the user has no selected any squares 
    public void emptyMap(){
        int N = sizeOfField();
        
        for (int i = 0; i < N; i++) {
            for (int x = 0; x < N; x++) {
                System.out.print("[ ]");
                
                
                
            }
            System.out.print("\n"); 
        }
    }
    //Creates an array filled with false boolean logic. Will be changed to true as the user guesses
    public void createGuessTracker(){

        int [] tempArray = finalArray();

        boolean []userGuess = new boolean[tempArray.length];
        for (int i = 0; i<tempArray.length; i++){
            userGuess[i] = false;
        }
        setUserGuesses(userGuess);
    }
    //Keeps track of the users guesses. 
    public void guessTracker(){
        int sizeOfField = sizeOfField();
        //Gets the users current guess chart
        boolean []GuessArray = getUserGuesses();
        //Asks the user to select a row and column 
        System.out.println("Please input a number to select the row.");
        Scanner num = new Scanner(System.in);
		int row = num.nextInt();
        System.out.println("Please input a number to select the column.");
		int col = num.nextInt();
        boolean running = true;
        int index = (row-1)*sizeOfField + (col-1);
        //Subtracts 1 from the row and column to account for the user not starting at index 0

        while (running) {
            //Ensure the users coords are within boundaries
            if ((index>=sizeOfField*sizeOfField) || index<0) {
                System.out.println("The coordinates selected are out of bounds. Please select numbers within the field.");
            }
            //Ensures the user hasnt picked the coords already
            else if (GuessArray[index]== true) {
                System.out.println("The coordinates have already been guessed. Please select new numbers.");
            }
            else {
                break;
            }
            //Keeps asking the user until they select proper coordinates 
            System.out.println("Please input a number to select the row.");
            row = num.nextInt();
            System.out.println("Please input a number to select the column.");
            col = num.nextInt();
            index = (row-1)*sizeOfField + (col-1);
            //Updates the new index num
        }
        //When the users guess is valid, updates the users guess array 
        GuessArray[index] = true;

        //Calls user guessed to add 1 to guesses and setUserGuesses to update guess array
        userGuessed();
        //Stores the user guess array
        setUserGuesses(GuessArray);
    }
    //Checks the users guesses to see if a bomb was picked. 
    public boolean bombPicked(){

        int sizeOfField = sizeOfField();
        boolean[]GuessArray = getUserGuesses(); 
        int[]finalArray = finalArray();

        boolean bombPicked = false;

        for (int i = 0; i<sizeOfField;i++){
            for (int x = 0; x<sizeOfField;x++){
                //Checks if the user has guessed the coordinates and if there is NOT a bomb. If there user has guessed and there is no bomb. Displays the proximity 
                if (GuessArray[x+i*sizeOfField] && finalArray[x+i*sizeOfField]!= -1){
                    System.out.print("["+finalArray[x+i*sizeOfField]+"]");
                }
                //If the user has guessed and there is a bomb. Displays [B] and sets bombPicked to true
                else if (finalArray[x+i*sizeOfField] == -1 && GuessArray[x+i*sizeOfField]) {
                    System.out.print("[B]");
                    bombPicked =true;
                }
                //If there user has not guessed the square, returns a blank square
                else if (!GuessArray[x+i*sizeOfField]){
                    System.out.print("[ ]");
                }
                
            }
            System.out.println();
            //Prints a new line for visuals
        }

        //If bomb is NOT picked, increases the number of guessed safe squares to calculate guesses left
        
        if (!bombPicked){
            incrementGuessedSafeSlot();
        }
        //Returns if the user picked a bomb or not
        return(bombPicked);


    }
    //Screen if the user loses.
    public void lost(){
        //Displays defeat screen
        System.out.println("Game Over. You picked a bomb.");
        //Prints complete map and uncovered map
        printMap();
        //Displays the users winstreak before the lose
        System.out.println("You finished with a "+ getUserWins()+" game winstreak.");
        //Clears winstreak
        userLost();

        //Asks the user if they would like to continue playing 

        System.out.println("Enter \"yes\" if you would like to keep playing!");
        Scanner word = new Scanner(System.in);
		String answer = word.nextLine();
		//Obtains the users answer if they would like to keep playing
		
		if (answer.equals("yes")) {
            //Clears users guesses and restarts the game as new
            resetGame();
			startGame();
			
		}
		else {
            //if not, closes the game
            System.out.println("Thank you for playing!\n");
		    System.exit(0); 
        }
			

    
    }
    //screen if the user wins
    public void win(){
        //displays victory screen
        System.out.println("Congrats!. You Win!.");
        //Increments user wins
        userWon();
        //displays user winstreak and says its current unlike lose screen
        System.out.println("You are on a "+ getUserWins()+" game winstreak!");
        //prints an uncovered and completed map
        printMap();
        System.out.println("Enter \"yes\" if you would like to keep playing!");
        Scanner word = new Scanner(System.in);
		String answer = word.nextLine();
		//Obtains the users answer if they would like to keep playing
		
		if (answer.equals("yes")) {

            //Restarts the game and guesses but keeps the winstreak unlike the lose screen
            resetGame();
			startGame();
			
		}
		else {
			System.out.println("Thank you for playing!\n");
			System.exit(0);
        }
    }
    
    public void userGuessingLoop(){
        //Bomb is false or game would be over
        boolean bombPicked = false;
        //While bomb is not picked and more safe spots can be picked, the user must continue playing
        while (!bombPicked && (getGuessedSafeSlots()<safeSlots())){
            //Makes the user pick a square in the field that was not already picked
            guessTracker();
            //Checks if the square has a bomb
            bombPicked = bombPicked();
        }
        //Depending if the user picked a bomb or ran out of safe squares, 
        if (bombPicked){
            lost();
        }
        if (getGuessedSafeSlots() == safeSlots()){
            win();
        }

    }
    public void startGame() {
        int sizeOfField = 0;
        int numOfBombs = 0;
        //intro message
        System.out.println("Welcome to Minesweeper! Please input a number to select options.\n");
        
        System.out.println("Option 1. Easy\nOption 2. Medium\nOption 3. Difficult\nOption 4. Custom Difficulty");

        Scanner num = new Scanner(System.in);
		int ID = num.nextInt();

        while (ID < 1 || ID >4) {
            System.out.println("Please select a valid number.");
			    ID = num.nextInt();
        }
        //Options of which level the user is playing. 1 = easy, 2 = medium and 3 = difficult. 4 = custom diificulty 

        switch(ID){
            case(1):
                sizeOfField = 3;
                numOfBombs = 2;
                break;
            case(2):
                sizeOfField =5;
                numOfBombs =6;
            break;
                case(3):
                sizeOfField = 10;
                numOfBombs = 30;

                break;
            case(4):
            //Forces user to select a number within bounds of size and number of bombs
                System.out.println("Select the length of the field.\nThe field is a square and the length must be between 2-50.");

                sizeOfField = num.nextInt();
                while (sizeOfField<2 || sizeOfField>50) {
                    System.out.println("Please select a valid number. The number must be bewteen 2-50.");
			        sizeOfField = num.nextInt();
                }
                int max = (sizeOfField*sizeOfField)-1;
                System.out.println("Select the number of bombs.\nThe number of bombs must be between 0-"+max+".");
                numOfBombs = num.nextInt();

                while ( numOfBombs< 0 || numOfBombs >(sizeOfField*sizeOfField)-1) {
                System.out.println("Please select a valid number.");
			    numOfBombs = num.nextInt();
                }
                break;
                
        

        }
        //Sets all the important info to be used by other functions later
        setSizeOfField(sizeOfField);
        setBombs(numOfBombs);

        if (numOfBombs!=0) {
            setIsthereBombs(true);
        }
        else setIsthereBombs(false);
        //Starts game
        playGame();
    }
    public void playGame(){
        System.out.println("Good Luck!");
        //Displays a good luck and creates the map, bomb coords, and guess tracker. Also displays an empty map
        
        int[] bombIndex = bombCoords();
        createField(bombIndex);
        emptyMap();
        createGuessTracker();
        //User begins to guess squares
        userGuessingLoop();
    }
    //Restarts user guesses and safeslots left
    public void resetGame() {
        guessedSafeSlots = 0;
        numOfGuesses = 0;
    }
    public static void main(String[] args) {
		
	
        Minesweeper newGame = new Minesweeper();
        newGame.startGame();
        //Starts game
	}


}


