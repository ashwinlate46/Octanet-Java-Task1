import java.util.HashMap;
import java.util.Scanner;

class ATM {
   private BankAccount bankAccount;
   private HashMap<String, BankAccount> accountMap;

   public ATM(BankAccount var1, HashMap<String, BankAccount> var2) {
      this.bankAccount = var1;
      this.accountMap = var2;
   }

   public void displayMenu() {
      System.out.println("ATM Menu:");
      System.out.println("1. Transaction History");
      System.out.println("2. Transfer");
      System.out.println("3. Withdraw");
      System.out.println("4. Deposit");
      System.out.println("5. Check Balance");
      System.out.println("6. Exit");
   }

   public void run() {
      Scanner var1 = new Scanner(System.in);

      try {
         BankAccount var2 = null;

         while(true) {
            while(true) {
               this.displayMenu();
               System.out.print("Enter your choice: ");
               int var3 = var1.nextInt();
               double var5;
               boolean var7;
               switch (var3) {
                  case 1:
                     this.bankAccount.printTransactionHistory();
                     break;
                  case 2:
                     System.out.print("Enter the recipient's account number: ");
                     String var4 = var1.next();
                     var2 = (BankAccount)this.accountMap.get(var4);
                     if (var2 == null) {
                        System.out.println("Invalid recipient account number.");
                     } else {
                        System.out.print("Enter the transfer amount: $");
                        var5 = var1.nextDouble();
                        var7 = this.bankAccount.transfer(var2, var5);
                        if (var7) {
                           System.out.println("Transfer successful.");
                        }
                     }
                     break;
                  case 3:
                     System.out.print("Enter the withdrawal amount: $");
                     var5 = var1.nextDouble();
                     var7 = this.bankAccount.withdraw(var5);
                     if (var7) {
                        System.out.println("Withdrawal successful.");
                     }
                     break;
                  case 4:
                     System.out.print("Enter the deposit amount: $");
                     double var8 = var1.nextDouble();
                     this.bankAccount.deposit(var8);
                     break;
                  case 5:
                     System.out.println("Current balance: $" + this.bankAccount.getBalance());
                     break;
                  case 6:
                     System.out.println("Thank you for using the ATM. Goodbye!");
                     System.exit(0);
                     break;
                  default:
                     System.out.println("Invalid choice. Please try again.");
               }
            }
         }
      } catch (Throwable var11) {
         try {
            var1.close();
         } catch (Throwable var10) {
            var11.addSuppressed(var10);
         }

         throw var11;
      }
   }
}
