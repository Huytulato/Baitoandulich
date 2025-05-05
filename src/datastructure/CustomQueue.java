package datastructure;

import java.util.NoSuchElementException;

public class CustomQueue<T> {

    private CustomLinkedList<T> list;

    public CustomQueue() {
        this.list = new CustomLinkedList<>();
    }

    /** Thêm vào cuối hàng đợi (O(1)) */
    public void enqueue(T item) {
        list.add(item); // Sử dụng add() (thêm vào cuối) của LinkedList
    }

    /** Lấy và xóa từ đầu hàng đợi (O(1)) */
    public T dequeue() {
        if (isEmpty()) { // Kiểm tra trước khi gọi removeFirst để có thông báo lỗi rõ ràng hơn
            throw new NoSuchElementException("Queue is empty");
        }
        return list.removeFirst(); // Sử dụng removeFirst() của LinkedList
    }

    /** Xem phần tử đầu hàng đợi (O(1)) */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return list.peekFirst(); // Sử dụng peekFirst() của LinkedList
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    // Không cần các phương thức "Internal" nữa
}