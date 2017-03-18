/**
 * Created by Vadim on 18.03.2017.
 */
public class Main {
    private static final int capacity = 10000000;
    private static final int numberOfThreads = 2;
    private static final float[] wholeArray = new float[capacity];
    private static final float[] arrayPartOne = new float[capacity / 2];
    private static final float[] arrayPartTwo = new float[capacity / 2];
    private static long stopTime;
    private static long startTime;


    public static void main(String[] args) {
        for (int i = 0; i <wholeArray.length ; i++) wholeArray[i] = 1;
        startTime = System.nanoTime();
        for (int i = 0; i <wholeArray.length ; i++) {
            wholeArray[i] = (float)(wholeArray[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        stopTime = System.nanoTime();
        System.out.println("Время выполнения операции без разделения массива: " + countAndDisplayTheExecutionTime(stopTime, startTime) + " сек");
        System.out.println("Сумма элементов массива: " + sumOfArrayElements(wholeArray));

        startTime = 0;
        startTime = 0;
        for (int i = 0; i <wholeArray.length ; i++) wholeArray[i] = 1;

        startTime = System.nanoTime();
        System.arraycopy(wholeArray, 0, arrayPartOne, 0, capacity / 2);
        System.arraycopy(wholeArray, capacity / 2, arrayPartTwo, 0, capacity / 2 );
        Thread first = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <arrayPartOne.length ; i++) {
                    arrayPartOne[i] = (float)(arrayPartOne[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        Thread second = new Thread(new Runnable() {
            @Override
            public void run() {
                long iteratorForArrayPartTwo = capacity / 2;
                for (int i = 0; i <arrayPartTwo.length ; i++) {
                    arrayPartTwo[i] = (float)(arrayPartTwo[i] * Math.sin(0.2f + iteratorForArrayPartTwo / 5) * Math.cos(0.2f + iteratorForArrayPartTwo / 5) * Math.cos(0.4f + iteratorForArrayPartTwo / 2));
                    iteratorForArrayPartTwo++;
                }
            }
//
        });
        first.start();
        second.start();
        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {e.printStackTrace();}
        System.arraycopy(arrayPartOne, 0, wholeArray, 0, capacity / 2);
        System.arraycopy(arrayPartTwo, 0, wholeArray, capacity / 2, capacity / 2);
        stopTime = System.nanoTime();
        System.out.println("Время выполнения операции c разделением массива на 2 части и использованием 2 разных потоков " +
                countAndDisplayTheExecutionTime(stopTime, startTime) + " сек");
        System.out.println("Сумма элементов массива: " + sumOfArrayElements(wholeArray));
    }

    private static float countAndDisplayTheExecutionTime(long stopTime, long startTime){
        float executionTimeOfTheOperation = (stopTime - startTime) * 0.000000001f;
        return executionTimeOfTheOperation;
    }

    private static float sumOfArrayElements(float[] array) {
        float sumOfArrayElements = 0;
        for (int i = 0; i < array.length; i++) sumOfArrayElements += array[i];
        return sumOfArrayElements;
    }




}
