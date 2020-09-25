package MultiThreadedMergeSort;

public class MergeSort<AnyType extends Comparable<? super AnyType>> implements Runnable {

	private int start;
	private int end;
	private AnyType[] dataset;
	private int numberOfThread;

	public MergeSort(AnyType[] dataset, int start, int end, int numberOfThread) {
		this.setDataset(dataset);
		this.setStart(start);
		this.setEnd(end);
		this.setNumberOfThread(numberOfThread);
	}

	public MergeSort(AnyType[] dataset) {
		this.setDataset(dataset);
		this.start = 0;
		this.end = dataset.length - 1;
		this.numberOfThread = Runtime.getRuntime().availableProcessors();
	}

	public AnyType[] getDataset() {
		return dataset;
	}

	public int getNumberOfThread() {
		return numberOfThread;
	}

	public void setNumberOfThread(int numberOfThread) {
		this.numberOfThread = numberOfThread;
	}

	public void setDataset(AnyType[] dataset) {
		this.dataset = dataset;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void mergeSort(AnyType[] a, int i, int j, int numberOfThread) {

		if (numberOfThread <= 1)
			mergeSort(a, i, j);

		if (j - i < 1)
			return;

		else if (j - i >= 2 && numberOfThread >= 2) {

			int pivot = (i + j) / 2;

			Thread leftThread = new Thread(new MergeSort<AnyType>(a, i, pivot, numberOfThread / 2));

			leftThread.start();

			Thread rightThread = new Thread(new MergeSort<AnyType>(a, pivot + 1, j, numberOfThread / 2));

			rightThread.start();

			try {
				leftThread.join();
				rightThread.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
			merge(a, i, pivot, j);
		}
	}

	public void mergeSort(AnyType[] a, int i, int j) {
		if (j - i < 1)
			return;

		int pivot = (i + j) / 2;

		mergeSort(a, i, pivot);
		mergeSort(a, pivot + 1, j);
		merge(a, i, pivot, j);

	}

	public void merge(AnyType a[], int leftStart, int pivot, int rightEnd) {

		int leftEnd = pivot - leftStart + 1;
		int rightStart = rightEnd - pivot;

		Object[] L = new Object[leftEnd];
		Object[] R = new Object[rightStart];

		for (int i = 0; i < leftEnd; i++)
			L[i] = a[leftStart + i];
		for (int j = 0; j < rightStart; j++)
			R[j] = a[pivot + j + 1];

		int left = 0;
		int right = 0;
		int index = leftStart;

		while (left < leftEnd && right < rightStart) {

			if (((AnyType) L[left]).compareTo((AnyType) R[right]) <= 0)
				a[index++] = (AnyType) L[left++];
			else
				a[index++] = (AnyType) R[right++];
		}
		while (left < leftEnd) {
			a[index++] = (AnyType) L[left++];
		}
		while (right < rightStart) {
			a[index++] = (AnyType) R[right++];
		}

	}

	@Override
	public void run() {
		try {
			mergeSort(getDataset(), getStart(), getEnd(), getNumberOfThread());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}