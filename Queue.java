package labb3;

public class Queue {

	String[] queue = new String[0];
	
	public String remove() {
		String headOfQueue = queue[0];
		String[] stackRemove = new String[queue.length - 1];
		for (int i = 0; i < queue.length; i++) {
			stackRemove[i] = queue[i + 1];
		}
		queue = stackRemove;
				
		return headOfQueue;
	}
	public void removeTemp(int position) {
		if (queue[queue.length-1].equals("temp")) {
			String[] queueRemove = new String[position];
			for (int a=0; a < position; a++) {
				queueRemove[a] = queue[a];
			}
			queue = queueRemove;
		}
	}
	
	public String peek() {
		if (queue.length == 0) {
			String[] newStack = new String[1];
			newStack[0] = "temp";
			queue = newStack;
		}
		return queue[queue.length-1];
	}
	
	public String[] add(String addObject) {
		String[] queueAdd = new String[queue.length + 1];
		for (int j = 0; j < queue.length; j++) {
			queueAdd[j] = queue[j];
		}
		queueAdd[queue.length] = addObject;
		queue = queueAdd;
		return queue;
	}
	
	public String[] getQueue() {
		return queue;
	}
	
	public int size() {
		return queue.length;
	}
}
