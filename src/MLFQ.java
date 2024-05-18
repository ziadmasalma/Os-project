import java.util.ArrayList;
import java.util.List;

public class MLFQ {
    List<Process> processes;

    int time;

    public MLFQ(List<Process> processes) {
        this.processes = processes;

        this.time = 0;
    }
    public MLFQ(){}


    public double[] Run(){
        int q1 = 10;
        int q2 = 50;
        int size = processes.size();
        List<Process> queue1 = new ArrayList<>();
        List<Process> queue2 = new ArrayList<>();
        List<Process> queue3 = new ArrayList<>();

        double totalTurnaroundTime = 0;
        double totalWaitingTime = 0;

        while (!processes.isEmpty() || !queue1.isEmpty() || !queue2.isEmpty() || !queue3.isEmpty()) {//while there are processes in any queue
            while (!processes.isEmpty() && processes.get(0).At <= time) {//while there are processes and the first process arrived
                queue1.add(processes.remove(0));
            }

            if (!queue1.isEmpty()) {//if queue1 is not empty
                Process p = queue1.remove(0);
                int Rt = Math.min(p.Bt, q1);
                time += Rt;
                p.Bt -= Rt;

                if (p.Bt > 0) {//if process is not finished
                    queue2.add(p);
                } else {//if process is finished
                    totalTurnaroundTime += (time - p.At);
                    totalWaitingTime += (time - p.At) - p.OBT;
                }
            } else if (!queue2.isEmpty()) {//if queue1 is empty and queue2 is not empty
                Process p = queue2.remove(0);
                int Rt = Math.min(p.Bt, q2);
                time += Rt;
                p.Bt -= Rt;

                if (p.Bt > 0) {//if process is not finished
                    queue3.add(p);
                } else {//if process is finished
                    totalTurnaroundTime += (time - p.At);
                    totalWaitingTime += (time - p.At) - p.OBT;
                }
            } else if (!queue3.isEmpty()) {//if queue1 and queue2 are empty and queue3 is not empty
                Process p = queue3.remove(0);
                time += p.Bt;
                totalTurnaroundTime += (time - p.At);
                totalWaitingTime += (time - p.At) - p.OBT;
            } else if (!processes.isEmpty()) {//if all queues are empty and there are processes
                time++;
            }
        }

        double avgTurnaroundTime = totalTurnaroundTime / size;
        double avgWaitingTime = totalWaitingTime / size;


        return new double[]{avgTurnaroundTime, avgWaitingTime};
    }


}
