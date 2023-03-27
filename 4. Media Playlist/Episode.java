class Episode
{
    private String title;
    private double length;

    public Episode next;
    public Episode prev;

    public Episode(String title, double length, Episode next, Episode prev)
    {
        this.title = title;
        
        this.length = length;
        this.next = next;
        this.prev = prev;
    }

    public String getTitle()
    {
        return this.title;
    }

    public double getLength()
    {
        return this.length;
    }

    public String toString()
    {
        return "("+ this.title + "|" + this.length + "MIN)" ;
    }
 }
