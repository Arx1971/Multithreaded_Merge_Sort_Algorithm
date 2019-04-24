package generic_merge_sort_using_multithread;

public class MergeSort<AnyType extends Comparable<? super AnyType>> implements Runnable {

	private int start;
	private int end;
	private AnyType[] dataset;
	private int numberOfThread;

	public MergeSort(AnyType[] dataset, int start, int end, int numberOfThread) {
		this.setDataset(dataset);
		this.setStart(start);
		this.setEnd(end);
		this.setnumberOfThread(numberOfThread);
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

	public int getnumberOfThread() {
		return numberOfThread;
	}

	public void setnumberOfThread(int numberOfThread) {
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

	public void mergesort(AnyType[] a, int i, int j, int numberOfThread) {

		if (numberOfThread <= 1)
			mergesort(a, i, j);

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

	public void mergesort(AnyType[] a, int i, int j) {
		if (j - i < 1)
			return;

		int pivot = (i + j) / 2;

		mergesort(a, i, pivot);
		mergesort(a, pivot + 1, j);
		merge(a, i, pivot, j);

	}

	@SuppressWarnings("unchecked")

	public void merge(AnyType a[], int leftstart, int pivot, int rightend) {

		int leftend = pivot - leftstart + 1;
		int rightstart = rightend - pivot;

		Object[] L = new Object[leftend];
		Object[] R = new Object[rightstart];

		for (int i = 0; i < leftend; i++)
			L[i] = a[leftstart + i];
		for (int j = 0; j < rightstart; j++)
			R[j] = a[pivot + j + 1];

		int left = 0;
		int right = 0;
		int index = leftstart;

		while (left < leftend && right < rightstart) {

			if (((AnyType) L[left]).compareTo((AnyType) R[right]) <= 0)
				a[index++] = (AnyType) L[left++];
			else
				a[index++] = (AnyType) R[right++];
		}
		while (left < leftend) {
			a[index++] = (AnyType) L[left++];
		}
		while (right < rightstart) {
			a[index++] = (AnyType) R[right++];
		}

	}

	@Override
	public void run() {
		try {
			mergesort(getDataset(), getStart(), getEnd(), getnumberOfThread());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}