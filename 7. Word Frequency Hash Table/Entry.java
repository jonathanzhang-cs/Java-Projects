public class Entry {
	private String key;
	private Integer value;

	public Entry(String key, Integer value){
		this.key = key;
		this.value = value;
	}

	public String getKey() {return key;}

	public int getValue() {return value;}

	public void setValue(int value) {this.value = value;}

	@Override
	public String toString(){
		return "[" + key + " : " + value + "]";
	}
}
