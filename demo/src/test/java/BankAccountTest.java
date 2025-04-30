import com.example.BankAccount;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class BankAccountTest {
    private BankAccount account;
    
    @BeforeClass
    public void setUp() {
        System.out.println("Initializing bank account tests");
    }
    
    @BeforeMethod
    public void prepareTest() {
        account = new BankAccount(1000.0);
    }
    
    @Test(groups = {"basic", "positive-tests"})
    public void testInitialBalance() {
        Assert.assertEquals(account.getBalance(), 1000.0, "The initial balance must be 1000");
    }
    
    @Test(groups = {"deposit", "positive-tests"})
    public void testDeposit() {
        account.deposit(500.0);
        Assert.assertEquals(account.getBalance(), 1500.0, "The balance after deposit should be 1500");
    }
    
    @Test(groups = {"withdraw", "positive-tests"})
    public void testWithdraw() {
        account.withdraw(300.0);
        Assert.assertEquals(account.getBalance(), 700.0, "The balance after withdrawal must be 700");
    }
    
    @Test(groups = {"exceptions", "negative-tests"}, expectedExceptions = IllegalArgumentException.class)
    public void testNegativeDeposit() {
        account.deposit(-100.0);
    }
    
    @Test(groups = {"exceptions", "negative-tests"}, expectedExceptions = IllegalArgumentException.class)
    public void testNegativeWithdraw() {
        account.withdraw(-50.0);
    }
    
    @Test(groups = {"exceptions", "negative-tests"}, expectedExceptions = IllegalArgumentException.class)
    public void testInsufficientFunds() {
        account.withdraw(1500.0);
    }
    
    @Test(dependsOnMethods = {"testDeposit", "testWithdraw"}, groups = {"complex"})
    public void testMultipleOperations() {
        account.deposit(200.0);
        account.withdraw(150.0);
        Assert.assertEquals(account.getBalance(), 1050.0, "Final balance should be 1050 after multiple operations");
    }

    @Test(groups = {"transfer", "positive-tests"})
    public void testTransfer() {
        BankAccount sourceAccount = new BankAccount(1000.0);
        BankAccount destinationAccount = new BankAccount(500.0);
        
        sourceAccount.transfer(destinationAccount, 300.0);
        
        Assert.assertEquals(sourceAccount.getBalance(), 700.0, "Source account balance should be 700");
        Assert.assertEquals(destinationAccount.getBalance(), 800.0, "Destination account balance should be 800");
    }

    @Test(groups = {"transfer", "negative-tests"}, expectedExceptions = IllegalArgumentException.class)
    public void testNegativeTransfer() {
        BankAccount sourceAccount = new BankAccount(1000.0);
        BankAccount destinationAccount = new BankAccount(500.0);
        
        sourceAccount.transfer(destinationAccount, -100.0);
    }

    @Test(groups = {"transfer", "negative-tests"}, expectedExceptions = IllegalArgumentException.class)
    public void testInsufficientFundsTransfer() {
        BankAccount sourceAccount = new BankAccount(1000.0);
        BankAccount destinationAccount = new BankAccount(500.0);
        
        sourceAccount.transfer(destinationAccount, 1500.0);
    }
    
    @DataProvider(name = "depositData")
    public Object[][] createDepositData() {
        return new Object[][] {
            {100.0, 1100.0},
            {500.0, 1500.0},
            {1000.0, 2000.0}
        };
    }
    
    @Test(dataProvider = "depositData", groups = {"parameterized"})
    public void testDepositWithParameters(double depositAmount, double expectedBalance) {
        account.deposit(depositAmount);
        Assert.assertEquals(account.getBalance(), expectedBalance, 
                "The balance after deposit must be " + expectedBalance);
    }
    
    @Parameters({"withdrawAmount"})
    @Test(groups = {"parameterized"})
    public void testWithdrawWithParameters(@Optional("200.0") double withdrawAmount) {
        account.withdraw(withdrawAmount);
        Assert.assertEquals(account.getBalance(), 1000.0 - withdrawAmount, 
                "The balance after withdrawal must be " + (1000.0 - withdrawAmount));
    }
    
    @AfterMethod
    public void cleanUpTest() {
        System.out.println("Test completed with final balance: " + account.getBalance());
    }
    
    @AfterClass
    public void tearDown() {
        System.out.println("Finalizing bank account tests");
    }
}
