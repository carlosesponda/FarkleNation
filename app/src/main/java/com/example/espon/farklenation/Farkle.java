package com.example.espon.farklenation;

import com.example.espon.farklenation.ScoringOption;
import com.example.espon.farklenation.SortedLinkedList;

import java.util.PriorityQueue;

/**
 * Created by espon on 10/2/2016.
 */
public class Farkle {
    static int winTotal = 10000;
    static int bankTotal = 500;

    static int startTotal=0;
    static final int minPointIncrement = 50;
    static final int[] dividedNums = {6, 36, 216, 1296, 7776, 46656};
    static int[][] caseOne = {{1, 1}, {100, 50}, {6, 6}};
    static int[][] caseTwo = {{1, 1, 2, 8, 8}, {200, 100, 150, 100, 50}, {6, 6, 6, 1, 1}};
    static int[][] caseThree = {{1, 1, 1, 1, 1, 1, 3, 3, 12, 12, 24, 48, 48}, {300, 200, 300, 400, 500, 600, 250, 150, 200, 100, 150, 100, 50}, {6, 6, 6, 6, 6, 6, 6, 6, 1, 1, 1, 2, 2}};
    static int[][] caseFour = {{6, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 16, 12, 12, 12, 16, 12, 6, 48, 48, 96, 96, 192, 240, 240}, {1000, 300, 400, 500, 600, 700, 350, 250, 350, 450, 650, 300, 200, 300, 400, 500, 600, 300, 250, 200, 200, 100, 150, 100, 50}, {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 1, 1, 1, 1, 1, 6, 1, 1, 2, 2, 2, 3, 3}};
    static int[][] caseFive = {{6, 25, 25, 100, 10, 10, 10, 20, 10, 10, 20, 10, 10, 20, 10, 10, 10, 20, 60, 60, 60, 80, 60, 80, 60, 60, 60, 60, 160, 90, 90, 90, 160, 90, 120, 480, 480, 600, 600, 1200, 1020, 1020}, {2000, 1100, 1050, 1000, 400, 400, 300, 350, 500, 400, 450, 600, 500, 550, 700, 800, 700, 750, 300, 400, 500, 600, 700, 350, 250, 350, 450, 650, 300, 200, 300, 400, 500, 600, 300, 250, 200, 200, 100, 150, 100, 50}, {6, 6, 6, 1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 3, 3, 3, 4, 4}};
    static int[][] caseSix = {{720, 2250, 300, 6, 30, 30, 120, 120, 480, 480, 720, 60, 60, 60, 60, 60, 60, 60, 60, 240, 180, 180, 360, 180, 180, 360, 180, 180, 360, 240, 180, 180, 360, 540, 540, 540, 960, 540, 960, 540, 540, 540, 540, 1200, 480, 480, 480, 1200, 480, 1080, 3600, 3600, 2520, 2520, 5400, 3600, 3600}, {1500, 1500, 2500, 3000, 2100, 2050, 2000, 1150, 1100, 1050, 1000, 450, 400, 550, 500, 650, 600, 850, 800, 400, 400, 300, 350, 500, 400, 450, 600, 500, 550, 700, 800, 700, 750, 300, 400, 500, 600, 700, 350, 250, 350, 450, 650, 300, 200, 300, 400, 500, 600, 300, 250, 200, 200, 100, 150, 100, 50}, {6, 6, 6, 6, 6, 6, 1, 6, 1, 1, 2, 6, 6, 6, 6, 6, 6, 6, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 2, 3, 3, 4, 4, 4, 5, 5}};

    static double[][] storedValues = new double[200][6]; // expected value of situation with [curr point total/50][dice left-1]

