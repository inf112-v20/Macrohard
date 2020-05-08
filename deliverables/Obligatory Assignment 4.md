## Compulsory Assignment 4

## Task 1)

Meeting summary:

These last sessions we have not been discussing as much as usual, but
focused more on implementing features and fixing bugs we already know
about. The goal has been to get our product as close to finished as we
could manage in the time we have left, and our meeting has therefore
been used more to show off what we have done, and to delegate the tasks
yet to be completed amongst the team.

02.04.20 Present : All Focus on game loop logic, and making it possible
to complete a full round

09.04.20 Present: All Some refactoring work and conveyor belt logic.

16.04.20 Present : All Game logic fixes such as flags and playerhand,
started on some graphical issues such as player-info graphics and
drawing priority on cards. Discussed plans for future meetings and work
delegation.

23.04.20 Present: Oscar, Henrik, Kim, Bendik Started working on power
down mechanics and making damage tokens lock a program if the player is
over the threshold.

28.04.20 Present: All Work on graphical elements such as cards and
buttons, and started on making all GUI-elements centered for all
different screen sizes.

30.04.20 Present : All Main menu improvements and refactoring. Added
song selection option in preferences and created game screen.

03.05.20 present : All Continued improvements, worked on sound effects
and win screen

04.05.20 Present: All Last meeting before the demo. Fixed some graphical
issues with resizing and player-info graphics. Added new colors to the
robot sprites.

05.05.20 Present: All Meeting after game demo. We discussed what was
left to do before the final deadline, and delegated the work tasks to
complete the last compulsory assignment.

## TEAM ROLES AND THEIR FUNCTION
Since last time, Kim has been assigned the
role as Lead designer, which corresponds to animation, graphics and
composing music. Henrik has been assigned the roles map designer and
sound designer. Otherwise, the roles and their respective functions
remain the same.

Vegard - Team Lead \* Responsible for having an overview of the project,
in order to delegate and keep the project on course.

Bendik - Lead Programmer and Lead Customer Contact \* Responsible for
having a dialog with the customer and notify the group of what is
demanded

Oscar - Lead Test Delegator \* Responsible for making sure the project
is provided with adequate testing

Henrik - Code Quality Insurance, Map & Sound-designer \* Responsible for
keeping the code clean and up to conventional standards. \* Responsible
for the map and sound design and integration.

Kim - Lead Designer, Code Quality Insurance \* Responsible for keeping
the code clean and up to conventional standards. \* Responsible for
creating music and animation for the game

## THINGS WE LEARNED
Our experience of working in a team has been good, and
we find that the project methodology we have been using has worked out
well. The problem here of course, is that we were forced to change the
methodology slightly when the virus broke out. We were pair programming
at first, and this was harder to manage when we weren't face to face.
Afterwards we have realized that there are ways we could have been pair
programming, (by using varying online resources), but we can't do much
about that now.

When it comes to both testing and user stories, we did not appreciate
their potential in the start. Testing became very helpful as the project
grew bigger and more complicated, especially after refactoring, when we
could use them to make sure the key functions of the product were still
in place. At first we created tests because it was demanded by the
assignment, in the end we created tests because we demanded them
ourselves. We never really became good at writing good user stories, and
we probably should have poured more effort into this, as it could have
given us a better overview of what we should focus on. User stories were
often created after we finished a problem, not before, and this of
course is the opposite of what a user story is meant for.

What we should have done earlier in the process, was to make clear cut
roles. This came more naturally into being when the virus came; now we
had to stick more to what each one of us were better at. This led to
more structure in the project; some took care of the logic, while others
took care of graphics, etc.

## RETROSPECTIVE
All in all we are really proud of what we have
accomplished - both individually and as a team. Months of cooperation
and hard work has paid off, yielding a final result far greater than
anyone of us thought was achievable in the beginning. Starting off with
next to zero experience in game development and ignorant to the utility
of project methodologies, our team has really matured during the process
of developing RoboRally. At the same time as we have gained knowledge
about the what’s and how’s of making a game in Java, we have also learnt
a lot about how one ought to work together as a team and which practices
which could be smart to adapt when working with software development in
general.

