package algorithms;

import datastructure.CustomLinkedList;
import graph.Location;

/**
 * Lớp để đóng gói kết quả của thuật toán Dijkstra.
 */
public class DijkstraResult {
    public final CustomLinkedList<Location> path; // Danh sách các địa điểm trên đường đi
    public final double totalValue; // Tổng giá trị (khoảng cách, thời gian, chi phí)
    public final boolean pathFound; // Cờ báo hiệu có tìm thấy đường đi không

    /**
     * Constructor cho trường hợp tìm thấy đường đi.
     * @param path Danh sách địa điểm.
     * @param totalValue Tổng giá trị.
     */
    public DijkstraResult(CustomLinkedList<Location> path, double totalValue) {
        this.path = path;
        this.totalValue = totalValue;
        this.pathFound = true;
    }

    /**
     * Constructor cho trường hợp không tìm thấy đường đi.
     */
    public DijkstraResult() {
        this.path = new CustomLinkedList<>(); // Danh sách rỗng
        this.totalValue = Double.POSITIVE_INFINITY;
        this.pathFound = false;
    }
}
