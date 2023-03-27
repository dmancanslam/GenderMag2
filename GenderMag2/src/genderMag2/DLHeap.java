package genderMag2;

import java.io.*;
import java.util.*;

interface DLQueue <E> {
	public void addSong(E songName);
	public void deleteAll();
	public boolean isEmpty();
	public E peekSong();
	public E pushSong(E pushee);
	public void removeSong(E songName);
	public int size();
}

public class DLHeap<E> extends Song<String, String, String, Integer> implements DLQueue<E> {
	
	static class MinMaxheap<E> implements Comparator<E> {
		@Override
		public int compare(E o1, E o2) {
			if (o1.hashCode() > o2.hashCode()) { // > is a max heap, < is a min heap
				return 1;
			}
			else if (o1.hashCode() == o2.hashCode()) {
				return 0;
			}
			else
				return -1;
		}
	}
	
	private java.util.ArrayList<E> list = new java.util.ArrayList<>();
	private java.util.Comparator<? super E> c;

	public DLHeap() {
		this.c = (e1, e2) -> ((Comparable<E>)e1).compareTo(e2);
	}
	
	/** Create a heap with a specified comparator */
	public DLHeap(java.util.Comparator<E> c) {
		this.c = c;
	}

	/** Create a heap from an array of objects */
	public DLHeap(E[] objects) {
		this.c = (e1, e2) -> ((Comparable<E>)e1).compareTo(e2);
		for (int i = 0; i < objects.length; i++)
			add(objects[i]);
	}

	/** Add a new object into the heap */
	public void add(E newObject) {
		list.add(newObject); // Append to the heap
		int currentIndex = list.size() - 1; // The index of the last node

		while (currentIndex > 0) {
			int parentIndex = (currentIndex - 1) / 2;
			// Swap if the current object is greater than its parent
			if (c.compare(list.get(currentIndex),
					list.get(parentIndex)) > 0) {
				E temp = list.get(currentIndex);
				list.set(currentIndex, list.get(parentIndex));
				list.set(parentIndex, temp);
			}
			else
				break; // the tree is a heap now

			currentIndex = parentIndex;
		}
	}

	/** Remove the root from the heap */
	public E remove() {
		if (list.size() == 0) return null;

		E removedObject = list.get(0);
		list.set(0, list.get(list.size() - 1));
		list.remove(list.size() - 1);

		int currentIndex = 0;
		while (currentIndex < list.size()) {
			int leftChildIndex = 2 * currentIndex + 1;
			int rightChildIndex = 2 * currentIndex + 2;

			// Find the maximum between two children
			if (leftChildIndex >= list.size()) break; // The tree is a heap
			int maxIndex = leftChildIndex;
			if (rightChildIndex < list.size()) {
				if (c.compare(list.get(maxIndex),
						list.get(rightChildIndex)) < 0) {
					maxIndex = rightChildIndex;
				}
			}

			// Swap if the current node is less than the maximum
			if (c.compare(list.get(currentIndex), 
					list.get(maxIndex)) < 0) {
				E temp = list.get(maxIndex);
				list.set(maxIndex, list.get(currentIndex));
				list.set(currentIndex, temp);
				currentIndex = maxIndex;
			}
			else
				break; // The tree is a heap
		}

		return removedObject;
	}

	/** Get the number of nodes in the tree */
	public int getSize() {
		return list.size();
	}
	public void printHeap() {
		System.out.println(this.list);
	}

	
	public static <E extends Comparable<E>> void heapSort(E[] list) {
		// Create a Heap of integers
		DLHeap<E> heap = new DLHeap<>();

		// Add elements to the heap
		for (int i = 0; i < list.length; i++)
			heap.add(list[i]);

		// Remove elements from the heap
		for (int i = list.length-1; i >= 0; i--){
			list[i] = heap.remove();
		}
	}
	
	public static void printSortedHeap(ArrayList<Integer> list) {
		Integer[] array = list.toArray(new Integer[0]);
		heapSort(array);
		for(int i=0;i<6;i++){											// for loop to print elements in a heap format
	        for(int j=0;j<Math.pow(2,i)&&j+Math.pow(2,i)<array.length;j++){		
	            System.out.print(array[j+(int)Math.pow(2,i)-1]+" ");
	        }
	        System.out.println();
	    }
	}
	

	@Override
	public void addSong(E songName) {
		this.add(songName);
	}

	@Override
	public void deleteAll() {
		while (this.isEmpty() == false) {
			this.remove();
		}
		
	}

	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

	@Override
	public E peekSong() {
		return list.get(0);
	}

	@Override
	public E pushSong(E pushee) {
		this.add(pushee);
		return pushee;
	}

	@Override
	public void removeSong(E songName) {
		for (int i = 0; i < this.size(); i++) {
			if (this.list == songName) {
				this.remove();
			}
		}
	}

	@Override
	public int size() {
		return this.size();
	}

	public static void main(String[] args) {
		File songFile = new File("newSongs.txt");
		DLHeap<String> heap = new DLHeap<String>(new MinMaxheap<String>());
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<Integer> intList = new ArrayList<Integer>();
		try {
			Scanner reader = new Scanner(songFile);
			while (reader.hasNextLine()) {
				String[] tokens = reader.nextLine().split(",");
				list.add(tokens[0]);
				intList.add(Integer.parseInt(tokens[3].replace(":", ""))); // here we are taking the length of the song and
			}															// changing it to an integer that can be sorted
			reader.close();												
		} catch (Exception e) {
			System.out.println("No good file");
			System.out.println(e);
		}

		for (int i=0; i < 21; i++) {
			heap.add(list.get(i));//task 7
		}
		heap.printHeap(); //task 7.5
		
		heap.remove(); //task 8
		heap.remove(); //task 8
		heap.remove(); //task 8
		heap.remove(); //task 8
		heap.remove(); //task 8
		heap.printHeap(); //task 8.5
		
		printSortedHeap(intList); //task 10

	}
}
