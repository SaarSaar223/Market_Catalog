public interface IProduce extends Comparable<IProduce>{
    /**
     * Returns a string that contains the price of the produce
     * @return price of the produce
     */
    public String getPrice();
    /**
     * Returns the name of the produce.
     * @return the name of the produce.
     */
    public String getName();
    /**
     * Returns the 4 digit, non-negative PLU number
     * @return PLU number of the produce
     */
    public String getPLU();
}
