package datastructure;
//Lớp node cơ bản cho customlinkedlist

public class Node<T> {
    T data;
    Node<T> next;
    Node(T data){
        this.data=data;
        this.next=null;
    }
}
