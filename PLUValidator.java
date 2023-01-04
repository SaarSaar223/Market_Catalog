public class PLUValidator implements IPLUValidator {
  @Override
  public boolean validate(String code) {
    try { return code.length() == 4 && !code.startsWith("0") && Integer.parseInt(code) > 0; }
    catch(NumberFormatException | NullPointerException e) { return false; }
  }
}
