import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SJF {
    List<Process> processes;
    List<Process> readyQueue;



    public SJF(List<Process> processes) {
        this.processes = processes;
        this.readyQueue = new ArrayList<>();
    }
    public double[] Run() {
        // Sort processes by arrival time
        Collections.sort(processes, (p1, p2) -> Integer.compare(p1.At, p2.At));
        int currentTime = 0;
        double totalTurnaroundTime = 0;
        double totalWaitingTime = 0;
        List<Process> completedProcesses = new ArrayList<>();
        Process runningProcess = null;

        while (completedProcesses.size() < processes.size()) {
            // Add processes that have arrived to the ready queue
            for (Process process : processes) {//loop on all processes
                if (process.At <= currentTime && !completedProcesses.contains(process)) {//if process arrived and not completed
                    if (runningProcess == null || process.Bt < runningProcess.Bt) {//if no process is running or the process is shorter than the running process
                        if (runningProcess != null) {//if there is a running process
                            readyQueue.add(runningProcess); // Preempt the running process if a shorter one arrives
                        }
                        runningProcess = process;
                    } else {
                        readyQueue.add(process);
                    }
                }
            }

            if (runningProcess != null) {
                // Execute the currently running process for one time unit
                runningProcess.Bt--;
                currentTime++;

                // Check if the running process has completed
                if (runningProcess.Bt == 0) {
                    // Calculate waiting time and turnaround time
                    int turnaroundTime = currentTime - runningProcess.At;
                    int waitingTime = turnaroundTime - runningProcess.OBT;
                    totalWaitingTime += waitingTime;
                    totalTurnaroundTime += turnaroundTime;

                    completedProcesses.add(runningProcess);
                    runningProcess = null;
                }
            } else {
                // If no process is ready, increment the current time
                currentTime++;
            }
        }

        double averageTurnaroundTime = totalTurnaroundTime / processes.size();
        double averageWaitingTime = totalWaitingTime / processes.size();

        return new double[]{averageTurnaroundTime, averageWaitingTime};
    }

}
