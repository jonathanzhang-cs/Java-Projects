public class HashSeparateChaining{
    private class Node{
        Entry entry;
        Node next;

        Node(Entry entry) {this.entry = entry;}
    }

    Node[] hashTable;
    int arraySize;
    
    int tableSize;

    public HashSeparateChaining(){
        hashTable = new Node[10];
        arraySize = 10;
        tableSize = 0;
    }

    // Double the size of the array when tableSize/arraySize >= 5
    // Halve the size of the array when tableSize/arraySize <= 3
    private void resize() {
        if (tableSize/arraySize >= 5 || tableSize / arraySize <= 3){
            Node[] oldHash= new Node[tableSize];
            for (int i = 0, k = 0; i < hashTable.length; i++){
                for (Node currentNode = hashTable[i]; currentNode != null; currentNode = currentNode.next){
                    oldHash[k++] = currentNode;
                }
            } 
            if (tableSize/arraySize >= 5) arraySize *= 2;
            else arraySize /= 2;            
            hashTable = new Node[arraySize];
            int newIndex;
            for (int i = 0; i < oldHash.length; i++){
                newIndex = hash(oldHash[i].entry.getKey());
                if (hashTable[newIndex] == null) oldHash[i].next = null;
                else                             oldHash[i].next = hashTable[newIndex];
                hashTable[newIndex] = oldHash[i];
            }  
        }    
    }

    private int hash(String key) {
        int hashCode = key.hashCode();
        return (hashCode & 0x7fffffff) % arraySize;
    }

    public int size() {return tableSize;}

    public boolean isEmpty() {return tableSize == 0;}

    private Node findEntry(String key) {
        int index = hash(key);
        Node currentNode = hashTable[index];
        while (currentNode != null && !currentNode.entry.getKey().equals(key))
            currentNode = currentNode.next;
        return currentNode;
    }

    public Integer get(String key) {
        Node searchResult = findEntry(key);
        if (searchResult != null) return searchResult.entry.getValue();
        else return null;
    }

    public void put(String key, Integer value) {
        Node searchResult = findEntry(key);
        if (searchResult != null){
            searchResult.entry.setValue(value);
            return;
        }
        Entry newEntry = new Entry(key, value);
        Node newNode = new Node(newEntry);
        int index = hash(key);
        if (hashTable[index] != null) newNode.next = hashTable[index];
        hashTable[index] = newNode;
        tableSize++;
        resize();
    }

    public void delete(String key) {
        Node searchResult = findEntry(key);
        if (searchResult == null) return;
        int index = hash(key);
        if (hashTable[index] == searchResult) hashTable[index] = searchResult.next;
        else{
            Node currentNode = hashTable[index];
            while (currentNode.next != searchResult) currentNode = currentNode.next;
            currentNode.next = searchResult.next;
        }
        tableSize--;
        resize();
    }

    @Override
    public String toString(){
        String output = "";
        for (int i = 0; i < arraySize; i++){
            output += "\n (" + i + "): ";
            Node currentNode = hashTable[i];
            if (currentNode == null) output += currentNode + "\n";
            else{
                while (currentNode != null){
                    output += " -> " + currentNode.entry;
                    currentNode = currentNode.next;
                }
                output += "\n";
            }
        }
        return output;
    }
}
