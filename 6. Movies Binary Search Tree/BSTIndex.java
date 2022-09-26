public class BSTIndex {
    // one movie node
    private class Node {
        private String key;
        private MovieInfo data;
        private Node left, right;

        public Node(MovieInfo info) {
            data = info;
            key = data.shortTitle;
        }
    }

    private Node root;
    
    public BSTIndex() {
        root = null;
    }

    public int calcHeight(){
        return calcNodeHeight(root);
    }

    public void insertMovie(MovieInfo data) {
        root = insertMovie(data, root);
    }

    public MovieInfo findMovie(String key) {
        return findMovie(root, key);
    }

    public void printMoviesPrefix(String prefix) {
        printMoviesPrefix(root, prefix);
    }

    private int calcNodeHeight(Node t) {
        if (t == null){
            return 0;
        }
        else{
            int leftHeight = calcNodeHeight(t.left);
            int rightHeight = calcNodeHeight(t.right);
            if (leftHeight > rightHeight) return leftHeight + 1;
            else                          return rightHeight + 1;
        }
    }

    private Node insertMovie(MovieInfo data, Node t) {
        if (t == null){
            t = new Node(data);
        }
        else{
            if (data.shortTitle.compareTo(t.key) > 0){
                t.right = insertMovie(data, t.right);
            }
            else if (data.shortTitle.compareTo(t.key) < 0){
                t.left = insertMovie(data, t.left);
            }
            else return t;
        }
        return t;
    }

    private MovieInfo findMovie(Node t, String key) {
        MovieInfo found;
        if (t == null) found = null;
        else{
            found = t.data;
            if (key.compareTo(t.key) > 0){
                found = findMovie(t.right, key);
            }
            else if (key.compareTo(t.key) < 0){
                found = findMovie(t.left, key);
            }
        }
        return found;
    }

    private void printMoviesPrefix(Node t, String prefix) {
        if (t == null) return;
        printMoviesPrefix(t.left, prefix);
        if (t.key.length() >= prefix.length() && t.key.substring(0, prefix.length()).equals(prefix)){
                System.out.println(t.key);
        }
        printMoviesPrefix(t.right, prefix);
    }
}
