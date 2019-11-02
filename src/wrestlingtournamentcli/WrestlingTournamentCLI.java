
package wrestlingtournamentcli;
import DataClasses.*;

/**
 * @author Jared Murphy
 */
public class WrestlingTournamentCLI {

    public static void main(String[] args) {
    Model m = new Model();
    Model.importTeamsFromText("teams.txt");
    Model.importWrestlersFromText("wrestlers.txt");
    Model.addTeamPoints("Miami Trace", 6);
    Model.updateWrestlerMatchRecord("MurphyJar", true);
    Model.updateWrestlerSeed("MurphyJar", 1);
    //Model.printWrestlerInformation("Jared");
    //Model.printWrestlerInformation("Will");
    Model.printTeams();
    Model.printWrestlers();
    try{
    Model.wrestlerLookup("jared");
    }catch(Exception e){
    System.out.println(e.getMessage());
    }
    
    
    }
    
}
