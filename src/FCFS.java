
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FCFS {
    List<Process> processes;
    List<Process> readyQueue;


    public FCFS(List<Process> processes) {
        this.processes = processes;
        this.readyQueue = new ArrayList<>();
    }
    public double[] Run() {
        Collections.sort(processes, (p1, p2) -> Integer.compare(p1.At, p2.At));
        readyQueue = new ArrayList<>(processes);
        double totalTurnaroundTime = 0;
        double totalWaitingTime = 0;
        int currentTime = 0;  // Current time in the system

        for (Process process : readyQueue) {//loop on all processes
            if (process.At > currentTime) {//if process arrived after current time
                // If the process arrives after the current time, update the current time to the arrival time
                currentTime = process.At;
            }

            int waitingTime = currentTime - process.At;  // Time process waits before execution
            totalWaitingTime += waitingTime;

            currentTime += process.Bt;  // Execute the process
            int turnaroundTime = currentTime - process.At;  // Total time from arrival to completion
            totalTurnaroundTime += turnaroundTime;
        }

        double averageTurnaroundTime = totalTurnaroundTime / processes.size();
        double averageWaitingTime = totalWaitingTime / processes.size();

        return new double[]{averageTurnaroundTime, averageWaitingTime};
    }

}
