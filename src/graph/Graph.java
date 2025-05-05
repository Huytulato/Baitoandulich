package graph;

import datastructure.CustomLinkedList; // Sử dụng LinkedList tự cài đặt

/**
 * Biểu diễn đồ thị có hướng, có trọng số sử dụng danh sách kề.
 */
public class Graph {
    private final int maxVertices; // Số lượng đỉnh tối đa (cần biết trước hoặc dùng cấu trúc động hơn)
    private Location[] locations; // Mảng lưu thông tin các đỉnh (Location)
    private CustomLinkedList<Route>[] adjList; // Mảng các danh sách kề (Route)
    private int numVertices; // Số lượng đỉnh hiện tại

    /**
     * Constructor.
     * @param maxVertices Số lượng đỉnh tối đa mà đồ thị có thể chứa.
     */
    @SuppressWarnings("unchecked") // Cần thiết vì Java không cho tạo mảng generic trực tiếp
    public Graph(int maxVertices) {
        if (maxVertices <= 0) {
            throw new IllegalArgumentException("Maximum vertices must be positive.");
        }
        this.maxVertices = maxVertices;
        this.locations = new Location[maxVertices];
        // Khởi tạo mảng danh sách kề, mỗi phần tử là một CustomLinkedList mới
        this.adjList = (CustomLinkedList<Route>[]) new CustomLinkedList[maxVertices];
        for (int i = 0; i < maxVertices; i++) {
            this.adjList[i] = new CustomLinkedList<>();
        }
        this.numVertices = 0;
    }

    /**
     * Thêm một đỉnh (địa điểm) vào đồ thị.
     * @param location Địa điểm cần thêm.
     * @throws IllegalStateException nếu đồ thị đã đầy.
     * @throws IllegalArgumentException nếu ID đã tồn tại hoặc không hợp lệ.
     */
    public void addVertex(Location location) {
        if (numVertices >= maxVertices) {
            throw new IllegalStateException("Graph is full. Cannot add more vertices.");
        }
        int id = location.id;
        if (id < 0 || id >= maxVertices) {
            throw new IllegalArgumentException("Invalid Location ID: " + id + ". Must be between 0 and " + (maxVertices - 1));
        }
        if (locations[id] != null) {
            throw new IllegalArgumentException("Location with ID " + id + " already exists.");
        }
        locations[id] = location;
        numVertices++; // Chỉ tăng khi thêm thành công và ID hợp lệ
    }


    /**
     * Thêm một cạnh có hướng từ sourceId đến destId với các trọng số.
     * @param sourceId ID đỉnh nguồn.
     * @param destId ID đỉnh đích.
     * @param distance Khoảng cách.
     * @param time Thời gian.
     * @param cost Chi phí.
     * @throws IllegalArgumentException nếu ID không hợp lệ hoặc đỉnh không tồn tại.
     */
    public void addEdge(int sourceId, int destId, double distance, double time, double cost) {
        validateVertexId(sourceId);
        validateVertexId(destId);
        if (locations[sourceId] == null) throw new IllegalArgumentException("Source vertex " + sourceId + " does not exist.");
        if (locations[destId] == null) throw new IllegalArgumentException("Destination vertex " + destId + " does not exist.");

        Route newRoute = new Route(destId, distance, time, cost);
        adjList[sourceId].add(newRoute);
    }

    /**
     * Lấy danh sách các cạnh kề (tuyến đường) xuất phát từ một đỉnh.
     * @param vertexId ID của đỉnh.
     * @return Danh sách liên kết các Route kề.
     * @throws IllegalArgumentException nếu ID không hợp lệ hoặc đỉnh không tồn tại.
     */
    public CustomLinkedList<Route> getNeighbors(int vertexId) {
        validateVertexId(vertexId);
        if (locations[vertexId] == null) throw new IllegalArgumentException("Vertex " + vertexId + " does not exist.");
        return adjList[vertexId];
    }

    /**
     * Lấy thông tin địa điểm dựa trên ID.
     * @param vertexId ID của đỉnh.
     * @return Đối tượng Location, hoặc null nếu không tồn tại.
     */
    public Location getLocationById(int vertexId) {
        if (vertexId < 0 || vertexId >= maxVertices) {
            return null; // ID không hợp lệ
        }
        return locations[vertexId];
    }

    /**
     * Lấy tổng số đỉnh tối đa của đồ thị.
     * @return Số đỉnh tối đa.
     */
    public int getMaxVertices() {
        return maxVertices;
    }

    /**
     * Lấy số lượng đỉnh hiện tại trong đồ thị.
     * @return Số lượng đỉnh hiện tại.
     */
    public int getNumVertices() {
        // Đếm số lượng location khác null để chính xác hơn
        int count = 0;
        for(Location loc : locations) {
            if (loc != null) {
                count++;
            }
        }
        return count;
        // return numVertices; // Cách cũ có thể không chính xác nếu addVertex bị lỗi giữa chừng
    }


    /**
     * Lấy danh sách tất cả các địa điểm có trong đồ thị.
     * @return Danh sách liên kết các Location.
     */
    public CustomLinkedList<Location> getAllLocations() {
        CustomLinkedList<Location> allLocs = new CustomLinkedList<>();
        for (Location loc : locations) {
            if (loc != null) {
                allLocs.add(loc);
            }
        }
        return allLocs;
    }


    /**
     * Kiểm tra tính hợp lệ của ID đỉnh.
     * @param vertexId ID cần kiểm tra.
     * @throws IllegalArgumentException nếu ID nằm ngoài phạm vi [0, maxVertices-1].
     */
    private void validateVertexId(int vertexId) {
        if (vertexId < 0 || vertexId >= maxVertices) {
            throw new IllegalArgumentException("Invalid vertex ID: " + vertexId + ". Must be between 0 and " + (maxVertices - 1));
        }
    }

    /**
     * In đồ thị ra console (chủ yếu để debug).
     */
    public void printGraph() {
        System.out.println("Graph Representation (Adjacency List):");
        for (int i = 0; i < maxVertices; i++) {
            Location loc = locations[i];
            if (loc != null) {
                System.out.print("Vertex " + loc.name + " (ID:" + i + "): ");
                if (adjList[i].isEmpty()) {
                    System.out.println("-> No outgoing routes");
                } else {
                    for (Route route : adjList[i]) {
                        Location destLoc = locations[route.destinationId];
                        String destName = (destLoc != null) ? destLoc.name : "Unknown";
                        System.out.print("-> " + destName + "(ID:" + route.destinationId + ") " +
                                "[D:" + route.distance + ", T:" + route.time + ", C:" + route.cost + "] ");
                    }
                    System.out.println();
                }
            }
        }
    }
}
