# Murphy Wrestling Tournament Manager
 Creator: Jared Murphy
 
## Description: 
Have you ever been to a wrestling tournament, or any other sport with individual participants? Watching from the stands, it can be difficult to know what is happening on the mat. For example, how does a participant’s family know when they are going to be up next? Or which mat they are going to be wrestling on? To help avoid this confusion, the Murphy Wrestling Tournament Manager (MWTM) was created.
## Purpose:
The MWTM, in its final state, will be a must-have software for school districts around the country. It will decrease the time and manpower required to host tournaments by automating monotonous processes, promote crowd involvement by allowing spectators to view tournament standings/results from their mobile devices, and make it easy to know when certain participants are about to take to the mat!

Most people do not know how much work goes into hosting a wrestling tournament. Before the tournament can even begin, administrators must acquire rosters from participating teams, sort wrestlers by weight class, seed wrestlers by their records, place seeded wrestlers into brackets, print and post the brackets for spectators to view. Once it has started, the administrators must assign matches to mats based on availability, record the results of each match as they are brought to the head table, keep track of team points (For Dual tournaments), announce which wrestlers are going to be wrestling soon, and advance the tournament once all matches in the round have been completed.

Most of the administrator’s work can be automated. For example, instead of the match results being recorded on a bout sheet (piece of paper) and physically transported to the head table, table managers at each mat could insert them directly into the MWTM. Additionally, instead of announcing which wrestlers will be on deck for each mat, the program could have a window that shows the matches assigned to each mat. If the school was in possession of a projector, it could be connected and the window displayed on the projector for everyone to see. There are many more opportunities for automation, but these strike me as the most time consuming. 
## Current Progress:
The application, in its current form, is only usable by tournament administrators. They are able to insert new teams and wrestlers into the tournament via text file, seed wrestlers based on win-loss ratios, generate brackets based off of wrestler seeding, input match results via command line, and save/load tournament progress.
## Design Patterns:
When planning to code this application, I created a UML Diagram to organize my thoughts. It utilizes MVC, Factory, and Singleton design patterns. As the application grows, it should also implement the Command design pattern so that users can undo/redo previous actions.

In the MWTM, there is only one Model (Also making it a Singleton). Changes are made to the Model via the “WrestlingTournamentCLI” class, which serves as the Model’s only Controller (for now). When the Controller receives a command from the user via the command line interface (View), it interprets the input and requests that the model execute the interpreted command. Since there are 7 parameters needed to create a Wrestler object, new Wrestlers are only created by the wrestlerFactory method within the Model. 
