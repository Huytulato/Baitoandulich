package algorithms;

import datastructure.CustomLinkedList;
import datastructure.CustomPriorityQueue;
import datastructure.HeapNode;
import graph.Graph;
import graph.Location;
import graph.Route;

/**
 * Chứa thuật toán tìm đường đi ngắn nhất (Dijkstra).
 */
public class ShortestPathAlgorithm {

    /**
     * Tìm đường đi ngắn nhất từ startId đến endId trong đồ thị theo tiêu chí.
     * @param graph Đồ thị.
     * @param startId ID đỉnh bắt đầu.
     * @param endId ID đỉnh kết thúc.
     * @param criteria Tiêu chí ("distance", "time", "cost").
     * @return Đối tượng DijkstraResult chứa đường đi và tổng giá trị,
     *         hoặc kết quả báo không tìm thấy đường đi.
     */
    public static DijkstraResult findShortestPath(Graph graph, int startId, int endId, String criteria) {
        int n = graph.getMaxVertices();
        double[] distance = new double[n]; // Khoảng cách ngắn nhất từ startId
        int[] previousVertex = new int[n]; // Đỉnh trước đó trên đường đi ngắn nhất
        boolean[] visited = new boolean[n]; // Đánh dấu đỉnh đã được tối ưu (lấy ra khỏi PQ)

        // Khởi tạo
        for (int i = 0; i < n; i++) {
            distance[i] = Double.POSITIVE_INFINITY;
            previousVertex[i] = -1; // -1 nghĩa là chưa có đỉnh trước đó
            visited[i] = false;
        }

        // Kiểm tra sự tồn tại của đỉnh bắt đầu và kết thúc
        if (graph.getLocationById(startId) == null) {
            System.err.println("Error: Start location with ID " + startId + " does not exist.");
            return new DijkstraResult(); // Không tìm thấy đường đi
        }
        if (graph.getLocationById(endId) == null) {
            System.err.println("Error: End location with ID " + endId + " does not exist.");
            return new DijkstraResult(); // Không tìm thấy đường đi
        }


        distance[startId] = 0.0;
        CustomPriorityQueue pq = new CustomPriorityQueue(graph.getNumVertices()); // Ước lượng kích thước
        pq.add(new HeapNode(0.0, startId));

        while (!pq.isEmpty()) {
            HeapNode current = pq.poll();
            int u = current.vertexId;
            double currentDist = current.priority;

            // Nếu đỉnh này đã được xử lý với khoảng cách tốt hơn, bỏ qua
            // Hoặc nếu đỉnh đã được tối ưu (lấy ra khỏi PQ trước đó), bỏ qua
            if (currentDist > distance[u] || visited[u]) {
                continue;
            }

            visited[u] = true; // Đánh dấu đỉnh này đã được tối ưu

            // Nếu đã đến đích, có thể dừng sớm (tối ưu hóa)
            if (u == endId) {
                break;
            }

            // Duyệt các đỉnh kề v của u
            CustomLinkedList<Route> neighbors = graph.getNeighbors(u);
            if (neighbors != null) {
                for (Route route : neighbors) {
                    int v = route.destinationId;
                    // Chỉ xét những đỉnh chưa được tối ưu hoàn toàn
                    if (!visited[v]) {
                        double weight;
                        try {
                            weight = route.getWeight(criteria);
                        } catch (IllegalArgumentException e) {
                            System.err.println("Error in weight criteria: " + e.getMessage());
                            return new DijkstraResult(); // Lỗi tiêu chí, không tìm thấy đường
                        }

                        double newDist = distance[u] + weight;

                        // Relaxation
                        if (newDist < distance[v]) {
                            distance[v] = newDist;
                            previousVertex[v] = u;
                            pq.add(new HeapNode(newDist, v)); // Thêm vào PQ để xét
                        }
                    }
                }
            }
        }

        // Kiểm tra xem có đến được đích không
        if (distance[endId] == Double.POSITIVE_INFINITY) {
            return new DijkstraResult(); // Không tìm thấy đường đi
        }

        // Truy vết đường đi
        CustomLinkedList<Location> path = tracePath(graph, previousVertex, startId, endId);
        return new DijkstraResult(path, distance[endId]);
    }

    /**
     * Truy vết đường đi từ mảng previousVertex.
     * @param graph Đồ thị (để lấy đối tượng Location).
     * @param previousVertex Mảng lưu đỉnh trước đó.
     * @param startId ID đỉnh bắt đầu.
     * @param endId ID đỉnh kết thúc.
     * @return Danh sách liên kết các Location trên đường đi (từ start đến end).
     */
    private static CustomLinkedList<Location> tracePath(Graph graph, int[] previousVertex, int startId, int endId) {
        CustomLinkedList<Location> path = new CustomLinkedList<>();
        int currentId = endId;

        // Lặp ngược từ đích về nguồn
        while (currentId != -1) {
            Location loc = graph.getLocationById(currentId);
            if (loc != null) {
                path.addFirst(loc); // Thêm vào đầu danh sách để có thứ tự đúng
            } else {
                System.err.println("Warning: Location with ID " + currentId + " not found during path tracing.");
                // Có thể dừng hoặc tiếp tục tùy logic xử lý lỗi
                break;
            }

            if (currentId == startId) { // Đã về đến điểm bắt đầu
                break;
            }
            currentId = previousVertex[currentId]; // Di chuyển đến đỉnh trước đó

            // Phòng trường hợp vòng lặp vô hạn nếu có lỗi trong previousVertex
            if (path.size() > graph.getMaxVertices()) {
                System.err.println("Error: Path tracing seems to be in an infinite loop. Aborting.");
                return new CustomLinkedList<>(); // Trả về đường đi rỗng
            }
        }

        // Kiểm tra xem có thực sự về được startId không
        if (path.isEmpty() || path.peekFirst().id != startId) {
            System.err.println("Error: Could not trace path back to start node.");
            return new CustomLinkedList<>(); // Trả về đường đi rỗng nếu không hợp lệ
        }


        return path;
    }
}
