package wrestlingtournamentcli;

import DataClasses.*;
import java.io.File;
import wrestlingtournamentcli.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author Jared Murphy https://github.com/murphman29
 *
 * ==Model==
 *
 * =Member Variables= -wrestlerList:ArrayList<Wrestler>
 * -CreatedMatches:ArrayList<Match>
 * -MatchesInProgress:ArrayList<Match>
 * -FinishedMatches:ArrayList<Match>
 * -weightClasses:ArrayList<WeightClass>
 * -tournamentRound:int -numberOfMats:int
 * -matchesByMat:ArrayList<ArrayList<Match>> -teamList:ArrayList<Team>
 *
 * =Methods= +ArrayList<Match>:getMatchesByRound(int round)
 * +ArrayList<Match>:getMatchesByWrestler(String wrestlerName)
 * +ArrayList<Match>:getMatchesByWrestler(Wrestler wrestler)
 * +void:importWrestlersFromText(String filePath)
 * +void:importWrestlersFromExcel(String filePath)
 * -Wrestler:wrestlerFactory(String wrestlerInfo)
 * +void:importTeamsFromText(String filePath) +void:importTeamsFromExcel(String
 * filePath) -String:separateArgumentsFromString(String largeString)
 * +void:initializeWeightClasses() +int[matNumber]:getMatchesByMat()
 * +int:getLeastUsedMat()
 */
public class Model {

    private static ArrayList<Team> teamList;
    private static ArrayList<Wrestler> wrestlerList;
    private static ArrayList<Integer> weightClasses;
    private static int matches;

    public Model() {
        this.teamList = new ArrayList();
        this.wrestlerList = new ArrayList();
        this.teamList.add(new Team("BYE", "", ""));
        initializeWeightClasses();
        matches = 0;
    }
    
     public static void generateTournament() {
        if (wrestlerList.size() == 0 || teamList.size() == 1) {
            System.out.println("Error: No Wrestlers or Teams Found");
            System.out.println("Please add wrestlers/teams before generating a tournament.");
            return;
        }
        
    }
     
    public static int getMatchID(){
        matches++;
        return matches;
    }
    //Takes in a wrestler's name and returns a nine character username
    //Ideally, it will use 6 from the lastname and 3 from the firstname.
    //If lastName.length < 6, it will switch over to the firstName early
    //If the firstName cannot fill the remaining characters, it will pad the end with " "
    public static String getUserName(String lastName, String firstName) {
        String returnValue = "";
        if (lastName == null || firstName == null) {
            return returnValue;
        } else {
            if (lastName.length() >= 6) {
                returnValue += lastName.substring(0, 6);
            } else {
                returnValue += lastName.substring(0, lastName.length());
            }
            int charRemaining = 9 - returnValue.length();
            for (int i = 0; charRemaining != 0; i++, charRemaining--) {
                if (i < firstName.length()) {
                    returnValue += firstName.charAt(i);
                } else {
                    returnValue += " ";
                }
            }
        }
        return returnValue;
    }

