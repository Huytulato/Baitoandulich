package datastructure;

import java.util.NoSuchElementException;

/**
 * Cài đặt Hàng đợi ưu tiên (Priority Queue) sử dụng MinHeap đã cài đặt.
 * Các phần tử được lấy ra theo thứ tự ưu tiên (priority nhỏ nhất trước).
 * Không sử dụng java.util.PriorityQueue.
 */
public class CustomPriorityQueue {

    private MinHeap minHeap;

    /**
     * Constructor mặc định.
     */
    public CustomPriorityQueue() {
        this.minHeap = new MinHeap();
    }

    /**
     * Constructor với dung lượng ban đầu.
     * @param initialCapacity Dung lượng khởi tạo cho MinHeap bên trong.
     */
    public CustomPriorityQueue(int initialCapacity) {
        this.minHeap = new MinHeap(initialCapacity);
    }

    /**
     * Thêm một phần tử vào hàng đợi ưu tiên.
     * @param node HeapNode cần thêm.
     */
    public void add(HeapNode node) {
        minHeap.insert(node);
    }

    /**
     * Lấy và xóa phần tử có độ ưu tiên cao nhất (priority nhỏ nhất).
     * @return HeapNode có priority nhỏ nhất.
     * @throws NoSuchElementException nếu hàng đợi rỗng.
     */
    public HeapNode poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority Queue is empty");
        }
        return minHeap.extractMin();
    }

    /**
     * Lấy phần tử có độ ưu tiên cao nhất mà không xóa.
     * @return HeapNode có priority nhỏ nhất.
     * @throws NoSuchElementException nếu hàng đợi rỗng.
     */
    public HeapNode peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority Queue is empty");
        }
        return minHeap.peekMin();
    }

    /**
     * Kiểm tra hàng đợi có rỗng không.
     * @return true nếu rỗng.
     */
    public boolean isEmpty() {
        return minHeap.isEmpty();
    }

    /**
     * Lấy số lượng phần tử hiện tại.
     * @return Số lượng phần tử.
     */
    public int size() {
        return minHeap.size();
    }
}
