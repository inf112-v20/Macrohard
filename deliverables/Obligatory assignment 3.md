# Obligatory assignment 3

#

# Task 1)

# Meeting Summary:

03.03.20
Present: All

This meeting&#39;s focus was on the movement cards and player hand. We added a visual part of dealing and selecting a hand, and started work on some of the logic.

05.03.20
Present: All

We added movement graphics and created a temp robot sprite. Also added new card graphics and started discussing how to implement phases and how to lock in a program.

11.03.20
Present: All except Kim André due to illness

This meeting was focused on bug-fixing and tile naming to make life easier when we want to implement tile behavior

12.03.20
Present: All

Discord meeting. We talked about how to proceed in the project, considering the university is closed due to COVID-19 and most of us have traveled home. Also started work on new sprites

19.03.20
Present: All except Vegard

Discord meeting. More planning and dispersion of tasks. Created a splash-screen.

24.03.20
Present: All except Oscar

General discussion about the compulsory.

26.03.20
Present: All

Merging the written part of the compulsory into one delivery, discussed future plans and delegated tasks.

#

#

Team roles and their function

There have not been any real functional changes to the team, but we decided to change the title of Vegard as &quot;coach&quot;, to the more unambiguous &quot;team lead&quot;. His function remains the same, but we feel &quot;team lead&quot; is a better, more fitting description of his role. This is what the group looks like now:

Vegard: Team lead
- Responsible for having an overview of the project, in order to delegate and keep the project on course

Bendik:  Customer contact
- Responsible for having a dialog with the costumer and notify the group of what is demanded

Oscar: Head of tests
- Responsible for making sure the project is provided with adequate testing

Kim: Code quality assurance
- Responsible for keeping the code clean and up to conventional standards

Henrik: Code quality assurance
- Responsible for keeping the code clean and up to conventional standards

\
We are all involved in both coding and testing, but because of the virus outbreak, it is harder to be programming in pairs like we used to. This makes it harder for those less skilled in the ways of Java to contribute to the programming aspect of the project, as it is becoming more advanced. The advantage of pair programming was that the advanced programmers could help the others more effortlessly. Even though we try to overcome this problem as best we can, it is possible that the amount of commits per person may not stabilize as desired.

# Retrospective

During the past few weeks we have needed to apply new strategies and refine our project methods in accordance with the restrictions and bans related to the recent outbreak of the COVID-19-virus. Looking back it becomes clear that, although some features initially planned for this delivery got left out, we have been quite successful in our effort to reorganize the development and uphold our productivity. Since social distancing is highly endorsed by both the University and the norwegian government, we have been using Discord as our main channel of communication during our weekly meetings. The impression is that we have managed to maintain good communication and cooperation throughout these meetings, even though we have had to leave XP-practices as open workspace and pair-programming out.

Meanwhile, we have found it harder to comply with the principles of test-driven development, both as our application grows more complex and entangled and as the focus of this iteration has been directed towards the graphical user interface. Admittedly, we are not sure if and how automatic tests for graphical functionality are meant to be written. Furthermore, the attendance at our regular meetings could definitely be improved. For several meetings this iteration it has been at least one group member missing and one or two members running late. This haltens our productivity. Last but not least, we still have some trouble keeping our project board and our issues on GitHub up to date. These are three aspects of our development that the group has decided to improve during the course of the next iteration.


![project board](/deliverables/project_board_oblig2.png)

([https://github.com/inf112-v20/Macrohard/projects/2/](https://github.com/inf112-v20/Macrohard/projects/2))

# Group dynamics

The group dynamics of Macrohard is good. By spending time together working with this project we have built trust in each other. Our work environment is fueled by positivity and humor. Undoubtedly, we find it quite fun working together on this project. Also, the division and delegation of labor has improved from the start. Now it feels like everyone is having something to work with most of the time. If not, the issue is addressed quickly and suited tasks are delegated to the member in question. Greater trust ensures better communication which in turn lowers the threshold for both delegating and requesting labor.

# Task 2)

# Since last time

Since last time we have focused on the GUI elements. This will make it easier to test different player hands, as well as ensuring that the movements of the robots are in line with the executed programs.

# User stories

**First user story:**

A user should be able to see the animation of an executed program in a way that is in accordance with the movement logically implied by the cards of the executed program.

**Acceptance criteria:**

Given that some cards are selected for the program by the user and the &quot;Go!&quot;-button is clicked, the purple sprite representing the robot should in sequence, for every card in the program:

- Rotate clockwise if the card has RotationType CLOCKWISE
- Rotate counter-clockwise if the card has RotationType COUNTERCLOCKWISE
- Rotate 180 degrees if the card has RotationType U\_TURN
- Given that the movement is within the bounds of the board and not resulting in a wall collision:
  - Move one tile size in the direction of its arms if the card has MovementType ONE\_FORWARD
  - Move two tile sizes in the direction of its arms if the card has MovementType TWO\_FORWARD
  - Move three tile sizes in the direction of its arms if the card has MovementType THREE\_FORWARD
  - Move one tile size in the pposite direction of its arms if the card has MovementType ONE\_BACKWARD

**Work tasks:**

- Implement a clickable button in GameScreen which calls a runProgram-method.
- Implement the runProgram-method which accesses the selected program and, for every card in the program, both updates the board-logic and animates the corresponding movement/rotation of the sprite representing the robot
- Code methods that, given a card, a player and it&#39;s direction, deduces and animates the correct movement/rotation of the player-sprite

**Second user story:**

As a player, I have to be dealt program cards, and see them, in order to select a program.

**Acceptance criteria:**

- Given that there exists a player and cards have been dealt, the player must be able to see which cards have been dealt to them on screen.
- The cards shown on screen must match the actual cards assigned to the player (no discrepancy between the game logic and the GUI)

**Work tasks:**

- Implement dealHand-method in Deck-class that deal program-cards to a player.
- Implement a list of program cards that represent the cards assigned to a  player.
- The number of cards assigned by the dealHand-method must be equal to the number of health points the player has.
- The deck of cards must resize properly after dealing a hand of cards.
- Create card-assets so that each card-type can be graphically represented on screen
- Graphically represent the program cards assigned to a player on screen.

**Third user story:**

As a player, I have to be able to select a program from the cards assigned to me, in order to move my robot.

**Acceptance criteria:**

- The player must be able to interact with the cards in order to select them (click &amp; drag)
- The player must be able to identify which cards have been selected for the program and in which phase the card will be played.
- The player must be able to lock in the selected program.

**Work tasks:**

- Implement a list of program cards that represent the selected program by the player
- Make graphical cards clickable and graphically represent which cards are clicked
- Give the clicked cards an index that represents the phase they will be played in, both graphically and in the game logic.

**Fourth user story:**

As a player, I want to have music in my game and the option to select volume.

**Acceptance criteria:**

- The volume of the track should be able to be adjusted to the users preference, or muted if wanted

**Work tasks:**

- Create a music track
- Make the music sliders change the music volume as expected

\
**MVP**

For this release our MVP is:

- A player is dealt program cards.
- A player can arrange program cards into a program.
- A player can lock in a program.
- The game executes the program and moves the robot.