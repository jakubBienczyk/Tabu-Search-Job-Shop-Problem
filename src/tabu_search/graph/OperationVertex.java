package tabu_search.graph;

public class OperationVertex {

    private int time, task, machine;
    private OperationVertex nextOnMachine, previousOnMachine;
    private OperationVertex nextOnTask, previousOnTask;

    public OperationVertex(int task, int machine, int time) {
        this.task = task;
        this.machine = machine;
        this.time = time;
    }

    public OperationVertex getNextOnMachine() {
        return nextOnMachine;
    }

    public void setNextOnMachine(OperationVertex nextOnMachine) {
        this.nextOnMachine = nextOnMachine;
    }

    public OperationVertex getPreviousOnMachine() {
        return previousOnMachine;
    }

    public void setPreviousOnMachine(OperationVertex previousOnMachine) {
        this.previousOnMachine = previousOnMachine;
    }

    public OperationVertex getNextOnTask() {
        return nextOnTask;
    }

    public void setNextOnTask(OperationVertex nextOnTask) {
        this.nextOnTask = nextOnTask;
    }

    public OperationVertex getPreviousOnTask() {
        return previousOnTask;
    }

    public void setPreviousOnTask(OperationVertex previousOnTask) {
        this.previousOnTask = previousOnTask;
    }

    public int getTime() {
        return time;
    }

    public int getTask() {
        return task;
    }

    public int getMachine() {
        return machine;
    }

    @Override
    public String toString() {
        return "time: " + time + ", task: " + task + ", machine: " + (machine + 1) + "\n";
    }

    @Override
    public OperationVertex clone() {
        OperationVertex clone = new OperationVertex(task, machine, time);
        return clone;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (! (object instanceof OperationVertex)) return false;
        OperationVertex op = (OperationVertex) object;
        if (op.getMachine() != machine)
            return false;
        if (op.getTask() != task)
            return false;
        return op.getTime() == time;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.time;
        hash = 29 * hash + this.task;
        hash = 29 * hash + this.machine;
        return hash;
    }
}