These lessons have been learnt not only through lectures, literature and
accumulated experience, but also through the countless mistakes that
have been done through the course of the project. We can’t hide the fact
that if we were to make a digital version of RoboRally from scratch
today, we would have done several things differently. To get more
thoroughly acquainted with the tools and opportunities provided the
libGDX-framework from the start, to follow the doctrine of test driven
development more persistently throughout the course of the project and
to use user stories more consistently for planning and prioritizing
further development.

That being said, there are also a lot of things we would not change if
we face a similar task in the future. Firstly, we are glad that we early
separated the logic of the game from the graphical representation. This
made it easier to test the logical aspects of the game without needing
to account for the graphical counterparts. Secondly, we are very
satisfied with both the frequency and the workflow of our group
meetings. We also have the impression that, although the number of
commits and code contributions is somewhat unevenly distributed within
the group, we have been good at finding suitable tasks for each member,
given their previous experiences and skills. When the project grew ever
more complex and the shutdown made it difficult for us to meet in
person, we still managed to find work tasks for members with less
experience in Java and programming in general.


![project board](/deliverables/project_board_oblig4.png)

([https://github.com/inf112-v20/Macrohard/projects/2/](https://github.com/inf112-v20/Macrohard/projects/2))

## GROUP DYNAMICS
The group dynamic has been very good, as we have all had
a similar understanding of what we wanted the game to be, and what was
needed for the product to be realized in this way. The reason for this
is not necessarily because we think alike, but because everybody has
been able to say their opinion, and each voice has been heard. Even
though this dynamic, (seeing each other as equals), was positive, it may
have hindered us from focusing on our roles, that probably would have
boosted our effectiveness. This came more into being later in the
project, and in the end we probably gained from being more specialized,
with some taking care of big picture stuff, and others the more nitpicky
parts.

As the virus forced us to stay inside the boundaries of our own home, we
were forced to plan for our meetings in the group chat, instead of
planning for them when we met at the university. This led to more
meetings being held, and although we noticed a fall in effectiveness per
meeting, (because of the lack of pair programming), this was made up for
by more frequent meetings.

## TASK 2)

Since last time Since last time we have focused on completing a game
loop such that the game is now fully playable start to finish. With the
addition of the game loop, we also decided to revamp the graphical user
interface. We have also put a lot of effort into making the game full of
life with animations, new maps, and sound design. This turned out to be
more difficult than first anticipated.

## USER STORIES

**User story 1:**  
As a player I want a fun game experience, which will be
boosted by cool animations.

Acceptance criteria:
- The robot has an idle animation
- When the robot rotates in game, the sprite should update in accordance with the
direction the robot is now facing
- The different players should have
different colored robots as to make it easy to know where you and your
enemies are

Work tasks:
- Create 3 unique sprite sheets, one for each side, front
and back - facing robot
- Implement methods to changes current sprite
to the sprite representing where the robot is now facing whenever a
robot turns
- Create (at least) eight different color schemes for the
robot sprites, to distinguish players from one another
- Make the
animation cycle on each robot different, to avoid synchronized idle
animation

**User story 2:**  
As a competitive player I want easy access to intuitively
represented information about how the other players are doing in the
game so that I can compare my own robot’s progress with my competitors’.

Acceptance criteria:
Given that there exists an instantiated
RoboRally-game which is active and displayed on screen, it should be
true that for all participating players:
-  There is a visible stat
table always within the bounds of the application-window with
information about the number of life tokens and damage tokens the player
currently has, as well as the number of flags hitherto touched by the
player
-  Their stat table holds the player’s name and is thus
distinguishable from stat tables of other players 
- The stat table
shows whether the player is in or has announced Power Down
 - The
information of the stat table is correctly updated each time the player
takes damage, loses a life, visits it’s next flag or announces, starts
or finishes a Power Down

Work tasks:
- Design or find an aesthetically pleasing sprite to serve
as the background of the stat table and place it in the assets-folder
- Make and implement a PlayerInfoGraphic-class as an extension of the
Table-class provided by LibGdx 
- Find suitable bounds for each of the
eight tables potentially displayed on game-screen such that they neither
overlap with each other nor with the board, cards or buttons displayed
- Make sure every InfoGraphic is added to the stage of the game and
implement an @Override act-method that updates the stat-labels when
needed

**User story 3:**  
As an average player, I need the game to progress through
phases and rounds such that the game can progress.

Acceptance criteria:
- The game should have the structure outlined in
the RoboRally rulebook such that one round of the game adheres to the
ruleset.
- A round in RoboRally should consist of five phases.
- In
each phase, there is a strict order to how the events in the game
unfold.
 - The game must progress such that a winner is declared in each
session of RoboRally. 
 - The player must be able to interact with the
graphical user interface in order to decide how he wants to progress
through the game.

Work tasks:
- Implement the “How to play” section of the RoboRally
ruleset. 
- Create a function that is continuously called and, depending
on the phase index input, executes events in an order that simulates the
game loop of the original board game.
 - Expand manual testing to easily
test individual game events occuring during a session of RoboRally.
- Expand the graphical user interface so that it adheres to the gameflow
specified by the ruleset.

**User story 4:**  
As an average player I need audio response on the robots’
actions such that I can have a better understanding of what is going on
in the game.

Acceptance criteria:
- All moving parts on the board that can affect a
robot should produce a sound that is appropriate to the action they
perform. 
- All actions performed by a robot should produce a sound that
is appropriate to the action they perform.

Work tasks:
- Implement a sound effects class.
-  Connect the sound
effects class to each action that can be performed by either board or
player.

**User story 5:**  
As an average player I need the application to
automatically lock and unlock cards selected for my program, both
logically and graphically, and in accordance with the rules of the game,
so that I don’t have to calculate and remember which cards that are
stuck and which that are not.

Acceptance criteria:
- Robots that have more than four damage tokens
should have their program registers locked according to the game rules.
- The player must be able to identify from the graphics the program
registers that are locked and those that are not.
- A locked card in
program registers should be played every round they are locked.

Work tasks:
- Implement method that locks program registers based on
damage tokens received. 
 - Adjust CardGraphic so that cards in locked
program registers are visibly locked.

**User story 6:**  
As a player, I want to be able to announce and enter power
down in order to prevent being destroyed.

Acceptance criteria:
- The graphical user interface should display the
power-down status of the player 
- The player should be able to interact
with the graphical user interface in order to change the power-down
status as the player sees fit. 
- When in power-down, the player should
discard all damage tokens and not receive cards this round.

Work tasks:
- Implement power-down status in the graphical user
interface.
- Create buttons etc such that player can choose to
continue/abort power-down after one round in power-down. 
- Adjust
GameLoop to remove all damage tokens and not deal cards when the player
has announced power-down.
- Make sure the powered down robot is
otherwise unprotected, it can still be pushed and take damage from
lasers, etc. 
- NPC’s announce and enter power-down when they have
received a certain amount of damage tokens.

**User story 7:**  
As a player, I should be able to interact with board
lasers and fire my own lasers in order to damage other robots and
maximize my chances to win the game.

Acceptance criteria:
- At the end of every phase, each robot fires a
laser in the direction it is facing 
- At the end of every phase, board
lasers are fired and robots in its path take damage. 
- It should be
clear from the graphics which robot inflicts damage and which robot
takes damage. 
- Robots shot by lasers from either the bord or another
player should receive one damage token.

Work tasks:
- Implement laser game logic. Lasers are blocked by other
players and walls. Laser collision is needed to fully implement lasers.
- Implement player lasers graphically. At the end of each phase,
accurately draw the laser trajectory of each robot on the board. At the
end of each phase accurately draw the laser trajectory of each laser
specified by the map design. 
-  Write automated tests to ensure correct
game logic implementation.

## MVP

For this release our MVP is:
- All basic game mechanics are executed
and handled in correct order by a game loop. 
- Both the client and the
NPC’s can announce, enter, and cancel Power Down in accordance with the
rules of the game. 
- Information about each player should be displayed
as separate and distinguishable graphical elements. 
- Implement board
elements such as conveyor belts, gears, repair sites, flags, board
lasers and player lasers. 
- Implement locking of program registers and
reducing cards dealt based on damage tokens received. 
- Events in the game should be accompanied by a sound effect. 
- Events in the game should be accompanied by an animation.
