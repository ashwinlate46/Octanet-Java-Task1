import java.util.ArrayList;
import java.util.List;

class BankAccount {
   private double balance;
   private int pin;
   private String accountNumber;
   private List<Transaction> transactionHistory;

   public BankAccount(double var1, int var3, String var4) {
      this.balance = var1;
      this.pin = var3;
      this.accountNumber = var4;
      this.transactionHistory = new ArrayList();
   }

   public double getBalance() {
      return this.balance;
   }

   public boolean validateCredentials(String var1, int var2) {
      return this.accountNumber.equals(var1) && this.pin == var2;
   }

   public String getAccountNumber() {
      return this.accountNumber;
   }

   public void deposit(double var1) {
      if (var1 > 0.0) {
         this.balance += var1;
         this.addTransaction(new DepositTransaction(var1));
         System.out.println("Deposit successful. New balance: $" + this.balance);
      } else {
         System.out.println("Invalid deposit amount.");
      }

   }

   public boolean withdraw(double var1) {
      if (var1 > 0.0) {
         if (var1 <= this.balance) {
            this.balance -= var1;
            this.addTransaction(new WithdrawalTransaction(var1));
            System.out.println("Withdrawal successful. New balance: $" + this.balance);
            return true;
         }

         System.out.println("Insufficient funds for withdrawal. Current balance: $" + this.balance);
      } else {
         System.out.println("Invalid withdrawal amount. Please enter a positive amount.");
      }

      return false;
   }

   public boolean transfer(BankAccount var1, double var2) {
      if (var2 > 0.0) {
         if (var2 <= this.balance) {
            this.balance -= var2;
            var1.balance += var2;
            this.addTransaction(new TransferTransaction(var1.getAccountNumber(), var2));
            System.out.println("Transfer successful. New balance: $" + this.balance);
            return true;
         }

         System.out.println("Insufficient funds for transfer. Current balance: $" + this.balance);
      } else {
         System.out.println("Invalid transfer amount. Please enter a positive amount.");
      }

      return false;
   }

   public void addTransaction(Transaction var1) {
      this.transactionHistory.add(var1);
   }

   public void printTransactionHistory() {
      System.out.println("\nTransaction history:");

      for(int var1 = 0; var1 < this.transactionHistory.size(); ++var1) {
         Transaction var2 = (Transaction)this.transactionHistory.get(var1);
         System.out.println(var1 + 1 + ". " + var2.getDescription());
      }

   }
}
