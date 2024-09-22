class TransferTransaction implements Transaction {
    private String recipientAccountNumber;
    private double amount;
 
    public TransferTransaction(String var1, double var2) {
       this.recipientAccountNumber = var1;
       this.amount = var2;
    }
 
    public String getRecipientAccountNumber() {
       return this.recipientAccountNumber;
    }
 
    public double getAmount() {
       return this.amount;
    }
 
    public String getDescription() {
       return String.format("Transfer of $%.2f to account %s", this.amount, this.recipientAccountNumber);
    }
 }
 