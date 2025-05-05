package datastructure;

/**
 * Đại diện cho một phần tử trong MinHeap/CustomPriorityQueue.
 * Chứa độ ưu tiên (ví dụ: khoảng cách) và ID của đỉnh.
 * Implement Comparable để MinHeap có thể so sánh các Node.
 */
public class HeapNode implements Comparable<HeapNode> {
    public double priority; // Độ ưu tiên (ví dụ: khoảng cách từ nguồn)
    public int vertexId;    // ID của đỉnh tương ứng

    /**
     * Constructor.
     * @param priority Độ ưu tiên.
     * @param vertexId ID của đỉnh.
     */
    public HeapNode(double priority, int vertexId) {
        this.priority = priority;
        this.vertexId = vertexId;
    }

    /**
     * So sánh hai HeapNode dựa trên độ ưu tiên (priority).
     * Cần thiết cho việc sắp xếp trong MinHeap.
     * @param other Node khác để so sánh.
     * @return giá trị âm nếu priority hiện tại nhỏ hơn,
     *         giá trị dương nếu priority hiện tại lớn hơn,
     *         0 nếu bằng nhau.
     */
    @Override
    public int compareTo(HeapNode other) {
        // So sánh double cần cẩn thận, nhưng dùng Double.compare là an toàn
        return Double.compare(this.priority, other.priority);
    }

    @Override
    public String toString() {
        return "HeapNode{" +
                "priority=" + priority +
                ", vertexId=" + vertexId +
                '}';
    }
}
