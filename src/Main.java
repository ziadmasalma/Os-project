
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Header for the simulation counts
        int[] simulationCounts = new int[]{100, 1000, 10000, 100000};
        // Create a 2D array to store the results of each algorithm
        double[][] totalFcfs = new double[simulationCounts.length][2];
        double[][] totalSrtf = new double[simulationCounts.length][2];
        double[][] totalRr = new double[simulationCounts.length][2];
        double[][] totalMlfq = new double[simulationCounts.length][2];

        for (int y=0 ; y<simulationCounts.length ; y++){// Loop through each simulation count
            int simulationCount = simulationCounts[y];// Run each algorithm for the current simulation count

            for (int i = 0; i < simulationCount; i++) {
                // Create a new set of processes for each algorithm
                List<Process> FCFSprocesses = CreateProcesses();
                List<Process> SJFprocesses = CreateProcesses();
                List<Process> RRprocesses = CreateProcesses();
                List<Process> MLFQprocesses = CreateProcesses();
                // Run each algorithm
                FCFS FCFS = new FCFS(FCFSprocesses);
                SJF SJF = new SJF(SJFprocesses);
                RR RR = new RR(RRprocesses);
                MLFQ MLFQ = new MLFQ(MLFQprocesses);
                // Store the results of each algorithm
                double[] Fcfs = FCFS.Run();
                totalFcfs[y]= Fcfs;

                double[] Sjf = SJF.Run();
                totalSrtf[y]= Sjf;

                double[] Rr = RR.Run();
                totalRr[y]= Rr;

                double[] Mlfq = MLFQ.Run();
                totalMlfq[y]= Mlfq;
            }

        }
        // Print results in a formatted table
        printResults("FCFS", totalFcfs);
        printResults("SJF", totalSrtf);
        printResults("RR", totalRr);
        printResults("MLFQ", totalMlfq);


    }
    private static void printResults(String algorithm, double[][] results) {

        System.out.println("\t\t100\t\t\t1000\t\t10000\t\t100000");
        // ATT
        System.out.print("ATT\t\t");
        for (double[] result : results) {
            System.out.printf("%.2f\t\t", result[0]);
        }
        System.out.println();

        // AWT
        System.out.print("AWT\t\t");
        for (double[] result : results) {
            System.out.printf("%.2f\t\t", result[1]);
        }
        System.out.println();
        System.out.printf("\t\t\t\t%s%n", algorithm);

        // Separator line
        System.out.println("--------------------------------------------------------------");
    }

    private static List<Process> CreateProcesses() {
        List<Process> processes = new ArrayList<>();
        Random random = new Random();

        for (int pid = 1; pid <= 8; pid++) {
            int arrivalTime = random.nextInt(11);// Random arrival time between 0 and 10
            int burstTime = random.nextInt(96) + 5;  // Random burst time between 5 and 100
            processes.add(new Process(pid, arrivalTime, burstTime));
        }

        return processes;
    }
}
