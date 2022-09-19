public class Playlist
{
    private Episode head;
    private int size;
    
    public Playlist(){
        head = null;
        size = 0;
    }
    
    public boolean isEmpty(){
        return head == null;
    }

    public int getSize(){
        return this.size;
    }

    // Example Output: [BEGIN] (PlanetMoney|26.0MIN) -> (TodayExplained|10.0MIN) -> (RadioLab|25.5MIN) [END]
    public void displayPlaylistForward()
    {
        String output = "[BEGIN] ";
        Episode current = head;
        if(current != null){
            while(current.next != head){
                output += current + " -> ";
                current = current.next;
            }
            output += current + " [END]\n";
        }
        else{
            output += " [END]\n";
        }
        System.out.println(output);
    }

    public void displayPlaylistBackward()
    {
        String output = " [BEGIN]\n";
        Episode current = this.head;
        if (current != null){
            for (current = head; current.next != head; current = current.next){
                output = " -> " + current + output;
            }
            output = "[END] " + current + output;
        }
        else{
            output = "[END] " + output;
        }
        System.out.println(output);
    }

    public void addFirst( String title, double length )
    {
        if (size > 0){
            Episode newFirst = new Episode(title, length, head, head.prev), last;
            
            for (last = head; last.next != head; last = last.next);
            last.next = head.prev = newFirst;
            head = newFirst;
        }
        else{
            head = new Episode(title, length, null, null);
            head.next = head;
            head.prev = head;
        }
        size++;
    }

    public void addLast( String title, double length )
    {
        if (size > 0){
            Episode oldLast;
            
            for (oldLast = head; oldLast.next != head; oldLast = oldLast.next);
            Episode newLast = new Episode(title, length, head, oldLast);
            oldLast.next = head.prev = newLast;
        }
        else{
            head = new Episode(title, length, null, null);
            head.next = head;
            head.prev = head;
        }
        size++;
    }

    public void add( String title, double length, int index )
    {
        if (index < 0){
            throw new IndexOutOfBoundsException("[Error] Input index is invalid: Less than 0!");
        }
        else if (index > size){
            throw new IndexOutOfBoundsException("[Error] Input index is invalid: Larger of the scope of the Playlist!");
        }
        else{
            if (index == 0){
                addFirst(title, length);
            }
            else if (index == size){
                addLast(title, length);
            }
            else{
                Episode current = head, previous = head.prev, newEpisode;
                for (int i = 0; i < index; i++){
                    current = current.next;
                    previous = previous.next;
                }
                newEpisode = new Episode(title, length, current, previous);
                previous.next = current.prev = newEpisode;
                size++;
            }
        }
    }
    
    public Episode deleteFirst()
    {
        Episode oldFirst = head;
        if (size > 1){
            Episode last;
            
            for (last = head; last.next != head; last = last.next);
            head = head.next;
            head.prev = last;
            last.next = head;
        }
        else if (size == 1){
            head = null;
        }
        else{
            throw new RuntimeException("[Error] Cannot delete episode from an empty Playlist!");
        }
        size--;
        return oldFirst;
    }

    public Episode deleteLast()
    {
        Episode oldLast = head;        
        if (size > 1){
            for (oldLast = head; oldLast.next != head; oldLast = oldLast.next);
            oldLast.prev.next = head;
            head.prev = oldLast.prev;
        }
        else if (size == 1){
            head = null;
        }
        else{
            throw new RuntimeException("[Error] Cannot delete episode from an empty Playlist!");
        }
        size--;
        return oldLast;
    }

    public Episode deleteEpisode(String title)
    {
        if (size >= 1){
            Episode output, last;
            
            for (last = head; last.next != head; last = last.next);
            if (head.getTitle().equals(title)){
                output = deleteFirst();
            }
            else if (last.getTitle().equals(title)){
                output = deleteLast();
            }
            else{
                Episode previous = head.prev, current = head;
                for (current = head; current.next != head; current = current.next){
                    if (current.getTitle().equals(title)){
                        previous.next = current.next;
                        current.next.prev = previous;
                        size--;
                        return current;
                    }
                    previous = previous.next;
                }
                throw new IllegalArgumentException("[Error] Input Episode is not in the Playlist!");
            }
            return output;
        }
        else{
            throw new RuntimeException("[Error] Cannot delete episode from an empty Playlist!");
        }
    }
    
    // Removes every m-th Episode in the Playlist, until only one Episode exists. The last episode is returned. 
    public Episode deleteEveryMthEpisode(int m)
    {
        if (m >= 1){
            if (size >= 1){
                Episode current = head.prev;
                while (size > 1){
                    for (int i = 0; i < m; i++){
                        current = current.next;
                    }
                    deleteEpisode(current.getTitle());
                }
                return head;
            }
            else{
                throw new RuntimeException("[Error] Cannot delete any episode from an empty Playlist!");
            }
        }
        else{
            throw new IllegalArgumentException("[Error] Input m value has to be greater or equal to 1!");
        }
    }
}
