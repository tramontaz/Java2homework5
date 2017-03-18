/**
 * Created by Vadim on 18.03.2017.
 */
public class Main {
    private static final int capacity = 10000000;
    private static final float[] wholeArray = new float[capacity];
    private static final float[] gluedArray = new float[capacity];
    private static final float[] arrayPartOne = new float[capacity / 2];
    private static final float[] arrayPartTwo = new float[capacity / 2];
    private static long stopTime;
    private static long startTime;


    public static void main(String[] args) {
        for (int i = 0; i <wholeArray.length ; i++) wholeArray[i] = 1;
        for (int i = 0; i <gluedArray.length ; i++) gluedArray[i] = 1;

        startTime = System.nanoTime();
        for (int i = 0; i <wholeArray.length ; i++) {
            wholeArray[i] = (float)(wholeArray[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        stopTime = System.nanoTime();
        System.out.println("The time of the operation without dividing the array: " + countAndDisplayTheExecutionTime(stopTime, startTime) + " sec");
//        System.out.println("sum of array elements: " + sumOfArrayElements(wholeArray)); // Comment off for show sum of the wholeArray

        startTime = 0;
        startTime = 0;

        startTime = System.nanoTime();
        System.arraycopy(gluedArray, 0, arrayPartOne, 0, capacity / 2);
        System.arraycopy(gluedArray, capacity / 2, arrayPartTwo, 0, capacity / 2 );
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
                /* problem here:
                The formula uses a loop iterator, so we need to change it to the iterator value as the iterator value in the whole cycle.*/
                long iteratorForArrayPartTwo = capacity / 2;
                for (int i = 0; i <arrayPartTwo.length ; i++) {
                    arrayPartTwo[i] = (float)(arrayPartTwo[i] * Math.sin(0.2f + iteratorForArrayPartTwo / 5) *
                            Math.cos(0.2f + iteratorForArrayPartTwo / 5) * Math.cos(0.4f + iteratorForArrayPartTwo / 2));
                    iteratorForArrayPartTwo++;
                }
            }
        });
        first.start();
        second.start();
        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {e.printStackTrace();}
        System.arraycopy(arrayPartOne, 0, gluedArray, 0, capacity / 2);
        System.arraycopy(arrayPartTwo, 0, gluedArray, capacity / 2, capacity / 2);
        stopTime = System.nanoTime();
        System.out.println("The time of the operation is performed by dividing the array into 2 parts and using 2 different threads: " +
                countAndDisplayTheExecutionTime(stopTime, startTime) + " sec");
//        System.out.println("sum of array elements: " + sumOfArrayElements(gluedArray)); // Comment off for show sum of the gluedArray
//        if (wholeArray.equals(gluedArray)) System.out.println("The calculations are correct, the results of time measurements can be applied."); //why it's doesn't work?
        if (sumOfArrayElements(wholeArray) == sumOfArrayElements(gluedArray)) System.out.println("\n" + "The calculations are correct, the results of time measurements can be applied.");
        else System.out.println("\n" + "The calculations are not correct, the results of time measurement are invalid.");
    }

    // method for counting execution time
    private static float countAndDisplayTheExecutionTime(long stopTime, long startTime){
        return (stopTime - startTime) * 0.000000001f;
    }

    // method for counting sum of array elements
    private static float sumOfArrayElements(float[] array) {
        float sumOfArrayElements = 0;
        for (int i = 0; i < array.length; i++) sumOfArrayElements += array[i];
        return sumOfArrayElements;
    }




}
