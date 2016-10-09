package com.example.espon.farklenation;

/**
 * Created by espon on 10/2/2016.
 */
public class ExpectedValues implements Comparable<ExpectedValues> {
    String move;
    double expectedValue;

    public ExpectedValues(String move, double expectedValue){
        this.move = move;
        this.expectedValue=expectedValue;
    }

    @Override
    public int compareTo(ExpectedValues o) {
        return (int)(o.expectedValue-expectedValue);
    }
}
