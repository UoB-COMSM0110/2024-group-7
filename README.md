# GROUP 7 💣  BOMBERMAN DUNGEON

## 1. TEAM
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

## 2. INTRODUCTION

Our Bomberman game mainly revolves around two game modes: In the single-player mode, the player enters the next round of the game by getting the key to enter the door, and the player aims to pass more levels. We have innovatively added various props, shops and skills to the single-player mode, and set up two difficulties to make the game more playable. In the multiplayer game mode, players must try to eliminate each other and win the game. In order to increase the excitement of the game, a countdown function is creatively added to narrow the scope of the confrontation between the two parties. Gameplay involves strategically placing bombs that explode in multiple directions after a certain time to destroy obstacles and kill enemies and other players. Players can pick up various power-ups, giving them benefits such as greater explosive power or the ability to place more bombs at once. Players will be killed if they come into contact with an enemy or get caught in a bomb explosion (including their own), requiring players to be careful about where they place their bombs.

## 3. REQUIREMENTS

The group evaluated 5+ options and decided to develop a game based on bomberman as it is a beloved classic that is not devalued by simple 2D graphics. The popularity of roguelite dungeon crawlers like [Hades](https://store.steampowered.com/appp1145360/Hades/) led us to believe that they wuld make a fun twist to Bomberman.

### 3.1 Use Case Diagram  

<center>
<figure>
    <img src="Assets/Diagrams/useCase.png"
            alt="Use Case Diagram">
</figure>
</center>

### 3.2 User Stories

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
>

### 3.3 Early Stages Design

We first used brainstorming methods to come up with some ideas in the group, and then discussed these ideas. We conducted in-depth discussions mainly on technical issues, playability issues, innovative issues and reliability issues, and analyzed the innovative points of each idea. and challenges, and finally chose two options.

### 3.4 Ideation Process

We used the methods we learned in class to make props to demonstrate the game, and drew a prototype of the game. After many discussions and game deductions, we had a relatively clear understanding of our project.Then we divided the tasks and used project management tools such as stack and jira.Finally, we started the project development, demo recording and other work.

[//]: # (### STAKEHOLDERS)
[//]: # (- Players)
[//]: # (- Developers)
[//]: # (- Markers)

### 3.5 CASE SPECIFICATIONS

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

[//]: # (### RREFLECTION)
[//]: # (This exercise has helped us explore actions and options that need )
[//]: # (to be implemented within our game.)

## 4. DESIGN

### 4.1 System Architecture

### 4.2 Class Diagrams

<center>
<figure>
    <img src="Assets/Diagrams/class.png"
            alt="Class Diagram">
</figure>
</center>

### 4.3 Behavioural diagrams

[//]: # (### communication Diagrams)

[//]: # (<center>)
[//]: # (<figure>)
[//]: # (    <img src="Assets/Diagrams/communication.png")
[//]: # (            alt="Communication Diagram">)
[//]: # (</figure>)
[//]: # (</center>)

[//]: # (### RREFLECTION)

[//]: # (This exercise has helped us structure our codeand assgign tasks )
[//]: # (within the group.)

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
