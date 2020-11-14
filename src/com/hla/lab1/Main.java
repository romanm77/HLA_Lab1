package com.hla.lab1;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(10);
        int size = 11;
        int middle = size / 2;
        int d = size - middle;
        int[] array = create(size);
        int[] a1 = new int[middle];
        int[] a2 = new int[d];

        print(array);
        System.arraycopy(array, 0, a1, 0, middle);
        System.arraycopy(array, middle, a2, 0, d);

        Future<?> f1 = pool.submit(() -> {
            sort(a1);
        });

        Future<?> f2 = pool.submit(() -> {
            sort(a2);
        });

        f1.get();
        f2.get();
        print(merge(a1, a2));
        pool.shutdown();
    }

    static void sort(int[] array) {
        boolean sorted = false;
        int temp;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i+1]) {
                    temp = array[i];
                    array[i] = array[i+1];
                    array[i+1] = temp;
                    sorted = false;
                }
            }
        }
    }

    static void print(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    static int[] create(int size) {
        int[] array = new int[size];
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextInt(10);
        }
        return array;
    }

    static int[] merge(int[] a1, int[] a2) {
        int cursorLeft = 0, cursorRight = 0, counter = 0;
        int[] merged = new int[a1.length + a2.length];
        while (cursorLeft < a1.length && cursorRight < a2.length) {
            if (a1[cursorLeft] < a2[cursorRight]) {
                merged[counter] = a1[cursorLeft];
                cursorLeft+=1;
            } else {
                merged[counter] = a2[cursorRight];
                cursorRight+=1;
            }
            counter++;
        }
        if (cursorLeft < a1.length) {
            System.arraycopy( a1, cursorLeft, merged, counter, merged.length - counter );
        }
        if (cursorRight < a2.length) {
            System.arraycopy( a2, cursorRight, merged, counter, merged.length - counter );
        }
        return merged;
    }
}
