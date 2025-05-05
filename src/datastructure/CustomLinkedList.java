package datastructure;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomLinkedList<T> implements Iterable<T> {

    // Lớp Node nên là private hoặc package-private
    private static class Node<T> { // Có thể để static nếu không cần truy cập thành viên của CustomLinkedList
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public CustomLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // --- Các phương thức cơ bản ---
    public void add(T data) { // Thêm vào cuối (O(1))
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // --- Các phương thức cần thiết cho Queue/Stack (O(1)) ---

    /**
     * Thêm một phần tử vào đầu danh sách.
     * Độ phức tạp: O(1).
     * @param data Dữ liệu cần thêm.
     */
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    /**
     * Lấy và xóa phần tử ở đầu danh sách.
     * Độ phức tạp: O(1).
     * @return Dữ liệu của phần tử đầu tiên đã bị xóa.
     * @throws NoSuchElementException nếu danh sách rỗng.
     */
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        T data = head.data;
        head = head.next;
        size--;
        if (isEmpty()) { // Nếu list rỗng sau khi xóa
            tail = null;
        }
        return data;
    }

    /**
     * Xem phần tử ở đầu danh sách mà không xóa.
     * Độ phức tạp: O(1).
     * @return Dữ liệu của phần tử đầu tiên.
     * @throws NoSuchElementException nếu danh sách rỗng.
     */
    public T peekFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return head.data;
    }

    // --- Iterator (vẫn giữ private) ---
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    // Lớp Iterator nội bộ, giữ private là tốt nhất
    private class LinkedListIterator implements Iterator<T> {
        private Node<T> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = current.data;
            current = current.next;
            return data;
        }
        // Không cần phương thức getCurrentNodeForQueue() nữa
    }

    // Phương thức get(index) vẫn có thể giữ lại nếu cần, nhưng nó là O(n)
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }
}
