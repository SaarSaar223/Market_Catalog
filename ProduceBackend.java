  
  public class ProduceBackend implements IProduceBackend{
    private RedBlackTree<IProduce> rbTree;
    
    public ProduceBackend() {
      rbTree = new RedBlackTree<>();
    }
    
    
    @Override
    public boolean addProduce(IProduce produce) {
      if(validateProduce(produce.getPLU())) {
        rbTree.insert(produce);
        return true;
      }
      else {
        return false;
      }
    }
  
    @Override
    public IProduce searchProduce(String PLU) {
      IProduce searched = null;
	IProduce temp = new Produce(null, null, PLU);

	if (PLU == null) {
        return null;
      }
      try {
        searched = rbTree.find(temp);
      } catch(IllegalArgumentException e) {
        return null;
      }
  
      return searched;
    }
    
    
  
    @Override
    public String removeProduce(String PLU) {
      IProduce removed;
      IProduce remo = new Produce(null, null, PLU);
	String price = null;
      String name = null;
	if(!rbTree.contains(remo)) {
        return "The given PLU number does not exist!";
      }
      try {
	IProduce temp = rbTree.find(remo);
        name = temp.getName();
        price = temp.getPrice();
        removed = rbTree.remove(remo);
      } catch(IllegalArgumentException e) {
        return "The given PLU number does not exist!";
      }
      
      return "The produce, "+ name + " ($" + price 
      + "), with the PLU number: " + PLU + " has been removed.";
    }
    
    public boolean validateProduce(String PLU) {
      PLUValidator validator = new PLUValidator();
      return validator.validate(PLU);
    }
    
    public int getSize() {
      return rbTree.size();
    }
  
  }
