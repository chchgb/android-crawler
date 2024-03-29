package it.unina.androidripper.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.nofatclips.androidtesting.model.Transition;

public class Plan implements Iterable<Transition> {

	private List<Transition> tasks = new ArrayList<Transition> ();

	public Iterator<Transition> iterator() {
		return this.tasks.iterator();
	}
	
	public boolean addTask (Transition t) {
		return this.tasks.add(t);
	}
	
	public int size () {
		return this.tasks.size();
	}
	
	public Transition getTask (int task) {
		return this.tasks.get(task);
	}
	
	public void removeTask (int task) {
		this.tasks.remove(task);
	}
	
	public boolean isEmpty () {
		return (size()==0);
	}
	
}