    public static String getOutput(String[] nameSettings, String[] pointSettings, int startingTotal, int currentTotal, int[] dice){
        setExpectedValues();
        startTotal=startingTotal;
        int currTotalInt = currentTotal;
        int diceRolled = dice[0]+dice[1]+dice[2]+dice[3]+dice[4]+dice[5];
        PriorityQueue<ExpectedValues> expectedValues= new PriorityQueue<>();
        if(isOneOne(dice)) {
            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3]), "OneOneInput");
            int index;
            if((currTotalInt+scoringOption.scoringValue)>9950){
                index=199;
            }
            else{
                index = (currTotalInt+scoringOption.scoringValue)/50;
            }
            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
        }
        if(isOneFive(dice)) {
            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[4]), "OneFiveInput");
            int index;
            if((currTotalInt+scoringOption.scoringValue)>9950){
                index=199;
            }
            else{
                index = (currTotalInt+scoringOption.scoringValue)/50;
            }

            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
        }
        if(isOneOne(dice)&&isOneFive(dice)) {
            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4]), "OneOneOneFiveInput");
            int index;
            if((currTotalInt+scoringOption.scoringValue)>9950){
                index=199;
            }
            else{
                index = (currTotalInt+scoringOption.scoringValue)/50;
            }
            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
        }
        if(isTwoFives(dice)) {
            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4]), "TwoFivesInput");
            int index;
            if((currTotalInt+scoringOption.scoringValue)>9950){
                index=199;
            }
            else{
                index = (currTotalInt+scoringOption.scoringValue)/50;
            }
            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
        }
        if(isTwoOnes(dice)) {
            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3]), "TwoOnesInput");
            int index;
            if((currTotalInt+scoringOption.scoringValue)>9950){
                index=199;
            }
            else{
                index = (currTotalInt+scoringOption.scoringValue)/50;
            }

            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
        }
        if(isThreeOnes(dice)) {
            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[5]), "ThreeOnesInput");
            int index;
            if((currTotalInt+scoringOption.scoringValue)>9950){
                index=199;
            }
            else{
                index = (currTotalInt+scoringOption.scoringValue)/50;
            }
            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
        }
        if(isThreeTwos(dice)) {
            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[6]), "ThreeTwosInput");
            int index;
            if((currTotalInt+scoringOption.scoringValue)>9950){
                index=199;
            }
            else{
                index = (currTotalInt+scoringOption.scoringValue)/50;
            }
            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
        }
        if(isThreeThree(dice)) {
            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[7]), "ThreeThreesInput");
            int index;
            if((currTotalInt+scoringOption.scoringValue)>9950){
                index=199;
            }
            else{
                index = (currTotalInt+scoringOption.scoringValue)/50;
            }
            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
        }
        if(isThreeFours(dice)) {
            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[8]), "isThreeFours");
            int index;
            if((currTotalInt+scoringOption.scoringValue)>9950){
                index=199;
            }
            else{
                index = (currTotalInt+scoringOption.scoringValue)/50;
            }
            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
        }
        if(isThreeFives(dice)) {
            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[9]), "THreeFivesInput");
            int index;
            if((currTotalInt+scoringOption.scoringValue)>9950){
                index=199;
            }
            else{
                index = (currTotalInt+scoringOption.scoringValue)/50;
            }
            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
        }
        if(isThreeSixs(dice)) {
            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[10]), "ThreeSixsInput");
            int index;
            if((currTotalInt+scoringOption.scoringValue)>9950){
                index=199;
            }
            else{
                index = (currTotalInt+scoringOption.scoringValue)/50;
            }
            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
        }
        if(isOneOne(dice) && isTwoFives(dice)) {
            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4]), "OneOneTwoFivesInput");
            int index;
            if((currTotalInt+scoringOption.scoringValue)>9950){
                index=199;
            }
            else{
                index = (currTotalInt+scoringOption.scoringValue)/50;
            }
            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
        }
        if(isOneFive(dice) && isTwoOnes(dice)) {
            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3]), "OneFiveTwoOnesInput");
            int index;
            if((currTotalInt+scoringOption.scoringValue)>9950){
                index=199;
            }
            else{
                index = (currTotalInt+scoringOption.scoringValue)/50;
            }
            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isFour(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[11]), "FourInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isTwoOnes(dice) && isTwoFives(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4]), "TwoOnesOneFiveInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneOne(dice) && isThreeTwos(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[6]), "OneOneThreeTwosInput");
                           int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneOne(dice) && isThreeThree(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[7]), "OneOneThreeThreesInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneOne(dice) && isThreeFours(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[8]), "OneOneThreeFoursInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneOne(dice) && isThreeFives(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[9]), "OneOneThreeFivesInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneOne(dice) && isThreeSixs(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[10]), "OneOneThreeSixs");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneFive(dice) && isThreeOnes(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[5]), "OneFiveThreeOnesInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneFive(dice) && isThreeTwos(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[6]), "OneFiveThreeTwosInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneFive(dice) && isThreeThree(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[7]), "OneFiveThreeThreesInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneFive(dice) && isThreeFours(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[8]), "OneFiveThreeFoursInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneFive(dice) && isThreeSixs(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[10]), "OneFiveThreeSixsInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }

                        if(isFive(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[12]), "FiveInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }

                        if(isFour(dice)&&isOneFive(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[11]), "FourPlusFiveInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isFour(dice)&&isOneOne(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[11]), "FourPlusOneInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneOne(dice) && isOneFive(dice) && isThreeTwos(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[6]), "OneOneOneFiveThreeTwosInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneOne(dice) && isOneFive(dice) && isThreeThree(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,450, "OneOneOneFiveThreeThreesInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneOne(dice) && isOneFive(dice) && isThreeFours(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[8]), "OneOneOneFiveThreeFoursInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneOne(dice) && isOneFive(dice) && isThreeSixs(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,750, "OneOneOneFiveThreeSixsInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isTwoOnes(dice) && isThreeTwos(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[6]), "TwoOnesThreeTwosInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isTwoOnes(dice) && isThreeThree(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[7]), "TwoOnesThreeThreesInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isTwoOnes(dice) && isThreeFours(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[8]), "TwoOnesThreeFoursInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isTwoOnes(dice) && isThreeFives(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[9]), "TwoOnesThreeFivesInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isTwoOnes(dice) && isThreeSixs(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[10]), "TwoOnesThreeSixsInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isTwoFives(dice) && isThreeOnes(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[5]), "TwoFivesThreeOnesInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isTwoFives(dice) && isThreeTwos(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[6]), "TwoFivesThreeTwosInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isTwoFives(dice) && isThreeThree(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[7]), "TwoFivesThreeThreesInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isTwoFives(dice) && isThreeFours(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[8]), "TwoFivesThreeFours");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isTwoFives(dice) && isThreeSixs(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[10]), "TwoFivesThreeSixsInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isTwoOnes(dice) && isOneFive(dice) && isThreeTwos(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[6]), "TwoOnesOneFiveThreeTwosInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isTwoOnes(dice) && isOneFive(dice) && isThreeThree(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[7]), "TwoOnesOneFiveThreeThreesInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isTwoOnes(dice) && isOneFive(dice) && isThreeFours(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[8]), "TwoOnesOneFiveThreeFoursInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isTwoOnes(dice) && isOneFive(dice) && isThreeSixs(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[10]), "TwoOnesOneFiveThreeSixsInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneOne(dice) && isTwoFives(dice) && isThreeTwos(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[6]), "OneOneTwoFiveThreeTwosInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneOne(dice) && isTwoFives(dice) && isThreeThree(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[7]), "OneOneTwoFivesThreeThreesInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneOne(dice) && isTwoFives(dice) && isThreeFours(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[8]), "OneOneTwoFivesThreeFoursInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isOneOne(dice) && isTwoFives(dice) && isThreeSixs(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[10]), "OneOneTwoFivesThreeSixsInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isSix(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[13]), "SixInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isStraight(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[16]), "StraightInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isTriplets(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[15]), "TripletsInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isPairs(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[14]), "PairsInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isFive(dice)&&isOneOne(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[12]), "FiveplusOneOneInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isFive(dice)&&isOneFive(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[12])+Integer.parseInt(pointSettings[4]), "FiveplusOneFiveInput");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isFour(dice)&&isOneFive(dice)&&isOneOne(dice)) {
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[11])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[3]), "FourplusOneOneOneFive");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
                        if(isFour(dice)&&isTwoOnes(dice)){
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[11])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3]), "FourplusTwoOnes");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));

                        }
                        if(isFour(dice)&&isTwoFives(dice)){
                            ScoringOption scoringOption =  new ScoringOption(diceRolled,Integer.parseInt(pointSettings[11])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4]), "FourplusTwoFives");
                            int index;
                            if((currTotalInt+scoringOption.scoringValue)>9950){
                                index=199;
                            }
                            else{
                                index = (currTotalInt+scoringOption.scoringValue)/50;
                            }

                            expectedValues.add(new ExpectedValues(scoringOption.name,storedValues[index][scoringOption.diceLeft-1]));
                        }
        if(expectedValues.isEmpty()){
            return "Sorry but you Farkled";
        }
        switch (expectedValues.peek().move)
        {
            case "OneOneInput" :
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])){
                    return "We recommend that you take one of the dice that are currently a 1 and bank that total!";
                }
                return "We recommend that you take one of the dice that are currently a 1 and reroll.";
            case "OneFiveInput" :
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[4])){
                    return "We recommend that you take one of the dice that are currently a 5 and bank that total!";
                }
                return "We recommend that you take one of the dice that are currently a 5 and reroll.";
            case "OneOneOneFiveInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[3])){
                    return "We recommend that you take a 1 and a 5 die and bank that total!";
                }
                return "We recommend that you take a 1 and a 5 die and reroll.";
            case "TwoFivesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])){
                    return "We recommend that you take two of the 1 dice and bank that total!";
                }
                return "We recommend that you take two of the 1 dice and reroll.";
            case "TwoOnesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])){
                    return "We recommend that you take two 1s and bank that total!";
                }
                return "We recommend that you take two 1s and reroll.";
            case "ThreeOnesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[5])){
                    return "We recommend that you take the three 1s and bank that total!";
                }
                return "We recommend that you take the three 1s and reroll.";
            case "ThreeTwosInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[6])){
                    return "We recommend that you take the three 2s and bank that total!";
                }
                return "We recommend that you take the three 2s and reroll.";
            case "ThreeThreesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[7])){
                    return "We recommend that you take the three 3s and bank that total!";
                }
                return "We recommend that you take the three 3s and reroll.";
            case "ThreeFoursInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[8])){
                    return "We recommend that you take the three 4s and bank that total!";
                }
                return "We recommend that you take the three 4s and reroll.";
            case "ThreeFivesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[9])){
                    return "We recommend that you take the three 5s and bank that total!";
                }
                return "We recommend that you take the three 5s and reroll.";
            case "ThreeSixsInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[10])){
                    return "We recommend that you take the three 6s and bank that total!";
                }
                return "We recommend that you take the three 6s and reroll.";
            case "OneOneTwoFivesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])){
                    return "We recommend that you take the one 1 and Two 5s and bank that total!";
                }
                return "We recommend that you take the three 1s and reroll.";
            case "OneFiveTwoOnesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[3]+Integer.parseInt(pointSettings[3]))){
                    return "We recommend that you take the one 5 and Two 1s and bank that total!";
                }
                return "We recommend that you take the one 5 and Two 1s  and reroll.";
            case "FourInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[11])){
                    return "We recommend that you take the Four of a kind and bank that total!";
                }
                return "We recommend that you take the Four of a kind and reroll.";

            case "TwoOnesTwoFivesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])){
                    return "We recommend that you take the Two 1s and Two 5s and bank that total!";
                }
                return "We recommend that you take the Two 1s and Two 5s and reroll.";

            case "OneOneThreeTwosInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[6])){
                    return "We recommend that you take the one 1 and Three 2s and bank that total!";
                }
                return "We recommend that you take the one 1 and Three 2s  and reroll.";

            case "OneOneThreeThreesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[7])){
                    return "We recommend that you take the one 1 and Three 3s and bank that total!";
                }
                return "We recommend that you take the one 1 and Three 3s  and reroll.";

            case "OneOneThreeFoursInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[8])){
                    return "We recommend that you take the one 1 and Three 4s and bank that total!";
                }
                return "We recommend that you take the one 1 and Three 4s  and reroll.";

            case "OneOneThreeFivesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[9])){
                    return "We recommend that you take the one 1 and Three 5s and bank that total!";
                }
                return "We recommend that you take the one 1 and Three 5s and reroll.";

            case "OneOneThreeSixsInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[10])){
                    return "We recommend that you take the one 1 and Three 6s and bank that total!";
                }
                return "We recommend that you take the one 1 and Three 6s and reroll.";

            case "OneFiveThreeOnesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[5])){
                    return "We recommend that you take the one 5 and Three 1s and bank that total!";
                }
                return "We recommend that you take the one 5 and Three 1s and reroll.";

            case "OneFiveThreeTwosInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[6])){
                    return "We recommend that you take the one 5 and Three 2s and bank that total!";
                }
                return "We recommend that you take the one 5 and Three 2s and reroll.";

            case "OneFiveThreeThreesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[7])){
                    return "We recommend that you take the one 5 and Three 3s and bank that total!";
                }
                return "We recommend that you take the one 5 and Three 3s and reroll.";

            case "OneFiveThreeFoursInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[8])){
                    return "We recommend that you take the one 5 and Three 4s and bank that total!";
                }
                return "We recommend that you take the one 5 and Three 4s and reroll.";

            case "OneFiveThreeSixsInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[10])){
                    return "We recommend that you take the one 5 and Three 6s and bank that total!";
                }
                return "We recommend that you take the one 5 and Three 6s and reroll.";


            case "FiveInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[12])){
                    return "We recommend that you take the five of a kind and bank that total!";
                }
                return "We recommend that you take the five of a kind and reroll.";

            case "FourPlusFiveInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[11])+Integer.parseInt(pointSettings[4])){
                    return "We recommend that you take the four of a kind and One 5 and bank that total!";
                }
                return "We recommend that you take the four of a kind and One 5 and reroll.";

            case "FourPlusOneInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[11])+Integer.parseInt(pointSettings[3])){
                    return "We recommend that you take the four of a kind and One 1 and bank that total!";
                }
                return "We recommend that you take the four of a kind and One 1 and reroll.";

            case "OneOneOneFiveThreeTwosInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[6])){
                    return "We recommend that you take the One 1,One 5, Three 2s and bank that total!";
                }
                return "We recommend that you take the One 1,One 5, Three 2s and reroll.";

            case "OneOneOneFiveThreeThreesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[7])){
                    return "We recommend that you take the One 1,One 5, Three 3s and bank that total!";
                }
                return "We recommend that you take the One 1,One 5, Three 3s and reroll.";

            case "OneOneOneFiveThreeFoursInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[8])){
                    return "We recommend that you take the One 1,One 5, Three 4s and bank that total!";
                }
                return "We recommend that you take the One 1,One 5, Three 4s and reroll.";

            case "OneOneOneFiveThreeSixsInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[10])){
                    return "We recommend that you take the One 1,One 5, Three 6s and bank that total!";
                }
                return "We recommend that you take the One 1,One 5, Three 6s and reroll.";

            case "TwoOnesThreeTwosInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[6])){
                    return "We recommend that you take the Two 1s and Three 2s and bank that total!";
                }
                return "We recommend that you take the Two 1s and Three 2s and reroll.";

            case "TwoOnesThreeThreesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[7])){
                    return "We recommend that you take the Two 1s and Three 3s and bank that total!";
                }
                return "We recommend that you take the Two 1s and Three 3s and reroll.";

            case "TwoOnesThreeFoursInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[8])){
                    return "We recommend that you take the Two 1s and Three 4s and bank that total!";
                }
                return "We recommend that you take the Two 1s and Three 4s and reroll.";

            case "TwoOnesThreeFivesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[9])){
                    return "We recommend that you take the Two 1s and Three 5s and bank that total!";
                }
                return "We recommend that you take the Two 1s and Three 5s and reroll.";

            case "TwoOnesThreeSixsInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[10])){
                    return "We recommend that you take the Two 1s and Three 6s and bank that total!";
                }
                return "We recommend that you take the Two 1s and Three 6s and reroll.";

            case "TwoFivesThreeOnesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[5])){
                    return "We recommend that you take the Two 5s and Three 1s and bank that total!";
                }
                return "We recommend that you take the Two 5s and Three 1s and reroll.";

            case "TwoFivesThreeTwosInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[6])){
                    return "We recommend that you take the Two 5s and Three 2s and bank that total!";
                }
                return "We recommend that you take the Two 5s and Three 2s and reroll.";

            case "TwoFivesThreeThreesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[7])){
                    return "We recommend that you take the Two 5s and Three 3s and bank that total!";
                }
                return "We recommend that you take the Two 5s and Three 3s and reroll.";

            case "TwoFivesThreeFoursInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[8])){
                    return "We recommend that you take the Two 5s and Three 4s and bank that total!";
                }
                return "We recommend that you take the Two 5s and Three 4s and reroll.";

            case "TwoFivesThreeSixsInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[10])){
                    return "We recommend that you take the Two 5s and Three 6s and bank that total!";
                }
                return "We recommend that you take the Two 5s and Three 6s and reroll.";

            case "TwoOnesOneFiveThreeTwosInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[6])){
                    return "We recommend that you take the Two 1s, One 5, and Three 2s and bank that total!";
                }
                return "We recommend that you take the Two 1s,One 5, and Three 2s and reroll.";

            case "TwoOnesOneFiveThreeThreesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[7])){
                    return "We recommend that you take the Two 1s,One 5, and Three 3s and bank that total!";
                }
                return "We recommend that you take the Two 1s,One 5, and Three 3s and reroll.";

            case "TwoOnesOneFiveThreeFoursInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[8])){
                    return "We recommend that you take the Two 1s,One 5, and Three 4s and bank that total!";
                }
                return "We recommend that you take the Two 1s,One 5, and Three 4s and reroll.";

            case "TwoOnesOneFiveThreeSixsInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[10])){
                    return "We recommend that you take the Two 1s,One 5, and Three 6s and bank that total!";
                }
                return "We recommend that you take the Two 1s,One 5, and Three 6s and reroll.";

            case "OneOneTwoFivesThreeTwosInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[6])){
                    return "We recommend that you take the One 1,Two 5s, and Three 2s and bank that total!";
                }
                return "We recommend that you take the One 1,Two 5s, and Three 2s and reroll.";

            case "OneOneTwoFivesThreeThreesInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[7])){
                    return "We recommend that you take the One 1,Two 5s, and Three 3s and bank that total!";
                }
                return "We recommend that you take the One 1,Two 5s, and Three 3s and reroll.";

            case "OneOneTwoFivesThreeFoursInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[8])){
                    return "We recommend that you take the One 1,Two 5s, and Three 4s and bank that total!";
                }
                return "We recommend that you take the One 1,Two 5s, and Three 4s and reroll.";

            case "OneOneTwoFivesThreeSixsInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[10])){
                    return "We recommend that you take the One 1,Two 5s, and Three 6s and bank that total!";
                }
                return "We recommend that you take the One 1,Two 5s, and Three 6s and reroll.";

            case "SixInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[13])){
                    return "We recommend that you take the Six of a Kind and bank that total!";
                }
                return "We recommend that you take the Six of a Kind and reroll.";

            case "StraightInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[16])){
                    return "We recommend that you take the Straight and bank that total!";
                }
                return "We recommend that you take the Straight and reroll.";

            case "TripletsInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[15])){
                    return "We recommend that you take the Triplets and bank that total!";
                }
                return "We recommend that you take the Triplets and reroll.";

            case "PairsInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[14])){
                    return "We recommend that you take the 3 Pairs and bank that total!";
                }
                return "We recommend that you take the 3 Pairs and reroll.";

            case "FiveplusOneOneInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[12])+Integer.parseInt(pointSettings[3])){
                    return "We recommend that you take the Five of a Kind,One 1, and bank that total!";
                }
                return "We recommend that you take the Five of a Kind,One 1, and reroll.";

            case "FiveplusOneFiveInput":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[12])+Integer.parseInt(pointSettings[4])){
                    return "We recommend that you take the Five of a Kind,One 5, and bank that total!";
                }
                return "We recommend that you take the Five of a Kind,One 5, and reroll.";

            case "FourplusOneOneOneFive":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[11])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4])){
                    return "We recommend that you take the Four of a Kind,One 1, and bank that total!";
                }
                return "We recommend that you take the Four of a Kind,One 1, and reroll.";

            case "FourplusTwoOnes":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[11])+Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[3])){
                    return "We recommend that you take the Four of a Kind,Two 1s, and bank that total!";
                }
                return "We recommend that you take the Four of a Kind,Two 1s, and reroll.";

            case "FourplusTwoFives":
                if(expectedValues.peek().expectedValue==currTotalInt+Integer.parseInt(pointSettings[11])+Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4])){
                    return "We recommend that you take the Four of a Kind,Two 5s, and bank that total!";
                }
                return "We recommend that you take the Four of a Kind,Two 5s, and reroll.";

            default:
                return "Error";

        }
    }
    public static void setExpectedValues(){
    for(int i = 0; i<storedValues.length; i++){
        for(int j = 0; j<storedValues[i].length; j++){
            storedValues[i][j] = -1;
        }
    }
    for(int i = 9950; i>=0; i-=minPointIncrement){
        System.out.print(i + "   ");
        for(int j = 1; j<=6; j++){
            storedValues[i/minPointIncrement][j-1] = ExpectedValue(i,j);
        }

    }

}

    public static double ExpectedValue(double currPoint, int diceLeft) {
        if (currPoint >= winTotal) {
            return currPoint;
        }
        int pointIndex = ((int)currPoint)/50;
        if(storedValues[pointIndex][diceLeft-1]!=-1){
            return storedValues[pointIndex][diceLeft-1];
        }
        /*if(diceLeft == 0){
            return currPoint+bonusRoll;
        }*/
        double s = S(currPoint, diceLeft);
        if (currPoint < bankTotal || s > 0) {
            return currPoint + s;
        } else {
            return currPoint;
        }
    }

    public static double S(double currPoint, int diceLeft) {
        if (currPoint > winTotal) {
            return -1;
        }
        double total = 0;
        switch (diceLeft) {
            case 1:
                for(int i = 0; i<caseOne[0].length; i++){
                    total += caseOne[0][i]*ExpectedValue(currPoint+caseOne[1][i],caseOne[2][i])/dividedNums[0];
                }
                break;
            case 2:
                for(int i = 0; i<caseTwo[0].length; i++){
                    total += caseTwo[0][i]*ExpectedValue(currPoint+caseTwo[1][i],caseTwo[2][i])/dividedNums[1];
                }
                break;
            case 3:
                for(int i = 0; i<caseThree[0].length; i++){
                    total += caseThree[0][i]*ExpectedValue(currPoint+caseThree[1][i],caseThree[2][i])/dividedNums[2];
                }
                break;
            case 4:
                for(int i = 0; i<caseFour[0].length; i++){
                    total += caseFour[0][i]*ExpectedValue(currPoint+caseFour[1][i],caseFour[2][i])/dividedNums[3];
                }
                break;
            case 5:
                for(int i = 0; i<caseFive[0].length; i++){
                    total += caseFive[0][i]*ExpectedValue(currPoint+caseFive[1][i],caseFive[2][i])/dividedNums[4];
                }
                break;
            case 6:
                for(int i = 0; i<caseSix[0].length; i++){
                    total += caseSix[0][i]*ExpectedValue(currPoint+caseSix[1][i],caseSix[2][i])/dividedNums[5];
                }
                break;
            default:
                break;
        }
        return total-currPoint;
    }
