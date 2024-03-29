# JavaRiskGame
Java implementation of the popular strategy game Risk

**Authors:** Marko M, MacK W, Mmedara J, Tami O

2020-11-09

Carleton Univeristy, SYSC 3110

**Milestone:** 4

## Initiating The Game

If cloned from github:
1. Build the project
2. Navigate to the 'Main' class and run the main method

If running the JAR file:
1. Execute the JAR file

## How to Play 

If you are not well versed in the rules of Risk, please refer to the 'Game Play' section of the following page:
https://www.ultraboardgames.com/risk/game-rules.php

Once the game is started, the Game UI will open and it will prompt you to enter any required game parameters such as how many players in the game, player names, and if the players are human or AI.

Once the game is in play, you will be able to click the following commands during your turn:

- **pass**: This command allows you to skip your turn if you do not wish to make any attacks.
- **reinforce**: This command allows you to distribute your extra troops amongst your occupied territoires. Reinforcement must be completed before proceeding with any other commands. You can reinforce a territory by selecting a territory from your territory list, then entering the number of soliders to reinforce with, then pressing reinfoce.
- **attack**: This command can be used to attack any of your territories' adjacencies by selecting one of your held territories, then selecting the adjacency you wish to attack from the list that appears. Then, enter the amount of rolls you wish to take (amount of soldiers you wish to send into the attack)(1-3), and have the defender do the same. Then, simply press attack.  
- **fortify**: This command allows you to move troops from one occupied territory to another occupied adjacent one. This command ends your turn. You can fortify by selecting the origin territory from your territory list, then selecting the adjacent destination territory, then entering the number of soliders to fortify with, then pressing fortify.
- **help**: This will display all the possible commands to enter and how to enter them properly.
- **save**: Save the current game. (save with extension .rsf)
- **load**: Load a saved game.
- **quit**: This allows you to quit the game.

## Navigating the User Interface

- **Top Left**: The territories held by the player are dispalyed. Beside each territory is the amount of soldiers in that territory.
- **Bottom Left**: The command buttons, and the fields to enter attacker rolls, reinforcement soliders, and fortify soliders.
- **Bottom Left, Beside Command Buttons**: Once a territory is selected from the list, that countries adjacnecies will appear here, and can be selected.
- **Middle Status Window**: Displays all the games messages such as who's turn it is, who won a battle, and if any errors occur. 
- **Right Side**: Displays the state of the entire board including all continents, their respective territories and amount of soldiers in each territory.

## Changes Made Since Previous Milestone

- Added ability to import and use custom maps as XML files
- Added ability to save game progress and load saved game progress
- Removed leftover frames from after closing main game frame. 
- Previous bugs fixed.
- New JOptionPane appears to request defneder dice rolls
- Tests for Save/Load and XML Reading.


## Known bugs 

- Save/Load feature not working properly due to issue with DocumentBuilderFactory
