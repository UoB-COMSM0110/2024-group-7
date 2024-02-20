# GROUP 7 💣  BOMBERMAN DUNGEON

<center>
<figure>
    <img src="Assets/Images/group_meeting.png"
            alt="Group members sitting behind a paper prototype of the game">
    <figcaption>05.02.2024</figcaption>
</figure>

| MEMBER | CONTACT |
| -----| ----- |
|[Tianyu Liu](https://github.com/bv23164) | [bv23164@bristol.ac.uk](mailto:bv23164@bristol.ac.uk)|
|[Lea Lewis](htps://github.com/le2310al) | [px23592@bristol.ac.uk](mailto:px23592@bristol.ac.uk)|
|[Yiguang Chen](htps://github.com/dcchenyg) | [te23143@bristol.ac.uk](mailto:te23143@bristol.ac.uk)|
|[Zilou Li](htps://github.com/ne23594g) | [ne23594@bristol.ac.uk](mailto:ne23594@bristol.ac.uk)|
|[Zora Chen](htps://github.com/fg23262) | [fg23262@bristol.ac.uk](mailto:fg23262@bristol.ac.uk)|
</center>

## INTRODUCTION

GAMES CONSIDERED: Crossy Road, Doodle Jump, Downwell, Ice Sliding Puzzle, Bubble Boble, God of Light, Pico Park

Most games in the Bomberman franchise largely revolve around two modes of play; single player campaigns where the player must defeat enemies and reach an exit to progress through levels, and multiplayer modes where players must attempt to eliminate each other and be the last one standing. Gameplay involves strategically placing down bombs, which explode in multiple directions after a certain amount of time, in order to destroy obstacles and kill enemies and other players. The player can pick up various power-ups, giving them benefits such as larger explosions or the ability to place more bombs down at a time. The player is killed if they touch an enemy or get caught up in a bomb's explosion, including their own, requiring players to be cautious of their own bomb placement. In addition to the main maze-based Bomberman games, some spin-off titles involve adventure, platformer, puzzle, and kart racing gameplay.

### TWIST

Singleplayer roguelite dungeon crawler featuring meta-progression powerups

## REQUIREMENTS

The group evaluated 5+ options and decided to develop a game based on bomberman as it is a beloved classic that is not devalued by simple 2D graphics. The popularity of roguelite dungeon crawlers like [Hades](https://store.steampowered.com/appp1145360/Hades/) led us to believe that they wuld make a fun twist to Bomberman.

### STAKEHOLDERS

- Players
- Developers
- Markers

### USER STORIES

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

### CASE SPECIFICATIONS

<center>

| Basic Flow | Alternative Flow |
| ----- | ----- |
| | Rebind Keys |
| | Change Character |
| | Choose Mode |
| | Reset Achievements |
| Play Untimed| Exit |
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
| Restart with Meta Progression  | Try other Mode instead ~~or exit~~|

</center>

### USE CASE DIAGRAM  

<center>
<figure>
    <img src="Assets/Diagrams/useCase.png"
            alt="Use Case Diagram">
</figure>
</center>

### RREFLECTION

This exercise has helped us explore actions and options that need 
to be implemented within our game.

## DESIGN

### CLASS DIAGRAM

<center>
<figure>
    <img src="Assets/Diagrams/class.png"
            alt="Class Diagram">
</figure>
</center>

### COMMUNICATION DIAGRAM

<center>
<figure>
    <img src="Assets/Diagrams/communication.png"
            alt="Communication Diagram">
</figure>
</center>

### RREFLECTION

This exercise has helped us structure our codeand assgign tasks 
within the group.

## IMPLEMENTATION

- CHALLENGE 1:
- CHALLENGE 2:
- CHALLENGE 3:

## EVALUATION

- QUALITATIVE:
- QUANTITATIVE:
- TESTING:

## PROCESS

### TOOLS

- [Slack](https://bombermandungeon.slack.com/ssb/redirect)
- [Jira](https://bomberman-dungeon.atlassian.net/jira/software/projects/BOM/boards/1)
- IntelliJ
- Github Flow

### CODE STYLE

```java
public class Bomberman extends PApplet {
   boolean play=true;

   public void settings() {
      size(width, height);

   }
}
```

### MEMBERS

<center>

| MEMBER | ROLE | CONTRIBUTIONS |
| -----| ----- | -----|
| Tianyu Liu | | |
| Lea Lewis | | |
| Yiguang Chen | | |
| Zilou Li | | |
| Zora Chen | | |

</center>

## CONCLUSION

<center>

| LESSONS | CHALLENGES | FUTURE WORK |
| -----| ----- | -----|

</center>