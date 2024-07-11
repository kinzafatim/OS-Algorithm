import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class OS_ass2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char runAgain = 'Y';

        while (runAgain == 'Y' || runAgain == 'y') {
            System.out.println("\n\t\t\tChoose Scheduling Algorithm\n");
            System.out.println("    Press 1 for FCFS Algorithm");
            System.out.println("    Press 2 for SJF Algorithm");
            System.out.println("    Press 3 for Priority Algorithm");
            System.out.println("    Press 4 for Round Robin Algorithm");
            System.out.println();
            System.out.print("    Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    FCFS();
                    break;
                case 2:
                    SJF();
                    break;
                case 3:
                    Priority();
                    break;
                case 4:
                    RoundRobin();
                    break;
                default:
                    System.out.println("Invalid choice!!!");
            }

            System.out.println("Do you want to run another algorithm? (Y/N): ");
            runAgain = scanner.next().charAt(0);
        }

        System.out.println("Program terminated.");
        scanner.close();
    }
    //FCFS
    private static void FCFS() {
        System.out.println("\nRunning FCFS Algorithm...\n");
        Scanner process = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int No_of_process = process.nextInt();
        System.out.println();
    
        int pid[] = new int[No_of_process];
        int bt[] = new int[No_of_process];// burst time
        int ar[] = new int[No_of_process];// arrival time
        int ct[] = new int[No_of_process];// completion time
        int tat[] = new int[No_of_process];// turnaround time
        int wt[] = new int[No_of_process];// waiting time
    
        Scanner in = new Scanner(System.in);
    
        for (int i = 0; i < No_of_process; i++) {
            System.out.print("Enter P" + (i + 1) + " arrival time: ");
            ar[i] = in.nextInt();
            System.out.print("Enter P" + (i + 1) + " burst time: ");
            bt[i] = in.nextInt();
            System.out.println();
            pid[i] = i + 1;
        }
    
        // Bubble sort processes by arrival time for FCFS
        for (int i = 0; i < No_of_process - 1; i++) {
            for (int j = 0; j < No_of_process - i - 1; j++) {
                if (ar[j] > ar[j + 1]) {
                    int temp = ar[j];
                    ar[j] = ar[j + 1];
                    ar[j + 1] = temp;
    
                    temp = pid[j];
                    pid[j] = pid[j + 1];
                    pid[j + 1] = temp;
    
                    temp = bt[j];
                    bt[j] = bt[j + 1];
                    bt[j + 1] = temp;
                }
            }
        }
    
        System.out.println();
        ct[0] = bt[0] + ar[0]; // completion time = burst time + arrival time
        for (int i = 1; i < No_of_process; i++) {
            ct[i] = Math.max(ct[i - 1], ar[i]) + bt[i];
        }
        for (int i = 0; i < No_of_process; i++) {
            tat[i] = ct[i] - ar[i]; // turnaround time = completion time - arrival time
            wt[i] = tat[i] - bt[i]; // waiting time = turnaround time - burst time
        }
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("Process\t\tAT\t\tBT\t\tCT\t\tTAT\t\tWT");
        System.out.println("-----------------------------------------------------------------------------------");
        for (int i = 0; i < No_of_process; i++) {
            System.out.println("P" + pid[i] + "\t\t" + ar[i] + "\t\t" + bt[i] + "\t\t" + ct[i] + "\t\t" + tat[i] + "\t\t" + wt[i]);
        }
    
        double avgtat = Average(tat); // calculate average turnaround time by average function
        double avgwt = Average(wt); // calculate average waiting time by average function
    
        System.out.print("---------------------------------------------");
        System.out.println("\nAverage Turnaround Time: " + avgtat);
        System.out.println("Average Waiting Time: " + avgwt);
        System.out.println("---------------------------------------------");
    
        System.out.println("\n---------------- Gantt Chart ----------------");
        for (int i = 0; i < No_of_process; i++) {
            System.out.print("| P" + pid[i] + " ");
        }
        System.out.println("\n---------------------------------------------");
    }
    private static void SJF() {
        System.out.println("\nRunning SJF Algorithm (Preemptive)...\n");
    
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int No_of_process = sc.nextInt();
        System.out.println();
    
        int pid[] = new int[No_of_process];
        int at[] = new int[No_of_process]; // arrival time
        int bt[] = new int[No_of_process]; // burst time
        int ct[] = new int[No_of_process]; // completion time
        int tat[] = new int[No_of_process]; // turnaround time
        int wt[] = new int[No_of_process]; // waiting time
        int rt[] = new int[No_of_process]; // remaining time
        int f[] = new int[No_of_process]; // process has been completed or not
    
        for (int i = 0; i < No_of_process; i++) {
            System.out.print("Enter P" + (i + 1) + " arrival time: ");
            at[i] = sc.nextInt();
            System.out.print("Enter P" + (i + 1) + " burst time: ");
            bt[i] = sc.nextInt();
            pid[i] = i + 1;
            f[i] = 0;
            rt[i] = bt[i];
        }
    
        int complete = 0, t = 0, minm = Integer.MAX_VALUE;
        int shortest = 0, finish_time;
    
        while (complete != No_of_process) {
            for (int j = 0; j < No_of_process; j++) {
                if ((at[j] <= t) && (rt[j] < minm) && (rt[j] > 0)) {
                    minm = rt[j];
                    shortest = j;
                }
            }
    
            rt[shortest]--;
    
            if (rt[shortest] == 0) {
                minm = Integer.MAX_VALUE;
                complete++;
    
                finish_time = t + 1;
                ct[shortest] = finish_time;
                tat[shortest] = ct[shortest] - at[shortest];
                wt[shortest] = tat[shortest] - bt[shortest];
    
                f[shortest] = 1;
            }
    
            t++;
        }
    
        double avgtat = Average(tat);
        double avgwt = Average(wt);
    
        System.out.print("---------------------------------------------");
        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        System.out.print("---------------------------------------------\n");
        for (int i = 0; i < No_of_process; i++) {
            System.out.println(pid[i] + "\t" + at[i] + "\t" + bt[i] + "\t" + ct[i] + "\t" + tat[i] + "\t" + wt[i]);
        }
    
        System.out.print("---------------------------------------------");
        System.out.println("\nAverage Turnaround Time: " + avgtat);
        System.out.println("Average Waiting Time: " + avgwt);
        System.out.println("---------------------------------------------");
    
        System.out.println("\n---------------- Gantt Chart ----------------");
        for (int i = 0; i < No_of_process; i++) {
            System.out.print("| P" + pid[i] + " ");
        }
        System.out.println("|\n---------------------------------------------");
    }
    
        // priority
        private static void Priority() {
            System.out.println("\nRunning Priority Algorithm...\n");
        
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter number of processes: ");
            int No_of_process = scanner.nextInt();
            System.out.println();
        
            int pid[] = new int[No_of_process];
            int at[] = new int[No_of_process]; // arrival time
            int bt[] = new int[No_of_process]; // burst time
            int pr[] = new int[No_of_process]; // priority
            int ct[] = new int[No_of_process]; // completion time
            int tat[] = new int[No_of_process]; // turnaround time
            int wt[] = new int[No_of_process]; // waiting time
            int f[] = new int[No_of_process]; // process has been completed or not
        
            Scanner in = new Scanner(System.in);
        
            for (int i = 0; i < No_of_process; i++) {
                System.out.print("Enter P" + (i + 1) + " arrival time: ");
                at[i] = in.nextInt();
                System.out.print("Enter P" + (i + 1) + " burst time: ");
                bt[i] = in.nextInt();
                System.out.print("Enter P" + (i + 1) + " priority: ");
                pr[i] = in.nextInt();
                pid[i] = i + 1;
                f[i] = 0;
            }
        
            // Sort processes based on priority (and arrival time for tie-breaking)
            sortByPriorityAndArrivalTime(pid, at, bt, pr);
        
            int st = 0; // current system time
            int tot = 0; // total number of completed processes
        
            while (true) {
                int c = No_of_process, highestPriority = -1;
        
                if (tot == No_of_process)
                    break;
        
                for (int i = 0; i < No_of_process; i++) {
                    if ((at[i] <= st) && (f[i] == 0) && (pr[i] > highestPriority)) {
                        highestPriority = pr[i];
                        c = i;
                    }
                }
        
                if (c == No_of_process)
                    st++;
                else {
                    ct[c] = st + bt[c];
                    st += bt[c];
                    tat[c] = ct[c] - at[c];
                    wt[c] = tat[c] - bt[c];
                    f[c] = 1;
                    tot++;
                }
            }
        
            System.out.print("---------------------------------------------");
            System.out.println("\nPID\tAT\tBT\tPriority\tCT\tTAT\tWT");
            System.out.print("---------------------------------------------\n");
            for (int i = 0; i < No_of_process; i++) {
                System.out.println("P" + pid[i] + "\t" + at[i] + "\t" + bt[i] + "\t" + pr[i] + "\t\t" + ct[i] + "\t" + tat[i] + "\t" + wt[i]);
            }
            Average(tat); // calculate average turnaround time by average function
            Average(wt); // calculate average waiting time by average function
        
            System.out.print("---------------------------------------------");
            System.out.println("\nAverage Turnaround Time: " + Average(tat));
            System.out.println("Average Waiting Time: " + Average(wt));
            System.out.println("---------------------------------------------");
        
            System.out.println("\n---------------- Gantt Chart ----------------");
            for (int i = 0; i < No_of_process; i++) {
                System.out.print("| P" + pid[i] + " ");
            }
            System.out.println("|\n---------------------------------------------");
        }
        
        private static void sortByPriorityAndArrivalTime(int[] pid, int[] at, int[] bt, int[] pr) {
            for (int i = 0; i < pid.length - 1; i++) {
                for (int j = 0; j < pid.length - i - 1; j++) {
                    if (pr[j] < pr[j + 1] || (pr[j] == pr[j + 1] && at[j] > at[j + 1])) {
                        swap(pid, j, j + 1);
                        swap(at, j, j + 1);
                        swap(bt, j, j + 1);
                        swap(pr, j, j + 1);
                    }
                }
            }
        }
        

    // Helper method to swap elements in an array
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    private static void RoundRobin() {
        System.out.println("\nRunning Round Robin Algorithm...\n");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int No_of_process = scanner.nextInt();
        System.out.println();

        int pid[] = new int[No_of_process];
        int bt[] = new int[No_of_process]; // burst time
        int ct[] = new int[No_of_process]; // completion time
        int tat[] = new int[No_of_process]; // turnaround time
        int wt[] = new int[No_of_process]; // waiting time
        int rt[] = new int[No_of_process]; // remaining time
        int quantum;

        Scanner in = new Scanner(System.in);

        for (int i = 0; i < No_of_process; i++) {
            System.out.print("Enter P" + (i + 1) + " burst time: ");
            bt[i] = in.nextInt();
            pid[i] = i + 1;
            rt[i] = bt[i];
        }

        System.out.print("Enter time quantum: ");
        quantum = scanner.nextInt();

        Queue<Integer> queue = new LinkedList<>();
        int t = 0; // current time

        while (true) {
            boolean done = true;

            for (int i = 0; i < No_of_process; i++) {
                if (rt[i] > 0) {
                    done = false;

                    if (rt[i] > quantum) {
                        t += quantum;
                        rt[i] -= quantum;
                    } else {
                        t += rt[i];
                        rt[i] = 0;
                        ct[i] = t;
                        tat[i] = ct[i];
                        wt[i] = tat[i] - bt[i];
                    }
                }
            }

            if (done) {
                break;
            }
        }

        System.out.print("---------------------------------------------");
        System.out.println("\nPID\tBT\tCT\tTAT\tWT");
        System.out.print("---------------------------------------------\n");
        for (int i = 0; i < No_of_process; i++) {
            System.out.println(pid[i] + "\t" + bt[i] + "\t" + ct[i] + "\t" + tat[i] + "\t" + wt[i]);
        }

        double avgtat = Average(tat);
        double avgwt = Average(wt);

        System.out.print("---------------------------------------------");
        System.out.println("\nAverage Turnaround Time: " + avgtat);
        System.out.println("Average Waiting Time: " + avgwt);
        System.out.println("---------------------------------------------");

        System.out.println("\n---------------- Gantt Chart ----------------");
        for (int i = 0; i < No_of_process; i++) {
            System.out.print("| P" + pid[i] + " ");
        }
        System.out.println("|\n---------------------------------------------");
    }


    private static double Average(int[] array) {
        int sum = 0;
        double avg = 0;
        for (int i = 0; i < array.length; i++) {
            sum = sum + array[i];
        }
        avg = (double) sum / array.length;
        return avg;
    }
}   
    

