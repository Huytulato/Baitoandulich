package graph;

/**
 * Biểu diễn một tuyến đường (cạnh) có trọng số trong đồ thị.
 */
public class Route {
    public final int destinationId; // ID của đỉnh đích
    public final double distance;
    public final double time;
    public final double cost;

    public Route(int destinationId, double distance, double time, double cost) {
        this.destinationId = destinationId;
        this.distance = distance;
        this.time = time;
        this.cost = cost;
    }

    /**
     * Lấy trọng số dựa trên tiêu chí.
     * @param criteria Chuỗi "distance", "time", hoặc "cost".
     * @return Trọng số tương ứng.
     * @throws IllegalArgumentException nếu tiêu chí không hợp lệ.
     */
    public double getWeight(String criteria) {
        switch (criteria.toLowerCase()) {
            case "distance":
                return distance;
            case "time":
                return time;
            case "cost":
                return cost;
            default:
                throw new IllegalArgumentException("Invalid criteria: " + criteria + ". Use 'distance', 'time', or 'cost'.");
        }
    }

    @Override
    public String toString() {
        return "-> ID " + destinationId + " (Dist:" + distance + ", Time:" + time + ", Cost:" + cost + ")";
    }
}