/*
    public static void getTable(String[] nameSettings, String[] pointSettings) {
        winTotal = Integer.parseInt(pointSettings[0]) - startTotal;
        if(startTotal != 0){
            bankTotal = Integer.parseInt(pointSettings[1]);
        }
        else{
            bankTotal = Integer.parseInt(pointSettings[2]);
        }
        fillCases(nameSettings,pointSettings);
        //(re)initialize table
        for (int i = 0; i < storedValues.length; i++) {
            for (int j = 0; j < storedValues[i].length; j++) {
                storedValues[i][j] = -1;
            }
        }
        //Loop through all current point total possibilities
        for (int i = winTotal - minPointIncrement; i >= startTotal; i -= minPointIncrement) {
            //Loop through all dice rolled possibilities
            for (int j = 1; j <= 6; j++) {
                //store the expected value
                storedValues[i / minPointIncrement][j - 1] = ExpectedValue(i, j);
            }
        }
    }

    public static double ExpectedValue(double currPoint, int diceLeft) {
        //if you have won stop
        if (currPoint >= winTotal) {
            return currPoint;
        }
        int pointIndex = ((int) currPoint) / 50;
        if (storedValues[pointIndex][diceLeft - 1] != -1) {
            return storedValues[pointIndex][diceLeft - 1];
        }
        double s = S(currPoint, diceLeft);
        if (currPoint < bankTotal || s > 0) {
            return currPoint + s;
        } else {
            return currPoint;
        }
    }

    public static double S(double currPoint, int diceLeft) {
        if (currPoint > winTotal) {
            return -1;
        }
        double total = 0;
        switch (diceLeft) {
            case 1:
                for (int i = 0; i < caseOne[0].length; i++) {
                    total += caseOne[0][i] * ExpectedValue(currPoint + caseOne[1][i], caseOne[2][i]) / dividedNums[0];
                }
                break;
            case 2:
                for (int i = 0; i < caseTwo[0].length; i++) {
                    total += caseTwo[0][i] * ExpectedValue(currPoint + caseTwo[1][i], caseTwo[2][i]) / dividedNums[1];
                }
                break;
            case 3:
                for (int i = 0; i < caseThree[0].length; i++) {
                    total += caseThree[0][i] * ExpectedValue(currPoint + caseThree[1][i], caseThree[2][i]) / dividedNums[2];
                }
                break;
            case 4:
                for (int i = 0; i < caseFour[0].length; i++) {
                    total += caseFour[0][i] * ExpectedValue(currPoint + caseFour[1][i], caseFour[2][i]) / dividedNums[3];
                }
                break;
            case 5:
                for (int i = 0; i < caseFive[0].length; i++) {
                    total += caseFive[0][i] * ExpectedValue(currPoint + caseFive[1][i], caseFive[2][i]) / dividedNums[4];
                }
                break;
            case 6:
                for (int i = 0; i < caseSix[0].length; i++) {
                    total += caseSix[0][i] * ExpectedValue(currPoint + caseSix[1][i], caseSix[2][i]) / dividedNums[5];
                }
                break;
            default:
                break;
        }
        return total - currPoint;
    }
    public static void fillCases(String[] nameSettings, String[] pointSettings){
        SortedLinkedList scoringOptionsCase1 = new SortedLinkedList();
        SortedLinkedList scoringOptionsCase2= new SortedLinkedList();
        SortedLinkedList scoringOptionsCase3= new SortedLinkedList();
        SortedLinkedList scoringOptionsCase4= new SortedLinkedList();
        SortedLinkedList scoringOptionsCase5= new SortedLinkedList();
        SortedLinkedList scoringOptionsCase6= new SortedLinkedList();

        int increment= 1;
        ScoringOption temp;
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4]), "OneOneTwoFivesInput");
        scoringOptionsCase1.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[3]), "OneFiveTwoOnesInput");
        scoringOptionsCase1.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]), "OneOneOneFiveInput");
        scoringOptionsCase1.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]), "TwoOnesInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]), "TwoFivesInput");
        scoringOptionsCase1.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]), "TwoOnesTwoFives");
        scoringOptionsCase1.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[6]), "OneOneThreeTwosInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[7]), "OneOneThreeThreesInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[8]), "OneOneThreeFoursInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[9]), "OneOneThreeFivesInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[10]), "OneOneThreeSixsInput");
        scoringOptionsCase1.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[5]), "OneFiveThreeOnesInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneFiveThreeTwosInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneFiveThreeThreesInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneFiveThreeFoursInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneFiveThreeSixsInput");
        scoringOptionsCase1.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourPlusFiveInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[11]), "FourPlusOneInput");
        scoringOptionsCase1.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneOneOneFiveThreeTwosInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneOneOneFiveThreeThreesInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneOneOneFiveThreeFoursInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneOneOneFiveThreeSixsInput");
        scoringOptionsCase1.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[6]), "TwoOnesThreeTwosInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[7]), "TwoOnesThreeThreesInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[8]), "TwoOnesThreeFoursInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[9]), "TwoOnesThreeFivesInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[10]), "TwoOnesThreeSixsInput");
        scoringOptionsCase1.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[5]), "TwoFivesThreeOnesInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "TwoFivesThreeTwosInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "TwoFivesThreeThreesInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "TwoFivesThreeFoursInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "TwoFivesThreeSixsInput");
        scoringOptionsCase1.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "TwoOnesOneFiveThreeTwosInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "TwoOnesOneFiveThreeThreesInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "TwoOnesOneFiveThreeFoursInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "TwoOnesOneFiveThreeSixsInput");
        scoringOptionsCase1.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneOneTwoFivesThreeTwosInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneOneTwoFivesThreeThreesInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneOneTwoFivesThreeFoursInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneOneTwoFivesThreeSixsInput");
        scoringOptionsCase1.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[12]), "FiveplusOneOneInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[12]), "FiveplusOneFiveInput");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourplusOneOneOneFive");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) +Integer.parseInt(pointSettings[11]), "FourplusTwoOnes");
        scoringOptionsCase1.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourplusTwoFives");
        scoringOptionsCase1.add(temp);
        for(int j = 3; j<pointSettings.length; j++) {
            scoringOptionsCase1.add(new ScoringOption(increment, Integer.parseInt(pointSettings[j]), nameSettings[j]));
        }

        increment= 2;
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4]), "OneOneTwoFivesInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[3]), "OneFiveTwoOnesInput");
        scoringOptionsCase2.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]), "OneOneOneFiveInput");
        scoringOptionsCase2.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]), "TwoOnesInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]), "TwoFivesInput");
        scoringOptionsCase2.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]), "TwoOnesTwoFives");
        scoringOptionsCase2.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[6]), "OneOneThreeTwosInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[7]), "OneOneThreeThreesInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[8]), "OneOneThreeFoursInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[9]), "OneOneThreeFivesInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[10]), "OneOneThreeSixsInput");
        scoringOptionsCase2.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[5]), "OneFiveThreeOnesInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneFiveThreeTwosInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneFiveThreeThreesInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneFiveThreeFoursInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneFiveThreeSixsInput");
        scoringOptionsCase2.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourPlusFiveInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[11]), "FourPlusOneInput");
        scoringOptionsCase2.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneOneOneFiveThreeTwosInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneOneOneFiveThreeThreesInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneOneOneFiveThreeFoursInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneOneOneFiveThreeSixsInput");
        scoringOptionsCase2.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[6]), "TwoOnesThreeTwosInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[7]), "TwoOnesThreeThreesInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[8]), "TwoOnesThreeFoursInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[9]), "TwoOnesThreeFivesInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[10]), "TwoOnesThreeSixsInput");
        scoringOptionsCase2.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[5]), "TwoFivesThreeOnesInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "TwoFivesThreeTwosInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "TwoFivesThreeThreesInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "TwoFivesThreeFoursInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "TwoFivesThreeSixsInput");
        scoringOptionsCase2.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "TwoOnesOneFiveThreeTwosInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "TwoOnesOneFiveThreeThreesInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "TwoOnesOneFiveThreeFoursInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "TwoOnesOneFiveThreeSixsInput");
        scoringOptionsCase2.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneOneTwoFivesThreeTwosInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneOneTwoFivesThreeThreesInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneOneTwoFivesThreeFoursInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneOneTwoFivesThreeSixsInput");
        scoringOptionsCase2.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[12]), "FiveplusOneOneInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[12]), "FiveplusOneFiveInput");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourplusOneOneOneFive");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) +Integer.parseInt(pointSettings[11]), "FourplusTwoOnes");
        scoringOptionsCase2.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourplusTwoFives");
        scoringOptionsCase2.add(temp);
        for(int j = 3; j<pointSettings.length; j++) {
            scoringOptionsCase2.add(new ScoringOption(increment, Integer.parseInt(pointSettings[j]), nameSettings[j]));
        }

        increment= 3;
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4]), "OneOneTwoFivesInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[3]), "OneFiveTwoOnesInput");
        scoringOptionsCase3.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]), "OneOneOneFiveInput");
        scoringOptionsCase3.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]), "TwoOnesInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]), "TwoFivesInput");
        scoringOptionsCase3.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]), "TwoOnesTwoFives");
        scoringOptionsCase3.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[6]), "OneOneThreeTwosInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[7]), "OneOneThreeThreesInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[8]), "OneOneThreeFoursInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[9]), "OneOneThreeFivesInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[10]), "OneOneThreeSixsInput");
        scoringOptionsCase3.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[5]), "OneFiveThreeOnesInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneFiveThreeTwosInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneFiveThreeThreesInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneFiveThreeFoursInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneFiveThreeSixsInput");
        scoringOptionsCase3.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourPlusFiveInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[11]), "FourPlusOneInput");
        scoringOptionsCase3.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneOneOneFiveThreeTwosInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneOneOneFiveThreeThreesInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneOneOneFiveThreeFoursInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneOneOneFiveThreeSixsInput");
        scoringOptionsCase3.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[6]), "TwoOnesThreeTwosInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[7]), "TwoOnesThreeThreesInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[8]), "TwoOnesThreeFoursInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[9]), "TwoOnesThreeFivesInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[10]), "TwoOnesThreeSixsInput");
        scoringOptionsCase3.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[5]), "TwoFivesThreeOnesInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "TwoFivesThreeTwosInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "TwoFivesThreeThreesInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "TwoFivesThreeFoursInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "TwoFivesThreeSixsInput");
        scoringOptionsCase3.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "TwoOnesOneFiveThreeTwosInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "TwoOnesOneFiveThreeThreesInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "TwoOnesOneFiveThreeFoursInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "TwoOnesOneFiveThreeSixsInput");
        scoringOptionsCase3.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneOneTwoFivesThreeTwosInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneOneTwoFivesThreeThreesInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneOneTwoFivesThreeFoursInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneOneTwoFivesThreeSixsInput");
        scoringOptionsCase3.add(temp);


        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[12]), "FiveplusOneOneInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[12]), "FiveplusOneFiveInput");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourplusOneOneOneFive");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) +Integer.parseInt(pointSettings[11]), "FourplusTwoOnes");
        scoringOptionsCase3.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourplusTwoFives");
        scoringOptionsCase3.add(temp);
        for(int j = 3; j<pointSettings.length; j++) {
            scoringOptionsCase3.add(new ScoringOption(increment, Integer.parseInt(pointSettings[j]), nameSettings[j]));
        }

        increment= 4;
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4]), "OneOneTwoFivesInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[3]), "OneFiveTwoOnesInput");
        scoringOptionsCase4.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]), "OneOneOneFiveInput");
        scoringOptionsCase4.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]), "TwoOnesInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]), "TwoFivesInput");
        scoringOptionsCase4.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]), "TwoOnesTwoFivesInput");
        scoringOptionsCase4.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[6]), "OneOneThreeTwosInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[7]), "OneOneThreeThreesInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[8]), "OneOneThreeFoursInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[9]), "OneOneThreeFivesInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[10]), "OneOneThreeSixsInput");
        scoringOptionsCase4.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[5]), "OneFiveThreeOnesInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneFiveThreeTwosInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneFiveThreeThreesInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneFiveThreeFoursInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneFiveThreeSixsInput");
        scoringOptionsCase4.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourPlusFiveInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[11]), "FourPlusOneInput");
        scoringOptionsCase4.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneOneOneFiveThreeTwosInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneOneOneFiveThreeThreesInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneOneOneFiveThreeFoursInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneOneOneFiveThreeSixsInput");
        scoringOptionsCase4.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[6]), "TwoOnesThreeTwosInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[7]), "TwoOnesThreeThreesInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[8]), "TwoOnesThreeFoursInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[9]), "TwoOnesThreeFivesInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[10]), "TwoOnesThreeSixsInput");
        scoringOptionsCase4.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[5]), "TwoFivesThreeOnesInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "TwoFivesThreeTwosInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "TwoFivesThreeThreesInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "TwoFivesThreeFoursInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "TwoFivesThreeSixsInput");
        scoringOptionsCase4.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "TwoOnesOneFiveThreeTwosInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "TwoOnesOneFiveThreeThreesInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "TwoOnesOneFiveThreeFoursInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "TwoOnesOneFiveThreeSixsInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneOneTwoFivesThreeTwosInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneOneTwoFivesThreeThreesInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneOneTwoFivesThreeFoursInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneOneTwoFivesThreeSixsInput");
        scoringOptionsCase4.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[12]), "FiveplusOneOneInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[12]), "FiveplusOneFiveInput");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourplusOneOneOneFive");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) +Integer.parseInt(pointSettings[11]), "FourplusTwoOnes");
        scoringOptionsCase4.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourplusTwoFives");
        scoringOptionsCase4.add(temp);
        for(int j = 3; j<pointSettings.length; j++) {
            scoringOptionsCase4.add(new ScoringOption(increment, Integer.parseInt(pointSettings[j]), nameSettings[j]));
        }

        increment= 5;
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4]), "OneOneTwoFivesInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[3]), "OneFiveTwoOnesInput");
        scoringOptionsCase5.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]), "OneOneOneFiveInput");
        scoringOptionsCase5.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]), "TwoOnesInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]), "TwoFivesInput");
        scoringOptionsCase5.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]), "TwoOnesTwoFivesInput");
        scoringOptionsCase5.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[6]), "OneOneThreeTwosInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[7]), "OneOneThreeThreesInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[8]), "OneOneThreeFoursInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[9]), "OneOneThreeFivesInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[10]), "OneOneThreeSixsInput");
        scoringOptionsCase5.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[5]), "OneFiveThreeOnesInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneFiveThreeTwosInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneFiveThreeThreesInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneFiveThreeFoursInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneFiveThreeSixsInput");
        scoringOptionsCase5.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourPlusFiveInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[11]), "FourPlusOneInput");
        scoringOptionsCase5.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneOneOneFiveThreeTwosInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneOneOneFiveThreeThreesInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneOneOneFiveThreeFoursInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneOneOneFiveThreeSixsInput");
        scoringOptionsCase5.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[6]), "TwoOnesThreeTwosInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[7]), "TwoOnesThreeThreesInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[8]), "TwoOnesThreeFoursInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[9]), "TwoOnesThreeFivesInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[10]), "TwoOnesThreeSixsInput");
        scoringOptionsCase5.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[5]), "TwoFivesThreeOnesInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "TwoFivesThreeTwosInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "TwoFivesThreeThreesInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "TwoFivesThreeFoursInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "TwoFivesThreeSixsInput");
        scoringOptionsCase5.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "TwoOnesOneFiveThreeTwosInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "TwoOnesOneFiveThreeThreesInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "TwoOnesOneFiveThreeFoursInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "TwoOnesOneFiveThreeSixsInput");
        scoringOptionsCase5.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneOneTwoFivesThreeTwosInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneOneTwoFivesThreeThreesInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneOneTwoFivesThreeFoursInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneOneTwoFivesThreeSixsInput");
        scoringOptionsCase5.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[12]), "FiveplusOneOneInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[12]), "FiveplusOneFiveInput");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourplusOneOneOneFive");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) +Integer.parseInt(pointSettings[11]), "FourplusTwoOnes");
        scoringOptionsCase5.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourplusTwoFives");
        scoringOptionsCase5.add(temp);
        for(int j = 3; j<pointSettings.length; j++) {
            scoringOptionsCase5.add(new ScoringOption(increment, Integer.parseInt(pointSettings[j]), nameSettings[j]));
        }

        increment= 6;
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[4]), "OneOneTwoFivesInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+Integer.parseInt(pointSettings[3]), "OneFiveTwoOnesInput");
        scoringOptionsCase6.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]), "OneOneOneFiveInput");
        scoringOptionsCase6.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]), "TwoOnesInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]), "TwoFivesInput");
        scoringOptionsCase6.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]), "TwoOnesTwoFivesInput");
        scoringOptionsCase6.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[6]), "OneOneThreeTwosInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[7]), "OneOneThreeThreesInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[8]), "OneOneThreeFoursInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[9]), "OneOneThreeFivesInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[10]), "OneOneThreeSixsInput");
        scoringOptionsCase6.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[5]), "OneFiveThreeOnesInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneFiveThreeTwosInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneFiveThreeThreesInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneFiveThreeFoursInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneFiveThreeSixsInput");
        scoringOptionsCase6.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourPlusFiveInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[11]), "FourPlusOneInput");
        scoringOptionsCase6.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneOneOneFiveThreeTwosInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneOneOneFiveThreeThreesInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneOneOneFiveThreeFoursInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneOneOneFiveThreeSixsInput");
        scoringOptionsCase6.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[6]), "TwoOnesThreeTwosInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[7]), "TwoOnesThreeThreesInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[8]), "TwoOnesThreeFoursInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[9]), "TwoOnesThreeFivesInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[10]), "TwoOnesThreeSixsInput");
        scoringOptionsCase6.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[5]), "TwoFivesThreeOnesInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "TwoFivesThreeTwosInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "TwoFivesThreeThreesInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "TwoFivesThreeFoursInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "TwoFivesThreeSixsInput");
        scoringOptionsCase6.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "TwoOnesOneFiveThreeTwosInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "TwoOnesOneFiveThreeThreesInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "TwoOnesOneFiveThreeFoursInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "TwoOnesOneFiveThreeSixsInput");
        scoringOptionsCase6.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[6]), "OneOneTwoFivesThreeTwosInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[7]), "OneOneTwoFivesThreeThreesInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[8]), "OneOneTwoFivesThreeFoursInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4])+ Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[10]), "OneOneTwoFivesThreeSixsInput");
        scoringOptionsCase6.add(temp);

        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[12]), "FiveplusOneOneInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[12]), "FiveplusOneFiveInput");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourplusOneOneOneFive");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[3]) + Integer.parseInt(pointSettings[3]) +Integer.parseInt(pointSettings[11]), "FourplusTwoOnes");
        scoringOptionsCase6.add(temp);
        temp= new ScoringOption(increment, Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[4]) + Integer.parseInt(pointSettings[11]), "FourplusTwoFives");
        scoringOptionsCase6.add(temp);
        for(int j = 3; j<pointSettings.length; j++) {
            scoringOptionsCase6.add(new ScoringOption(increment, Integer.parseInt(pointSettings[j]), nameSettings[j]));
        }

        for(int i=1;i<=6;i++) {
            int dice[] = {0, 0, 0, 0, 0, 0};
            dice[i - 1]++;
            //System.out.println(dice[0] + " "+dice[1] + " "+dice[2] + " "+dice[3] + " "+dice[4] + " "+dice[5]);
            updateCase(scoringOptionsCase1,dice);
        }
        for(int i=1;i<=6;i++) {
            for (int j = 1; j <= 6; j++) {
                int dice[] = {0, 0, 0, 0, 0, 0};
                dice[j - 1]++;
                dice[i - 1]++;
                updateCase(scoringOptionsCase2,dice);
            }
        }
        for(int i=1;i<=6;i++) {
            for (int j = 1; j <= 6; j++) {
                for (int k = 1; k <= 6; k++) {
                    int dice[] = {0, 0, 0, 0, 0, 0};
                    dice[k - 1]++;
                    dice[j - 1]++;
                    dice[i - 1]++;
                    updateCase(scoringOptionsCase3,dice);
                }
            }
        }
        for(int i=1;i<=6;i++) {
            for (int j = 1; j <= 6; j++) {
                for (int k = 1; k <= 6; k++) {
                    for (int l = 1; l <= 6; l++) {
                        int dice[] = {0, 0, 0, 0, 0, 0};
                        dice[l - 1]++;
                        dice[k - 1]++;
                        dice[j - 1]++;
                        dice[i - 1]++;
                        updateCase(scoringOptionsCase4,dice);
                    }
                }
            }
        }
        for(int i=1;i<=6;i++) {
            for (int j = 1; j <= 6; j++) {
                for (int k = 1; k <= 6; k++) {
                    for (int l = 1; l <= 6; l++) {
                        for (int m = 1; m <= 6; m++) {
                            int dice[] = {0, 0, 0, 0, 0, 0};
                            dice[l - 1]++;
                            dice[m - 1]++;
                            dice[k - 1]++;
                            dice[j - 1]++;
                            dice[i - 1]++;
                            updateCase(scoringOptionsCase5,dice);
                        }
                    }
                }
            }
        }
        for(int i=1;i<=6;i++) {
            for (int j = 1; j <= 6; j++) {
                for (int k = 1; k <= 6; k++) {
                    for (int l = 1; l <= 6; l++) {
                        for (int m = 1; m <= 6; m++) {
                            for (int n = 1; n <= 6; n++) {
                                int dice[] = {0, 0, 0, 0, 0, 0};
                                dice[l - 1]++;
                                dice[m - 1]++;
                                dice[k - 1]++;
                                dice[j - 1]++;
                                dice[i - 1]++;
                                dice[n - 1]++;
                                updateCase(scoringOptionsCase6,dice);
                            }
                        }
                    }
                }
            }
        }
        int caseOneIncrement = 0;
        int caseTwoIncrement = 0;
        int caseThreeIncrement = 0;
        int caseFourIncrement = 0;
        int caseFiveIncrement = 0;
        int caseSixIncrement = 0;
        for(int i = 0; i<scoringOptionsCase1.size(); i++){
            //System.out.println("Name: " + scoringOptionsCase5.getName(i) + "Value: " + scoringOptionsCase5.getValue(i) + " Occurances: " + scoringOptionsCase5.getOccurances(i) + " Dice Left: " + scoringOptionsCase5.getDice(i));
           /*System.out.println("Value: " + scoringOptionsCase2.getValue(i) + " Occurances: " + scoringOptionsCase2.getOccurances(i) + " Dice Left: " + scoringOptionsCase2.getDice(i));
           System.out.println("Value: " + scoringOptionsCase3.getValue(i) + " Occurances: " + scoringOptionsCase3.getOccurances(i) + " Dice Left: " + scoringOptionsCase3.getDice(i));
           System.out.println();
            if(scoringOptionsCase1.getOccurances(i)>0){
                caseOne[0][caseOneIncrement] = scoringOptionsCase1.getOccurances(i);
                caseOne[1][caseOneIncrement] = scoringOptionsCase1.getValue(i);
                caseOne[2][caseOneIncrement] = scoringOptionsCase1.getDice(i);
                caseOneIncrement++;
            }
            if(scoringOptionsCase2.getOccurances(i)>0){
                caseTwo[0][caseTwoIncrement] = scoringOptionsCase2.getOccurances(i);
                caseTwo[1][caseTwoIncrement] = scoringOptionsCase2.getValue(i);
                caseTwo[2][caseTwoIncrement] = scoringOptionsCase2.getDice(i);
                caseTwoIncrement++;
            }
            if(scoringOptionsCase3.getOccurances(i)>0){
                caseThree[0][caseThreeIncrement] = scoringOptionsCase3.getOccurances(i);
                caseThree[1][caseThreeIncrement] = scoringOptionsCase3.getValue(i);
                caseThree[2][caseThreeIncrement] = scoringOptionsCase3.getDice(i);
                caseThreeIncrement++;
            }
            if(scoringOptionsCase4.getOccurances(i)>0){
                caseFour[0][caseFourIncrement] = scoringOptionsCase4.getOccurances(i);
                caseFour[1][caseFourIncrement] = scoringOptionsCase4.getValue(i);
                caseFour[2][caseFourIncrement] = scoringOptionsCase4.getDice(i);
                caseFourIncrement++;
            }
            if(scoringOptionsCase5.getOccurances(i)>0){
                caseFive[0][caseFiveIncrement] = scoringOptionsCase5.getOccurances(i);
                caseFive[1][caseFiveIncrement] = scoringOptionsCase5.getValue(i);
                caseFive[2][caseFiveIncrement] = scoringOptionsCase5.getDice(i);
                caseFiveIncrement++;
            }
            if(scoringOptionsCase6.getOccurances(i)>0){
                caseSix[0][caseSixIncrement] = scoringOptionsCase6.getOccurances(i);
                caseSix[1][caseSixIncrement] = scoringOptionsCase6.getValue(i);
                caseSix[2][caseSixIncrement] = scoringOptionsCase6.getDice(i);
                caseSixIncrement++;
            }
        }
    }

    public static void updateCase(SortedLinkedList scoringOptions, int[] currRoll){
        for(int i= 0; i<scoringOptions.size(); i++){
            switch(scoringOptions.getName(i)){
                case "OneOneInput" :
                    if(isOneOne(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneFiveInput" :
                    if(isOneFive(currRoll)) {
                        //System.out.println(currRoll[0]+ " " + currRoll[1]+ " " + currRoll[2]+ " " + currRoll[3]+ " " + currRoll[4]+ " " + currRoll[5]);
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneOneOneFiveInput":
                    if(isOneOne(currRoll)&&isOneFive(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TwoFivesInput":
                    if(isTwoFives(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TwoOnesInput":
                    if(isTwoOnes(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "ThreeOnesInput":
                    if(isThreeOnes(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "ThreeTwosInput":
                    if(isThreeTwos(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "ThreeThreesInput":
                    if(isThreeThree(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "ThreeFoursInput":
                    if(isThreeFours(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "ThreeFivesInput":
                    if(isThreeFives(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "ThreeSixsInput":
                    if(isThreeSixs(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneOneTwoFivesInput":
                    if(isOneOne(currRoll) && isTwoFives(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneFiveTwoOnesInput":
                    if(isOneFive(currRoll) && isTwoOnes(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "FourInput":
                    if(isFour(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TwoOnesTwoFivesInput":
                    if(isTwoOnes(currRoll) && isTwoFives(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneOneThreeTwosInput":
                    if(isOneOne(currRoll) && isThreeTwos(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneOneThreeThreesInput":
                    if(isOneOne(currRoll) && isThreeThree(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneOneThreeFoursInput":
                    if(isOneOne(currRoll) && isThreeFours(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneOneThreeFivesInput":
                    if(isOneOne(currRoll) && isThreeFives(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneOneThreeSixsInput":
                    if(isOneOne(currRoll) && isThreeSixs(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneFiveThreeOnesInput":
                    if(isOneFive(currRoll) && isThreeOnes(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneFiveThreeTwosInput":
                    if(isOneFive(currRoll) && isThreeTwos(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneFiveThreeThreesInput":
                    if(isOneFive(currRoll) && isThreeThree(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneFiveThreeFoursInput":
                    if(isOneFive(currRoll) && isThreeFours(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneFiveThreeSixsInput":
                    if(isOneFive(currRoll) && isThreeSixs(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "FiveInput":
                    if(isFive(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "FourPlusFiveInput":
                    if(isFour(currRoll)&&isOneFive(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "FourPlusOneInput":
                    if(isFour(currRoll)&&isOneOne(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneOneOneFiveThreeTwosInput":
                    if(isOneOne(currRoll) && isOneFive(currRoll) && isThreeTwos(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneOneOneFiveThreeThreesInput":
                    if(isOneOne(currRoll) && isOneFive(currRoll) && isThreeThree(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneOneOneFiveThreeFoursInput":
                    if(isOneOne(currRoll) && isOneFive(currRoll) && isThreeFours(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneOneOneFiveThreeSixsInput":
                    if(isOneOne(currRoll) && isOneFive(currRoll) && isThreeSixs(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TwoOnesThreeTwosInput":
                    if(isTwoOnes(currRoll) && isThreeTwos(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TwoOnesThreeThreesInput":
                    if(isTwoOnes(currRoll) && isThreeThree(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TwoOnesThreeFoursInput":
                    if(isTwoOnes(currRoll) && isThreeFours(currRoll)) {
                        scoringOptions.addOccurance(i);
                    }
                    break;
                case "TwoOnesThreeFivesInput":
                    if(isTwoOnes(currRoll) && isThreeFives(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TwoOnesThreeSixsInput":
                    if(isTwoOnes(currRoll) && isThreeSixs(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TwoFivesThreeOnesInput":
                    if(isTwoFives(currRoll) && isThreeOnes(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TwoFivesThreeTwosInput":
                    if(isTwoFives(currRoll) && isThreeTwos(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TwoFivesThreeThreesInput":
                    if(isTwoFives(currRoll) && isThreeThree(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TwoFivesThreeFoursInput":
                    if(isTwoFives(currRoll) && isThreeFours(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TwoFivesThreeSixsInput":
                    if(isTwoFives(currRoll) && isThreeSixs(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TwoOnesOneFiveThreeTwosInput":
                    if(isTwoOnes(currRoll) && isOneFive(currRoll) && isThreeTwos(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TwoOnesOneFiveThreeThreesInput":
                    if(isTwoOnes(currRoll) && isOneFive(currRoll) && isThreeThree(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TwoOnesOneFiveThreeFoursInput":
                    if(isTwoOnes(currRoll) && isOneFive(currRoll) && isThreeFours(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TwoOnesOneFiveThreeSixsInput":
                    if(isTwoOnes(currRoll) && isOneFive(currRoll) && isThreeSixs(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneOneTwoFivesThreeTwosInput":
                    if(isOneOne(currRoll) && isTwoFives(currRoll) && isThreeTwos(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneOneTwoFivesThreeThreesInput":
                    if(isOneOne(currRoll) && isTwoFives(currRoll) && isThreeThree(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneOneTwoFivesThreeFoursInput":
                    if(isOneOne(currRoll) && isTwoFives(currRoll) && isThreeFours(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "OneOneTwoFivesThreeSixsInput":
                    if(isOneOne(currRoll) && isTwoFives(currRoll) && isThreeSixs(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "SixInput":
                    if(isSix(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "StraightInput":
                    if(isStraight(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "TripletsInput":
                    if(isTriplets(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "PairsInput":
                    if(isPairs(currRoll)) {
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "FiveplusOneOneInput":
                    if(isFive(currRoll)&&isOneOne(currRoll)){
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "FiveplusOneFiveInput":
                    if(isFive(currRoll)&&isOneFive(currRoll)){
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "FourplusOneOneOneFive":
                    if(isFour(currRoll)&&isOneFive(currRoll)&&isOneOne(currRoll)){
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "FourplusTwoOnes":
                    if(isFour(currRoll)&&isTwoOnes(currRoll)){
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                case "FourplusTwoFives":
                    if(isFour(currRoll)&&isTwoFives(currRoll)){
                        scoringOptions.addOccurance(i);
                        return;
                    }
                    break;
                default: break;
            }
        }

    }*/

    public static boolean isOneOne(int[] currRoll){
        if(currRoll[0]>=1){
            return true;
        }
        return false;
    }
    public static boolean isTwoOnes(int[] currRoll){
        if(currRoll[0]>=2){
            return true;
        }
        return false;
    }
    public static boolean isOneFive(int[] currRoll){
        if(currRoll[4]>=1){
            return true;
        }
        return false;
    }
    public static boolean isTwoFives(int[] currRoll){
        if(currRoll[4]>=2){
            return true;
        }
        return false;
    }
    public static boolean isThreeOnes(int[] currRoll){
        if(currRoll[0]>=3){
            return true;
        }
        return false;
    }
    public static boolean isThreeTwos(int[] currRoll){
        if(currRoll[1]>=3){
            return true;
        }
        return false;
    }
    public static boolean isThreeThree(int[] currRoll){
        if(currRoll[2]>=3){
            return true;
        }
        return false;
    }
    public static boolean isThreeFours(int[] currRoll){
        if(currRoll[3]>=3){
            return true;
        }
        return false;
    }
    public static boolean isThreeFives(int[] currRoll){
        if(currRoll[4]>=3){
            return true;
        }
        return false;
    }
    public static boolean isThreeSixs(int[] currRoll){
        if(currRoll[5]>=3){
            return true;
        }
        return false;
    }
    public static boolean isFour(int[] currRoll){
        if(currRoll[0]==4||currRoll[1]==4||currRoll[2]==4||currRoll[3]==4||currRoll[4]==4||currRoll[5]==4){
            return true;
        }
        return false;
    }
    public static boolean isFive(int[] currRoll){
        if(currRoll[0]==5||currRoll[1]==5||currRoll[2]==5||currRoll[3]==5||currRoll[4]==5||currRoll[5]==5){
            return true;
        }
        return false;
    }
    public static boolean isSix(int[] currRoll){
        if(currRoll[0]==6||currRoll[1]==6||currRoll[2]==6||currRoll[3]==6||currRoll[4]==6||currRoll[5]==6){
            return true;
        }
        return false;
    }
    public static boolean isPairs(int[] currRoll){
        if(isSix(currRoll)){
            return true;
        }
        if(isFour(currRoll)){
            if(currRoll[0]==2||currRoll[1]==2||currRoll[2]==2||currRoll[3]==2||currRoll[4]==2||currRoll[5]==2){
                return true;
            }
        }
        for(int i = 0; i<6;i++){
            if(currRoll[i]==2){
                i++;
                while(i<6){
                    if(currRoll[i]==2) {
                        i++;
                        while (i < 6) {
                            if(currRoll[i]==2) {
                                return true;
                            }
                            i++;
                        }
                    }
                    i++;
                }
            }
        }
        return false;
    }
    public static boolean isTriplets(int[] currRoll){
        for(int i = 0; i<6;i++){
            if(currRoll[i]==3){
                i++;
                while(i<6){
                    if(currRoll[i]==3) {
                        return true;
                    }
                    i++;
                }
            }
        }
        return false;
    }
    public static boolean isStraight(int[] currRoll){
        if(currRoll[0]==1&&currRoll[1]==1&&currRoll[2]==1&&currRoll[3]==1&&currRoll[4]==1&&currRoll[5]==1){
            return true;
        }
        return false;
    }
}


