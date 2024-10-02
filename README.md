# Task Tracker CLI

A simple Command Line Interface (CLI) Task Tracker built in Java. This project helps you manage your tasks, including adding, updating, deleting tasks and changing task statuses such as "To Do", "In Progress" and "Done". All tasks are stored in a JSON file.

## Features
- Add, Update and Delete Tasks
- Mark tasks as "In Progress" or "Done"
- List tasks by status ("To Do", "In Progress", "Done")
- Store tasks in a JSON file
- CLI-based interface

## Requirements
- Java Development Kit (JDK) 8 or higher

## How to Run
1. Clone the Repository:
`git clone https://github.com/your-username/task-tracker-cli.git`
`cd task-tracker-cli`

2. Compile the Program:
`javac -cp ".:json-simple-1.1.1.jar" TaskTrackerCLI.java`

3. Run the Program:
`java -cp ".:json-simple-1.1.1.jar" TaskTrackerCLI`
Note: Ensure json-simple-1.1.1.jar is in your classpath for JSON parsing.

4. Usage:
Once the program is running, you can enter the "help" command to see the list of executable commands.

## Task Properties
Each task is stored with the following properties:
- ID: A unique identifier for the task
- Description: A short description of the task
- Status: The status of the task (todo, in-progress, done)
- Created At: The date and time the task was created
- Updated At: The date and time the task was last updated

## JSON Storage
All tasks are stored in a tasks.json file in the current directory. The JSON file is created automatically if it does not exist.

## Example JSON Format
    [
      {
        "id": 1,
        "description": "Buy groceries",
        "status": "todo",
        "createdAt": "2024-10-01T10:30:00",
        "updatedAt": "2024-10-01T10:30:00"
      },
      {
        "id": 2,
        "description": "Finish project documentation",
        "status": "in-progress",
        "createdAt": "2024-10-01T11:00:00",
        "updatedAt": "2024-10-01T11:30:00"
      }
    ]

## Future Enhancements
- Add due dates for tasks
- Add priority levels for tasks
- Allow filtering by task priority or due date

## Contributing
If you find a bug or want to add a feature, feel free to create an issue or submit a pull request.
Project URL: [Link](https://github.com/Sivabala-sba/Task-Tracker-CLI "Link")
