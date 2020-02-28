Obligatory Assignment 2 Task 1

-   The roles worked out fine, but they were a little vague. All of us
    had roles as developer and tester, but only two of us had
    additional, more specific roles. The additional roles were: “coach”,
    assigned to Vegard, and “customer contact”, assigned to Bendik.
    These roles will be kept as is, but we found out that the rest of us
    should have additional roles as well, to keep the team well
    organized.

-   With new additional roles, Kim and Henrik are responsible for code
    quality assurance, and Oscar is head of tests. The roles of Bendik
    and Vegard remain the same, as mentioned in the paragraph above. As
    of now, we do not have a need for other roles. Here’s a list of team
    roles and what they mean:
-   Coach: responsible for having an overview of the project, in order
    to delegate and keep the project on course.
-   Customer contact: responsible for having a dialog with the customer
    and notify the group of what is demanded.
-   Head of tests: responsible for making sure the project is provided
    with sufficient testing.\
-   Code quality assurance: responsible for keeping the code clean and
    up to conventional standards.
-   So far the group dynamic has been very good. We are most
    productive during our meetings where we split up and work in pairs.
    Help is easily accessible during meetings and in our group-chat
    otherwise. With the new roles ‘Code quality assurance’ and ‘Head of
    tests’, tasks are now easier to distribute.

-   Initially, we decided that our main communication-channel would be a
    group-chat in addition to the project board on GitHub. So far this
    group-chat is mostly used to plan meetings and ask questions if any
    help is needed. The majority of our communication takes place during
    meetings and group sessions where we distribute tasks to work on. We
    also use the meetings to discuss which features should be
    prioritized next.

-   The past few weeks our scheduled Friday-meetings have been
    rescheduled to Thursdays due to Bendik and Oscar traveling and
    obligatory assignments in other courses. However, this didn’t impact
    project progress in any significant way. We all agreed that we
    should try to reinstate our regular Friday-meetings for the coming
    weeks. With the added roles questions about testing can now be
    directed to Oscar and questions regarding code quality to either Kim
    or Henrik. This has significantly improved communication and is
    something we should have added earlier.

-   Currently, Henrik is behind on commits. This is because his computer
    was very slow, and whenever we pair programmed we always used
    another computer. Now he has bought a new one, and the commits
    should even out from here on out. We have not been too strict as to
    who commits what, and most changes we do happen whenever we meet. In
    this way, we ensure that everyone gets their opinion heard and we
    are all on the same page as to how far we are in the project, and
    what we have left to do.

Meeting summary: \* Thursday 13.02.20 \* Got feedback from the TA’s, and
got to thinking about how we could adapt our approach to the project
going forward. We discussed adding more specified roles (such as code
quality assurance, head of tests, etc) \* Thursday 10.02.20 \* Added the
main menu to the project. Planned how we would structure the project,
what abstract classes we need, and how we want to separate logic from
the visual. \* Added new Card and Deck class. The plan here is to have
two decks, one for program cards and one for ability cards. \* Tuesday
25.02.20 \* Made main menu interactable,

-   Thursday 27.02.20
-   Refactored the card data structure.
-   Added an enum class for directions.
-   Added direction property to Player

Improvements for next time: \* Meet more regularly (Fridays seems to be
an ideal day) \* Spread commits evenly throughout team members \*
Commenting and documenting code Task 2

User stories 
1. The game mechanics require two types of programming
cards, move cards and rotate cards, so that a robot can move across
fields, and change the direction the robot points. Given that the turns
and phases of the game are to be played out according to the rules, two
types of programming cards are required. 

2. The programming cards need
to have move values, as to determine how many fields the robot can move
across. Given that you need your robot to move from its current position
to a new one, there must be a value to the programming cards signifying
how many fields it is allowed to move across. 
3. The programming cards
(with move values) need to indicate if the robot is moving in a forward
or backward direction. Given that the programming cards (with move
values) are to behave according to the game mechanics, a majority of the
cards should be moving the robot in a forwards direction, whilst some
cards should be moving it in a backwards direction.\
4. The game mechanics requires the programming cards to have a priority
value, so that the turn of which robot is to move is held in order.
Given that the players are about to move their robot, the game must
control which robot is to move first, second, etc. 
5. As a user, I need
to be dealt programming cards, so that I can program my robot. Given
that you are about to play one round, you have to be dealt programming
cards, so that your robot can be programmed. 
6. As a user, I need to be
able to see what cards I’m given, so that I can program my robot the
right way. Given that you want to program your robot, you must be able
to see what the programming cards do, in order to make strategies. 
7. As
a user, I need to be able to order my cards in the way that I want them
to be played. Given that you are about to program your robot, you need
to be able to rearrange the programming cards in the order you want, to
make your program run the right way. 
8. As a user, I need to be able to
choose to start a new game in a menu. Given that you want to choose to
start a new game, you should be able to choose this action from a menu.
9. As a user, I need to be able to choose my preferences for game
effects from a menu. Given that you want to change game preferences, you
should be able to do this from a menu. 
10. As a user, I need to be able
to exit the application from the menu. Given that you are in the menus
and want to exit the application, you should be able to press an exit
button in order to shut the application down.

-   Since the previous version, we have completed four MVPs. We have
    implemented a data structure to represent the playing cards and we
    have implemented the main menu. The playing card data structure
    consists of an abstract card class, a deck class (with Knuth
    shuffle), a class for movement cards and a class for rotation cards.
    In the main menu there are currently three options, start a new
    game, a ‘preferences’ tab or exit the game.

MVPs completed since the previous version \* The product should have a
method representing a deck of cards containing 84 ‘programming cards’ \*
The ‘programming cards’ should have a priority value \* The product
should have a method to shuffle the deck of ‘programming cards’ \* The
application should have a main menu.

-   In the next version, we plan to implement the playing cards
    graphically and make the player move using cards. We also plan to
    implement collision and start work on giving tiles functionality
    (making the player move while standing on conveyor belts etc.).

-   The list of MVPs remains unchanged from the last version and we will
    continue to work on MVPs outlined in the previous assignment.


