package MultiThreadedMergeSort;

import java.util.Random;

public class Assignment1 {

	final static int size = 20000;

	public static void main(String[] args) throws InterruptedException {

		Random random = new Random();

		Integer[] arr = new Integer[size];

		for (int i = 0; i < size; i++) {
			arr[i] = random.nextInt();
		}

		System.out.println("Input Unsorted Data: ");

		for (Integer value : arr) {
			System.out.println(value);
		}

		Thread mainThread = new Thread(new MergeSort<Integer>(arr));

		mainThread.start();

		try {
			mainThread.join();
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("\nSorted Data: ");

		for (Integer integer : arr) {
			System.out.println(integer);
		}

	}

}