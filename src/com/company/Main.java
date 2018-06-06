package com.company;

/* Author: Abdul El Badaoui
* Student Number: 5745716
* Description: The following program provides the optimal path that needs to be taken in the dynamic programing Amazing
* Race problem. This problem is the derivative of the assembly line problem and was used as the bases reference
* to comeout with the solution of this question.
* */

import java.io.*;
import java.util.Scanner;


public class Main {

    static Scanner fileInput; // to read in the input file
    static PrintWriter fileOutput; // to output the file in the end


    public static void main(String[] args) {
        //try catch clasue to handle the input/output files
        try {
            fileInput = new Scanner(new FileInputStream(new File("a1q2in.txt")));// readin input file
            fileOutput = new PrintWriter("a1q2out.txt", "UTF-8");// output file creation
            int m = fileInput.nextInt(); // number of scenarios
            for (int i =1; i<=m ; i++){
                fileOutput.println("Scenario Number " +i+ "!");
                inputs();// read in the rest of the inputs per each scenario
            }
            fileOutput.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    //take in the rest of the inputs
    public static void inputs(){
        int n = fileInput.nextInt();// number of steps
        int k = fileInput.nextInt();//number of stops
        int [] e = new int[k];// array to hold the weight time of each entry per stop
        for ( int j=0; j<k ; j++){// storing the entry times
            e[j] = fileInput.nextInt();
        }
        int [] x = new int[k]; // array to hold the weight time of each exit per stop
        for (int j=0; j<k ; j++){//storing the exit times
            x[j] = fileInput.nextInt();
        }

        int [][][] t = new int [n-1][k][k];// 3d array to hold the transfer time between stops
        for (int i=0; i<n-1; i++){//storing the transfer times
            for (int j = 0; j<k ; j++){
                for (int j_prime = 0 ; j_prime<k; j_prime++){
                    t[i][j][j_prime]= fileInput.nextInt();
                }
            }

        }
        int [][] a= new int [n][k];// array to hold the activity time at each step and stop
        for (int i =0; i<n; i++){//storing the activity times
            for (int j=0; j<k ; j++){
                a[i][j] = fileInput.nextInt();
            }
        }
        dpAmazingRace(n, k, e, x, t, a);// running the dynamic programing algorithm


    }
    //algorithm to find the optimal path (dynamic programing)
    public static void dpAmazingRace(int n, int k, int[] e, int[] x, int[][][] t, int[][] a){
        int fStar = Integer.MAX_VALUE;// declaring fStar which will be the final solution
        int lStar=0;// declaring lStar which will be the exit stop
        int [][] f = new int[n][k];// array to hold the optimal time to complete the sum of task after each step
        int [][] l = new int[n-1][k];//array to store the stop choosen from the previous step

        for (int j = 0; j<k ; j++){// intializing the first steps total time per stop
            f[0][j] = e[j] + a[0][j];
        }
        int minSelectedStep;// declaring a value to find the best solution to add
        for (int i = 1; i<n; i++){
            for (int j_1=0; j_1<k; j_1++){
                minSelectedStep = Integer.MAX_VALUE;// initializing to infinite
                for (int j_2=0; j_2<k; j_2++){
                    // if the solution is better then the minSelected step do the following
                    if (f[i-1][j_2] + t[i-1][j_2][j_1] + a[i][j_1]<= minSelectedStep){
                        //current cell will be the least time sum of the previous f[step][stop] + transfer time to
                        // current step and stop and activity time of that step and stop
                        f[i][j_1]= f[i-1][j_2] + t[i-1][j_2][j_1] + a[i][j_1];
                        minSelectedStep = f[i][j_1];// minSelectedStep will be the current best function of current cell
                        l[i-1][j_1]=j_2+1;//stores the previous function stop that was added to the current function
                    }
                }
            }
        }

        for (int i=0; i<k ; i++){//finding fStar
            if (f[n-1][i] + x[i] < fStar){// finding the best minimum overall time
                fStar = f[n-1][i] + x[i];
                lStar = i+1;// storing the best exit stop to come from
            }

        }

        fileOutput.println("The Minimum Overall Time is: " +fStar);// print the overall time
        int [] path = new int [n];// store the path choose per each step

        for (int i =n-1 ; i>=0; i--){// works backward to get  the correct path
            if (i == n-1){
                path[i] = lStar;
            }
            else{
                path[i] = l[i][path[i+1]-1];
            }


        }
        for (int i =1 ; i<=n; i++){// prints path
            fileOutput.println("Step " +i+ ":    Stop " +path[i-1]);

        }

        fileOutput.println();

    }
}
