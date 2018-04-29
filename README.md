# Bombero
<img src="img/bomberman_title.png" height="450px" width="100%" />

## Description
Bombero is a 2D game similar to Dyna Blaster (Bomberman). 
Goal of the game is eliminating all enemies and escaping through hidden gate. This is achieved by using bombs. Player has limited amount of time to do that.

### Menu
After game starts, player can see a menu with following options:

- **New game** - starts a new game with level #1. 
Player has 3 lives available. 
Explosion size: 1
Available boms: 1 

- **Select level** - opens list of available levels where player can select specific level or return to main menu screen.
After selecting level game starts in that level with same properties as the New game option.

- **_Options_** - (not guaranteed feature)
   - opens possible settings such as difficulty (# lives), viewable area of map, music volume ... 
                
   - uses libGDX Preferences to store permanent data

- **Exit** - shuts down game.

### Initialization of game
1. Load level - using level loader
2. Place items - gate and powerups
3. Place enemies

### Main game cycle
#### Repeat:
1. Read input
2. Update world
3. Resolve collisions
4. Check game over conditions
5. Render game

#### Game over:
- **success**: continue to next level
- **fail**: substract 1 life, if lives > 0 restart level, otherwise it is game over

### Map
Map contains static and dynamic objects:
#### A) Static objects
**Wall block** 
- undestructible
- nobody can moves through them

**Brick block** 
- destructible
- some enemies can move through, player cannot
- can contain item, which is revealed after contact with explosion

**Item** 
- hidden in bricks
- power ups (bomb count, explosion size), gate

**Gate** 
- opened when there are no enemies
- enemies are spawned after contact with explosion

**Fire powerup** 
- increases size of explosion (1,3,5)

**Bomb powerup** 
- increases number of placable bombs at one moment (1,2,3)

**Bomb** 
- planted by player
- after some time it will explode and destroy enemies, player, brick blocks and items in range
- will also explode after contact with explosion
- neither player nor enemies can go through
- default amount is 1 ATM

**Explosion** 
- replaces bomb after +-4 seconds
- makes fire in 4 directions, except directions where are wall blocks
- destroys bricks, items, enemies and also player if they are in contact
- default size of fire is 1, but can be enlarged by powerup item

#### B) Dynamic objects
**Player** 
- can move to 4 directions: up, down, left, right 
- can plant bombs (1 or more ATM)
- can collect items
- lose 1 life after contact with enemy or explosion

**Enemy** 
- movement depends on specific enemy type (multiple strategies)
- player receives points after elimination (amount depends on enemy type)       

### Level loading
Level data are stored in image files. 
Each pixel of image represents object on map or empty field. 
Every object has a specific color.
Map size is defined by image dimensions.

## Architecture
### Class diagram
Following diagram displays basic concept of game's structure. Diagram focuses on showing connections between classes. 

To keep diagram **_clean_**:
- some fields are left out (e.g. rendering fields - size, dimensions, position...)
- some methods (dispose)
- some classes are not in diagram (specialized EnemyStrategy classes)

<img src="img/ClassDiagram_concept.png" alt="Class diagram" width="100%"/>

There are __four colors__, which distinguish purpose (package) of class:

&nbsp; &nbsp; **1. orange** - main classes, contains logic and game cycle

&nbsp; &nbsp; **2. blue** - entity classes

&nbsp; &nbsp; **3. green** - helper classes

&nbsp; &nbsp; **4. gray** - libGDX classes

### Design patterns
#### Singleton
- *Bombero, World controller, Assets*
- there is just one instance of these classes and it is desirable to have acess to them from other classes
- getInstance() methods can be replaced with eager initiliazation (initialized with application start) - direct acess (instance is `public static final`)

#### Flyweight
- *Assets*
- Each texture is loaded just once

#### Strategy
- *EnemyStrategy*
- defines type of enemy - movement and score for elimination

## Graphics

## Testing
### Player movement
### Enemy movement
### Collisions