package core.nmvc.persistence;

public interface Key extends Comparable<Object>{
	
	public Object getKeyObject();
	public int getColumnIndex();
	
	default public int compareTo(Key k) {
		return 0;
	}
}
