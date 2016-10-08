package com.example.espon.farklenation;

public class ScoringOption implements Comparable<ScoringOption> {
    int diceRolled;//pass in
    int scoringValue ;//pass in
    String name ;//pass in
    int occurances;// 0
    int diceLeft;//call set dice_left
    ScoringOption(int rolled,int value, String n)
    {
        diceRolled=rolled;
        scoringValue=value;
        name=n;
        occurances=0;
        diceLeft=getdiceLeft();
    }

    public int getdiceLeft()
    {
        int diceleft=-1;
        //System.out.println(diceRolled);
        switch (name)
        {
            case "OneOneInput" :
            case "OneFiveInput" :
                diceleft=diceRolled-1;
                break;
            case "OneOneOneFiveInput":
            case "TwoFivesInput":
            case "TwoOnesInput":
                diceleft=diceRolled-2;
                break;
            case "ThreeOnesInput":
            case "ThreeTwosInput":
            case "ThreeThreesInput":
            case "ThreeFoursInput":
            case "ThreeFivesInput":
            case "ThreeSixsInput":
            case "OneOneTwoFivesInput":
            case "OneFiveTwoOnesInput":
                diceleft=diceRolled-3;
                break;
            case "FourInput":
            case "TwoOnesTwoFivesInput":
            case "OneOneThreeTwosInput":
            case "OneOneThreeThreesInput":
            case "OneOneThreeFoursInput":
            case "OneOneThreeFivesInput":
            case "OneOneThreeSixsInput":
            case "OneFiveThreeOnesInput":
            case "OneFiveThreeTwosInput":
            case "OneFiveThreeThreesInput":
            case "OneFiveThreeFoursInput":
            case "OneFiveThreeSixsInput":
                diceleft=diceRolled-4;
                break;
            case "FiveInput":
            case "FourPlusFiveInput":
            case "FourPlusOneInput":
            case "OneOneOneFiveThreeTwosInput":
            case "OneOneOneFiveThreeThreesInput":
            case "OneOneOneFiveThreeFoursInput":
            case "OneOneOneFiveThreeSixsInput":
            case "TwoOnesThreeTwosInput":
            case "TwoOnesThreeThreesInput":
            case "TwoOnesThreeFoursInput":
            case "TwoOnesThreeFivesInput":
            case "TwoOnesThreeSixsInput":
            case "TwoFivesThreeOnesInput":
            case "TwoFivesThreeTwosInput":
            case "TwoFivesThreeThreesInput":
            case "TwoFivesThreeFoursInput":
            case "TwoFivesThreeSixsInput":
                diceleft=diceRolled-5;
                break;
            case "TwoOnesOneFiveThreeTwosInput":
            case "TwoOnesOneFiveThreeThreesInput":
            case "TwoOnesOneFiveThreeFoursInput":
            case "TwoOnesOneFiveThreeSixsInput":
            case "OneOneTwoFivesThreeTwosInput":
            case "OneOneTwoFivesThreeThreesInput":
            case "OneOneTwoFivesThreeFoursInput":
            case "OneOneTwoFivesThreeSixsInput":
            case "SixInput":
            case "StraightInput":
            case "TripletsInput":
            case "PairsInput":
            case "FiveplusOneOneInput":
            case "FiveplusOneFiveInput":
            case "FourplusOneOneOneFive":
            case "FourplusTwoOnes":
            case "FourplusTwoFives":
                diceleft=diceRolled-6;
                break;

        }
        if(diceleft==0){
            return 6;
        }
        return diceleft;

    }

    @Override
    public int compareTo(ScoringOption another) {
        return another.scoringValue-scoringValue;
    }
}
