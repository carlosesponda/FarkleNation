package com.example.espon.farklenation;

import java.util.LinkedList;

/**
 * Created by chris710 on 10/1/2016.
 */
public class SortedLinkedList {
    LinkedList<ScoringOption> options;

    public SortedLinkedList(){
        options = new LinkedList<>();
    }

    public void add(ScoringOption option){
        for(int i = 0; i< options.size(); i++){
            if(options.get(i).compareTo(option)>0){
                options.add(i,option);
                return;
            }
        }
        options.addLast(option);
    }

    public String getName(int i){
        return options.get(i).name;
    }

    public void addOccurance(int i){
        options.get(i).occurances++;
    }

    public int size(){
        return options.size();
    }

    public int getOccurances(int i){
        return options.get(i).occurances;
    }

    public int getValue(int i){
        return options.get(i).scoringValue;
    }
    public int getDice(int i){
        return options.get(i).diceLeft;
    }
}

