package DataClasses;

import java.io.Serializable;
import wrestlingtournamentcli.*;

/**
 * @author Jared Murphy https://github.com/murphman29
 */
public class Wrestler implements Serializable, Comparable<Wrestler> {

    String firstName;
    String lastName;
    String userName; //Length 9
    Team team;
    Integer weightClass;
    int grade;
    int tournamentWinCount;
    int tournamentMatchCount;
    int totalWinCount;
    int totalMatchCount;
    int seed;
    double rating;

    public Wrestler(String fn, String ln, String teamAlias, int grade, int weightClass, int totalWins, int totalMatches) throws Exception {
        this.firstName = fn.toUpperCase();
        this.lastName = ln.toUpperCase();
        this.team = Model.teamLookup(teamAlias.toUpperCase());
        this.userName = Model.getUserName(lastName, firstName);
        this.grade = grade;
        if (Model.verifyWeightClass(weightClass)) {
            this.weightClass = weightClass;
        } else {
            throw new NotFoundException(weightClass);
        }
        this.totalWinCount = totalWins;
        this.totalMatchCount = totalMatches;
        this.rating = generateRating();
        this.seed = 100;
    }

    public Wrestler(String fn, String ln, int grade, int weightClass, int totalWins, int totalMatches) throws Exception {//Used ONLY for testing
        this.firstName = fn.toUpperCase();
        this.lastName = ln.toUpperCase();
        this.team = new Team("TEST", "T_T");
        this.userName = Model.getUserName(lastName, firstName);
        this.grade = grade;
        this.weightClass = weightClass;
        this.totalWinCount = totalWins;
        this.totalMatchCount = totalMatches;
        this.rating = generateRating();
        this.seed = 100;
    }

    public Wrestler(int weightClass) {
        this.firstName = "";
        this.lastName = "";
        this.weightClass = weightClass;
        try {
            this.team = Model.teamLookup("BYE");
            this.userName = "   BYE   ";
            this.totalMatchCount = 100;
            this.totalWinCount = 0;
            this.rating = 0;
            this.seed = 100;
        } catch (Exception e) {

        }
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUserName() {
        return userName;
    }

    public int getWeightClass() {
        return weightClass;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public int getSeed() {
        return seed;
    }

    private double generateRating() {
        return (double) ((double) totalWinCount / (double) totalMatchCount) * ((double) totalMatchCount / 10.00);
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public String getTeamID() {
        return team.getTeamName();
    }

    public void addMatch(boolean win) {
        totalMatchCount++;
        tournamentMatchCount++;
        if (win) {
            totalWinCount++;
            tournamentWinCount++;
        }
    }

    public String getLongString() {
        String rs = "";
        rs += "Name: " + lastName + ", " + firstName + "\n";
        rs += "Username: " + userName + "\n";
        rs += "Weight Class: " + weightClass + "\n";
        rs += "Team: " + team.getTeamName() + "\n";
        rs += "Grade: " + grade + "\n";
        rs += "Seed: " + seed + "\n";
        rs += "Rating: " + rating + "\n";
        if (totalMatchCount != 0) {
            rs += "YTD Record:\n\t" + "Wins: " + totalWinCount + "\n\tMatches: " + totalMatchCount + "\n\tW/L Ratio: " + ((double) totalWinCount / (double) totalMatchCount) + "\n";
        }
        if (tournamentMatchCount != 0) {
            rs += "Tournament Record:\n\t" + "Wins: " + tournamentWinCount + "\n\tMatches: " + tournamentMatchCount + "\n\tW/L Ratio: " + (tournamentWinCount / tournamentMatchCount) + "\n";
        }
        return rs;
    }

    @Override
    public String toString() {
        return lastName + ", " + firstName + "\t(" + userName + ")\tClass: " + weightClass + "\tTeam: " + team.getTeamName();
    }

    @Override
    public int compareTo(Wrestler other) {
        //Check weight class
        //Check whether or not the guy is seeded
        //Check rating
        try {
            switch (Integer.compare(this.weightClass, other.getWeightClass())) { //Compare weight classes
                case -1:
                    return -1;
                case 1:
                    return 1;
                case 0: //If in the same weight class, compare seeding
                    switch (Integer.compare(this.seed, other.getSeed())) {
                        case -1:
                            return -1;
                        case 1:
                            return 1;
                        case 0:
                            switch (Double.compare(this.rating, other.getRating())) { //If they're both unseeded, compare their ratings
                                case -1:
                                    return 1;
                                case 1:
                                    return -1;
                                case 0:
                                    return 0;
                            }
                    }
                default:
                    return 0;
            }

        } catch (Exception e) {
            System.out.println("Switch statement failure in Wrestler.compareTo()");
            return 0;
        }
    }
}
