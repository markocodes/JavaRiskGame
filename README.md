# JavaRiskGame
Java implementation of the popular strategy game Risk

**Authors:** Marko M, MacK W, Mmedara J, Tami A

2020-10-15

Carleton Univeristy, SYSC 3110

**Milestone:** 1

## Initiating The Game

If cloned from github:
1. Build the project
2. Navigate to the 'Main' class and run the main method

If running the JAR file:
1. Execute the JAR file

## How to Play 

If you are not well versed in the rules of Risk, please refer to the 'Game Play' section of the following page:
https://www.ultraboardgames.com/risk/game-rules.php

Once the game is started, you will be promted through the console to enter values and commands. 
Once the game is in play, you will be able to use the following commands during your turn:

- **state**: This will print out the state of the game and the map including which territories are occupied, by whom and with how many troops.
- **pass**: This command allows you to skip your turn if you donot wish to make any attacks.
- **attack**: This command can be used to attack any of your territories' adjacencies by entering "attack A from B" where A is the territory you wish to attack, 
   and B is the territory you hold and are attacking from. Enter **help** for more info on this command.
- **adjacent**: Lists all countries and their respective adjacencies.
- **help**: This will display all the possible commands to enter and how to enter them properly.
- **quit**: This allows you to quit the game.

## Known bugs 
- The **state** command is currently not displaying the correct state of the game. This is likely due to the Territory and Object classes not storing the correct information and thus not providing the correct to the methods requesting it. 
- The processing of an attack command is still showing some functionality issues. It consistently displays that some territories are not valid territories in the game, when indeed they are. This may be an issue with how the board is handled.
- The **adjacent** command does not provide the desired information. 

These bugs will be addressed and fixed for the upcoming milsetone (2).

## Next Steps
1. Resolving any unresolved bugs
2. Implementing a GUI
3. Implementing unit tests
4. Implementing futher game features such as troop movement, bonus army placement, and AI players
5. Implementing saving and loading features
6. Added ability to load custom game maps
