import java.util.Scanner;

public class Array {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Какой массив вы хотите?\n 1 - одномерный;\n 2 - двумерный; ");
        int x = in.nextInt();
        if (x == 1) {
            System.out.print("Вы выбрали одномерный массив;");
            firstMassiv(in);
        } else if (x == 2) {
            System.out.println("Вы выбрали двумерный массив;");
            secondArray(in);
        }

        in.close();
    }

    static void firstMassiv(Scanner in) {
        System.out.print("Введите длину вашего массива: ");
        int size = in.nextInt();

        System.out.println("Вы выбрали одномерный массив длинной " + size);

        int[] array = new int[size];
        System.out.print("Введите содержание массива: \n");

        for (int i = 0; i < size; i++) {
            array[i] = in.nextInt();
        }
        System.out.println("Элементы массива: ");
        for (int i = 0; i < size; i++) {
            System.out.println(" " + array[i]);
        }
    }

    static void secondArray(Scanner in) {
        int width, length;
        System.out.println("Введите ширину массива: ");
        width = in.nextInt();
        System.out.println("Введите длину массива: ");
        length = in.nextInt();
        int[][] array = new int[width][length];
        System.out.println("Введите элементы массива: ");

        for (int i = 0; i < width; i++) {
            for (int y = 0; y < length; y++) {
                System.out.println("Заполняем введенные данные [" + i + "] " + " [" + y + "]");
                array[i][y] = in.nextInt();
            }
        }

        for (int i = 0; i < width; i++) {
            System.out.println();
            for (int y = 0; y < length; y++) {
                System.out.print(array[i][y] + "\t");
            }
        }
    }
}