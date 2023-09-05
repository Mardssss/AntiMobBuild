
# Anti Mob Build - Minecraft Plugin

Anti Mob Build is a Minecraft plugin designed to prevent the creation and spawning of mobs in your Minecraft server. This can help you create mob-free zones or customize the mob spawning behavior in your server's world.

## Features
- **Broadcast Messages**

- **Entity Configuration**

- **Natural Mob Spawning**

- **Installation**

**Download the plugin**: You can download the latest release from the [Releases](https://github.com/YourUsername/AntiMobBuild/releases) section of this repository.

**Place the JAR file**: Put the downloaded JAR file into your server's `plugins` folder.

**Restart your server**: Restart or reload your server to enable the plugin.

## Configuration

You can configure Anti Mob Build by editing the `config.yml` file located in the `plugins/AntiMobBuild` folder.

Here's an example of a basic configuration:

```
# AntiMobBuild Configuration

############################################
#                                          #
#            ___     __  ___ ____          #
#           /   |   /  |/  // __ )         #
#          / /| |  / /|_/ // __  |         #
#         / ___ | / /  / // /_/ /          #
#        /_/  |_|/_/  /_//_____/           #
#                                          #
#            AntiMobBuild                  #
#                                          #
############################################

# Set to 'true' to enable broadcasting messages to players
# when mob spawns are canceled by the plugin.
# Set to 'false' to disable broadcasting messages to players.
broadcast-messages-to-player: true

# Set to 'true' to enable broadcasting messages to the console
# when mob spawns are canceled by the plugin.
# Set to 'false' to disable broadcasting messages to the console.
broadcast-messages-to-console: true

# You can configure the entities that can be spawned here

# Set to 'true' to allow the creation of Iron Golems.
# Set to 'false' to prevent the creation of Iron Golems.
can-create-irongolem: false

# Set to 'true' to allow the creation of Snow Golems.
# Set to 'false' to prevent the creation of Snow Golems.
can-create-snowman: false

# Set to 'true' to allow the creation of Wither Bosses.
# Set to 'false' to prevent the creation of Wither Bosses.
can-create-wither: false

# Set to 'true' to allow the curing of infected Villagers.
# Set to 'false' to prevent the curing of infected Villagers.
can-cure-villager: false

# Set to 'true' to allow Villagers to be infected by zombies.
# Set to 'false' to prevent Villagers from being infected.
can-infect-villager: false

# Set to 'true' to allow Silverfish to spawn from blocks.
# Set to 'false' to prevent Silverfish from spawning.
can-spawn-silver-fish-on-blocks: false

# Set to 'true' to allow mobs to spawn from eggs.
# Set to 'false' to prevent mobs from spawning from eggs.
can-spawn-from-eggs:
  enabled: true
  exceptions:
    - WOLF
# set to false to enable natural mob spawning
# set it to true to disable natural mob spawning
# add exceptions , if you set WOLF as exceptions and natural-mob-spawn is true , only wolf can spawn
natural-mob-spawn:
  enabled: true
  exceptions:
    - WOLF
    - ZOMBIE
    - CREEPER
```
## Usage

To protect a specific area from mob spawning, define the protected zone in the `config.yml` file as shown in the example above.
    
Configure any additional settings in the `config.yml` to suit your server's needs.
    
Restart your server to apply the changes.
    

## Contributing

Contributions are welcome! If you would like to contribute to this project, please open an issue or submit a pull request.


--------

Enjoy using Anti Mob Build on your Minecraft server!
