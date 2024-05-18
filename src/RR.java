import java.util.*;

public class RR {
    List<Process> processes;
    int quantum;
    public RR(List<Process> processes) {
        this.processes = processes;
        this.quantum = 20;
    }

    public double[] Run (){
        Collections.sort(processes, Comparator.comparingInt(p -> p.At));//sort processes by arrival time
        Queue<Process> readyQueue = new LinkedList<>();
        double totalTurnaroundTime = 0;
        double totalWaitingTime = 0;
        int currentTime = 0;
        int size = processes.size();


        while (!readyQueue.isEmpty() || !processes.isEmpty()){//while there are processes in any queue

            while (!processes.isEmpty() && processes.get(0).At <= currentTime) {//while there are processes and the first process arrived
                readyQueue.add(processes.remove(0));
            }
            while (!readyQueue.isEmpty()){//while ready queue is not empty
                while (!processes.isEmpty() && processes.get(0).At <= currentTime) {//while there are processes and the first process arrived
                    readyQueue.add(processes.remove(0));
                }
                Process currentProcess = readyQueue.poll();
                int timeToRun = Math.min(currentProcess.Rt, quantum);
                currentProcess.Rt -= timeToRun;
                currentTime += timeToRun;

                if (currentProcess.Rt > 0) {//if process is not finished
                    while (!processes.isEmpty() && processes.get(0).At <= currentTime) {//while there are processes and the first process arrived
                        readyQueue.add(processes.remove(0));
                    }
                    readyQueue.add(currentProcess);
                } else {//if process is finished
                    totalTurnaroundTime += (currentTime - currentProcess.At);
                    totalWaitingTime+=(currentTime - currentProcess.At)-currentProcess.OBT;
                }

                while (!processes.isEmpty() && readyQueue.isEmpty()){//while there are processes and ready queue is empty
                    currentTime++;
                    while (!processes.isEmpty() && processes.get(0).At <= currentTime) {//while there are processes and the first process arrived
                        readyQueue.add(processes.remove(0));
                    }
                }
            }
            while (!processes.isEmpty() && readyQueue.isEmpty()){//while there are processes and ready queue is empty
                currentTime++;
                while (!processes.isEmpty() && processes.get(0).At <= currentTime) {//while there are processes and the first process arrived
                    readyQueue.add(processes.remove(0));
                }
            }

        }
        double avgTurnaroundTime = totalTurnaroundTime / size;
        double avgWaitingTime = totalWaitingTime / size;
        return new double[]{avgTurnaroundTime, avgWaitingTime};
    }
}
