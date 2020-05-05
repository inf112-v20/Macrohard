Manual testing guide for v2.2

testing GameLoop:

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

A. Robots Move B. Board Elements Move. 1. Express conveyor belts move 1
space in the direction of the arrows. 2. Express conveyor belts and
normal conveyor belts move 1 space in the direction of the arrows. 3.
Gears rotate 90 degrees in the direction of the arrows. C. Lasers and
PlayerLasers Fire. D. Touch Checkpoints.

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

testing preferences:

-   Run program.
-   click on the dropdown menu to the right of Song to choose between a
    list of soundtracks.
-   click "preferences"-tab in main menu.
-   click on sliders to adjust music volume and sound volume.
-   click on checkbox "music" to completely mute game music.
-   click on checkbox "sound" to completely mute game sound.
-   click on "back"-button to return to main menu.

