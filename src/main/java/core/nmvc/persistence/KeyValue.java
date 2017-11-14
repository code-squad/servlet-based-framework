package core.nmvc.persistence;

public interface KeyValue extends Comparable<Object>{
	
	public Object getKeyObject();
	public int getColumnIndex();
	
	default public int compareTo(KeyValue k) {
		return 0;
	}
}
