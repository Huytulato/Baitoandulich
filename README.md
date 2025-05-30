# Ứng dụng Lập Kế Hoạch Du Lịch (Travel Planner)

Đây là một ứng dụng Java đơn giản giúp người dùng lập kế hoạch tuyến đường tối ưu giữa các địa điểm khác nhau. Ứng dụng sử dụng cấu trúc dữ liệu đồ thị để biểu diễn các địa điểm và tuyến đường, đồng thời áp dụng thuật toán Dijkstra để tìm đường đi ngắn nhất dựa trên các tiêu chí như khoảng cách, thời gian di chuyển, hoặc chi phí.

Dự án này được phát triển với mục tiêu tự cài đặt các cấu trúc dữ liệu và giải thuật cốt lõi mà không sử dụng các thư viện có sẵn của Java cho các thành phần này.

## Tính năng chính

*   Tìm đường đi tối ưu giữa hai địa điểm được chọn với phương tiện tùy chọn.
*   Tối ưu hóa lộ trình dựa trên một trong ba tiêu chí:
    *   Khoảng cách ngắn nhất
    *   Thời gian di chuyển nhanh nhất
    *   Chi phí thấp nhất
*   Giao diện đồ họa người dùng (GUI) được xây dựng bằng Java Swing.
*   Dữ liệu mẫu về các địa điểm và tuyến đường được định nghĩa sẵn trong chương trình.

## Cấu trúc Dự án

Mã nguồn được tổ chức thành các package chính sau trong thư mục `src`:

*   `algorithms`: Chứa logic cài đặt thuật toán Dijkstra và các lớp liên quan đến kết quả tìm đường.
*   `datastructure`: Chứa các cài đặt tự xây dựng cho các cấu trúc dữ liệu cơ bản như Danh sách liên kết, Min-Heap, Hàng đợi ưu tiên.
*   `graph`: Chứa các lớp định nghĩa và biểu diễn cấu trúc đồ thị (Địa điểm, Tuyến đường, Đồ thị).
*   `main`: Chứa lớp chính của ứng dụng với giao diện đồ họa (`TravelPlannerGUI.java`).

## Yêu cầu Hệ thống

*   Java Development Kit (JDK) phiên bản 8 trở lên (khuyến nghị JDK 11 hoặc mới hơn).

## Hướng dẫn Biên dịch và Chạy Ứng dụng

Cách để biên dịch và chạy ứng dụng:

**Sử dụng Môi trường Phát triển Tích hợp (IDE) như IntelliJ IDEA, Eclipse, NetBeans**

1.  **Clone hoặc Tải Dự án:**
    *   Nếu dự án được lưu trữ trên Git (ví dụ: GitHub), clone repository về máy của bạn:
        ```bash
        git clone [URL_CUA_REPOSITORY_CUA_BAN]
        ```
    *   Nếu bạn có mã nguồn dưới dạng file ZIP, giải nén nó ra một thư mục.
2.  **Mở Dự án trong IDE:**
    *   Mở IDE của bạn (ví dụ: IntelliJ IDEA).
    *   Chọn "Open" hoặc "Import Project" và trỏ đến thư mục gốc của dự án vừa clone/giải nén. IDE thường sẽ tự động nhận diện cấu trúc dự án Java.
3.  **Biên dịch (Build):**
    *   Hầu hết các IDE sẽ tự động biên dịch mã nguồn khi bạn mở dự án hoặc khi có thay đổi. Nếu không, bạn có thể tìm tùy chọn "Build Project" (thường nằm trong menu "Build" hoặc "Project").
4.  **Chạy Ứng dụng:**
    *   Tìm file `TravelPlannerGUI.java` trong package `main` (thường là `src/main/TravelPlannerGUI.java`).
    *   Nhấp chuột phải vào file `TravelPlannerGUI.java`.
    *   Chọn "Run 'TravelPlannerGUI.main()'" .
    *   Giao diện đồ họa của ứng dụng sẽ xuất hiện.

## Hướng dẫn Sử dụng Giao diện Đồ họa

Sau khi ứng dụng khởi chạy, bạn sẽ thấy giao diện đồ họa với các thành phần sau:

1.  **Chọn Địa điểm Bắt đầu (Start Location):** Một danh sách thả xuống (combo box) cho phép bạn chọn địa điểm xuất phát từ các địa điểm có sẵn.
2.  **Chọn Địa điểm Kết thúc (End Location):** Một danh sách thả xuống tương tự để chọn địa điểm đích.
3.  **Chọn Tiêu chí Tối ưu (Optimization Criteria):** Một danh sách thả xuống cho phép bạn chọn một trong các tiêu chí:
    *   `distance` (Khoảng cách)
    *   `time` (Thời gian)
    *   `cost` (Chi phí)
4.  **Chọn Phương tiện (Transport Mode):** 
5.  **Nút "Tìm Đường" (Find Path Button):** Sau khi đã chọn tất cả các thông tin cần thiết, nhấp vào nút này.
6.  **Khu vực Hiển thị Kết quả:**
    *   Lộ trình tối ưu tìm được sẽ được hiển thị dưới dạng một chuỗi các địa điểm.
    *   Tổng giá trị (khoảng cách, thời gian, hoặc chi phí) của lộ trình đó cũng sẽ được hiển thị.
    *   Nếu không tìm thấy đường đi, một thông báo tương ứng sẽ xuất hiện.

## Dữ liệu Mẫu

Ứng dụng sử dụng một bộ dữ liệu mẫu về các địa điểm và tuyến đường được định nghĩa sẵn trong mã nguồn (trong phương thức `createSampleGraph()` của lớp `TravelPlannerGUI` hoặc một lớp tiện ích khác). Bạn có thể xem và sửa đổi dữ liệu này trực tiếp trong mã nguồn nếu muốn thử nghiệm với các kịch bản khác nhau.

## Đóng góp

Hiện tại, dự án này được phát triển cho mục đích học tập. Nếu bạn có ý tưởng cải thiện hoặc muốn đóng góp, vui lòng tạo một "Issue" hoặc "Pull Request" (nếu dự án được quản lý trên một nền tảng như GitHub).



---
