<!-- @format -->

#  CPU-Scheduling-Simulator

This project is a Java-based simulation of various CPU scheduling algorithms, including:

- **First Come First Serve (FCFS)**
- **Shortest Job First (SJF)**
- **Shortest Remaining Time First (SRTF)**
- **Round Robin (RR)**

The program allows users to input processes with their arrival times and burst times, and then simulates the scheduling algorithm of their choice. It displays the Gantt Chart, waiting times, turnaround times, and average waiting and turnaround times for the processes.

---

## Features

- **FCFS**: Executes processes in the order of their arrival.
- **SJF**: Executes the process with the shortest burst time first (non-preemptive).
- **SRTF**: Executes the process with the shortest remaining burst time first (preemptive).
- **Round Robin**: Executes processes in time slices (quantum) and allows preemption.

---

## How to Use the Program

### Prerequisites

- Ensure you have **Java Development Kit (JDK)** installed on your system.
- A terminal or command prompt to run the program.

### Steps

1. **Compile the Program**:

   - Open a terminal or command prompt.
   - Navigate to the directory where the `ProcessScheduling.java` file is located.
   - Compile the program using the `javac` command:

     ```bash
     javac ProcessScheduling.java
     ```

2. **Run the Program**:

   - Run the compiled program using the `java` command:

     ```bash
     java ProcessScheduling
     ```

3. **Follow the On-Screen Instructions**:

   - Enter the number of processes.
   - For each process, provide the process ID, arrival time, and burst time.
   - Choose a scheduling algorithm from the menu:
     - `1` for FCFS
     - `2` for SJF
     - `3` for SRTF
     - `4` for Round Robin
     - `5` to exit the program.

4. **View the Output**:
   - The program will display the Gantt Chart, waiting times, turnaround times, and average waiting and turnaround times for the selected scheduling algorithm.

---
