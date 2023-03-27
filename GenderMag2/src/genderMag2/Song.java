package genderMag2;

import java.io.*;
import java.util.*;

public class Song<E, T, K, S> {
	private E songData1;
	private T songData2;
	private K songData3;
	private S songData4;

	public Song() {

	}

	public Song(E e, T t, K k, S s) {
		songData1 = e;
		songData2 = t;
		songData3 = k;
		songData4 = s;
	}

	public E getSongData1() {
		return songData1;
	}

	public void setSongData1(E songData1) {
		this.songData1 = songData1;
	}

	public T getSongData2() {
		return songData2;
	}

	public void setSongData2(T songData2) {
		this.songData2 = songData2;
	}

	public K getSongData3() {
		return songData3;
	}

	public void setSongData3(K songData3) {
		this.songData3 = songData3;
	}

	public S getSongData4() {
		return songData4;
	}

	public void setSongData4(S songData4) {
		this.songData4 = songData4;
	}

	// prints first 3 generic data from all the songs in a linked list
	public <E, T, K, S> void displayAllSongs(LinkedList<Song<E, T, K, S>> songs) {
		songs.forEach(e -> System.out.println(e.getSongData1() + ", " + e.getSongData2() + ", " + e.getSongData3()));
	}

	// create a linked list of songs based on file input
	public LinkedList<Song<String, String, String, Integer>> readSongs(File file) {
		LinkedList<Song<String, String, String, Integer>> list = new LinkedList();
		try {
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()) {
				String[] tokens = reader.nextLine().split(",");
				list.add(new Song(tokens[0], tokens[1], tokens[2], Integer.parseInt(tokens[4])));
			}
			reader.close();
		} catch (Exception e) {
			System.out.println("No good file");
			System.out.println(e);
		}
		return list;
	}

	// create an array of songs based on file input
	// I just copied the linkedlist and replaced with arraylist because you can
	// just use the toArray() method yummy
	public Song<String, String, String, Integer>[] readSongsArray(File file) {
		ArrayList<Song<String, String, String, Integer>> list = new ArrayList();
		try {
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()) {
				String[] tokens = reader.nextLine().split(",");
				list.add(new Song(tokens[0], tokens[1], tokens[2], Integer.parseInt(tokens[4])));
			}
			reader.close();
		} catch (Exception e) {
			System.out.println("No good file");
			System.out.println(e);
		}
		
		Song<String,String,String,Integer>[] ret = new Song[list.size()];
		for(int i = 0; i<list.size();i++) {
			ret[i] = list.get(i);
		}
		
		return ret;
	}

	// search for song based on name in an array
	public Song<String, String, String, Integer> searchSongsArray(Song<String, String, String, Integer>[] list,
			String target) {
		for (Song<String, String, String, Integer> e : list) {
			if (e.getSongData1().equals(target)) {
				return e;
			}
		}
		System.out.println("song not found");
		return null;
	}

	// search for song based key then name in an linked list
	public Song<String, String, String, Integer> searchSongsLinked(
			LinkedList<Song<String, String, String, Integer>> list, Integer i, String s) {
		for (Song<String, String, String, Integer> e : list) {
			if (e.getSongData4().equals(i)) {
				return e;
			}
		}
		for (Song<String, String, String, Integer> e : list) {
			if (e.getSongData1().equals(s)) {
				return e;
			}
		}
		System.out.println("song not found");
		return null;
	}

	static class CompByNum implements Comparator<Song<String, String, String, Integer>> {
		@Override
		public int compare(Song<String, String, String, Integer> o1, Song<String, String, String, Integer> o2) {
			if (o1.getSongData4().hashCode() - o2.getSongData4().hashCode() != 0) {
				return o1.getSongData4().hashCode() - o2.getSongData4().hashCode();
			} else {
				return o1.getSongData1().hashCode() - o2.getSongData1().hashCode();
			}
		}

	}

	static class CompByName implements Comparator<Song<String, String, String, Integer>> {
		@Override
		public int compare(Song<String, String, String, Integer> o1, Song<String, String, String, Integer> o2) {
			return o1.getSongData1().hashCode() - o2.getSongData1().hashCode();
		}

	}

	public static void main(String[] args) {
		// task 12 testing more methods
		File songFile = new File("newSongs.txt");
		Song<String, String, String, Integer> driver = new Song();
		LinkedList<Song<String, String, String, Integer>> linked = driver.readSongs(songFile);
		Song<String, String, String, Integer>[] arr = driver.readSongsArray(songFile);
		linked.sort(new CompByName());
		linked.forEach(e -> System.out.println(e.getSongData1()));
		System.out.println();

		for (Song<String, String, String, Integer> e : arr) {
			System.out.println(e.getSongData1() + ", " + e.getSongData4());
		}

		// task 8, testing first set of methods
		/*
		 * Song<String, String, String, Integer> song1 = new Song("name", "artist",
		 * "album", 10); Song<String, String, String, Integer> song2 = new Song("name",
		 * "artist", "album", 10); Song<String, String, String, Integer> song3 = new
		 * Song("name", "artist", "album", 10); System.out.println(song1.getSongData1()
		 * + " " + song1.getSongData2() + " " + song1.getSongData3() + " " +
		 * song1.getSongData4()); song1.setSongData1("newName");
		 * song1.setSongData2("newName"); song1.setSongData3("newName");
		 * song1.setSongData4(12); LinkedList<Song<String, String, String, Integer>>
		 * list = new LinkedList(); list.add(song1); list.add(song2); list.add(song3);
		 * song1.displayAllSongs(list);
		 */

		/*
		 * //all this nonsense is to write a new file with appended 7 digit key //to
		 * each song Random rand = new Random(); try{ PrintWriter songWriter = new
		 * PrintWriter("newSongs.txt","UTF-8"); Scanner reader = new Scanner(new
		 * File("songs.txt")); String line; while(reader.hasNextLine()) { line =
		 * reader.nextLine();//we just copy the line to new file then append number to
		 * that line songWriter.println(line + "," + rand.nextInt(100000,9999999)); }
		 * songWriter.close();//always gotta close files after use reader.close(); }
		 * catch(Exception e) { System.out.println("e"); }
		 */
	}

}
