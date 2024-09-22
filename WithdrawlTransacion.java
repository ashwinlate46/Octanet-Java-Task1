class WithdrawalTransaction implements Transaction {
    private double amount;
 
    public WithdrawalTransaction(double var1) {
       this.amount = var1;
    }
 
    public double getAmount() {
       return this.amount;
    }
 
    public String getDescription() {
       return String.format("Withdrawal of $%.2f", this.amount);
    }
 }
 