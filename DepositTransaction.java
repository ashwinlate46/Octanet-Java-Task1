class DepositTransaction implements Transaction {
    private double amount;
 
    public DepositTransaction(double var1) {
       this.amount = var1;
    }
 
    public double getAmount() {
       return this.amount;
    }
 
    public String getDescription() {
       return String.format("Deposit of $%.2f", this.amount);
    }
 }
 