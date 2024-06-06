import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//
public class TestThreadCheckArray {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            Thread thread1, thread2;
            System.out.println("Enter list size");
            int num = input.nextInt();
            List<Integer> list = new ArrayList<>(num);
            System.out.println("Enter numbers for list");
            
            for (int index = 0; index < num; index++) {
                list.add(input.nextInt());
            }
            
            System.out.println("Enter number");
            num = input.nextInt();
            
            SharedData sd = new SharedData(list, num);
            
            thread1 = new Thread(new ThreadCheckArray(sd), "thread1");
            thread2 = new Thread(new ThreadCheckArray(sd), "thread2");
            thread1.start();
            thread2.start();
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!sd.getFlag()) {
                System.out.println("Sorry");
                return;
            }
            System.out.println("Solution for b : " + sd.getB() + ", n = " + sd.getList().size());
            System.out.print("I:    ");
            for (int index = 0; index < sd.getList().size(); index++) {
                System.out.print(index + "    ");
            }
            System.out.println();
            System.out.print("A:    ");
            for (int index : sd.getList()) {
                System.out.print(index);
                int counter = 5;
                while (true) {
                    index = index / 10;
                    counter--;
                    if (index == 0)
                        break;
                }
                for (int i = 0; i < counter; i++)
                    System.out.print(" ");
            }
            System.out.println();
            System.out.print("C:    ");
            for (boolean index : sd.getWinArray()) {
                if (index)
                    System.out.print("1    ");
                else
                    System.out.print("0    ");
            }
        }
    }
}

class SharedData {
    private final List<Integer> list;
    private final boolean[] winArray;
    private final int b;
    private boolean flag;

    public SharedData(List<Integer> list, int b) {
        this.list = list;
        this.b = b;
        this.winArray = new boolean[list.size()];
    }

    public List<Integer> getList() {
        return list;
    }

    public boolean[] getWinArray() {
        return winArray;
    }

    public int getB() {
        return b;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}

class ThreadCheckArray implements Runnable {
    private final SharedData sd;

    public ThreadCheckArray(SharedData sd) {
        this.sd = sd;
    }

    @Override
    public void run() {
        List<Integer> list = sd.getList();
        int b = sd.getB();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == b) {
                sd.getWinArray()[i] = true;
                sd.setFlag(true);
            }
        }
    }
}