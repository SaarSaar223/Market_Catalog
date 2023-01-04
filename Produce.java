public class Produce implements Comparable<IProduce>, IProduce{
  
  private String price;
  private String name;
  private String PLU;
  
  public Produce(String name, String price, String PLU) {
    this.price = price;
    this.name = name;
    this.PLU = PLU;
  }
  
  @Override
  public String getPrice() {
    // TODO Auto-generated method stub
    return price;
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return name;
  }

  @Override
  public String getPLU() {
    // TODO Auto-generated method stub
    return PLU;
  }

  @Override
  public int compareTo(IProduce produce) {
    if(Integer.parseInt(PLU) < Integer.parseInt(produce.getPLU())) {
      return 1;
    }
    else if (Integer.parseInt(PLU) > Integer.parseInt(produce.getPLU())) {
      return -1;
    }
    return 0;
  }
}
