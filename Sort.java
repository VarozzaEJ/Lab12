import java.util.Comparator;

/**
 * Class for sorting lists that implement the IndexedUnsortedList interface,
 * using ordering defined by class of objects in list or a Comparator.
 * As written uses Quicksort algorithm.
 *
 * @author CPSC 221 Instructors
 */
public class Sort {	
	/**
	 * Returns a new list that implements the IndexedUnsortedList interface. 
	 * As configured, uses WrappedDLL. Must be changed if using 
	 * your own IUDoubleLinkedList class. 
	 * 
	 * @return a new list that implements the IndexedUnsortedList interface
	 */
	private static <E> IndexedUnsortedList<E> newList() {
		return new WrappedDLL<E>();
	}
	
	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <E>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @see IndexedUnsortedList 
	 */
	public static <E extends Comparable<E>> void sort(IndexedUnsortedList<E> list) {
		quicksort(list);
	}

	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using given Comparator.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <E>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 * @see IndexedUnsortedList 
	 */
	public static <E> void sort(IndexedUnsortedList <E> list, Comparator<E> c) {
		quicksort(list, c);
	}
	
	/**
	 * Quicksort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface, 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <E>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 */
	private static <E extends Comparable<E>> void quicksort(IndexedUnsortedList<E> list) {
		// Wrapper to call quicksort with natural ordering comparator.
		if (list == null || list.size() <= 1) {
			return;
		}

		// Define natural ordering comparator using compareTo method.
		Comparator<E> naturalOrder = new Comparator<E>() {
			@Override
			public int compare(E a, E b) {
				return a.compareTo(b);
			}
		};
		
		quicksort(list, naturalOrder);
	}
		
	/**
	 * Quicksort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface,
	 * using the given Comparator.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <E>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 */
	private static <E> void quicksort(IndexedUnsortedList<E> list, Comparator<E> c) {
		// Recursive, list-based quicksort that avoids indexed operations.
		if (list == null || c == null || list.size() <= 1) {
			return; // base case: empty or single-element list is already sorted
		}
		
		// Choose pivot (here, first element).
		E pivot = list.removeFirst();
		
		// Partition into less-than-or-equal and greater-than lists.
		IndexedUnsortedList<E> less = newList();
		IndexedUnsortedList<E> greater = newList();
		
		// Distribute elements into the two partitions.
		for (E element : list) {
			if (c.compare(element, pivot) <= 0) {
				less.add(element);
			} else {
				greater.add(element);
			}
		}
		
		// Clear the original list (it currently still contains all elements except the pivot).
		while (!list.isEmpty()) {
			list.removeFirst();
		}
		
		// Recursively sort the partitions.
		quicksort(less, c);
		quicksort(greater, c);
		
		// Reassemble: [sorted less] + pivot + [sorted greater]
		for (E element : less) {
			list.add(element);
		}
		list.add(pivot);
		for (E element : greater) {
			list.add(element);
		}
	}
	
}
