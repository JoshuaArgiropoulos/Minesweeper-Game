import java.util.Scanner;
import java.util.Random;

import java.util.HashSet;

import java.util.Set;

public class Minesweeper {
    private boolean bombs = false;

    
    

//Accepts input of length of array, coords of bombs. R[] is the rows and C is the columns
    public int[] createBombs(int N, int[] R, int[] C) {
        for (int i = 0; i < R.length; i++) {
            placeHolder = (R[i] * N) + C[i];
            printOrder[placeHolder] = -1;
            bombs = true;
        }
//Uses input of field and num of bombs to create all the locations of the bombs that is unique
    public int[] bombCoords(int sizeOfField, int numOfBombs){
        Set<Integer> uniqueBombs = new HashSet<>();
        Random rand = new Random();

        while (uniqueBombs.size() < numOfBombs) {
            int randomNumber = rand.nextInt(sizeOfField^2);
            uniqueBombs.add(randomNumber);
        }

        Integer[] array = uniqueBombs.toArray(new Integer[0]);
     
        int[][] twoDimensionalArray = new int[sizeOfField][sizeOfField];

        int row = 0, col = 0;
        for (int i = 0; i < array.length; i++) {
            twoDimensionalArray[row][col] = array[i];
            col++;
            if (col == numOfColumns) {
                row++;
                col = 0;
            }
        }
        return(twoDimensionalArray);


        System.out.println("Generated unique random numbers in 2D array:");
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfColumns; j++) {
                System.out.print(twoDimensionalArray[i][j] + " ");
            }
            System.out.println();
        }
    }
    



//Setter and getter functions for the bomb varible
    public void setBombs(boolean setBombs){
        bombs = setBombs;
     }
    public boolean getBombs() {
        return(bombs);
    }

//Used to gather how many bombs are around a square. Inputs are the array of bombs and the square checking for surroundings
    public int getProxitmity(int index, int[]printOrder) {
        int proximity = 0;
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
                return proximity;
    }

    //Slightly different than the function above, used to create an array with both bombs and proximity numbers. T
    public int[] createField(int N, int bombArray[]){
        int proximity;
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
        return (printOrder[]);
    }

    //Used to print the complete map. Checks if there are no bombs and prints the completed map. The user will have an option to select amount of bombs in a level and must check if there are any bombs
    public void printMap(int N, int[]printOrder){
        if (!getBombs()) {
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
    public void startGame() {
        int sizeOfField;
        int numOfBombs
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

                System.out.println("Select the number of bombs.\n The number of bombs must be between 0-%d",(sizeOfField^2)-1);
                int numOfBombs = num.nextInt();

                while ( numOfBombs< 0 || numOfBombs >(sizeOfField^2)-1) {
                System.out.println("Please select a valid number.");
			    numOfBombs = num.nextInt();
                }
                break;
                
        

        }
        playGame(sizeOfField, numOfBombs);
    }
    public void playGame(int sizeOfField, int numOfBombs){
        System.out.println("Good Luck!");
        
        int[][] gameArray = bombCoords(int sizeOfField, int numOfBombs);


    }


}



