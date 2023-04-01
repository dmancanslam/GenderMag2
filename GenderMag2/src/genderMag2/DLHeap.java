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

public class DLHeap<E> implements DLQueue<E> {
	
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
	
	public void heapSort(ArrayList<Song<String, String, String, String>> songArray) {
		// Create a Heap of integers
		DLHeap<Song<String, String, String, String>> heap = new DLHeap<>();

		// Add elements to the heap
		for (int i = 0; i < songArray.size(); i++)
			heap.add(songArray.get(i));

		// Remove elements from the heap
		for (int i = songArray.size()-1; i >= 0; i--){
			songArray.add(i,heap.remove()); 
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
		DLHeap<Song<String, String, String, String>> heap = new DLHeap(new MinMaxheap());
		ArrayList<Song<String, String, String, String>> songArray = new ArrayList();	
		
		try {
			Scanner reader = new Scanner(songFile);
			while (reader.hasNextLine()) {
				String[] tokens = reader.nextLine().split(",");
				songArray.add(new Song<String, String, String, String>(tokens[0],tokens[1],tokens[2], tokens[3]));
			}
			reader.close();												
		} catch (Exception e) {
			System.out.println("No good file");
			System.out.println(e);
		}

		for (int i=0; i < 21; i++) {
			heap.add(songArray.get(i));//task 7
		}
		heap.printHeap(); //task 7.5
	
		
		heap.remove(); //task 8
		heap.remove(); //task 8
		heap.remove(); //task 8
		heap.remove(); //task 8
		heap.remove(); //task 8
		heap.printHeap(); //task 8.5
		
		heap.heapSort(songArray); //task 11
		for(Song<String,String,String,String> e:songArray) {
			System.out.println(e.getSongData1()); // task 10
		}
		
		
		

	}
}
