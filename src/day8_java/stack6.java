package day08_java;

public class stack6 {
    int queueLength = 4;
    int items[] = new int[queueLength];
    int front = -1;
    int back = -1;

    void deQue(){
        if(isEmpty()){
            System.out.println("Queue is empty. Nothing to dequeue");
        } else if (front == back){
            front = back = -1;
        } else {
            front++;
        }
    }

    void enQue(int itemValue) {
        if(isFull()){
            System.out.println("Queue is full");
        } else if(front == -1 && back == -1){
            front = back = 0;
            items[back] = itemValue;
        } else{
            back++;
            items[back] = itemValue;
        }
    }

    boolean isFull(){
        if(back == queueLength - 1){
            return true;
        } else {
            return false;
        }
    }

    boolean isEmpty(){
        if(front == -1 && back == -1){
            return true;
        } else {
            return false;
        }
    }

    void display(){
        int i;

        if(isEmpty()){
            System.out.println("Queue is empty");
        } else {
            for(i = front; i <= back; i++){
                System.out.println(items[i]);
            }
        }
    }

    void peek(){
        System.out.println("Front value is: " + items[front]);
    }
    public static void main(String[] args) {
        stack6 myQueue = new stack6();
        myQueue.enQue(100);
        myQueue.enQue(200);
        myQueue.enQue(300);
        myQueue.enQue(400);

        myQueue.display();

        myQueue.peek();
    }

}