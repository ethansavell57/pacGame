/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Savell_1_pacGame;

/**
 *
 * @author ethan
 */
public class gameUser implements Comparable<gameUser> {

    String name;
    int score;
    int rank;

    
    
    
        @Override
    public int compareTo(gameUser s)
    {
//        return this.score - s.score;     //Sorts the objects in ascending order
         
          return s.score - this.score;    //Sorts the objects in descending order
    }
     
    @Override
    public String toString()
    {

        return " "+name+"          "+score+"";
    }

    
    
    
    public gameUser(int score, String name) {

    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }



}
