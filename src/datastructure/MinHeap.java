package datastructure;

import java.util.Arrays; // Chỉ dùng Arrays.copyOf để thay đổi kích thước mảng
import java.util.NoSuchElementException;

/**
 * Cài đặt cấu trúc dữ liệu Min-Heap cơ bản sử dụng mảng.
 * Lưu trữ các đối tượng HeapNode và sắp xếp chúng theo priority nhỏ nhất ở gốc.
 * Không sử dụng java.util.PriorityQueue.
 */
public class MinHeap {

    private static final int DEFAULT_CAPACITY = 10;
    private HeapNode[] heap; // Mảng lưu trữ các node của heap
    private int size;        // Số lượng phần tử hiện tại trong heap

    /**
     * Constructor mặc định.
     */
    public MinHeap() {
        this.heap = new HeapNode[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Constructor với dung lượng ban đầu.
     * @param initialCapacity Dung lượng khởi tạo.
     */
    public MinHeap(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive");
        }
        this.heap = new HeapNode[initialCapacity];
        this.size = 0;
    }

    // --- Các hàm trợ giúp tính chỉ số ---
    private int getParentIndex(int index) { return (index - 1) / 2; }
    private int getLeftChildIndex(int index) { return 2 * index + 1; }
    private int getRightChildIndex(int index) { return 2 * index + 2; }

    private boolean hasParent(int index) { return getParentIndex(index) >= 0; }
    private boolean hasLeftChild(int index) { return getLeftChildIndex(index) < size; }
    private boolean hasRightChild(int index) { return getRightChildIndex(index) < size; }

    // --- Các hàm trợ giúp lấy giá trị Node ---
    private HeapNode parent(int index) { return heap[getParentIndex(index)]; }
    private HeapNode leftChild(int index) { return heap[getLeftChildIndex(index)]; }
    private HeapNode rightChild(int index) { return heap[getRightChildIndex(index)]; }

    /**
     * Đổi chỗ hai phần tử trong mảng heap.
     */
    private void swap(int index1, int index2) {
        HeapNode temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    /**
     * Đảm bảo đủ dung lượng mảng, tăng gấp đôi nếu cần.
     */
    private void ensureCapacity() {
        if (size == heap.length) {
            // Chỉ dùng Arrays.copyOf cho việc thay đổi kích thước mảng cơ bản
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
    }

    /**
     * Lấy phần tử nhỏ nhất (ở gốc) mà không xóa.
     * @return HeapNode nhỏ nhất.
     * @throws NoSuchElementException nếu heap rỗng.
     */
    public HeapNode peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return heap[0];
    }

    /**
     * Lấy và xóa phần tử nhỏ nhất (ở gốc).
     * @return HeapNode nhỏ nhất đã bị xóa.
     * @throws NoSuchElementException nếu heap rỗng.
     */
    public HeapNode extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        HeapNode min = heap[0];
        heap[0] = heap[size - 1]; // Đưa phần tử cuối lên gốc
        heap[size - 1] = null; // Giúp GC
        size--;
        heapifyDown(0); // Vun đống xuống từ gốc
        return min;
    }

    /**
     * Thêm một phần tử mới vào heap.
     * @param node HeapNode cần thêm.
     */
    public void insert(HeapNode node) {
        ensureCapacity();
        heap[size] = node; // Thêm vào cuối
        size++;
        heapifyUp(size - 1); // Vun đống lên từ vị trí vừa thêm
    }

    /**
     * Di chuyển một node lên trên để duy trì tính chất heap sau khi insert.
     * @param index Chỉ số của node cần vun lên.
     */
    private void heapifyUp(int index) {
        // Khi node hiện tại nhỏ hơn cha của nó thì đổi chỗ và đi lên tiếp
        while (hasParent(index) && heap[index].compareTo(parent(index)) < 0) {
            swap(index, getParentIndex(index));
            index = getParentIndex(index); // Di chuyển lên vị trí cha
        }
    }

    /**
     * Di chuyển một node xuống dưới để duy trì tính chất heap sau khi extractMin.
     * @param index Chỉ số của node cần vun xuống.
     */
    private void heapifyDown(int index) {
        while (hasLeftChild(index)) { // Chỉ cần kiểm tra con trái là đủ (vì heap là cây gần hoàn chỉnh)
            int smallerChildIndex = getLeftChildIndex(index);
            // Kiểm tra xem có con phải và con phải có nhỏ hơn con trái không
            if (hasRightChild(index) && rightChild(index).compareTo(leftChild(index)) < 0) {
                smallerChildIndex = getRightChildIndex(index);
            }

            // Nếu node hiện tại đã nhỏ hơn hoặc bằng con nhỏ nhất của nó thì dừng
            if (heap[index].compareTo(heap[smallerChildIndex]) <= 0) {
                break;
            } else {
                swap(index, smallerChildIndex); // Đổi chỗ với con nhỏ hơn
            }
            index = smallerChildIndex; // Di chuyển xuống vị trí con vừa đổi chỗ
        }
    }

    /**
     * Kiểm tra heap có rỗng không.
     * @return true nếu rỗng.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Lấy số lượng phần tử hiện tại.
     * @return Số lượng phần tử.
     */
    public int size() {
        return size;
    }

    // --- (Optional) Cài đặt decreaseKey ---
    // Việc cài đặt decreaseKey hiệu quả thường cần thêm một cấu trúc
    // để map từ vertexId sang vị trí của nó trong heap, phức tạp hơn.
    // Trong nhiều trường hợp, chỉ cần insert node mới vào PQ cũng đủ.
}
