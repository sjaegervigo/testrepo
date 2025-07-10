import java.util.InputMismatchException;
import java.util.Scanner;

class ItemNotFoundException extends Exception {
    public ItemNotFoundException(String message){
        super(message);
    }
}

public class GroceryShopping {

    public static int searchItem(String[] items, String itemName) {
        int itemIndex = -1;
        for(int i = 0; i < items.length; i++){
            if(itemName.equalsIgnoreCase(items[i])){
                itemIndex = i;
                System.out.println("Index of the item: "+ i);
                break;
            }
        }
        if(itemIndex ==-1){
            System.out.println("Item not found");            
        }
        return itemIndex;        
    }

    public static void calculateAveragePrice(Float[] prices) {
        float total = 0.0f;
        float average = 0.0f;
        for(int i = 0; i < prices.length; i++) {
            total+=prices[i];
        }
        average = total / prices.length;
        System.out.println("The average price is = $" + average);

    }

    public static void filterItemsBelowPrice(String[] items, Float[] prices, float filterPrice){
        for (int i = 0; i < prices.length; i++){
            if(prices[i]<filterPrice){
                System.out.println(items[i]);
            }
        }
    }

    public static void main(String s[]) {
        String[] items = {"Apple","Lemon","Egg","Cookie","Coke","Chicken","Bread","Jum","Sausage","Cheese","Butter","Takis"};
        Float[] prices = {1.5f, 0.25f, 0.3f, 1.2f, 1.0f, 5.59f, 2.49f, 3.0f, 0.99f, 4.1f, 3.35f, 1.89f};
        int[] stock = {50, 200, 150, 20, 20, 20, 100, 15, 30, 12, 50, 10};

        Scanner scanner = new Scanner(System.in);
        String userInput = new String();
        float totalBill = 0.0f;

        calculateAveragePrice(prices);

        System.out.println("Welcome to the Sebastian Grocery Store!!!");
        while(true){    
            
            // Exit
            System.out.println("Type cart to buy.\nType filter to fiter items bellow a price.\nType exit to leave.");
            userInput = scanner.nextLine();
            if(userInput.equalsIgnoreCase("exit")){

                System.out.println("Thank you for using the shopping cart. Goodbye!");
                break;

            } 
            
            // To filter items bellow a determined price
            if(userInput.equalsIgnoreCase("filter")){
                System.out.println("Enter the price to filter the items bellow it.");
                try{
                    userInput = scanner.nextLine();
                    float filterPrice = Float.parseFloat(userInput);
                    filterItemsBelowPrice(items, prices, filterPrice);
                } catch (NumberFormatException nfe) {
                    System.out.println("Incorrect format number, only floats allowed. Please try again.");                    
                } catch (NullPointerException npe) {
                    System.out.println("Empty string, float number needed. Please try again.");  
                } finally {
                    scanner.nextLine();
                }               
                
            }
            
            // To buy.
            if(userInput.equalsIgnoreCase("cart")){

                while(true) {
                    int itemIndex=-1;
                    System.out.println("Type the item name");
                    userInput = scanner.nextLine();
                    if (userInput.equalsIgnoreCase("finish")){
                        System.out.println("Finishing...");
                        if(totalBill > 100.0f){
                            System.out.println("Total bill exceeds $100, applying 10% discount...\nFinal bill = $"+(totalBill*0.9)+" (10% dsct.)");
                        }
                        break;
                    } else {                    
    
                        // Main functionality
                        try{                       
                    
                            itemIndex = searchItem(items, userInput);
                                                    
                            if (itemIndex == -1){
                                throw new ItemNotFoundException("Item not found exception. Please try again.");
                            } else { 
                                
                                if(stock[itemIndex]==0){
                                    System.out.println("Product unavailable, sorry for the inconvenience...");                                    
                                } else {

                                    int quantity = 0;
                                    do{
                                        
                                        System.out.println("Enter the quantity of "+items[itemIndex]+", stock = " + stock[itemIndex] + ":");
                                        quantity = scanner.nextInt();
                                        if(quantity <= 0) System.out.println("Quantity must be greater than 0.");
                                        if(quantity > stock[itemIndex]) System.out.println("Quantity requested exceeds stock.");
                                        
        
                                    } while (quantity <= 0 || quantity > stock[itemIndex]);
                                    stock[itemIndex] -= quantity;
                                    scanner.nextLine();
                                    float subtotal=prices[itemIndex] * quantity;
                                    totalBill += subtotal;
                                    System.out.println("Item "+userInput+" added "+ quantity +" units, unit price: $"+ prices[itemIndex]+" -> Subtotal = $"+subtotal+" \nTotal bill = $"+totalBill);

                                }                       
    
                            }
                            
    
                        } catch (ItemNotFoundException infe) {
                            System.out.println(infe.getMessage());
                        } catch (NumberFormatException nfe) {
                            System.out.println("Incorrect format number, only integers allowed. Please try again.");
                            scanner.nextLine();
                        } catch (InputMismatchException imme) {
                            System.out.println("Incorrect quantity given, just integers allowed. Please try again.\n" + imme.getMessage());
                            scanner.nextLine();
                        }
                        
                    }                
                }

            }

        }


    }
}