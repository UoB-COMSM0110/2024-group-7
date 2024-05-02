# GROUP 7 💣  BOMBERMAN DUNGEON

## 🎥 PROMOTIONAL VIDEO

[![Promotional Video](Assets/Images/mainMenu9.PNG)](https://www.youtube.com/watch?v=ispMEXkR_t4)

## 1. TEAM

![Group members sitting behind a paper prototype of the game](Assets/Images/group_meeting.png)

05.02.2024

| MEMBER | EMAIL |
| -----| ----- |
|[Tianyu Liu](https://github.com/bv23164) | [bv23164@bristol.ac.uk](mailto:bv23164@bristol.ac.uk)|
|[Lea Lewis](htps://github.com/le2310al) | [px23592@bristol.ac.uk](mailto:px23592@bristol.ac.uk)|
|[Yiguang Chen](htps://github.com/dcchenyg) | [te23143@bristol.ac.uk](mailto:te23143@bristol.ac.uk)|
|[Zilou Li](htps://github.com/ne23594g) | [ne23594@bristol.ac.uk](mailto:ne23594@bristol.ac.uk)|
|[Zora Chen](htps://github.com/fg23262) | [fg23262@bristol.ac.uk](mailto:fg23262@bristol.ac.uk)|

## 2. INTRODUCTION

Our game ‘Bomberman Dungeon’ is based on NES and SNES era [Bomberman]( https://en.wikipedia.org/wiki/Bomberman) games. Like it’s namesake our game offers a single player as well as a multiplayer mode in which players clear their way through a grid-based map with the help of their trusty bombs.
Both modes feature the established combat mechanics of bombs being able to blast through breakable walls,  enemies as well as the players themselves. Multiplayer mode largely remains true to its inspiration, offering a two-player death match on a map that dwindles as time progresses. Single player mode however adds two exciting new features. Not only is the player now able to collect coins instead of points, which they can then trade for all-new single or multi-use power ups in the store. But they can also find hidden doors leading deeper into the dungeon increasing the difficulty of gameplay. 

## 3. REQUIREMENTS

### 3.1 Ideation Process

We evaluated 4+ options and decided to develop a game based on [Bomberman]( https://en.wikipedia.org/wiki/Bomberman) as it is a beloved classic that is not devalued by simple 2D graphics. It’s winning formula has remained largely unchanged throughout the years which allows for a variety of possible twists. The popularity of rogue-lite dungeon crawlers like [Hades](https://store.steampowered.com/appp1145360/Hades/) led us to believe that randomised maps and meta progression would be the best course of action, although platform level design such as in [Spelunky]( https://en.wikipedia.org/wiki/Spelunky) was considered. Working through the Stakeholder Strories and Case Specifications helped us explore actions and options that needed implementing in our game.

| Selection of Other Games Considered |
| --- |
| 1. [Pokémon Showdown]( https://pokemonshowdown.com/): A Pokémon battle simulator. The underlying mechanics for this game are too simple to recreate and its enjoyability is overly reliant on monster design and associated nostalgia. Creating balanced and challenging powers would prove too difficult and overshadow other parts of the development process. |
| 2. [Crossy Road]( https://www.crossyroad.com/): An 2.5D endless runner version of the arcade classic [Frogger](https://en.wikipedia.org/wiki/Frogger) in which a chicken must cross the road ad infinitum. Unfortunately, this game has already been recreated in Processing. |
| 3. [Lith]( https://apps.apple.com/us/app/lith/id897768601): An ice slide puzzle game. This game would have required us to hand craft levels, and we were unable to think of a fun twist on the underlying mechanics that hadn’t already been explored in the game. |
| 4. [Downwell]( https://store.steampowered.com/app/360740/Downwell/): A precision platformer in which the player must clear their way of enemies and obstacles while in free fall. As the gameplay is a twist on [Doodle Jump](https://en.wikipedia.org/wiki/Doodle_Jump) and introduces many new mechanics, introducing our own twist would not improve upon the game. |

### 3.2 Early Stage Design

During the week 3 workshop we quickly made a firm decision to go ahead with 'Bomberman Dungeon' and created a rudimentary paper prototype. As we received positive feedback from other groups exploring our prototype, we did not create a second prototype and instead dove straight into creating sprites and other assets to make a digital prototype. The digital prototype aimed to both identify elements in need of implementation as well as provide sprites/assets to aid testing the playability of the game early on.

[![Prototype](Assets/Images/prototypeMainMenu.PNG)]()

![Sprite Sheet](Assets/Sprites/spriteSheet.png)

### 3.3 Stakeholder Stories

>"As a player, I want intuitive and reconfigurable controls." (Physical Impairment)

>"As a player, I want sprites to be distingushable based on shape, not colour." (Visual Impairment)

>"As a player, I want to customise my character."

>"As a player, I want a clear understanding of the game mechanics."

>"As a player, I want achievable yet challenging goals."

>"As a player, I want a straightforward HUD to efffectively strategize."

>"As a player, I want a to keep track of my achievements."

>"As a player, I want replayability and a sense of progression."

>"As a player, I want to explore different game modes and difficultiy levels"

>"As a player, I want to relive the nostalgia of 8-bit games on a CRT."

>"As a player, I want to blow up my friends, even if they are far away."

>"As a developer, I want to gain industry relevant skills"

>"As a developer, I want to work well as part of a team"

>"As a developer, I want to plan my time well and do my fair share of work."

>"As a developer, I want to set a good example for the next cohort."

>"As a marker, I want to experience all core game mechanics within 5 minutes."

>"As a marker, I want to feel compelled to play the game in my own time."

>"As a marker, I want to not be bored by a generic game clone."

### 3.4 Use Case Diagram

![Use Case Diagram](Assets/Diagrams/useCase.png)

Created with PlantUML

### 3.5 CASE SPECIFICATIONS

| Basic Flow | Alternative Flow |
| ----- | ----- |
| | Rebind Keys |
| | Change Character |
| | Choose Mode |
| | Reset Achievements |
| Play Untimed Mode| Exit |
| Explore Dungeon | Walk into Wall|
| Bomb Breakable Wall | <span style="color:red;"> Bomb Yourself: Game over </span> |
| <span style="color:green;"> Find Door </span> | |
| Spot Enemy | <span style="color:red;"> Walk into Enemy: Game Over </span> |
| Bomb Enemy | <span style="color:red;"> Bomb Yourself: Game Over </span> |
| <span style="color:green;"> Receve Points </span> | |
| <span style="color:green;"> Receve Key </span> | |
| Open Door | Explore more ~~or get lost~~ |
| Choose ' Health Up' Power up | Choose other Power Up ~~or none~~ |
| <span style="color:orange;"> Walk into Enemy: Lose Life </span> | Spot Enemy |
| <span style="color:red;"> Bomb Yourself: Game Over </span> | ~~Or be better at the Game~~ |
| Restart with Meta Progression  | Try other Timed Mode instead ~~or exit~~|

## 4. DESIGN

### 4.1 System Architecture

#### 4.1.1 Game Overview

Our game has two modes, single-player mode and two-player mode. The single-player mode is divided into two difficulties. Players can get the key to find the door and enter the next level. Players aim to enter a deeper level; in the two-player mode , two players will compete against each other to defeat each other and achieve victory.

#### 4.1.2 Game world

Bomberman is a two-dimensional game. The game map has destructible and non-destructible walls as well as monsters and various props. It is very playable and has many innovations compared to the original version, such as various skills. The game is generally more casual and entertaining. The game may be a little difficult at the beginning, but as the game progresses, players will become stronger and stronger. Players can not only attack deeper levels in the game, but also enjoy the fun of confrontation with friends.

#### 4.1.3 Game Characters

The game character has some basic values, namely health and movement speed. The bombs placed by the character have bomb range and bomb quantity. These values can be improved by obtaining props in the single-player mode. However, for the sake of fair confrontation, in the two-player mode Fixed value. In addition, there is also the addition of a store, where keys and skills can be purchased, and the required gold coins need to be picked up by the characters on the map.

#### 4.1.4 Game Mechanics

In addition to operating the character to move up, down, left, and right, the player also has two keys. One is to place a bomb. The bomb will explode after a period of time. It can clear the monsters and destructible walls that the player comes into contact with. The health value will also be deducted. The other key is the skill key. Pressing it can trigger powerful effects, such as killing all monsters.
In single-player mode, players need to get the key and enter the door. In the process, they can pick up props and purchase props in the mall. In two-player mode, players need to kill each other to win the game

#### 4.1.5 Game system

The game's prop system can improve the various values ​​of characters and bombs, making players continuously stronger; the monster system is designed with action AI to make monsters move logically; in the combat system, players can place bombs and use skills to kill monsters or destroy them. Wall; in the economic system, players can pick up gold coins, which can be used to buy powerful skills and keys in the store; in the confrontation system, players from both sides can compete, and in order to ensure fairness, the values ​​are constant; Game key positions can be changed in the system settings. The prop system and combat system ensure the strength of the characters, and the economic system adds playability to the game.

#### 4.1.6 Game content

In the simple single-player mode, players need to pick up gold coins to improve the economy and pick up props to improve their own values. The props and gold coins are hidden in destructible walls. Different props can make each game experience different. At the same time, players should also avoid the pursuit of monsters or place bombs to kill monsters. Monsters will continue to move and cause interference to players. When the player accumulates enough gold coins and finds the location of the door, he can go to the store to buy a key and enter the door to enter the next level. If you want to play quickly, you can use accumulated gold coins to purchase powerful skills to speed up the game progress. In the difficult single-player mode, the player's field of view becomes smaller, which increases the difficulty of the game. In the two-player mode, the confrontation range will shrink every 10 seconds. If you step out of this range, your life will be reduced, and the party whose life value reaches zero first will lose.

#### 4.1.7 Progress and Reward

The game's economic system is a reward mode. Players can purchase powerful skills and keys by accumulating gold coins, such as destructible walls that can clear the screen, clear monsters, teleport to the door, super large bomb flames, etc. This can Speed up the progress of the game and enter deeper levels faster. This result will be recorded on the menu, which can satisfy those players who like to challenge themselves. In the confrontation mode, we use the joy of winning when players defeat each other as a reward mode. The unique interaction method and appropriate system prompts make it easy to get started with the game; achievements can be displayed on the desktop, giving users a sense of satisfaction after completing the level. The addition of skills and stores makes the game playable.

#### 4.1.8 Map Generation

The project contains unused code that allows for a grid-based map to be procedurally generated according to specifications such as number and size of rooms. Objects and enemies can be included in the generation process with customisable spawn rates. A data structure containing the status and description of each tile on the map is returned and can be used to update the game state, conduct collision detection, and easily render a select number of game elements to improve game performance.

![Example Map](Assets/Images/map.PNG)

Legend: # Unbreakable Wall

### 4.2 Class Diagram

![Class Diagram](Assets/Diagrams/class.png)

Created with PlantUML

### 4.3 Behavioural Diagram

![Behavioural Diagram](Assets/Diagrams/behavioural.png)

## 5. IMPLEMENTATION

During the development process, we encountered numerous challenges. While the implementation of individual features was relatively straightforward, integrating them often required modifications to the existing code base. Three challenges stood out.

### 5.1 Smooth Player Movement

Prior to populating the map with additional items, collisions were detected by comparing the players goal coordinates with the coordinates of all walls and rocks present.
Adding new items initially did not affect framerates, however once the powerup increasing player speed was introduced, we were baffled to see that player movement would not increase regardless of the chosen speed value. Once we identified the culprit to be a lack of optimization of collision detection, we were able to tackle this challenge. Our solution took the form of a 2D Boolean Array that marks impassable areas on the map as false. Subsequently goal coordinates must now only be checked against their corresponding Boolean value within the array.
However, by the time this issue had been addressed, we had already implemented map RNG and the association of powerups with breakable rocks. Modifying the collision detection therefore also required changes to the aforementioned features, substantially increasing the workload. 

### 5.2 Power-ups

Adding powerups to the game revealed itself to be a compound of two challenges.
The first challenge involved ensuring that the same rock was not repeatedly chosen to hide different powerups during random map generation. This was solved by first assigning each item a unique index and then storing the index within a Hash Set to indicate that the item was hiding behind a specific rock. Due to the random and unique nature of Hash Set values, this approach prevented an item from being hidden behind the same rock twice.
The second challenge involved displaying the correct item once their associated rocks were destroyed. Unfortunately, the existing approach to destroying rocks did not support adding this feature requiring modifications to both classes for bombs and flames. We chose to introduce an intermediate state between the destruction and disappearance of the rock. Furthermore, we added a Boolean flag to the rock class indicating whether or not it stored an item. When the rock was put in the intermediate state, its other properties determined which item to generate, and an instance of the item was created.


### 5.3 Resetting the Game State

Nearing completion of the game, we were missing a feature that would allow the player to reset the map and any progress made. Unfortunately map RNG was bound to the setup section of Processing, where statements are executed only once at the start of the game.
Implementing this feature required adjustments to vast amounts of code pertaining to the map, enemies, items, and players. We ended up writing individual reset functions for each class, which were executed upon starting the game.
For instance, in order to reset breakable rocks, the map must first be cleared of and repopulated with rocks. Then the collision detection array must be updated according to the placement of the new rocks. Finally, new items need to be associated with the rocks.

## 6. EVALUATION

We invited a total of 10 users to participate in user testing in order to gain a better understanding of the functionality, usability, and reliability of our game. Insights gained were used for further optimization of our game.

### 6.1 Qualitative

We used the Think Aloud method to perform a qualitative analysis. Half of the participants had prior experience playing games within the Bomberman franchise, whereas the others didn’t. Participants were asked to verbalize their thoughts, feeling and experiences while playing our game. 

| Areas of Improvement Identified |
| --- |
| 1. Missing Player Instructions: Keybinding are now visible on the main menu |
| 2. Unreliable Movement Control: Collision Detection was refined, allowing for turning, stopping, and acceleration of the player to be handled more smoothly |
| 3. Unbalanced Difficulty Level: Number of Items and Enemies was adjusted and an addition hard game mode was introduced |

| Positive Feedback Received |
| --- |
| 1. Multiplayer mode is very enjoyable |
| 2. Original powers greatly add to the user experience |

### 6.2 Quantitative

We asked users to fill out both SUS and NASA-TLX questionaiires after immersinng themselves in both the single and multiplayer mode of our game.

### 6.2.1 NASA-TLX

Analysing survey results using the Wilcoxon Signed-Rank Test with a significance level of 5%, indicates no significant difference between the single and multiplayer modes (W Test Statistic = 15). Taking a detailed look at all six dimensions of the test, reveals that scores for Mental Demand, Physical Demand, Effort, and Frustration are similar in both game modes. Therefore, we can conclude a balanced user experience in which the player can leisurely play without experiencing pronounced levels of frustration.
It is worth noting that Temporal Demand is significantly higher in the multiplayer mode than in the singleplayer mode. This confirms the intended effect of the countdown timer in multiplayer mode that restricts player movement and encourages them to approach and defeat their enemy. This feature seemingly also impacts Performance scores leaving players less satisfied due to the competitive element.

![NASA-TLX: Single-Player mode](Assets/Images/NASA_single.png)
![NASA-TLX: Two-Player mode](Assets/Images/NASA_two.png)

### 6.2.2 SUS

The average score for singleplayer and multiplayer mode are 76.5 and 79.5, respectively. Both of which are higher than the general usability benchmark of 68. Results of a Wilcoxon Signed-Rank Test at 5% Significance level further indicated no significant difference in usability between single and multiplayer mode (W Test Statistic = 21). This outcome aligns with our expectations, as fundamental mechanics as well as assets do not differ between modes.


![SUS: Single-Player mode](Assets/Images/SUS_single.png)
![SUS: Two-Player mode](Assets/Images/SUS_two.png)

### 6.3 Code Testing

As our codebase was in constant flux and was refactored multiple times, we did not conduct unit testing but rather focused our efforts on playtesting during the development of new features as well as after their integration. This has allowed us to identify numerous bugs ranging from errors in the HUD to dead zones within the map. With the addition of user testing, we are confident in the reliability of our game.

## 7. PROCESS

### 7.1.1 Game

Due to the small size of our team, we never assigned ourselves clear roles as multifaceted contributions were required from all team members. Initiative as a team lead alone was based on availability, with Lea directing efforts prior to the easter break and Zilou from the easter break onwards.
The main concepts represented by our game title ‘Bomberman Dungeon’ were devised by Tianyu and Lea. Yiguang and Lea were the first to familiarise themselves with Processing and wrote a skeleton covering a basic UI, player movement and collision mechanics.
Due to an unfortunate miscommunication, further improvements made to the code by Lea were accidently discarded. These improvements included procedural map generation and predictive correction of user input for smooth player movement.
The remaining fundamental mechanics were split between Yiguang, Zora and Zilou. Yiguang handled bomb mechanics such as the explosion radius and its interactions with the player, enemies, and breakable rocks. Zora implemented a variety of classic power-ups that improve the players walking speed and the capabilities of the bombs in terms of range, firing power, and carrying capacity. Zilou devised the enemy AI and refactored code for readability.
With an agile approach in mind additional mechanics deviated from our initial plans. Further functionality to the skill system was added by Zora who implemented coins as a reward system, and Zilou who crafted original skills which can be purchased in a shop. Zilou additionally introduced the option to enter a new level by respawning the map as well as the multiplayer mode.
The game is tied together by Tianyu’s work on the UI allowing players to rebind keys, seamlessly transition between menu options and be up to date on the game state at all times. 
Finishing touches in the form of sound effects and updated assets were made by Yiguang.

### 7.1.2 Video

The Video was scripted, directed and edited by Zilou.

### 7.1.3 Report

-	Section 2: Tianyu
-	Section 3: Lea 
-	Section 4.1 + 4.3: Zilou
-	Section 4.2: Lea
-	Section 5: Yiguang
-	Section 6: Zora
-	Section 7: Lea
-	Section 8: Tianyu

### 7.2 TOOLS

- [WeChat](https://www.wechat.com/): As every team member was already familiar with this instant messenger, it was used to organise meetings and share progress reports
- [Slack]( https://slack.com/intl/en-gb): During the research phase different channels were used to effectively organise and share materials. Unfortunately, usage did not gain traction and WeChat remained the sole communication platform.
- [Jira]( https://www.atlassian.com/software/jira): We utilized a classic Kanban Board split into sections ‘In Progress’, ‘To-Do’ and ‘Done’. During in person meetings members would review proposed tasks and assign themselves those they preferred doing and matched their schedules best.
- [IntelliJ IDEA]( https://www.jetbrains.com/idea/): We mistakenly started developing in Java, instead importing Processing as a library. Therefore, a majority of development was undertaken in IntelliJ. We later switched to [PDE]( https://processing.org/) to verify that our .pde files ran correctly.
- [Git]( https://docs.github.com/en/get-started/using-github/github-flow): We aimed to and for the most part succeeded in using ‘GitHub flow’ workflow in which features are developed in their own short lived branches before being merged back into main.


### 7.3 Code Style

```java
public class Bomberman extends PApplet {
   boolean play=true;

   public void settings() {
      size(width, height);

   }
}
```

## 8. CONCLUSION
In conclusion, we based our game on the iconic Bomberman, but infused lots of innovative ideas to enrich the gaming experience throughout our development process. For example, in the single-player mode, the primary goal for players is to enter as more as possible round of the game by getting the key to enter the door. So we innovatively introduced coins, shops and five special skills, players can buy skills in the shop by collocting coins to achieve higher achievements. In the two-player mode, the main purpose for player is to eliminate the opponent to win the game. To increase the tension and excitement of the game, we creatively added a countdown feature to narrow the combat zone. All these innovations not only set our game apart from the traditional Bomberman but also enhance the game’s enjoyment and novelty.  

During the process of the game development, to align with the agile principles, we used Jira to assign tasks and effectively monitor team progress. We communicated in the real time via WeChat. And code updated and maintenance were conducted through GitHub.

However, we encounted several challenges while we were developing our game. While the implementation of individual features was relatively straightforward, implementing some features required modifications to other already completed sections. These intertwined issues made implementation particularly difficult. Collison detect, power-ups and game restarts are the main challenges that we faced. Nevertheless, due to our exceptional teamwork, we successfully overcame all obstacles and significantly enhanced team members’ game development capabilitie.

Moreover, to ensure the game was user-oriented, in the later stages of game development, we collected player feedback through the Think Aloud method and quantitative survey, then we optimized our game based on those constructive comments.

To sum up, we are all proud of the game we have developed. In terms of innovation, fun, and usability, we believe our game has reached an impressive level. However, due to the time limit, there are some feeatures that could be optimized and impoved in the future, For instance, a feature where players would return to the starting position and lose all items and skills they had upon death could make the game more challanging. Additionally, the bomb’s firepower and timing of their explosions may be affected by nearby explosives, so that it would increase the thill and competition in the two-player mode.


