package tabu_search.heuristic;

public class Operation implements Comparable<Operation> {

    public int job_number;
    public int time,task, machine, operationIndex;
    
    public Operation(int job_number, int time) {
        this.job_number = job_number;
        this.time = time;
    }

    public Operation(int task, int machine, int time, int operationIndex) {
        this.task = task;
        this.machine = machine;
        this.time = time;
        this.operationIndex = operationIndex;
    }

    @Override
    public int compareTo(Operation o) {
        return time - o.time;
    }
}
