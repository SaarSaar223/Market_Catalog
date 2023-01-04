  
  public class ProduceBackend2 implements IProduceBackend{
    private RBTreeBack<IProduce> rbTree;
    
    public ProduceBackend2() {
      rbTree = new RBTreeBack<>();
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
      IProduce searched;
      try {
        searched = rbTree.searchPLU(PLU);
      } catch(IllegalArgumentException e) {
        return null;
      }
  
      return searched;
    }
    
    
  
    @Override
    public String removeProduce(String PLU) {
      IProduce removed;
      IProduce remo = new Produce(null, null, PLU);
      try {
        removed = rbTree.remove(remo);
      } catch(IllegalArgumentException e) {
        return "The given PLU number does not exist!";
      }
      
      return "The produce, "+ removed.getName() + " ($" + removed.getPrice() 
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