    public static void addTeamPoints(String teamAlias, int points) {
        try {
            Team t = teamLookup(teamAlias);
            int pos = teamList.indexOf(t);
            teamList.get(pos).addTeamScore(points);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateWrestlerMatchRecord(String alias, boolean win) {
        try {
            Wrestler w = wrestlerLookup(alias);
            int pos = wrestlerList.indexOf(w);
            wrestlerList.get(pos).addMatch(win);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

   

    public static void updateWrestlerSeed(String alias, int seed) {
        try {
            Wrestler w = wrestlerLookup(alias);
            int pos = wrestlerList.indexOf(w);
            wrestlerList.get(pos).setSeed(seed);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Team teamLookup(String alias) throws NotFoundException {
        alias = alias.toUpperCase();
        for (Team team : teamList) {
            if (team.getTeamName().contains(alias)) {
                return team;
            } else if (team.getTeamMascot().contains(alias)) {
                return team;
            } else if (team.getInitials().contains(alias)) {
                return team;
            }
        }
        throw new NotFoundException(alias);
    }

    public static Wrestler wrestlerLookup(String alias) throws NotFoundException {
        alias = alias.toUpperCase();
        for (Wrestler w : wrestlerList) {
            if (w.getLastName().contains(alias)) {
                return w;
            } else if (w.getFirstName().contains(alias)) {
                return w;
            } else if (w.getUserName().contains(alias)) {
                return w;
            }
        }
        throw new NotFoundException(alias);
    }

    public static void importWrestlersFromText(String filePath) {
        File file;
        Scanner s = null;
        int importCount = 0;
        try {
            file = new File(filePath);
            s = new Scanner(file);
        } catch (Exception e) {
            System.out.println("The file could not be found/opened.");
            return;
        }
        while (s.hasNextLine()) {
            try {
                String line = s.nextLine();
                Wrestler w = wrestlerFactory(line);
                wrestlerList.add(w);
                importCount++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Successfully imported " + importCount + " wrestlers.");
    }

    public static void importTeamsFromText(String filePath) {
        File file;
        Scanner s = null;
        int importCount = 0;
        try {
            file = new File(filePath);
            s = new Scanner(file);
        } catch (Exception e) {
            System.out.println("The file could not be found/opened.");
            return;
        }
        while (s.hasNextLine()) {
            try {
                Team t = null;
                String line = s.nextLine();
                Scanner ls = new Scanner(line);
                ArrayList<String> tokens = new ArrayList();
                String token = "";
                for (int i = 0; ls.hasNext(); i++) { //For the three fields possible
                    while (ls.hasNext()) {
                        token += ls.next();
                        if (token.charAt(token.length() - 1) == ',') {
                            tokens.add(token.substring(0, token.length() - 1));
                            token = "";
                            break;
                        } else {
                            token += " ";
                        }
                    }
                }
                if (tokens.size() == 2) {
                    t = new Team(tokens.get(0), tokens.get(1));
                } else {
                    t = new Team(tokens.get(0), tokens.get(1), tokens.get(2));
                }
                teamList.add(t);
                importCount++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Successfully imported " + importCount + " teams.");
    }

    private static Wrestler wrestlerFactory(String wrestlerInfo) throws Exception {
        try {
            Scanner s = new Scanner(wrestlerInfo);
            String fn = s.next();
            String ln = s.next();
            String teamAlias = s.next();
            int grade = s.nextInt();
            int weightClass = s.nextInt();
            int wins = s.nextInt();
            int matches = s.nextInt();
            Wrestler w = new Wrestler(fn, ln, teamAlias, grade, weightClass, wins, matches);
            return w;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new IncorrectFormatException(wrestlerInfo);
        }
    }

    public static void printWrestlers() {
        Collections.sort(wrestlerList);
        System.out.println("List of Wrestlers: ");
        for (int i = 0; i != wrestlerList.size(); i++) {
            System.out.println(wrestlerList.get(i));
        }
    }

    public static void printTeams() {
        Collections.sort(teamList);
        System.out.println("List of Teams: ");
        for (int i = 0; i != teamList.size(); i++) {
            System.out.println(teamList.get(i));
        }
    }

    public static void printWrestlerInformation(String alias) {
        try {
            Wrestler w = wrestlerLookup(alias);
            int pos = wrestlerList.indexOf(w);
            String rs = wrestlerList.get(pos).getLongString();
            System.out.println(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void initializeWeightClasses() {
        weightClasses = new ArrayList();
        weightClasses.add(106);
        weightClasses.add(113);
        weightClasses.add(120);
        weightClasses.add(126);
        weightClasses.add(132);
        weightClasses.add(138);
        weightClasses.add(145);
        weightClasses.add(152);
        weightClasses.add(160);
        weightClasses.add(170);
        weightClasses.add(182);
        weightClasses.add(195);
        weightClasses.add(220);
        weightClasses.add(285);
        Collections.sort(weightClasses);
    }

    public static boolean verifyWeightClass(int weightClass) {
        return weightClasses.contains(weightClass);
    }

}
