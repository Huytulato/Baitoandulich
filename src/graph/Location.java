package graph;

import java.util.Objects; // Cần import Objects nếu dùng trong hashCode

public class Location {
    public final int id; // Nên để final nếu giá trị không đổi sau khi tạo
    public final String name; // Nên để final

    // Constructor (Cần có để khởi tạo các trường final)
    public Location(int id, String name) {
        // Thêm kiểm tra đầu vào nếu cần (ví dụ: name không null)
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }

    // Optional: equals và hashCode nếu cần lưu trong cấu trúc dữ liệu khác
    // (Ví dụ: HashMap, HashSet)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // Nên dùng instanceof thay vì getClass() để hỗ trợ kế thừa nếu có
        // Hoặc dùng getClass() nếu muốn so sánh chính xác cùng kiểu
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id; // Chỉ so sánh dựa trên ID là đủ nếu ID là duy nhất
    }

    @Override
    public int hashCode() {
        // Quan trọng: Phải override hashCode nếu override equals
        // Cách đơn giản nhất là dựa trên các trường dùng trong equals
        return Objects.hash(id);
        // Hoặc return Integer.hashCode(id);
    }
}

