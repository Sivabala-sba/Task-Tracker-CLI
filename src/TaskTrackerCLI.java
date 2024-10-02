import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;

public class TaskTrackerCLI {

    private static final String TASK_FILE = "tasks.json";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        TaskTrackerCLI cli = new TaskTrackerCLI();

        System.out.println("Welcome to Task Tracker CLI");
        System.out.println("Type 'help' to see the list of commands.");

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine();
            String[] commandArgs = input.split(" ", 2); // Split only into command and remaining part

            String command = commandArgs[0].trim();
            String commandOptions = commandArgs.length > 1 ? commandArgs[1].trim() : "";

            if (command.equals("exit")) {
                System.out.println("Exiting Task Tracker.");
                break;
            }

            try {
                switch (command) {
                    case "add":
                        if (commandOptions.isEmpty()) {
                            System.out.println("Please provide a task description.");
                        } else {
                            cli.addTask(commandOptions);
                        }
                        break;

                    case "update":
                        String[] updateArgs = commandOptions.split(" ", 2);
                        if (updateArgs.length < 2) {
                            System.out.println("Please provide task ID and new description.");
                        } else {
                            cli.updateTask(Integer.parseInt(updateArgs[0]), updateArgs[1]);
                        }
                        break;

                    case "delete":
                        if (commandOptions.isEmpty()) {
                            System.out.println("Please provide a task ID.");
                        } else {
                            cli.deleteTask(Integer.parseInt(commandOptions));
                        }
                        break;

                    case "mark-in-progress":
                        if (commandOptions.isEmpty()) {
                            System.out.println("Please provide a task ID.");
                        } else {
                            cli.updateStatus(Integer.parseInt(commandOptions), "in-progress");
                        }
                        break;

                    case "mark-done":
                        if (commandOptions.isEmpty()) {
                            System.out.println("Please provide a task ID.");
                        } else {
                            cli.updateStatus(Integer.parseInt(commandOptions), "done");
                        }
                        break;

                    case "list":
                        if (commandOptions.isEmpty()) {
                            cli.listTasks(null);
                        } else {
                            cli.listTasks(commandOptions);
                        }
                        break;

                    case "help":
                        cli.printHelp();
                        break;

                    default:
                        System.out.println("Unknown command: " + command);
                        cli.printHelp();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private JSONArray readTasks() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(TASK_FILE)) {
            Object obj = parser.parse(reader);
            return (JSONArray) obj;
        } catch (IOException | ParseException e) {
            return new JSONArray();
        }
    }

    private void writeTasks(JSONArray tasks) throws IOException {
        try (FileWriter file = new FileWriter(TASK_FILE)) {
            file.write(tasks.toJSONString());
            file.flush();
        }
    }

    private void addTask(String description) throws IOException, ParseException {
        JSONArray tasks = readTasks();
        JSONObject newTask = new JSONObject();

        int id = tasks.size() + 1;
        newTask.put("id", id);
        newTask.put("description", description);
        newTask.put("status", "todo");
        newTask.put("createdAt", LocalDateTime.now().toString());
        newTask.put("updatedAt", LocalDateTime.now().toString());

        tasks.add(newTask);
        writeTasks(tasks);

        System.out.println("Task added successfully (ID: " + id + ")");
    }

    private void updateTask(int id, String description) throws IOException, ParseException {
        JSONArray tasks = readTasks();
        for (Object obj : tasks) {
            JSONObject task = (JSONObject) obj;
            if (Integer.parseInt(task.get("id").toString()) == id) {
                task.put("description", description);
                task.put("updatedAt", LocalDateTime.now().toString());
                writeTasks(tasks);
                System.out.println("Task updated successfully.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    private void deleteTask(int id) throws IOException, ParseException {
        JSONArray tasks = readTasks();
        Iterator<?> iterator = tasks.iterator();

        while (iterator.hasNext()) {
            JSONObject task = (JSONObject) iterator.next();
            if (Integer.parseInt(task.get("id").toString()) == id) {
                iterator.remove();
                writeTasks(tasks);
                System.out.println("Task deleted successfully.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    private void updateStatus(int id, String status) throws IOException, ParseException {
        JSONArray tasks = readTasks();
        for (Object obj : tasks) {
            JSONObject task = (JSONObject) obj;
            if (Integer.parseInt(task.get("id").toString()) == id) {
                task.put("status", status);
                task.put("updatedAt", LocalDateTime.now().toString());
                writeTasks(tasks);
                System.out.println("Task marked as " + status + ".");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    private void listTasks(String status) throws IOException, ParseException {
        JSONArray tasks = readTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        for (Object obj : tasks) {
            JSONObject task = (JSONObject) obj;
            if (status == null || task.get("status").toString().equalsIgnoreCase(status)) {
                System.out.println("ID: " + task.get("id") + ", Description: " + task.get("description")
                        + ", Status: " + task.get("status") + ", Created At: " + task.get("createdAt")
                        + ", Updated At: " + task.get("updatedAt"));
            }
        }
    }

    private void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  add [description] - Add a new task");
        System.out.println("  update [id] [new description] - Update an existing task");
        System.out.println("  delete [id] - Delete a task by ID");
        System.out.println("  mark-in-progress [id] - Mark a task as in progress");
        System.out.println("  mark-done [id] - Mark a task as done");
        System.out.println("  list [status] - List tasks (optional: status can be 'done', 'todo', 'in-progress')");
        System.out.println("  help - Show this help menu");
        System.out.println("  exit - Exit the application");
    }
}