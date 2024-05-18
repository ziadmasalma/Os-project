
class Process {
    int pid;
    int At;//arrival time
    int Bt;//burst time
    int Rt; //remaining time
    int Tt; //turnaround time
    int OBT; //original burst time
    int waitingTime;


    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.At = arrivalTime;
        this.Bt = burstTime;
        this.Rt = burstTime;
        this.Tt = 0;
        this.OBT = burstTime;
        this.waitingTime = 0;


    }


}