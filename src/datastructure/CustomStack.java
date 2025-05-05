package datastructure;

import java.util.NoSuchElementException;

public class CustomStack<T> {

    private CustomLinkedList<T> list;

    public CustomStack() {
        this.list = new CustomLinkedList<>();
    }

    /** Thêm vào đỉnh ngăn xếp (O(1)) */
    public void push(T item) {
        list.addFirst(item); // Sử dụng addFirst() của LinkedList
    }

    /** Lấy và xóa từ đỉnh ngăn xếp (O(1)) */
    public T pop() {
        if (isEmpty()) { // Kiểm tra trước
            throw new NoSuchElementException("Stack is empty");
        }
        return list.removeFirst(); // Sử dụng removeFirst() của LinkedList
    }

    /** Xem phần tử ở đỉnh ngăn xếp (O(1)) */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
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

