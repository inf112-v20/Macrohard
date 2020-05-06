Manual testing guide for v2.2

##testing GameLoop:

-   Run Program.
-   click on "New Game"-tab in main menu.
-   Use the dropdown menus to select map and choose number of players.
-   click "create" to start game.
-   Select a program from the cards dealt.
-   The "lock program" turns green when you have entered a valid
    program.
-   click the green "lock program" button.
-   (Optional) click the power down button to announce powerdown.
-   Observe that the following events happens in the given order.

This outlines ONE phase. A round has FIVE phases.

A. Robots Move   
B. Board Elements Move.
>  -    Express conveyor belts move 1 space in the direction of the arrows.  
>  -    Express conveyor belts and normal conveyor belts move 1 space in the direction of the arrows.
>  -    Gears rotate 90 degrees in the direction of the arrows.
>
C. Lasers andPlayerLasers Fire.  
D. Touch Checkpoints.

-   Observe that five phases are displayed as outlined above before a
    new round is started.
-   On the right side of the map is a display highlighting lives,
    damage, flags touched and powerdown-status for each player in the
    game.
-   Use the display to monitor that events on the board updates player
    values correctly.
-   Repeat until either one robot remaining or one robot has
    successfully visited each flag in correct order.
-   The GameLoop should launch a victory screen and the RoboRally
    session is now finished.


##testing preferences:

-   Run program.
-   click on the dropdown menu to the right of Song to choose between a
    list of soundtracks.
-   click "preferences"-tab in main menu.
-   click on sliders to adjust music volume and sound volume.
-   click on checkbox "music" to completely mute game music.
-   click on checkbox "sound" to completely mute game sound.
-   click on "back"-button to return to main menu.

## testing individual board elements:
NOTE: use keyboard arrows and keys for easy testing

Testing Gears:
-  Move robot on top of any gear visible on the board
- Press "G" and observe that the robot is rotated according to the arrows on the gear.

Testing express conveyor belts:
- Move robot on top of any visible express conveyor belt (blue with two arrows)
- press "E" and observe that the robot is moved an rotated two tiles in the direction of the arrows whenever such movement is legal.

Testing regular conveyor belts:
- Move robot on top of any visible conveyor belt (orange with one arrow)
- press "R" and observe that the robot is moved and rotated one tile in the direction of the arrows whenever such movement is legal.

Testing board lasers
- press "B" to activate board lasers.
- move robot on top of any visible board laser.
- press "B" again to reactivate board lasers
- Observe in player graphic to the right of the screen that damage has been taken.
- Observe that "taking damage"- animation is played (Robot should blink red)

Testing player lasers
- move robot around map
- press "L" to activate player laser and observe that lasers are drawn in the direction the robot is facing and is blocked by certain board elements such as walls or other robots.
NOTE: when two players are shooting eachother the lasers are not drawn properly. This bug only appears when using keyboard instead of programming cards. "Damage taken"-animation is still played and the player graphic to the right of the map still registers damage. The bug is purely visual.

Testing board holes
- move robot to any visible hole on the board
- observe that the player fades away from the board and that the player lose a life in the player graphic

Testing repair sites
- Use any of the methods specified above to take damage
- Move robot to tile marked with tool or hammer.
- Press "F" and observe that one damage token is removed from the robot.
- Destroy robot in any way you like.
- Press "M" to reboot robot and observe that archive marker was updated.

Testing flags.
- Move robot to any flag visible on the board
- Press "F" to interact with flag
- Observe that any other sequence of flag visits other than 1-2-3 is not registered in the player graphic.
- Destroy robot in any way you like.
- Press "M" to reboot robot and observe that archive marker was updated.
