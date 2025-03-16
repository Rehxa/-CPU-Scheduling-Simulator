package CPU_Scheduling_Simulator;

import java.util.ArrayList;
import java.util.Scanner;

// Class to represent a process with its attributes
class InputProcess {
    int processId; // Process ID (user input)
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnAroundTime;
    int waitingTime;
    int remainingTime;

    // Constructor to initialize process attributes
    public InputProcess(int processId, int arrivalTime, int burstTime) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.completionTime = 0;
        this.turnAroundTime = 0;
        this.waitingTime = 0;
        this.remainingTime = burstTime;
    }
}

public class ProcessScheduling {

    // Method to check if the input is a valid positive integer
    public int inputChecker(String check) {
        if (check.matches("[0-9]+")) {
            if (Integer.parseInt(check) > 0) {
                return Integer.parseInt(check);
            } else {
                System.out.println("Please enter a positive number.");
                return -1;
            }
        } else {
            System.out.println("Please enter a valid number.");
            return -1;
        }
    }

    // Method to take input for processes
    public ArrayList<InputProcess> input() {
        System.out.print("Enter the number of processes: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ArrayList<InputProcess> processList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter the process id: ");
            String Id = sc.next();
            int processId = inputChecker(Id);
            System.out.println("Enter the arrival time and burst time for process " + (i + 1) + ": ");
            System.out.print("Arrival Time: ");
            String arrival = sc.next();
            int arrivalTime = inputChecker(arrival);
            System.out.print("Burst Time: ");
            String burst = sc.next();
            int burstTime = inputChecker(burst);
            if (processId == -1 || arrivalTime == -1 || burstTime == -1) {
                System.out.println("Please enter the process details again.");
                i--;
                continue;
            }
            processList.add(new InputProcess(processId, arrivalTime, burstTime));
        }
        return processList;
    }

    // Method to display the results of the scheduling
    public void displayResult(ArrayList<InputProcess> processList) {
        int n = processList.size();

        // Display Waiting Time
        System.out.print("Waiting Times: ");
        for (InputProcess process : processList) {
            System.out.print("P" + process.processId + " = " + process.waitingTime + ", ");
        }
        System.out.println();

        // Display Turnaround Time
        System.out.print("Turnaround Times: ");
        for (InputProcess process : processList) {
            System.out.print("P" + process.processId + " = " + process.turnAroundTime + ", ");
        }
        System.out.println();

        // Calculate and Display Average Waiting Time
        double avgWaitingTime = 0;
        for (InputProcess process : processList) {
            avgWaitingTime += process.waitingTime;
        }
        System.out.println("Average Waiting Time: " + (avgWaitingTime / n));

        // Calculate and Display Average Turnaround Time
        double avgTurnaroundTime = 0;
        for (InputProcess process : processList) {
            avgTurnaroundTime += process.turnAroundTime;
        }
        System.out.println("Average Turnaround Time: " + (avgTurnaroundTime / n));
    }

    // Method to implement First Come First Serve scheduling
    public void First_Come_First_Served() {
        ArrayList<InputProcess> processList = input();
        int n = processList.size();
        int currentTime = 0;

        // Sort processes by arrival time (FCFS order)
        processList.sort((p1, p2) -> Integer.compare(p1.arrivalTime, p2.arrivalTime));

        // Display Gantt Chart
        System.out.print("\nGantt Chart: ");
        for (InputProcess process : processList) {
            int startTime = Math.max(currentTime, process.arrivalTime);
            int endTime = startTime + process.burstTime;
            System.out.print("P" + process.processId + " (" + startTime + "-" + endTime + ") -> ");
            currentTime = endTime;

            // Calculate completion time, turnaround time, and waiting time
            process.completionTime = endTime;
            process.turnAroundTime = process.completionTime - process.arrivalTime;
            process.waitingTime = process.turnAroundTime - process.burstTime;
        }
        System.out.println();

        // Display results
        displayResult(processList);
    }

    // Method to implement Shortest Job First scheduling (non-preemptive)
    public void Shortest_Job_First() {
        ArrayList<InputProcess> processList = input();
        int n = processList.size();
        int currentTime = 0;
        int completedProcesses = 0;

        // Initialize remaining time for all processes
        for (InputProcess process : processList) {
            process.remainingTime = process.burstTime;
        }

        // Display Gantt Chart
        System.out.print("\nGantt Chart: ");
        while (completedProcesses < n) {
            InputProcess shortestProcess = null;
            int minBurstTime = Integer.MAX_VALUE;

            // Find the process with the shortest remaining burst time that has arrived
            for (InputProcess process : processList) {
                if (process.arrivalTime <= currentTime && process.remainingTime < minBurstTime && process.remainingTime > 0) {
                    minBurstTime = process.remainingTime;
                    shortestProcess = process;
                }
            }

            if (shortestProcess == null) {
                currentTime++; // No process is available, increment time
            } else {
                // Execute the shortest process
                int startTime = currentTime;
                int endTime = startTime + shortestProcess.remainingTime;
                System.out.print("P" + shortestProcess.processId + " (" + startTime + "-" + endTime + ") -> ");
                currentTime = endTime;

                // Calculate completion time, turnaround time, and waiting time
                shortestProcess.completionTime = endTime;
                shortestProcess.turnAroundTime = shortestProcess.completionTime - shortestProcess.arrivalTime;
                shortestProcess.waitingTime = shortestProcess.turnAroundTime - shortestProcess.burstTime;

                // Mark the process as completed
                shortestProcess.remainingTime = 0;
                completedProcesses++;
            }
        }
        System.out.println();

        // Display results
        displayResult(processList);
    }

    // Method to implement Shortest Remaining Time First scheduling (preemptive)
    public void Shortest_Remaining_Time_First() {
        ArrayList<InputProcess> processList = input();
        int n = processList.size();
        int currentTime = 0;
        int completedProcesses = 0;

        // Initialize remaining time for all processes
        for (InputProcess process : processList) {
            process.remainingTime = process.burstTime;
        }

        // Display Gantt Chart
        System.out.print("\nGantt Chart: ");
        while (completedProcesses < n) {
            InputProcess shortestProcess = null;
            int minRemainingTime = Integer.MAX_VALUE;

            // Find the process with the shortest remaining time that has arrived
            for (InputProcess process : processList) {
                if (process.arrivalTime <= currentTime && process.remainingTime < minRemainingTime && process.remainingTime > 0) {
                    minRemainingTime = process.remainingTime;
                    shortestProcess = process;
                }
            }

            if (shortestProcess == null) {
                currentTime++; // No process is available, increment time
            } else {
                // Execute the shortest process for 1 unit of time
                int startTime = currentTime;
                int endTime = startTime + 1;
                System.out.print("P" + shortestProcess.processId + " (" + startTime + "-" + endTime + ") -> ");
                currentTime = endTime;

                // Update remaining time
                shortestProcess.remainingTime--;

                // If the process is completed
                if (shortestProcess.remainingTime == 0) {
                    completedProcesses++;
                    shortestProcess.completionTime = currentTime;
                    shortestProcess.turnAroundTime = shortestProcess.completionTime - shortestProcess.arrivalTime;
                    shortestProcess.waitingTime = shortestProcess.turnAroundTime - shortestProcess.burstTime;
                }
            }
        }
        System.out.println();

        // Display results
        displayResult(processList);
    }

    // Method to implement Round Robin scheduling
    public void Round_Robin() {
        ArrayList<InputProcess> processList = input();
        int n = processList.size();
        int[] remainingTime = new int[n];

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the quantum: ");
        int quantum = sc.nextInt();

        // Initialize remaining time for all processes
        for (int i = 0; i < n; i++) {
            remainingTime[i] = processList.get(i).burstTime;
        }

        int currentTime = 0;
        boolean[] isCompleted = new boolean[n]; // Track completed processes
        int completedProcesses = 0;

        // Display Gantt Chart
        System.out.print("\nGantt Chart: ");
        while (completedProcesses < n) {
            boolean isIdle = true; // Check if the CPU is idle in the current cycle

            for (int i = 0; i < n; i++) {
                if (!isCompleted[i] && processList.get(i).arrivalTime <= currentTime) {
                    isIdle = false; // CPU is not idle

                    // Execute the process for the quantum or its remaining time, whichever is smaller
                    int executionTime = Math.min(quantum, remainingTime[i]);
                    System.out.print("P" + processList.get(i).processId + " (" + currentTime + "-" + (currentTime + executionTime) + ") -> ");
                    currentTime += executionTime;
                    remainingTime[i] -= executionTime;

                    // If the process is completed
                    if (remainingTime[i] == 0) {
                        isCompleted[i] = true;
                        completedProcesses++;
                        processList.get(i).completionTime = currentTime;
                        processList.get(i).turnAroundTime = processList.get(i).completionTime - processList.get(i).arrivalTime;
                        processList.get(i).waitingTime = processList.get(i).turnAroundTime - processList.get(i).burstTime;
                    }
                }
            }

            // If no process was executed in this cycle, increment time
            if (isIdle) {
                currentTime++;
            }
        }
        System.out.println();

        // Display results
        displayResult(processList);
    }

    // Main method to display menu and take user input for scheduling algorithm choice
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int exit = 0;
        while (exit != 5) {
            System.out.println("Process Scheduling");
            System.out.println("1. First Come First Serve (FCFS)");
            System.out.println("2. Shortest Job First (SJF)");
            System.out.println("3. Shortest Remaining Time First (SRTF)");
            System.out.println("4. Round Robin (RR)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int option = sc.nextInt();
            switch (option) {
                case 1:
                    new ProcessScheduling().First_Come_First_Served();
                    break;
                case 2:
                    new ProcessScheduling().Shortest_Job_First();
                    break;
                case 3:
                    new ProcessScheduling().Shortest_Remaining_Time_First();
                    break;
                case 4:
                    new ProcessScheduling().Round_Robin();
                    break;
                case 5:
                    System.out.println("Exiting");
                    sc.close();
                    exit = 5;
                    break;
                default:
                    System.out.println("Invalid choice. Choice must be between 1 and 5.");
                    break;
            }
        }
    }
}