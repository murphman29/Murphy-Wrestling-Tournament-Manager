package DataClasses;

import java.util.ArrayList;
import java.util.Collections;
import wrestlingtournamentcli.Model;

/**
 * @author Jared Murphy
 * https://github.com/murphman29
 * 
// * ==Bracket==//Pick up here
//
//	=Member Variables=
//	-roundThreeTwo:Match[16]
//	-roundOneSix:Match[8]
//	-roundZeroEight:Match[4]
//	-roundZeroFour:Match[2]
//	-roundZeroTwo:Match[1]
//	-winner:Wrestler
//	-roundsNeeded:int
//	-weightClass:WeightClass
//	-primary:boolean //Primary or Consolation bracket
//	
//	=Methods=
//	+void:Bracket(ArrayList<Wrestler>, WeightClass, boolean)
//	+ArrayList<Match>:makeMatches(int round)
//	+void:advanceWrestler(int round, int matchID, String winningColor)
//	+void:printBracket()
 //*/
public class Bracket {
ArrayList<ArrayList<Match>> bracket = new ArrayList();
private ArrayList<Wrestler> wrestlerList;
String winner;
int roundsNeeded;
int currentRound;
int weightClass;
boolean primary;

public Bracket(ArrayList<Wrestler> wrestlerList){
this.wrestlerList = wrestlerList;
this.roundsNeeded = determineRounds();
initializeBracket();
this.currentRound = 1;
}

public int determineRounds(){
while(!bracketable(wrestlerList.size())){
Wrestler BYE = new Wrestler();
wrestlerList.add(BYE);
}
return twoPow(wrestlerList.size());
}

public int twoPow(int number){
int twos = 0;
while(number != 1){
if(number % 2 != 0){
    return -2;
}
number = number / 2;
twos++;
}
return twos;
}

public boolean bracketable(int number){
if(twoPow(number) == -2){
    return false;
}else{
    return true;
}
}

public void initializeBracket(){
for(int i = 0; i != roundsNeeded; i++){
    bracket.add(new ArrayList());
}
seedWrestlers();
ArrayList<Match> temp = new ArrayList();
for(int i = 0, j = wrestlerList.size(); i != j; i++, j--){
temp.add(new Match(Model.getMatchID(),wrestlerList.get(i).getUserName(),wrestlerList.get(j).getUserName(),currentRound,weightClass));
}
bracket.set(0, arrangeInitialMatches(temp));
}

public void seedWrestlers(){
Collections.sort(wrestlerList);
for(int i = 0; i != wrestlerList.size(); i++){
if(wrestlerList.get(i).getSeed() == 100){
wrestlerList.get(i).setSeed(i);//Update Locally
Model.updateWrestlerSeed(wrestlerList.get(i).getUserName(), i);//Update Model
}
}
}

public ArrayList<Match> arrangeInitialMatches(ArrayList<Match> matchList){
int[] guide = new int[]{1,32,16,17,8,25,9,24,4,29,13,20,5,28,12,21,2,31,15,18,7,26,10,23,3,30,14,19,6,27,11,22};
ArrayList<Match> returnList = new ArrayList();
    for(int i = 0; i != matchList.size(); i = i+(guide.length/matchList.size())){
       returnList.add(matchList.get(guide[i]));
}
    return returnList;
}

}
