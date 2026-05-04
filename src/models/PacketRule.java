package models;

public class PacketRule implements Comparable<PacketRule> {
    private int id;
    private String sourceIp;
    private String destIp;
    private int priority;

    public PacketRule(int id, String sourceIp, String destIp, int priority) {
        this.id = id;
        this.sourceIp = sourceIp;
        this.destIp = destIp;
        this.priority = priority;
    }

    public int getId() { return id; }
    public String getSourceIp() { return sourceIp; }
    public String getDestIp() { return destIp; }
    public int getPriority() { return priority; }

    @Override
    public int compareTo(PacketRule other) {
        if (this.priority != other.priority) {
            return Integer.compare(other.priority, this.priority);
        }
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return String.format("PacketRule{id=%d, prio=%d, src=%s, dst=%s}",
                id, priority, sourceIp, destIp);
    }
}