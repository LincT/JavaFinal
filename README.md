# BlockMaker

##### This will be a mod that allows users to add custom aesthetic content to an existing game without any code knowledge

(apologies in advance if this seems excessively detailed.)

#### TODO: 
- [x] make screenshot links work.
- [ ] make texture rendering work from external directory

## Setup:
After pulling this project from github, next steps to run as follows:
in terminal run "gradlew runclient"

If all is as it should be, you'll start a development version of Minecraft. 
(it may take a moment as there's a bit going on behind the scenes)

## Post load
You'll want to go to [Single Player](https://github.com/LincT/JavaFinal/blob/master/screenshots/Main%20Menu.JPG)
click on the file called "new world"
click on "play selected world" [WorldSelect](https://github.com/LincT/JavaFinal/blob/master/screenshots/WorldSelect.JPG)

## Gameplay

##### necessary information to view features

`WASD` to move forwards, backwards, and to strafe left and right.

Mouse to control looking direction

`/`opens the commandline with the `/` already present (tab autocomplete supported)

i.e. `/gamemode c` to go into creative mode 

`e` to open inventory

## Features
once the game world loads, you'll see hopefully really nice blocks, otherwise if the code is incomplete you'll
see blocks colored [black and pink](https://github.com/LincT/JavaFinal/blob/master/screenshots/MissingTexture.JPG), absolute eyesore, but at least they are there.

a brief example of the implemented goal is if you go to the inventory screen in creative mode, 
go to the tab for `block maker mod` and you should see a block there for every entry in 
`%appdata%\.minecraft\config\bmm\blockNames.txt`

another example is viewable with a [debug command](https://github.com/LincT/JavaFinal/blob/master/screenshots/CLIOutput.JPG). 
it's a good development example that allows a user to view data in 
real time without writing to a text file or parsing the terminal for output. 
currently it's coded to sample data, such as player name, mod id, dimension and location in game, and a few directory
path tests. this format can easily be implemented in other commands for either gameplay or debugging by any developer.

## Code Files

while most of these should be commented, a little background here:

##### FileIO 

should be the only class doing actual file reading, anything that needs to load something should go through this.

##### CommandDebug and CommandGUI

both follow a template reverse engineered from a much more complex example, I wanted to show a starting point for 
others looking to start a new commands. CommandGUI is not fully implemented, I developed a GUI to modify the text file
that adds blocks, however while the gui loads in a normal java program, it is not callable from the minecraft CLI

##### BlockImporter

Uses fileIO to read the block list, translates this to data for generating instances of MonoTextureBlock,
then generates blocks and registers them with the game accordingly, including setting a display name by bypassing a 
resource file and adding entries from the external blockNames.txt file. This is also the class responsible for making
custom added blocks show in the tab for the game add on.
Ideally this would be where a texture name is assigned.
Future development will ask the users for more
customizable traits of the blocks such as material, light, hardness, sound when broken, etc.

##### MonoTextureBlock

This class is a wrapper for a standard block object in the game, within it, it should have the logic to assign the 
correct texture from a string, as well as any other attributes that need to be assigned.

##### BlockMakerMod

the main file for the mod. normally it is without a main(). I however use one here as a hacky way to bring up the 
BlockUI so we can see there is some interface logic already designed and functional.

##### BlockMakerModel

a model for displaying blocks in the jtable of the BlockUI form. 

extensible as comma delimited lines display in multiple columns

(I might have some logic in the wrong place between the model and the form)