<h1 align="center">QuizMaze</h1>

<p align="center">
  <em>A strategic maze survival game built in Java where trivia knowledge is your key to escaping crocodiles and reaching the exit.</em>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21%2B-orange?logo=openjdk&logoColor=white" alt="Java" />
<img src="https://img.shields.io/badge/Swing-UI-blue?logo=openjdk&logoColor=white" alt="Swing" />
  <img src="https://img.shields.io/badge/MySQL-Database-4479A1?logo=mysql&logoColor=white" alt="MySQL" />
</p>

---

## 📚 Table of Contents

- [📝 Description](#-description "Learn more about the game's concept and purpose")
- [⚙️ Installation](#️-installation "Instructions to install and run the project locally")
- [🕹️ How to Play](#%EF%B8%8F-how-to-play "Step-by-step guide on how to play the game")
- [🔧 Game Architecture](#-game-architecture "Understand the structure and logic behind the game")
- [🧠 Technologies](#-technologies "See which technologies were used to build the project")
- [👥 Authors](#-authors "The autors of the proyect")

---

## 📝 Description

QuizMaze is a Java-based interactive desktop game developed as a group project. It combines logic, strategy, and trivia knowledge. The core concept revolves around navigating procedurally generated mazes where progress is determined not just by pathfinding, but by answering questions correctly.

The application features a robust **Management System** allowing users to design custom maze configurations (size, difficulty, item density) and a **Gameplay Mode** where users attempt to survive the maze while managing their health points against obstacles and trivia challenges.

**Key Features:**

- **User Management:** Secure login and registration system.

- **Maze Builder:** Create custom mazes with defined parameters (width, height, number of enemies/items).

- **Dynamic Gameplay:** Mazes are populated with:

  - 🐊 **Crocodiles:** Enemies that inflict damage.

  - ❤️ **Medkits:** Items that restore health.

  - ❓ **Trivia Questions:** Required to advance through specific sections or penalties.

- **Persistence:** All data (users, mazes, game history, questions) is stored locally using SQLite.

- **Leaderboard:** Tracks top scores and fastest times for each maze configuration.

---

## ⚙️ Installation

To run this program, you can easily use the **installer** in the [latest release](https://github.com/Davitroon/QuizMaze/releases "See latest release").

Otherwise, you can manually download the proyect following these steps:

### Prerequisites

- **Java Development Kit (JDK) 21** or higher.

- An IDE (Eclipse, IntelliJ, or VS Code) is recommended.

- **SQLite JDBC Driver** (ensure this library is added to your project's build path/classpath).

### Setup

1. **Clone/Download** the repository to your local machine.

```Bash
git clone https://github.com/usuario/nombre-del-repo.git
```

2. Open the project in your preferred IDE.

3. Ensure the `lib` folder (or build path) contains the SQLite JDBC `.jar` file.

4. Navigate to `src/logic/Launcher.java`.

5. Run the `main` method.

> **Note on Database:** You do not need to manually install a database server. The application includes a DBInitializer class that will automatically detect if quiz_maze.db is missing and generate the file along with all necessary tables and default data (including 50+ trivia questions) upon the first launch.

---

## 🕹️ How to Play

### Login

Upon launching, you will be greeted by the Login screen.

- **Default Credentials:** You can log in immediately using the pre-installed admin user:

    - **User:** `admin`

    - **Password:** `admin`

- Alternatively, you can register a new account.

### Modes

Once logged in, you have two main options:

- **Create Maze:** Define the rules of the game.

  - Set the grid size (Width/Height).

  - Define the number of **Crocodiles and Medkits**.

  - Set **Damage/Heal values** (how much life a croc takes or a medkit gives).

  - Configure **Questions** (Time limit to answer and damage taken for wrong answers).

    <div align="center">
        <img src="/docs/creation-showcase.gif" alt="Maze creation showcase">
    </div>

- **Play Maze**: Select an existing maze configuration.

    - **Goal:** Move your character from the starting point (0,0) to the exit (bottom-right).

    - **Movement:** Navigate the grid. If you encounter a question trigger, you must answer correctly within the time limit.

    - **Survival:** Keep your health above 0. If you hit 0 HP, it is Game Over.

    <div align="center">
        <img src="/docs/game-showcase.gif" alt="Maze creation showcase">
    </div>

    - **Winning:** Reach the end of the maze to save your score and time to the leaderboard.

    <div align="center">
        <img src="/docs/leadboard-showcase.png" alt="Maze creation showcase">
    </div>

---

## 🔧 Game Architecture

The project is structured using the **Model-View-Controller (MVC)** architectural pattern to ensure a clean separation of concerns and maintainable code.

### 📂 Package Structure

- `dao` **(Data Access Object)**:

  - Handles all direct interactions with the SQLite database.

  - `DBConnector.java`: Contains SQL queries for inserting/retrieving mazes, users, and game results.

  - `DBInitializer.java`: Handles the automatic setup of the database schema and default data injection.

- `model`:

  - Represents the data objects of the application.

  - `Maze.java`: Stores configuration (size, difficulty settings).

  - `Disposition.java`: Represents the specific grid layout (where walls, items, and enemies are located).

  - `User.java`: Stores player state (health, coordinates, points).

  - `Question.java`: Structure for trivia questions and answers.

- `logic` **(Controller)**:

  - Acts as the bridge between the UI and the Data.

  - `Controller.java`: The central hub that coordinates the app.

  - `DBController.java`: A wrapper that safely exposes database functions to the rest of the app.

  - `Launcher.java`: The entry point (main method) of the application.

- `ui` **(View)**:

  - Contains all `JFrame` classes responsible for the graphical user interface.

  - Managed by `UIController.java` which handles screen transitions (e.g., swapping from Login to Maze View).

---

## 🧠 Technologies

This project was built using the following technologies:

| Technology                                                                              | Description                                                     |
| --------------------------------------------------------------------------------------- | --------------------------------------------------------------- |
| ![Java](https://img.shields.io/badge/Java-21%2B-orange?logo=openjdk&logoColor=white)    | Main programming language used to build the entire application. |
| ![Swing](https://img.shields.io/badge/Javax%20Swing-UI-blue?logo=openjdk&logoColor=white)  | Used to create the graphical user interface.                    |
| ![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?logo=mysql&logoColor=white) | Relational database for storing users, games, and statistics.   |
| ![JDBC](https://img.shields.io/badge/JDBC-Connector-yellow?logo=openjdk&logoColor=white)   | Enables communication between Java and the MySQL database.      |

---

## 👥 Authors
<div align="center">
  <table>
    <tr>
      <td align="center">
        <a href="https://github.com/Davitroon" title="Davitroon's profile">
          <img src="https://github.com/Davitroon.png" width="100px;" alt="Davitroon's avatar"/>
          <br />
          <sub><b>Davitroon</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/exili0" title="exili0's profile">
          <img src="https://github.com/exili0.png" width="100px;" alt="exili0's avatar"/>
          <br />
          <sub><b>exili0</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/Tineo06" title="Tineo06's profile">
          <img src="https://github.com/Tineo06.png" width="100px;" alt="Tineo06's avatar"/>
          <br />
          <sub><b>Tineo06</b></sub>
        </a>
      </td>
    </tr>
  </table>
</div>
