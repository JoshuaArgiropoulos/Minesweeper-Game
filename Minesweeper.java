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
    private int safeSlots;
    private int guessedSafeSlots;


    private boolean [] userGuessArray;

    public int getGuessedSafeSlots(){
        return guessedSafeSlots;
    }
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


    public int[] bombCoords(){

    int areaOfField = sizeOfField() * sizeOfField();


    Set<Integer> uniqueBombs = new HashSet<>();
    Random rand = new Random();

    while (uniqueBombs.size() < getBombs()) {
        int randomNumber = rand.nextInt(areaOfField);
        uniqueBombs.add(randomNumber);
    }
    Integer[] bombIndex = new Integer[uniqueBombs.size()];
    uniqueBombs.toArray(bombIndex);

    int [] intBombIndex = new int[areaOfField];
    for (int i = 0; i < areaOfField; i++) {
        intBombIndex[i] = 0;
    }
    for (int i = 0; i<bombIndex.length;i++){
        intBombIndex[bombIndex[i]] = -1;
    }

    
    return intBombIndex;
    
}
//Setter and getter functions for the bomb varible
    public void setIsthereBombs(boolean setBombs){
        bombs = setBombs;
    }
    public boolean isBombs() {
        return(bombs);
    }

    //Slightly different than the function above, used to create an array with both bombs and proximity numbers. T
    public void createField(int printOrder[]){
        int proximity;
        int N = sizeOfField();
        //Goes through the array by going row by row
        for (int i = 0; i < N; i++) {
            for (int x = 0; x < N; x++) {
                //Resets proximity after each unoccupied postion 
                proximity = 0;
                int index = x + i * N;
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

        setFinalArray(printOrder);
        
    }

    //Used to print the complete map. Checks if there are no bombs and prints the completed map. The user will have an option to select amount of bombs in a level and must check if there are any bombs
    public void printMap(){
        int N = sizeOfField();
        int [] printOrder = finalArray();
        if (getBombs()==0) {
            for (int i = 0; i < N; i++) {
                for (int x = 0; x < N; x++) {
                    System.out.print("0");
                }
                System.out.println();
            }
        } else {
            for (int i = 0; i < N; i++) {
                for (int x = 0; x < N; x++) {
                    if (printOrder[x + i * N] == -1) {
                        System.out.print("B");
                    } else {
                        System.out.print(printOrder[x + i * N]);
                    }
                }
                System.out.println();
            }
        }
    }
    public void emptyMap(){
        int N = sizeOfField();
        
        for (int i = 0; i < N; i++) {
            for (int x = 0; x < N; x++) {
                System.out.print("[]");
                
                
                
            }
            System.out.print("\n"); 
        }
    }
    //Creates an array filled with false. Will be changed to true as the user guesses
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
        boolean []GuessArray = getUserGuesses();
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
            System.out.println("Please input a number to select the row.");
            row = num.nextInt();
            System.out.println("Please input a number to select the column.");
            col = num.nextInt();
            index = (row-1)*sizeOfField + (col-1);
        }
        GuessArray[index] = true;

        //Calls user guessed to add 1 to guesses and setUserGuesses to update guess array
        userGuessed();

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
                if (GuessArray[x+i*sizeOfField] && finalArray[x+i*sizeOfField]!= -1){
                    System.out.print("["+finalArray[x+i*sizeOfField]+"]");
                }
                else if (finalArray[x+i*sizeOfField] == -1 && GuessArray[x+i*sizeOfField]) {
                    System.out.print("[B]");
                    bombPicked =true;
                }
                else if (!GuessArray[x+i*sizeOfField]){
                    System.out.print("[ ]");
                }
                
            }
            System.out.println();
        }

        
        
        if (!bombPicked){
            incrementGuessedSafeSlot();
        }
        return(bombPicked);


    }
    //Screen if the user loses.
    public void lost(){
        System.out.println("Game Over. You picked a bomb.");
        printMap();

        System.out.println("Enter \"yes\" if you would like to keep playing!");
        Scanner word = new Scanner(System.in);
		String answer = word.nextLine();
		//Obtains the users answer if they would like to keep playing
		
		if (answer.equals("yes")) {
            
			startGame();
			//brings back to selection menu
		}
		else {
            System.out.println("Thank you for playing!\n");
		    System.exit(0); 
        }
			

    
    }
    //screen if the user wins
    public void win(){
        System.out.println("Congrats!. You Win!.");

        printMap();
        System.out.println("Enter \"yes\" if you would like to keep playing!");
        Scanner word = new Scanner(System.in);
		String answer = word.nextLine();
		//Obtains the users answer if they would like to keep playing
		
		if (answer.equals("yes")) {
			startGame();
			//brings back to selection menu
		}
		else {
			System.out.println("Thank you for playing!\n");
			System.exit(0);
        }
    }
    
    public void userGuessingLoop(){


        boolean bombPicked = false;



        int guessesLeft = (sizeOfField()*sizeOfField())-getGuess()-getBombs();

        while ((!bombPicked && (guessesLeft>0)) && (getGuessedSafeSlots() < safeSlots() )){
            guessTracker();

            bombPicked = bombPicked();

            guessesLeft = (sizeOfField()^sizeOfField())-getGuess()-getBombs();



        }
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
        System.out.println("Welcome to Minesweeper! Please input a number to select options.\n");
        
        System.out.println("Option 1. Easy\n Option 2. Medium\n Option 3. Difficult\n Option 4. Custom Difficulty");

        Scanner num = new Scanner(System.in);
		int ID = num.nextInt();

        while (ID < 1 || ID >4) {
            System.out.println("Please select a valid number.");
			    ID = num.nextInt();
        }
        //If the user selects custom difficulty

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
                System.out.println("Select the length of the field.\n The field is a square and the length must be between 2-50.");

                sizeOfField = num.nextInt();
                while (sizeOfField<2 || sizeOfField>50) {
                    System.out.println("Please select a valid number. The number must be bewteen 2-50.");
			        sizeOfField = num.nextInt();
                }
                int max = (sizeOfField*sizeOfField)-1;
                System.out.println("Select the number of bombs.\n The number of bombs must be between 0-"+max+".");
                numOfBombs = num.nextInt();

                while ( numOfBombs< 0 || numOfBombs >(sizeOfField*sizeOfField)-1) {
                System.out.println("Please select a valid number.");
			    numOfBombs = num.nextInt();
                }
                break;
                
        

        }
        setSizeOfField(sizeOfField);
        setBombs(numOfBombs);
        if (numOfBombs!=0) {
            setIsthereBombs(true);
        }
        else setIsthereBombs(false);

        playGame();
    }
    public void playGame(){
        System.out.println("Good Luck!");
        
        int[] bombIndex = bombCoords();
        createField(bombIndex);
        emptyMap();
        createGuessTracker();
        userGuessingLoop();



    }
    public static void main(String[] args) {
		
	
        Minesweeper newGame = new Minesweeper();
        newGame.startGame();
        //Starts game
	}


}


