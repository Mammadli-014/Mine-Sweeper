package util;

import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    private final int size;
    private int mine_numbers;   //mine numbers
    private boolean isEnd=false;
    private String[][] rotation; // line, pillar

    public MineSweeper(int size) {
        this.size = size;
        rotation = new String[this.size][this.size];

    }

    public void start() {
        mine_creator();
        showGame();
    }

    private void mine_creator() { //places mines randomly
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                rotation[i][j] = "-";    // it does "-" all coordinates

        Random r = new Random();
        int area = size * size;   //area
        mine_numbers = area / 4;

        while (mine_numbers > 0) {
            mine_numbers--;
            int random_pillar = r.nextInt(1, size + 1), random_line = r.nextInt(1, size + 1);
            if (!(rotation[random_line - 1][random_pillar - 1].equals("*")))
                rotation[random_line - 1][random_pillar - 1] = "*";
        }
    }

    private void showGame() {  //print game design
        System.out.println("-------------------");
        System.out.print(" ");
        for (int i = 0; i < size; i++)
            System.out.print((i + 1) + " ");
        System.out.println();
        for (int i = 0; i < size; i++) {//line
            System.out.print(i + 1 + ")");
            for (int j = 0; j < size; j++) { //pillar
                if (!isEnd) {//If game did not finish hide mines coordinates
                    if (!(rotation[i][j].equals("-")) && !(rotation[i][j].equals("*")))
                        System.out.print(rotation[i][j] + " "); // If there is not any mine, prints
                    else System.out.print("- ");
                } else{ //If game ended,prints coordinate of mines
                    System.out.print(rotation[i][j]);}
                if (j == size - 1) System.out.println();
            }

        }checkWin();
        if (!isEnd)check();
    }

    private void check() {

        Scanner s = new Scanner(System.in);
        System.out.println("Enter line number");
        int lineNumber = s.nextInt();
        System.out.println("Enter pillar number");
        int pillarNumber = s.nextInt();
        try {
            if (rotation[lineNumber - 1][pillarNumber - 1].equals("*")) {// shecks whether the selected point is a mine or not
                System.err.println("GAME OVER!");isEnd=true;showGame();//If there is a mine game will end
                System.exit(5);
            } else if (rotation[lineNumber - 1][pillarNumber - 1].equals("-")) {
                rotation[lineNumber - 1][pillarNumber - 1] = showMinas(lineNumber, pillarNumber);
                showGame();
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Wrong coordinate was entered");
            check();
        }checkWin();
    }

    private String showMinas(int line, int pillar) {//check border of selected point
        int mines_near = 0;  //default
        String[][] borderNumbers = new String[3][3];//crate a new array[3][3] and adds points which is border of selected point
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    borderNumbers[i][j] = rotation[line + i - 2][pillar + j - 2];
                } catch (ArrayIndexOutOfBoundsException e) {
                    borderNumbers[i][j] = "";//If border is null ,adds ""
                }
            }
        }
        borderNumbers[1][1] = "Selected point"; //selected point is in the centre of array
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (borderNumbers[i][j].equals("*")) mines_near++;
        return Integer.toString(mines_near);
    }
//  line-1,pillar-1
//  line-1,pillar
//  line-1,pillar+1
//
//  line,pillar-1
//  line,pillar
//  line,pillar+1

    //  line+1,pillar-1
//  line+1,pillar
//  line+1,pillar+1
    private void checkWin() {//cheks wheter game ended or not
        int lastEmptyArea=0;
        for (String []ss:rotation)
            for (String s:ss) {
                if (s.equals("-")) lastEmptyArea++;
            }if (lastEmptyArea==0){
            System.out.println("YOU WIN!");System.exit(30);
        }
    }
}
