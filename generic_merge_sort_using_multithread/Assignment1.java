package generic_merge_sort_using_multithread;

import java.util.Random;

public class Assignment1 {

	// confirmation number: ef19b6d1-96d3-46ec-8cba-b9fcc3accd92

	final static int size = 10000000;

	public static void main(String args[]) throws InterruptedException {

		Random random = new Random();

		Integer arr[] = new Integer[size];

		for (int i = 0; i < size; i++) {
			arr[i] = random.nextInt();
		}

		System.out.println("Input Unsorted Data: ");

		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}

		Thread mainThread = new Thread(new MergeSort<Integer>(arr));

		mainThread.start();

		try {
			mainThread.join();
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("\nSorted Data: ");

		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}

	}

}