package main;

import algorithms.DijkstraResult;
import algorithms.ShortestPathAlgorithm;
import datastructure.CustomLinkedList;
import graph.Graph;
import graph.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class TravelPlannerGUI extends JFrame {

    private JComboBox<String> startLocationCombo;
    private JComboBox<String> endLocationCombo;
    private JComboBox<String> criteriaCombo;
    private JComboBox<String> transportModeCombo;
    private JButton findPathButton;
    private JTextArea resultArea;
    private JLabel totalValueLabel;

    private Map<String, Location> locationMap;
    // Danh sách tất cả các địa điểm được định nghĩa, dùng để khởi tạo combobox và đồ thị
    private CustomLinkedList<Location> allDefinedLocations;

    private static final String[] TRANSPORT_MODES = {"car", "motorbike", "train", "plane"};

    // Định nghĩa các đối tượng Location một lần để dễ tham chiếu
    private static final Location HANOI = new Location(0, "Hanoi");
    private static final Location HAIPHONG = new Location(1, "Hai Phong");
    private static final Location DANANG = new Location(2, "Da Nang");
    private static final Location HCMC = new Location(3, "Ho Chi Minh City");
    private static final Location HUE = new Location(4, "Hue");
    private static final Location NHATRANG = new Location(5, "Nha Trang");

    public TravelPlannerGUI() {
        this.allDefinedLocations = defineStaticLocations(); // Khởi tạo danh sách địa điểm
        this.locationMap = new HashMap<>();
        for (Location loc : this.allDefinedLocations) {
            locationMap.put(loc.name + " (ID: " + loc.id + ")", loc);
        }

        setTitle("Travel Planner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 500);
        setLayout(new BorderLayout(10, 10));

        initComponents();
        populateComboBoxes(); // Sử dụng this.allDefinedLocations

        setLocationRelativeTo(null);
    }

    // Phương thức để định nghĩa tất cả các địa điểm có thể có
    private CustomLinkedList<Location> defineStaticLocations() {
        CustomLinkedList<Location> locations = new CustomLinkedList<>();
        locations.add(HANOI);
        locations.add(HAIPHONG);
        locations.add(DANANG);
        locations.add(HCMC);
        locations.add(HUE);
        locations.add(NHATRANG);
        return locations;
    }

    private void initComponents() {
        // --- Selection Panel ---
        JPanel selectionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        selectionPanel.add(new JLabel("Start Location:"), gbc);
        startLocationCombo = new JComboBox<>();
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        selectionPanel.add(startLocationCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        selectionPanel.add(new JLabel("End Location:"), gbc);
        endLocationCombo = new JComboBox<>();
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        selectionPanel.add(endLocationCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        selectionPanel.add(new JLabel("Optimization Criteria:"), gbc);
        criteriaCombo = new JComboBox<>(new String[]{"distance", "time", "cost"});
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        selectionPanel.add(criteriaCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        selectionPanel.add(new JLabel("Transport Mode:"), gbc);
        transportModeCombo = new JComboBox<>(TRANSPORT_MODES);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        selectionPanel.add(transportModeCombo, gbc);

        add(selectionPanel, BorderLayout.NORTH);

        // --- Result Panel ---
        JPanel resultPanel = new JPanel(new BorderLayout(5, 5));
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        totalValueLabel = new JLabel("Total Value: ");
        totalValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
        resultPanel.add(totalValueLabel, BorderLayout.SOUTH);
        resultPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        add(resultPanel, BorderLayout.CENTER);

        // --- Action Button Panel ---
        findPathButton = new JButton("Find Route");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(findPathButton);
        add(buttonPanel, BorderLayout.SOUTH);

        findPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findAndDisplayPath();
            }
        });
    }

    private void populateComboBoxes() {
        if (this.allDefinedLocations.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No locations defined.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Location loc : this.allDefinedLocations) {
            String item = loc.name + " (ID: " + loc.id + ")";
            startLocationCombo.addItem(item);
            endLocationCombo.addItem(item);
        }
    }

    private void findAndDisplayPath() {
        String startLocationString = (String) startLocationCombo.getSelectedItem();
        String endLocationString = (String) endLocationCombo.getSelectedItem();
        String criteria = (String) criteriaCombo.getSelectedItem();
        String transportMode = (String) transportModeCombo.getSelectedItem(); // Lấy phương tiện đã chọn

        if (startLocationString == null || endLocationString == null || criteria == null || transportMode == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select all required information (start, end, criteria, and transport mode).",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Location startLoc = locationMap.get(startLocationString);
        Location endLoc = locationMap.get(endLocationString);

        if (startLoc == null || endLoc == null) {
            JOptionPane.showMessageDialog(this, "Invalid location selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (startLoc.id == endLoc.id) {
            JOptionPane.showMessageDialog(this,
                    "Start and end locations cannot be the same.",
                    "Note",
                    JOptionPane.INFORMATION_MESSAGE);
            resultArea.setText("Start and end locations are the same.");
            String descriptiveLabelPrefixSamePoint = "Total Value";
            switch (criteria) {
                case "distance": descriptiveLabelPrefixSamePoint = "Total Distance"; break;
                case "time": descriptiveLabelPrefixSamePoint = "Total Time"; break;
                case "cost": descriptiveLabelPrefixSamePoint = "Total Cost"; break;
            }
            totalValueLabel.setText(descriptiveLabelPrefixSamePoint + ": 0");
            return;
        }

        // Xây dựng đồ thị CHỈ với dữ liệu của phương tiện đã chọn
        Graph graphForCurrentMode = buildGraphForMode(transportMode);

        // Gọi thuật toán tìm đường ngắn nhất.
        DijkstraResult result = ShortestPathAlgorithm.findShortestPath(graphForCurrentMode, startLoc.id, endLoc.id, criteria);

        String descriptiveLabelPrefix = "Total Value";
        String unit = "";

        switch (criteria) {
            case "distance":
                descriptiveLabelPrefix = "Total Distance"; unit = "km"; break;
            case "time":
                descriptiveLabelPrefix = "Total Time"; unit = "hours"; break;
            case "cost":
                descriptiveLabelPrefix = "Total Cost"; unit = "USD"; break;
        }

        if (result.pathFound) {
            StringBuilder pathText = new StringBuilder("Optimal path using '" + transportMode + "' based on '" + criteria + "':\n");
            boolean first = true;
            for (Location locNode : result.path) {
                if (!first) {
                    pathText.append(" -> ");
                }
                pathText.append(locNode.name);
                first = false;
            }
            resultArea.setText(pathText.toString());
            totalValueLabel.setText(String.format("%s: %.2f %s", descriptiveLabelPrefix, result.totalValue, unit));

        } else {
            resultArea.setText("No path found between " + startLoc.name + " and " + endLoc.name +
                    " using '" + transportMode + "' for the selected criteria.");
            totalValueLabel.setText(descriptiveLabelPrefix + ": N/A");
        }
    }

    /**
     * Xây dựng và trả về một đối tượng Graph chỉ chứa các cạnh và dữ liệu
     * (distance, time, cost) tương ứng với `transportMode` được chọn.
     * Giả sử lớp Graph có addVertex(Location) và addEdge(fromId, toId, distance, time, cost).
     */
    private Graph buildGraphForMode(String transportMode) {
        Graph modeSpecificGraph = new Graph(this.allDefinedLocations.size() + 5); // Cung cấp kích thước khởi tạo

        // Thêm tất cả các đỉnh (địa điểm) đã định nghĩa vào đồ thị này
        for (Location loc : this.allDefinedLocations) {
            modeSpecificGraph.addVertex(loc);
        }

        // Thêm các cạnh với dữ liệu cụ thể cho transportMode đã chọn
        // Dữ liệu mẫu (distance km, time hours, cost USD)

        // --- Tuyến Hà Nội (HANOI.id) ---
        // Hà Nội <-> Hải Phòng (HAIPHONG.id)
        if (transportMode.equals("car")) {
            modeSpecificGraph.addEdge(HANOI.id, HAIPHONG.id, 120, 2.0, 15);
            modeSpecificGraph.addEdge(HAIPHONG.id, HANOI.id, 120, 2.0, 15);
        } else if (transportMode.equals("motorbike")) {
            modeSpecificGraph.addEdge(HANOI.id, HAIPHONG.id, 125, 2.5, 7);
            modeSpecificGraph.addEdge(HAIPHONG.id, HANOI.id, 125, 2.5, 7);
        } else if (transportMode.equals("train")) {
            modeSpecificGraph.addEdge(HANOI.id, HAIPHONG.id, 105, 2.2, 10);
            modeSpecificGraph.addEdge(HAIPHONG.id, HANOI.id, 105, 2.2, 10);
        } // Không có "plane" cho Hà Nội-Hải Phòng

        // Hà Nội <-> Đà Nẵng (DANANG.id)
        if (transportMode.equals("car")) {
            modeSpecificGraph.addEdge(HANOI.id, DANANG.id, 770, 14.0, 70);
            modeSpecificGraph.addEdge(DANANG.id, HANOI.id, 770, 14.0, 70);
        } else if (transportMode.equals("motorbike")) {
            modeSpecificGraph.addEdge(HANOI.id, DANANG.id, 800, 18.0, 45);
            modeSpecificGraph.addEdge(DANANG.id, HANOI.id, 800, 18.0, 45);
        } else if (transportMode.equals("train")) {
            modeSpecificGraph.addEdge(HANOI.id, DANANG.id, 790, 16.0, 50);
            modeSpecificGraph.addEdge(DANANG.id, HANOI.id, 790, 16.0, 50);
        } else if (transportMode.equals("plane")) {
            modeSpecificGraph.addEdge(HANOI.id, DANANG.id, 630, 1.25, 65); // Khoảng cách đường bay
            modeSpecificGraph.addEdge(DANANG.id, HANOI.id, 630, 1.25, 65);
        }

        // Hà Nội <-> Huế (HUE.id)
        if (transportMode.equals("car")) {
            modeSpecificGraph.addEdge(HANOI.id, HUE.id, 670, 12.0, 60);
            modeSpecificGraph.addEdge(HUE.id, HANOI.id, 670, 12.0, 60);
        } else if (transportMode.equals("motorbike")) {
            modeSpecificGraph.addEdge(HANOI.id, HUE.id, 690, 15.0, 40);
            modeSpecificGraph.addEdge(HUE.id, HANOI.id, 690, 15.0, 40);
        } else if (transportMode.equals("train")) {
            modeSpecificGraph.addEdge(HANOI.id, HUE.id, 688, 13.5, 45);
            modeSpecificGraph.addEdge(HUE.id, HANOI.id, 688, 13.5, 45);
        } else if (transportMode.equals("plane")) {
            modeSpecificGraph.addEdge(HANOI.id, HUE.id, 540, 1.15, 60);
            modeSpecificGraph.addEdge(HUE.id, HANOI.id, 540, 1.15, 60);
        }
        // Hà nội <-> Hồ Chí Minh
        if (transportMode.equals("car")) {
            modeSpecificGraph.addEdge(HANOI.id, HCMC.id, 1680, 30.0, 110);
            modeSpecificGraph.addEdge(HCMC.id, HANOI.id, 1680, 30.0, 110);
        } else if (transportMode.equals("motorbike")) {
            modeSpecificGraph.addEdge(HANOI.id, HCMC.id, 1730, 43.25, 85);
            modeSpecificGraph.addEdge(HCMC.id, HANOI.id, 1730, 43.25, 85);
        } else if (transportMode.equals("train")) {
            modeSpecificGraph.addEdge(HANOI.id, HCMC.id, 1710, 38.25, 95);
            modeSpecificGraph.addEdge(HCMC.id, HANOI.id, 1710, 38.25, 95);
        } else if (transportMode.equals("plane")) {
            modeSpecificGraph.addEdge(HANOI.id, HCMC.id, 1160, 2.15, 125);
            modeSpecificGraph.addEdge(HCMC.id, HANOI.id, 1160, 2.15, 125);
        }

        // --- Tuyến Hải Phòng (HAIPHONG.id) ---
        // Hải Phòng <-> Đà Nẵng (DANANG.id)
        // Đối với tàu/máy bay thường nối chuyến qua Hà Nội.
        // Để đơn giản, chỉ thêm xe hơi/xe máy trực tiếp.
        if (transportMode.equals("car")) {
            modeSpecificGraph.addEdge(HAIPHONG.id, DANANG.id, 890, 16.0, 85);
            modeSpecificGraph.addEdge(DANANG.id, HAIPHONG.id, 890, 16.0, 85);
        } else if (transportMode.equals("motorbike")) {
            modeSpecificGraph.addEdge(HAIPHONG.id, DANANG.id, 920, 20.0, 55);
            modeSpecificGraph.addEdge(DANANG.id, HAIPHONG.id, 920, 20.0, 55);
        }

        // --- Tuyến Huế (HUE.id) ---
        // Huế <-> Đà Nẵng (DANANG.id)
        if (transportMode.equals("car")) {
            modeSpecificGraph.addEdge(HUE.id, DANANG.id, 100, 2.0, 12); // Qua hầm nhanh hơn
            modeSpecificGraph.addEdge(DANANG.id, HUE.id, 100, 2.0, 12);
        } else if (transportMode.equals("motorbike")) {
            modeSpecificGraph.addEdge(HUE.id, DANANG.id, 120, 3.5, 8); // Qua đèo Hải Vân
            modeSpecificGraph.addEdge(DANANG.id, HUE.id, 120, 3.5, 8);
        } else if (transportMode.equals("train")) {
            modeSpecificGraph.addEdge(HUE.id, DANANG.id, 103, 2.5, 7);
            modeSpecificGraph.addEdge(DANANG.id, HUE.id, 103, 2.5, 7);
        } // Không có "plane" cho Huế-Đà Nẵng

        // Huế <-> TP. Hồ Chí Minh (HCMC.id)
        if (transportMode.equals("car")) {
            modeSpecificGraph.addEdge(HUE.id, HCMC.id, 950, 18.0, 90);
            modeSpecificGraph.addEdge(HCMC.id, HUE.id, 950, 18.0, 90);
        } else if (transportMode.equals("motorbike")) {
            modeSpecificGraph.addEdge(HUE.id, HCMC.id, 980, 22.0, 60);
            modeSpecificGraph.addEdge(HCMC.id, HUE.id, 980, 22.0, 60);
        } else if (transportMode.equals("train")) {
            modeSpecificGraph.addEdge(HUE.id, HCMC.id, 1040, 20.0, 70);
            modeSpecificGraph.addEdge(HCMC.id, HUE.id, 1040, 20.0, 70);
        } else if (transportMode.equals("plane")) {
            modeSpecificGraph.addEdge(HUE.id, HCMC.id, 615, 1.4, 75);
            modeSpecificGraph.addEdge(HCMC.id, HUE.id, 615, 1.4, 75);
        }

        // --- Tuyến Đà Nẵng (DANANG.id) ---
        // Đà Nẵng <-> Nha Trang (NHATRANG.id)
        if (transportMode.equals("car")) {
            modeSpecificGraph.addEdge(DANANG.id, NHATRANG.id, 530, 9.0, 50);
            modeSpecificGraph.addEdge(NHATRANG.id, DANANG.id, 530, 9.0, 50);
        } else if (transportMode.equals("motorbike")) {
            modeSpecificGraph.addEdge(DANANG.id, NHATRANG.id, 550, 11.0, 30);
            modeSpecificGraph.addEdge(NHATRANG.id, DANANG.id, 550, 11.0, 30);
        } else if (transportMode.equals("train")) {
            modeSpecificGraph.addEdge(DANANG.id, NHATRANG.id, 524, 10.0, 40);
            modeSpecificGraph.addEdge(NHATRANG.id, DANANG.id, 524, 10.0, 40);
        } else if (transportMode.equals("plane")) {
            modeSpecificGraph.addEdge(DANANG.id, NHATRANG.id, 400, 1.0, 55);
            modeSpecificGraph.addEdge(NHATRANG.id, DANANG.id, 400, 1.0, 55);
        }

        // Đà Nẵng <-> TP. Hồ Chí Minh (HCMC.id)
        if (transportMode.equals("car")) {
            modeSpecificGraph.addEdge(DANANG.id, HCMC.id, 850, 16.0, 80);
            modeSpecificGraph.addEdge(HCMC.id, DANANG.id, 850, 16.0, 80);
        } else if (transportMode.equals("motorbike")) {
            modeSpecificGraph.addEdge(DANANG.id, HCMC.id, 900, 20.0, 50);
            modeSpecificGraph.addEdge(HCMC.id, DANANG.id, 900, 20.0, 50);
        } else if (transportMode.equals("train")) {
            modeSpecificGraph.addEdge(DANANG.id, HCMC.id, 935, 17.0, 60);
            modeSpecificGraph.addEdge(HCMC.id, DANANG.id, 935, 17.0, 60);
        } else if (transportMode.equals("plane")) {
            modeSpecificGraph.addEdge(DANANG.id, HCMC.id, 610, 1.3, 70);
            modeSpecificGraph.addEdge(HCMC.id, DANANG.id, 610, 1.3, 70);
        }

        // --- Tuyến Nha Trang (NHATRANG.id) ---
        // Nha Trang <-> TP. Hồ Chí Minh (HCMC.id)
        if (transportMode.equals("car")) {
            modeSpecificGraph.addEdge(NHATRANG.id, HCMC.id, 430, 7.5, 40);
            modeSpecificGraph.addEdge(HCMC.id, NHATRANG.id, 430, 7.5, 40);
        } else if (transportMode.equals("motorbike")) {
            modeSpecificGraph.addEdge(NHATRANG.id, HCMC.id, 450, 9.0, 25);
            modeSpecificGraph.addEdge(HCMC.id, NHATRANG.id, 450, 9.0, 25);
        } else if (transportMode.equals("train")) {
            modeSpecificGraph.addEdge(NHATRANG.id, HCMC.id, 411, 8.0, 30);
            modeSpecificGraph.addEdge(HCMC.id, NHATRANG.id, 411, 8.0, 30);
        } else if (transportMode.equals("plane")) {
            modeSpecificGraph.addEdge(NHATRANG.id, HCMC.id, 305, 1.0, 50);
            modeSpecificGraph.addEdge(HCMC.id, NHATRANG.id, 305, 1.0, 50);
        }

        return modeSpecificGraph;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TravelPlannerGUI().setVisible(true);
            }
        });
    }
}