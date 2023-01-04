/**
 * Instances of this interface implement the search, remove, and add functionality
 * of the Main Produce application using red black tree.
 */
public interface IProduceBackend {
    /**
     * Adds a new produce to the backend's database and is stored in
     * a red black tree internally.
     *
     * @param produce the produce to add
     * @return true if the produce has been added successfully, otherwise false
     */
    public boolean addProduce(IProduce produce);

    /**
     * Search through all the produce and return the produce whose PLU number matches the input
     *
     * @param PLU PLU of produce to be searched
     * @return Produce details otherwise null
     */
    public IProduce searchProduce(String PLU);

    /**
     * Removes the produce with given PLU if it exists in the red black tree
     * @param PLU PLU of produce to be removed
     * @return PLU of removed produce otherwise null
     */
    public String removeProduce(String PLU);
}
