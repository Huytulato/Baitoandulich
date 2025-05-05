package main;

import algorithms.DijkstraResult;
import algorithms.ShortestPathAlgorithm;
import datastructure.CustomLinkedList;
import graph.Graph;
import graph.Location;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Lớp chính của ứng dụng lập kế hoạch du lịch.
 */
public class TravelPlannerApp {

    public static void main(String[] args) {
        // 1. Tạo đồ thị và thêm dữ liệu mẫu
        Graph travelGraph = createSampleGraph();
        // travelGraph.printGraph(); // Bỏ comment để xem cấu trúc đồ thị

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // 2. Hiển thị các địa điểm có sẵn
            System.out.println("\n--- Available Locations ---");
            CustomLinkedList<Location> locations = travelGraph.getAllLocations();
            if (locations.isEmpty()) {
                System.out.println("No locations available in the graph.");
                break; // Thoát nếu không có địa điểm
            }
            for (Location loc : locations) {
                System.out.println("- " + loc.name + " (ID: " + loc.id + ")");
            }
            System.out.println("-------------------------");

            // 3. Nhận input từ người dùng
            int startId = -1, endId = -1;
            String criteria = "";

            try {
                System.out.print("Enter Start Location ID: ");
                startId = scanner.nextInt();

                System.out.print("Enter End Location ID: ");
                endId = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                System.out.print("Enter Optimization Criteria (distance, time, cost): ");
                criteria = scanner.nextLine().trim().toLowerCase();

                // Kiểm tra tiêu chí hợp lệ cơ bản
                if (!criteria.equals("distance") && !criteria.equals("time") && !criteria.equals("cost")) {
                    System.out.println("Invalid criteria. Please enter 'distance', 'time', or 'cost'.");
                    continue; // Quay lại vòng lặp
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numeric IDs.");
                scanner.nextLine(); // Consume invalid input
                continue; // Quay lại vòng lặp
            }

            // 4. Gọi thuật toán tìm đường đi ngắn nhất
            DijkstraResult result = ShortestPathAlgorithm.findShortestPath(travelGraph, startId, endId, criteria);

            // 5. Hiển thị kết quả
            System.out.println("\n--- Result ---");
            if (result.pathFound) {
                System.out.println("Optimal path found based on '" + criteria + "':");
                boolean first = true;
                for (Location loc : result.path) {
                    if (!first) {
                        System.out.print(" -> ");
                    }
                    System.out.print(loc.name);
                    first = false;
                }
                System.out.println(); // Xuống dòng

                // Định dạng output cho giá trị tổng
                String unit = "";
                switch (criteria) {
                    case "distance": unit = "km"; break; // Giả sử đơn vị
                    case "time": unit = "hours"; break; // Giả sử đơn vị
                    case "cost": unit = "USD"; break; // Giả sử đơn vị
                }
                System.out.printf("Total %s: %.2f %s\n", criteria, result.totalValue, unit);

            } else {
                System.out.println("No path found between the specified locations.");
                // Có thể in thêm lý do nếu thuật toán trả về chi tiết hơn
            }
            System.out.println("--------------");

            // Hỏi người dùng có muốn tiếp tục không
            System.out.print("Find another route? (yes/no): ");
            String continueChoice = scanner.nextLine().trim().toLowerCase();
            if (!continueChoice.equals("yes")) {
                break; // Thoát vòng lặp
            }
        }

        System.out.println("\nExiting Travel Planner. Goodbye!");
        scanner.close();
    }

    /**
     * Tạo đồ thị mẫu với một số địa điểm và tuyến đường.
     * @return Đối tượng Graph đã có dữ liệu.
     */
    private static Graph createSampleGraph() {
        // Chọn số đỉnh tối đa lớn hơn số đỉnh thực tế một chút
        Graph graph = new Graph(10);

        // Thêm địa điểm (Vertex) - ID phải từ 0 đến maxVertices-1
        Location hanoi = new Location(0, "Hanoi");
        Location haiphong = new Location(1, "Hai Phong");
        Location danang = new Location(2, "Da Nang");
        Location hcmc = new Location(3, "Ho Chi Minh City");
        Location hue = new Location(4, "Hue");
        Location nhaTrang = new Location(5, "Nha Trang");

        graph.addVertex(hanoi);
        graph.addVertex(haiphong);
        graph.addVertex(danang);
        graph.addVertex(hcmc);
        graph.addVertex(hue);
        graph.addVertex(nhaTrang);


        // Thêm tuyến đường (Edge) - distance, time, cost
        // Lưu ý: Thêm cạnh 2 chiều nếu đường đi là 2 chiều
        // Hanoi <-> Hai Phong
        graph.addEdge(hanoi.id, haiphong.id, 120, 2.0, 10);
        graph.addEdge(haiphong.id, hanoi.id, 120, 2.0, 10);

        // Hanoi -> Da Nang
        graph.addEdge(hanoi.id, danang.id, 760, 12.0, 50);
        // Da Nang -> Hanoi (có thể khác)
        graph.addEdge(danang.id, hanoi.id, 770, 12.5, 55);

        // Hanoi -> Hue (qua Da Nang là đường khác)
        graph.addEdge(hanoi.id, hue.id, 660, 11.0, 45);
        graph.addEdge(hue.id, hanoi.id, 660, 11.0, 45);

        // Hai Phong -> Da Nang
        graph.addEdge(haiphong.id, danang.id, 880, 14.0, 60);
        graph.addEdge(danang.id, haiphong.id, 880, 14.0, 60);

        // Hue <-> Da Nang
        graph.addEdge(hue.id, danang.id, 100, 1.5, 8);
        graph.addEdge(danang.id, hue.id, 100, 1.5, 8);

        // Da Nang -> Nha Trang
        graph.addEdge(danang.id, nhaTrang.id, 530, 8.0, 40);
        graph.addEdge(nhaTrang.id, danang.id, 530, 8.0, 40);

        // Da Nang -> HCMC
        graph.addEdge(danang.id, hcmc.id, 850, 15.0, 70);
        graph.addEdge(hcmc.id, danang.id, 850, 15.0, 70);

        // Nha Trang <-> HCMC
        graph.addEdge(nhaTrang.id, hcmc.id, 430, 7.0, 35);
        graph.addEdge(hcmc.id, nhaTrang.id, 430, 7.0, 35);

        return graph;
    }
}
