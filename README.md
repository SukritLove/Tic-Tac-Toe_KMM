# TicTacToe KMM

This is a Kotlin Multiplatform project targeting Android, iOS.

## How to setup

1. Install Plugin:
    - Kotlin Multiplatform Mobile
    - SQLDelight
2. Install Xcode to run IOS Application on an IOS Device
3. Before running the application, execute `./gradlew generateComposeResClass` in terminal to
   generate the Res Class.
4. After completing steps 1-3, you should be able to run this application in Android Studio.

## How to run

### Android
1. **Open Project:** Launch Android Studio and open the project.
2. **Run:** Choose to run the project on either an emulator or a physical device directly from
   Android Studio.

### IOS
1. **Pre-requisites:** Ensure Xcode is installed on your macOS device.
2. **Use Kotlin Multiplatform Mobile Plugin:** In Android Studio, utilize the Kotlin Multiplatform
   Mobile (KMM) plugin for running the project.
3. **Run:** Execute the project, which will open in Xcode due to the KMM plugin's integration,
   allowing you to run it on an iOS emulator or physical device.

## Built with

+ [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) -> Share code across iOS,
  Android, and Web platforms.
+ [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) -> Build
  cross-platform UIs with Kotlin.
+ [Voyager](https://voyager.adriel.cafe/) -> Multiplatform Navigation library
+ [SQLDelight](https://cashapp.github.io/sqldelight/2.0.1/) -> Multiplatform SQL Database library
+ [KotlinXDatetime](https://github.com/Kotlin/kotlinx-datetime) -> Multiplatform Datetime library
+ [moko-mvvm](https://github.com/icerockdev/moko-mvvm) -> Multiplatform Model-View-ViewModel
  architecture library

## How to design a program

### Home Screen

1. **Selectable Board Size:** Users have the option to choose the size of the game board, ranging
   from 3x3 to 8x8.
2. **Game Mode Selection:**
    - **PvP (Player vs. Player):** In this mode, users can select the size of the board.
    - **AI Modes (AI vs. Player or Player vs. AI):** The game can only be played on a 3x3 board.
3. **Gameplay Options:** Users can play the game, view the game history, and exit the game.

### Play Screen

1. **Board Size Dependency:** The size of the board corresponds directly to the user's selection
   from the home page.
2. **Game Mode and Player Visibility:** Players can see the current game mode they are in and which
   player's turn it is to play.
3. **Post-Game Dialogue Box:**
    - After a game concludes, a dialogue box appears to announce the winner or declare the game as a
      tie.
    - Users have the option to restart the game or exit.
4. **Quit Button:**
    - Located at the bottom of the screen, allowing users to quit the game at any time.
    - Upon confirming through a dialogue box, users are guided back to a specified starting point or
      menu.

### History Screen

1. **Game Mode Filtering:** Users can filter the game mode by selecting either "PvP" or "AI" at the
   top of the table, which will then display games corresponding to the selected mode.
2. **History Overview**
    - **Winner Identification:** Information on who won each round, clearly indicating the player's
      name or if the game resulted in a tie.
    - **Board Size:** The size of the board used in each game, offering insight into the game's
      complexity and duration.
    - **Game End Time:** The exact time when each game concluded, providing a temporal reference for
      the game's length and when it was played.
3. **Empty Table Notification:** If the table shows no data, it indicates that there have been no
   games played in the selected game mode yet.
4. **Clearing History:** Users can clear the game history by clicking the "Clear History" button. A
   confirmation dialogue box will appear to confirm the action before the history is permanently
   cleared.
5. **Navigation to Home Screen:** Users can return to the home screen at any time by clicking the "
   Back" button.

### Exit the application

1. **Using the Exit Button:** Users can exit the application by clicking the "Exit" button. A
   confirmation dialogue box will then appear to ensure the user's intent to exit. Upon
   confirmation, the application will close.
2. **Quick Exit for Android Users:** Android users have the option to exit the game immediately by
   pressing the back button on their device twice. This action bypasses the confirmation dialogue
   box and closes the game directly.
3. **Quick Exit for iOS Users:**
    - Using Home Button (for models with a Home button): Quickly press the Home button twice and
      swipe up on the app's preview to close it immediately.
    - Using Gesture (for models without a Home button): Swipe up from the bottom of the screen and
      pause in the middle of the screen to bring up the app switcher. Then, swipe up on the app's
      preview to close it.

## Algorithm

- **Minimax Algorithm** Determines the best move for 'O', aiming to maximize AI's win chances while
  minimizing the player's.
- **Alpha-Beta Pruning** Optimizes Minimax by eliminating unlikely branches, speeding up
  decision-making without affecting outcomes.