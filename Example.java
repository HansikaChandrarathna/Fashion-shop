import java.util.*;

public class Example {
	
	
	// Clears the console screen.
	public final static void clearConsole() {
		try {
		final String os = System.getProperty("os.name");
		if (os.contains("Windows")) {
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} else {
		System.out.print("\033[H\033[2J");
		System.out.flush();
		}
		} catch (final Exception e) {
		e.printStackTrace();
		// Handle any exceptions.
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	 // It calls the homepage() method to start the user interface.
	public static void main(String[] args) {
        homepage();
		
    }
    
    
    // Checks if the provided option in homepage is valid.
     public static boolean isOption(int option) {
		int[] optionList = {1, 2, 3, 4, 5, 6};
		for (int i = 0; i < optionList.length; i++) {
			if (optionList[i] == option) {
				return true;
			}
		}
		return false;
	}
		
    
    public static void homepage(){
		Scanner input = new Scanner(System.in);
		System.out.print("\r\n       /$$$$$$$$                 /$$       /$$                            /$$$$$$  /$$                          \r\n      | $$_____/                | $$      |__/                           /$$__  $$| $$                          \r\n      | $$    /$$$$$$   /$$$$$$$| $$$$$$$  /$$  /$$$$$$  /$$$$$$$       | $$  \\__/| $$$$$$$   /$$$$$$   /$$$$$$ \r\n      | $$$$$|____  $$ /$$_____/| $$__  $$| $$ /$$__  $$| $$__  $$      |  $$$$$$ | $$__  $$ /$$__  $$ /$$__  $$\r\n      | $$__/ /$$$$$$$|  $$$$$$ | $$  \\ $$| $$| $$  \\ $$| $$  \\ $$       \\____  $$| $$  \\ $$| $$  \\ $$| $$  \\ $$\r\n      | $$   /$$__  $$ \\____  $$| $$  | $$| $$| $$  | $$| $$  | $$       /$$  \\ $$| $$  | $$| $$  | $$| $$  | $$\r\n      | $$  |  $$$$$$$ /$$$$$$$/| $$  | $$| $$|  $$$$$$/| $$  | $$      |  $$$$$$/| $$  | $$|  $$$$$$/| $$$$$$$/\r\n      |__/   \\_______/|_______/ |__/  |__/|__/ \\______/ |__/  |__/       \\______/ |__/  |__/ \\______/ | $$____/ \r\n                                                                                                      | $$      \r\n                                                                                                      | $$      \r\n                                                                                                      |__/      \r\n");
			
		System.out.println("   ______________________________________________________________________________________________________________\n\n");
			
		System.out.println("\t\t[1] Place Order\t\t\t\t\t[2] Search Customer\n");
		System.out.println("\t\t[3] Search Order\t\t\t\t[4] View Reports\n");
		System.out.println("\t\t[5] Change Order Status\t\t\t\t[6] Delete Order\n\n");
		
		while(true){
			System.out.print("\t\tInput Option : ");
			int option = input.nextInt();
			
			 if (isOption(option)) {     // Check if the input option is valid.
                clearConsole();
                
                switch (option){
					case 1 : clearConsole();PlaceOrder();break;
					case 2 : clearConsole();SearchCustomer();break;
					case 3 : clearConsole();SearchOrder();break;
					case 4 : clearConsole();ViewReports();break;
					case 5 : clearConsole();ChangeOrderStatus();break;
					case 6 : clearConsole();DeleteOrder();break;
				}
				return;
					
			 } else { 
				 
                System.out.print("\033[1A");      // If the input option is invalid, clear the input line.      
                System.out.print("\033[0J"); 
            }
		}		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// Checks if the provided phone number is valid.
	public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10) {           // Check if the phone number has exactly 10 digits.
            return false;
        }
        if (!phoneNumber.startsWith("0")) {         // Check if the phone number starts with "0".
            return false;
        }
        
        char[] digits = phoneNumber.toCharArray();
		for (char digit : digits) {                  // Check if all characters in the phone number are digits.
			if (digit < '0' || digit > '9') {
				return false;
			}
		}
        return true;
    }
	
	
	// Check if targetSize is a valid size
	 public static boolean isSize(String targetSize) {
		 
		String[] sizeArray = {"XS", "S", "M", "L", "XL", "XXL"};    // List of valid sizes
		
        for (String size : sizeArray) {
            if (size.equals(targetSize)) {
                return true;
            }
        }
        return false;
    }
    
    
    // Check if the quantity is greater than 0
    public static boolean isQTY(int quantity) {
        if (quantity > 0){
			return true;
		}else{
			return false;
		}
    }
    
    //Calculates the total amount for an order based on the selected size and quantity. 
    public static double calculateAmount(String size, int quantity){
		
		String[] sizeArray = {"XS", "S", "M", "L", "XL", "XXL"};       // Available sizes
		double[] prices = {600.00, 800.00, 900.00, 1000.00, 1100.00, 1200.00};        // Corresponding prices for each size
		
		int index = -1;
		for (int i = 0; i < sizeArray.length; i++) {         // Find the index required size in the size array
			if (size.equals(sizeArray[i])) {
				index = i;
				break;
			}
		}

		double price = prices[index];
		double amount = price*quantity;
		return amount;
		
	}
	
  
	/* 2D array that stores order data. Each row represents an order.
	 Each row contains details like customer ID, order ID, size, quantity, amount, and order status.
	 The array is currently empty and will be filled with order information after orders are placed.*/
	public static String[][] DataBase = {};
	
	
    // Generates a new order ID based on the last order ID in the database. 
    public static String generateId(String[][] DataBase){
		int id = Integer.parseInt(DataBase[DataBase.length - 1][1].split("[#]")[1]);
		id++;
		return String.format("ODR#%05d",id);
			
	}
	
	//  Possible order status names.
    public static final String[] ORDER_STATUS_NAMES = {"Processing", "Delivering", "Delivered"};
    
    
    //  Adds a new order data to the database.        
	public static String[][] addData(String[] orderData) {
        String[][] newDataBase = new String[DataBase.length + 1][];

        for (int i = 0; i < DataBase.length; i++) {        // Copy elements from DataBase to newDataBase
            newDataBase[i] = DataBase[i];
        }
    
        newDataBase[DataBase.length] = orderData;         // Add the new orderData array to newDataBase

        DataBase = newDataBase;
        
		return DataBase;
    }

	/*The order ID of the current order being processed.
	It will be updated with a new value after an order is placed.*/
	public static String orderId = null;
	
	
	/*
    This method allows the user to place an order by inputting customer details, size, and quantity.
    It then calculates the amount and updates the order database with the new order information.
    The updated database is returned.*/
	public static String[][] PlaceOrder(){
		Scanner input = new Scanner(System.in);
		System.out.print("\r\n     _____  _                   ____          _           \r\n    |  __ \\| |                 / __ \\        | |          \r\n    | |__) | | __ _  ___ ___  | |  | |_ __ __| | ___ _ __ \r\n    |  ___/| |/ _` |/ __/ _ \\ | |  | | \'__/ _` |/ _ \\ \'__|\r\n    | |    | | (_| | (_|  __/ | |__| | | | (_| |  __/ |   \r\n    |_|    |_|\\__,_|\\___\\___|  \\____/|_|  \\__,_|\\___|_|   \r\n                                                          \r\n                                                          \r");
		System.out.print("    _____________________________________________________\n\n");
		System.out.print("\n\tEnter Order ID : ");
	
		if (DataBase.length==0){
			orderId = "ODR#00001";	     // Initialize orderId
		}
		System.out.println(orderId);
		   
		   
		L1:while (true) {
			System.out.print("\n\tEnter Customer Phone Number : ");
			String phoneNumber = input.nextLine();

			if (isValidPhoneNumber(phoneNumber)) {       // Validate phone number
					
				L3:while (true){
					System.out.print("\n\tEnter T-Shirt Size (XS/S/M/L/XL/XXL) : ");
					String size = input.next().toUpperCase(); 
					if (isSize(size)) {                  // Validate size
						
						L4:while (true){
							System.out.print("\n\tEnter QTY : ");        
							int QTY = input.nextInt();
							input.nextLine();
							if (isQTY(QTY)){               // Validate quantity
								
								double amount = calculateAmount(size,QTY);               // Calculate amount 
								System.out.printf("\n\tAmount : %.2f",amount);
								
								L5:while(true){
									System.out.print("\n\n\n\tDo you want to place this order? (y/n) : ");
									String answer = input.nextLine();
									if (answer.equalsIgnoreCase("y")||answer.equalsIgnoreCase("n")) {
										if (answer.equalsIgnoreCase("y")){
											System.out.println("\n\t\tOrder placed..!");
												
											String[] sizeList = {"XS", "S", "M", "L", "XL", "XXL"};           // Prepare data for the order
											int sizeIndex = Arrays.asList(sizeList).indexOf(size);
                
											Integer[] qtyList = {0,0,0,0,0,0};
											qtyList[sizeIndex] = QTY;

											Double[] amountList = {0.00,0.00,0.00,0.00,0.00,0.00};  
											amountList[sizeIndex] = amount;
												
											String orderStatus = ORDER_STATUS_NAMES[0];
												
												
											String[] orderData = new String[6];                   // Prepare order data and update database
											orderData[0] = phoneNumber;
											orderData[1] = orderId;
											orderData[2] = Arrays.toString(sizeList);
											orderData[3] = Arrays.toString(qtyList);
											orderData[4] = Arrays.toString(amountList);
											orderData[5] = orderStatus;
											
											DataBase = addData(orderData);                        // Update database with the new order data
											String newId = generateId(DataBase);                  // Generate new order ID
											orderId = newId;
												
										}
										L6:while (true){
											System.out.print("\n\tDo you want to place another order? (y/n) : ");
											String answer_1 = input.nextLine();
											
											if (answer_1.equalsIgnoreCase("n")) {
												clearConsole();
												homepage();
												
											}else if (answer_1.equalsIgnoreCase("y")) {
												clearConsole();
												PlaceOrder();
												
											}else{
												System.out.print("\033[2A");
												System.out.print("\033[0J");
												continue L6; 
											}
											break;
										}
											
									}else{
										System.out.print("\033[3A");
										System.out.print("\033[0J");
										System.out.print("\033[1A"); 
										continue L5;	
									}
									break;
								}
										
							}else{
								System.out.print("\033[2A"); 
								System.out.print("\033[0J");
								continue L4;
							}
							break;
						}
							
					}else{
						System.out.print("\033[2A"); 
						System.out.print("\033[0J");
						continue L3;
					}
					break;
				}
			
					
			} else {
				System.out.println("\n\t\tInvalid Phone number.. Try again.");
				L2:while (true){
					System.out.print("\n\tDo you want to enter phone number again? (y/n) : ");
					String answer = input.nextLine();
					if (answer.equalsIgnoreCase("y")) {
						System.out.print("\033[6A"); 
						System.out.print("\033[0J"); 
						continue L1;
						
					}else if (answer.equalsIgnoreCase("n")) {
						clearConsole();
						homepage();
						break L1;
					}else {
						System.out.print("\033[2A");
						System.out.print("\033[0J"); 
					}
				}
			}
				
		}
			
		return DataBase;
			
	}
		
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    //  Checks if a given phone number exists in the database.
    public static boolean checkPhoneNumber(String[][] DataBase, String phoneNumber) {
		for (String[] orderData : DataBase) {
			if (orderData[0].equals(phoneNumber)) {
				return true;
			}
		}
		return false;
    }
    
    /* Converts a string array to an integer array.
       In DataBase qty array is in as string array it converts to integer array*/
    public static int[] convertStringArrayToIntArray(String[] stringArray) {
		int[] intArray = new int[stringArray.length];
		for (int i = 0; i < stringArray.length; i++) {
			intArray[i] = Integer.parseInt(stringArray[i]);
		}
		return intArray;
	}
	
	 /* Converts a string array to a double array.
       In DataBase amount array is in as string array it converts to double array*/
	public static double[] convertStringArrayToDoubleArray(String[] stringArray) {
		double[] doubleArray = new double[stringArray.length];
		for (int i = 0; i < stringArray.length; i++) {
			doubleArray[i] = Double.parseDouble(stringArray[i]);
		}
		return doubleArray;
	}
    
     public static void processDataBase(String phoneNumber) {
        int[] qtyArray = {0, 0, 0, 0, 0, 0};
        double[] amountArray = {0.00, 0.00, 0.00, 0.00, 0.00, 0.00};

        for (int i = 0; i < DataBase.length; i++) {
            if (phoneNumber.equals(DataBase[i][0])) {
				
				// Extract quantities and amounts as string arrays from the database entry
                String[] qtyListStr = DataBase[i][3].replaceAll("[\\[\\]]", "").split(", ");         
                String[] amountListStr = DataBase[i][4].replaceAll("[\\[\\]]", "").split(", ");
                
                // Convert string arrays to int and double arrays
                int[] qtyList1 = convertStringArrayToIntArray(qtyListStr);
                double[] amountList2 = convertStringArrayToDoubleArray(amountListStr);
                
				// Accumulate quantities and amounts
                for (int j = 0; j < qtyArray.length; j++) {
                    qtyArray[j] += qtyList1[j];
                }

                for (int j = 0; j < amountArray.length; j++) {
                    amountArray[j] += amountList2[j];
                }
            }
        }
        
         // Size list used for sorting
        String[] sizeList = {"XS", "S", "M", "L", "XL", "XXL"};
        
        
        // Sort aggregated arrays in descending order based on amounts
        for (int i = 0; i < amountArray.length - 1; i++) {
            for (int j = 0; j < amountArray.length - i - 1; j++) {
                if (amountArray[j] < amountArray[j + 1]) {
                    // Swap elements
                    double tempReserve = amountArray[j];
                    amountArray[j] = amountArray[j + 1];
                    amountArray[j + 1] = tempReserve;

                    // Swap corresponding qty 
                    Integer tempqty = qtyArray[j];
                    qtyArray[j] = qtyArray[j + 1];
                    qtyArray[j + 1] = tempqty;
                    
                    // Swap corresponding elements in sizeList
					String tempSize = sizeList[j];
					sizeList[j] = sizeList[j + 1];
					sizeList[j + 1] = tempSize;
                }
           }
        
      }
      
        double totalAmount = 0.0;
		System.out.println("\t\t\t+-------------+---------+---------------+");
		System.out.println("\t\t\t|    Size     |   QTY   |    Amount     |");
		System.out.println("\t\t\t+-------------+---------+---------------+");

		for (int i = 0; i < sizeList.length; i++) {
			System.out.println("\t\t\t|             |         |               |");
			String row = String.format("\t\t\t|  %-10s |   %-5d |%14.2f |", sizeList[i], qtyArray[i], amountArray[i]);
			System.out.println(row);

			totalAmount += amountArray[i];
		}
		System.out.println("\t\t\t|             |         |               |");
		System.out.println("\t\t\t+-------------+---------+---------------+");
		System.out.printf("\t\t\t|  Total Amount         |%14.2f |\n", totalAmount);
		System.out.println("\t\t\t+-----------------------+---------------+");

  
    }

    
    // Search for customer reports based on their phone number. 
    public static void SearchCustomer(){
		Scanner input = new Scanner(System.in);
		System.out.print("\r\n      _____                     _        _____          _                             \r\n     / ____|                   | |      / ____|        | |                            \r\n    | (___   ___  __ _ _ __ ___| |__   | |    _   _ ___| |_ ___  _ __ ___   ___ _ __  \r\n     \\___ \\ / _ \\/ _` | \'__/ __| \'_ \\  | |   | | | / __| __/ _ \\| \'_ ` _ \\ / _ \\ \'__| \r\n     ____) |  __/ (_| | | | (__| | | | | |___| |_| \\__ \\ || (_) | | | | | |  __/ |    \r\n    |_____/ \\___|\\__,_|_|  \\___|_| |_|  \\_____\\__,_|___/\\__\\___/|_| |_| |_|\\___|_|    \r\n                                                                                      \r");
		System.out.print("   ___________________________________________________________________________________");
		L1:while (true){
			System.out.print("\n\n\n\n\tEnter Customer Phone Number : ");
			String phoneNumber = input.nextLine();
		
			if (checkPhoneNumber(DataBase,phoneNumber)) {            // Check that phone number is in database
				processDataBase(phoneNumber);                       //  Prints the results
				
				L2:while (true){
					System.out.print("\n\tDo you want to search another customer report? (y/n) : ");
					String answer = input.nextLine();
					
					if (answer.equalsIgnoreCase("y")) {
						clearConsole();
						SearchCustomer();
						break L2;
						
					}else if (answer.equalsIgnoreCase("n")) {
						clearConsole();
						homepage();
						break L1;
						
					}else {
						System.out.print("\033[2A");
						System.out.print("\033[0J"); 
					}
				}
				
				
			}else {
				System.out.println("\n\t\tInvalid input..");
				
				L3:while (true){
					System.out.print("\n\tDo you want to search another customer report? (y/n) : ");
					String answer = input.nextLine();
					
					if (answer.equalsIgnoreCase("y")) {
						clearConsole();
						SearchCustomer();
						break L1;
						
					}else if (answer.equalsIgnoreCase("n")) {
						clearConsole();
						homepage();
						break L3;
						
					}else {
						System.out.print("\033[2A");
						System.out.print("\033[0J"); 
					}
				}
			}
		}
	}
		
	 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 
	 //Searches and displays details of a specific order based on its index in the database.
	 public static void SearchOrderDetails(String[][] DataBase, int k){          // k The index of the order to be searched in the database.
		 
		// Extract quantities and amounts as string arrays from the database entry
        String[] qtyListStr = DataBase[k][3].replaceAll("[\\[\\]]", "").split(", ");
        String[] amountListStr = DataBase[k][4].replaceAll("[\\[\\]]", "").split(", ");

        // Convert string arrays to int and double arrays        
        int[] qtyArray = convertStringArrayToIntArray(qtyListStr);
        double[] amountArray = convertStringArrayToDoubleArray(amountListStr);
        
        // Size list used for sorting        
        String[] sizeList = {"XS", "S", "M", "L", "XL", "XXL"};
        
        // Sort aggregated arrays in descending order based on amounts        
		for (int i = 0; i < amountArray.length - 1; i++) {
			for (int j = 0; j < amountArray.length - i - 1; j++) {
				if (amountArray[j] < amountArray[j + 1]) {
					// Swap elements
					double tempReserve = amountArray[j];
					amountArray[j] = amountArray[j + 1];
					amountArray[j + 1] = tempReserve;

					// Swap corresponding qty 
					Integer tempqty = qtyArray[j];
					qtyArray[j] = qtyArray[j + 1];
					qtyArray[j + 1] = tempqty;
							
					// Swap corresponding elements in sizeList
					String tempSize = sizeList[j];
					sizeList[j] = sizeList[j + 1];
					sizeList[j + 1] = tempSize;
				}
			}
		}
		System.out.println("\n\tPhone Number : "+DataBase[k][0]);
		System.out.println("\tSize         : "+sizeList[0]);
		System.out.println("\tQTY          : "+qtyArray[0]);
		System.out.printf("\tAmount       : %.2f" ,amountArray[0]);
		System.out.println("\n\tStatus       : "+DataBase[k][5]);
					
    }
 
    //Searches for an order based on the order ID and displays its details. 
    public static void SearchOrder(){
		Scanner input = new Scanner(System.in);
		System.out.print("\r\n      _____                     _        ____          _            \r\n     / ____|                   | |      / __ \\        | |           \r\n    | (___   ___  __ _ _ __ ___| |__   | |  | |_ __ __| | ___ _ __  \r\n     \\___ \\ / _ \\/ _` | \'__/ __| \'_ \\  | |  | | \'__/ _` |/ _ \\ \'__| \r\n     ____) |  __/ (_| | | | (__| | | | | |__| | | | (_| |  __/ |    \r\n    |_____/ \\___|\\__,_|_|  \\___|_| |_|  \\____/|_|  \\__,_|\\___|_|    \r\n                                                                    \r\n                                                                    \r\n");
		System.out.print("   _________________________________________________________________");
		System.out.print("\n\n\n\n\tEnter order ID : ");
		String orderId = input.nextLine();
		
		for (int k = 0; k < DataBase.length; k++) {         // Search for the order based on the order ID
            if (orderId.equals(DataBase[k][1])) {
				SearchOrderDetails(DataBase,k);            // k = index of orderId
				while (true){
					System.out.print("\n\n\tDo you want to search another order? (y/n) : ");
					String answer = input.nextLine();
					if (answer.equalsIgnoreCase("y")) {
						clearConsole();
						SearchOrder();
					}else if (answer.equalsIgnoreCase("n")) {
						clearConsole();
						homepage();
						break;
					}else {
						System.out.print("\033[3A");
						System.out.print("\033[0J"); 
					}
				}
			
			}
		}
		System.out.println("\n\t\tInvalid ID..");
		while (true){
			System.out.print("\n\tDo you want to search another order? (y/n) : ");
			String answer = input.nextLine();
			if (answer.equalsIgnoreCase("y")) {
				clearConsole();
				SearchOrder();
			}else if (answer.equalsIgnoreCase("n")) {
				clearConsole();
				homepage();
				break;
			}else {
				System.out.print("\033[2A");
				System.out.print("\033[0J"); 
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
			
	 //  Displays various reports based on user choice.
	 public static void ViewReports(){
		Scanner input = new Scanner(System.in);
		System.out.print("\r\n       _____                       _       \r\n      |  __ \\                     | |      \r\n      | |__) |___ _ __   ___  _ __| |_ ___ \r\n      |  _  // _ \\ \'_ \\ / _ \\| \'__| __/ __|\r\n      | | \\ \\  __/ |_) | (_) | |  | |_\\__ \\\r\n      |_|  \\_\\___| .__/ \\___/|_|   \\__|___/\r\n                 | |                       \r\n                 |_|                       \r\n");
		System.out.print("   ________________________________________");
		
		System.out.println("\n\n\n\t\t[1] Customer Reports\n");
		System.out.println("\t\t[2] Item Reports\n");
		System.out.println("\t\t[3] Orders Reports\n");
		
		L1:while(true){
			System.out.print("\n\tEnter Option : ");
			int option = input.nextInt();
			
			 if (option==1|| option==2|| option==3) {
                clearConsole();
                
                switch (option){
					case 1 : clearConsole();CustomerReports();break;
					case 2 : clearConsole();ItemReports();break;
					case 3 : clearConsole();OrdersReports();break;
				}
				return;	
			 } else { 
                System.out.println("\n\t\tInvalid Input..");
				L2:while (true){
					System.out.print("\n\n\tDo you want to enter option again? (y/n) : ");
					String answer = input.nextLine();
					if (answer.equalsIgnoreCase("y")) {
						System.out.print("\033[6A"); 
						System.out.print("\033[0J"); 
						break L2;
					}else if (answer.equalsIgnoreCase("n")) {
						clearConsole();
						homepage();
						break L1;
					}else {
						System.out.print("\033[2A"); 
						System.out.print("\033[0J");
						System.out.print("\033[1A"); 
							
					}
				}
			}
		
		}
	}
	 
	  //-----------------------------------------------------------------------------------------------------------------------------------
	 
	 //  Calculates the sum of integers in an QTY array. 
	 public static int findSum (int[] Qty){
		 int sum = 0; 

        for (int num : Qty) {
            sum += num;
        }
        return sum;
     }
     
     //  Calculates the sum of integers in an Amount array.
     public static double findSum (double[] Amount){
		 double sum = 0; 

        for (double num : Amount) {
            sum += num;
        }
        return sum;
     }
    
    //  Displays customer-related reports based on the selected option.   
	public static void CustomerReportsOptions(String[][] DataBase, int option) {
		Scanner input = new Scanner(System.in);
		
		int dataSize = DataBase.length;
		String[] phoneNumbers = new String[dataSize];
		int[] totalQty = new int[dataSize];
		int[][] QtyList = new int[dataSize][6]; // Initialize QtyList
		double[] totalAmount = new double[dataSize];
		int numberOfPhoneNumbers = 0; // Keep track of the number of unique phone numbers

		for (String[] data : DataBase) {
			String phoneNumber = data[0];

			// Check if the phone number already exists in the array
			int existingIndex = -1;
			for (int j = 0; j < numberOfPhoneNumbers; j++) {
				if (phoneNumbers[j] != null && phoneNumbers[j].equals(phoneNumber)) {
					existingIndex = j;
					break;
				}
			}

			String[] qtyListStr = data[3].replaceAll("[\\[\\]]", "").split(", ");
			int[] Qty = convertStringArrayToIntArray(qtyListStr);
			int iQty = findSum(Qty);

			String[] amountListStr = data[4].replaceAll("[\\[\\]]", "").split(", ");
			double[] Amount = convertStringArrayToDoubleArray(amountListStr);
			double dAmount = findSum(Amount);

			if (existingIndex == -1) {
				// Phone number doesn't exist in the array, add it
				phoneNumbers[numberOfPhoneNumbers] = phoneNumber;
				totalQty[numberOfPhoneNumbers] = iQty;
				totalAmount[numberOfPhoneNumbers] = dAmount;
				QtyList[numberOfPhoneNumbers] = Qty; // Add the Qty array
				numberOfPhoneNumbers++;
			} else {
				// Phone number already exists, update qty and amount at the existing index
				totalQty[existingIndex] += iQty;
				totalAmount[existingIndex] += dAmount;
				for (int i = 0; i < 6; i++) {
					QtyList[existingIndex][i] += Qty[i]; // Add corresponding Qty values
				}
			}
		}
		if (option ==1 || option ==2){
		
			if (option ==1){
				System.out.print("\r\n       ____            _     _____          _____          _                                \r\n      |  _ \\          | |   |_   _|        / ____|        | |                               \r\n      | |_) | ___  ___| |_    | |  _ __   | |    _   _ ___| |_ ___  _ __ ___   ___ _ __ ___ \r\n      |  _ < / _ \\/ __| __|   | | | \'_ \\  | |   | | | / __| __/ _ \\| \'_ ` _ \\ / _ \\ \'__/ __|\r\n      | |_) |  __/\\__ \\ |_   _| |_| | | | | |___| |_| \\__ \\ || (_) | | | | | |  __/ |  \\__ \\\r\n      |____/ \\___||___/\\__| |_____|_| |_|  \\_____\\__,_|___/\\__\\___/|_| |_| |_|\\___|_|  |___/\r\n                                                                                            \r\n                                                                                            \r");
				System.out.print("      ______________________________________________________________________________________");
				
				// Sort aggregated arrays in descending order based on amounts 
				for (int i = 0; i < totalAmount.length - 1; i++) {
					for (int j = 0; j < totalAmount.length - i - 1; j++) {
						if (totalAmount[j] < totalAmount[j + 1]) {
							// Swap elements
							double tempReserve = totalAmount[j];
							totalAmount[j] = totalAmount[j + 1];
							totalAmount[j + 1] = tempReserve;

							// Swap corresponding qty 
							Integer tempqty = totalQty[j];
							totalQty[j] = totalQty[j + 1];
							totalQty[j + 1] = tempqty;
							
							 // Swap corresponding elements in sizeList
							String tempnum = phoneNumbers[j];
							phoneNumbers[j] = phoneNumbers[j + 1];
							phoneNumbers[j + 1] = tempnum;
						}
				   }
				  
				}
				
			}else if (option ==2){
				System.out.print("\r\n      __      ___                  _____          _                                \r\n      \\ \\    / (_)                / ____|        | |                               \r\n       \\ \\  / / _  _____      __ | |    _   _ ___| |_ ___  _ __ ___   ___ _ __ ___ \r\n        \\ \\/ / | |/ _ \\ \\ /\\ / / | |   | | | / __| __/ _ \\| \'_ ` _ \\ / _ \\ \'__/ __|\r\n         \\  /  | |  __/\\ V  V /  | |___| |_| \\__ \\ || (_) | | | | | |  __/ |  \\__ \\\r\n          \\/   |_|\\___| \\_/\\_/    \\_____\\__,_|___/\\__\\___/|_| |_| |_|\\___|_|  |___/\r\n                                                                                   \r\n                                                                                   \r");
				System.out.print("      ______________________________________________________________________________");
			
			}
			  
			System.out.println("\n\n\t\t\t+-------------+---------+---------------+");
			System.out.println("\t\t\t| Customer ID | All QTY |  Total Amount |");
			System.out.println("\t\t\t+-------------+---------+---------------+");

			for (int i = 0; i < phoneNumbers.length; i++) {
				if (phoneNumbers[i] != null) {
					System.out.println("\t\t\t|             |         |               |");
					String row = String.format("\t\t\t|  %-10s |   %-5d |%14.2f |",phoneNumbers[i],  totalQty[i], totalAmount[i]);
					System.out.println(row);
				}
			}
			System.out.println("\t\t\t+-------------+---------+---------------+");
			
			while (true){
				System.out.print("\n\tTo access the Main menu, please enter 0 : ");
				int answer = input.nextInt();
				if (answer==0){
					clearConsole();
					homepage();
				}else{
					System.out.print("\033[2A"); 
					System.out.print("\033[0J"); ;
					
				}
			}	
		}else{
			System.out.print("\r\n                _ _    _____          _                              _____                       _   \r\n          /\\   | | |  / ____|        | |                            |  __ \\                     | |  \r\n         /  \\  | | | | |    _   _ ___| |_ ___  _ __ ___   ___ _ __  | |__) |___ _ __   ___  _ __| |_ \r\n        / /\\ \\ | | | | |   | | | / __| __/ _ \\| \'_ ` _ \\ / _ \\ \'__| |  _  // _ \\ \'_ \\ / _ \\| \'__| __|\r\n       / ____ \\| | | | |___| |_| \\__ \\ || (_) | | | | | |  __/ |    | | \\ \\  __/ |_) | (_) | |  | |_ \r\n      /_/    \\_\\_|_|  \\_____\\__,_|___/\\__\\___/|_| |_| |_|\\___|_|    |_|  \\_\\___| .__/ \\___/|_|   \\__|\r\n                                                                               | |                   \r\n                                                                               |_|                   \r");
			System.out.print("      _________________________________________________________________________________________________________");
			System.out.println("\n\n\t\t\t+-------------+-----+----+----+----+-----+-----+--------------+");
			System.out.println("\t\t\t| Phone Number | XS | S  | M  | L  | XL  | XXL | Total Amount |");
			System.out.println("\t\t\t+-------------+-----+----+----+----+-----+-----+--------------+");

			for (int i = 0; i < phoneNumbers.length; i++) {
				if (phoneNumbers[i] != null) {
					System.out.println("\t\t\t|             |     |    |    |    |     |     |              |");
					String row = String.format(
						"\t\t\t|  %-10s | %3d | %2d | %2d | %2d | %3d | %3d | %12.2f |",
						phoneNumbers[i],
						QtyList[i][0], QtyList[i][1], QtyList[i][2], QtyList[i][3], QtyList[i][4], QtyList[i][5],
						totalAmount[i]
					);
					System.out.println(row);
				}
			}
			System.out.println("\t\t\t+-------------+-----+----+----+----+-----+-----+--------------+");
			while (true){
				System.out.print("\n\tTo access the Main menu, please enter 0 : ");
				int answer = input.nextInt();
				if (answer==0){
					clearConsole();
					homepage();
				}else{
					System.out.print("\033[2A"); 
					System.out.print("\033[0J"); ;
					
				}
			}	
		}
	}

	//  Displays customer-related reports based on user choice.
	 public static void CustomerReports(){
		Scanner input = new Scanner(System.in);
		System.out.print("\r\n        _____          _                              _____                       _       \r\n       / ____|        | |                            |  __ \\                     | |      \r\n      | |    _   _ ___| |_ ___  _ __ ___   ___ _ __  | |__) |___ _ __   ___  _ __| |_ ___ \r\n      | |   | | | / __| __/ _ \\| \'_ ` _ \\ / _ \\ \'__| |  _  // _ \\ \'_ \\ / _ \\| \'__| __/ __|\r\n      | |___| |_| \\__ \\ || (_) | | | | | |  __/ |    | | \\ \\  __/ |_) | (_) | |  | |_\\__ \\\r\n       \\_____\\__,_|___/\\__\\___/|_| |_| |_|\\___|_|    |_|  \\_\\___| .__/ \\___/|_|   \\__|___/\r\n                                                                | |                       \r\n                                                                |_|                       \r");
		System.out.print("      ______________________________________________________________________________________");
			
		System.out.println("\n\n\n\t\t[1] Best in Customers\n");
		System.out.println("\t\t[2] View Customers\n");
		System.out.println("\t\t[3] All Customer Report\n");
			
		L1:while(true){
			System.out.print("\n\tEnter Option : ");
			int option = input.nextInt();
				
				if (option==1|| option==2|| option==3) {
					clearConsole();
					CustomerReportsOptions(DataBase,option);                 // To display the results according to the option
					break;	
				} else { 
					System.out.println("\n\t\tInvalid Input..");
					L2:while (true){
						System.out.print("\n\n\tDo you want to enter option again? (y/n) : ");
						String answer = input.nextLine();
						if (answer.equalsIgnoreCase("y")) {
							System.out.print("\033[6A"); 
							System.out.print("\033[0J"); 
							break L2;
						}else if (answer.equalsIgnoreCase("n")) {
							clearConsole();
							ViewReports();
							break L1;
						}else {
							System.out.print("\033[2A"); 
							System.out.print("\033[0J");
							System.out.print("\033[1A"); 
							
						}
					}
			 }
		}
				 
	}
	
	//-----------------------------------------------------------------------------------------------------------------------------------
	
	// This method handles the item reports options based on the provided option.
	public static void ItemReportsOptions(String[][] DataBase, int option){
		Scanner input = new Scanner(System.in);
		
		// Initialize totalQty and totalAmount arrays
		int[] totalQty = new int[6];
		double[] totalAmount = new double[6];

		// Iterate through the DataBase
		for (String[] data : DataBase) {
			String[] qtyListStr = data[3].replaceAll("[\\[\\]]", "").split(", ");
			int[] Qty = new int[6];
			for (int i = 0; i < Qty.length; i++) {
				Qty[i] = Integer.parseInt(qtyListStr[i]);
				totalQty[i] += Qty[i]; // Add to totalQty
			}
			
			String[] amountListStr = data[4].replaceAll("[\\[\\]]", "").split(", ");
			double[] Amount = new double[6];
			for (int i = 0; i < Amount.length; i++) {
				Amount[i] = Double.parseDouble(amountListStr[i]);
				totalAmount[i] += Amount[i]; // Add to totalAmount
			}
		}
		String[] sizeList = {"XS", "S", "M", "L", "XL", "XXL"};
		
		if (option ==1){
			System.out.print("\r\n        _____            _           _   ____           ____ _________     __\r\n       / ____|          | |         | | |  _ \\         / __ \\__   __\\ \\   / /\r\n      | (___   ___  _ __| |_ ___  __| | | |_) |_   _  | |  | | | |   \\ \\_/ / \r\n       \\___ \\ / _ \\| \'__| __/ _ \\/ _` | |  _ <| | | | | |  | | | |    \\   /  \r\n       ____) | (_) | |  | ||  __/ (_| | | |_) | |_| | | |__| | | |     | |   \r\n      |_____/ \\___/|_|   \\__\\___|\\__,_| |____/ \\__, |  \\___\\_\\ |_|     |_|   \r\n                                                __/ |                        \r\n                                               |___/                         \r\n");
			System.out.print("     ______________________________________________________________________");
			// Sort by quantity// Sort by quantity
			for (int i = 0; i < totalQty.length - 1; i++) {
				for (int j = 0; j < totalQty.length - i - 1; j++) {
					if (totalQty[j] < totalQty[j + 1]) {
						// Swap quantities
						int tempQty = totalQty[j];
						totalQty[j] = totalQty[j + 1];
						totalQty[j + 1] = tempQty;
						
						// Swap corresponding elements in sizeList
						String tempSize = sizeList[j];
						sizeList[j] = sizeList[j + 1];
						sizeList[j + 1] = tempSize;
						
						// Swap corresponding elements in totalAmount
						double tempAmount = totalAmount[j];
						totalAmount[j] = totalAmount[j + 1];
						totalAmount[j + 1] = tempAmount;
					}
			   }
			}

			
		}else if (option==2){
			System.out.print("\r\n        _____            _           _   ____                                               _   \r\n       / ____|          | |         | | |  _ \\            /\\                               | |  \r\n      | (___   ___  _ __| |_ ___  __| | | |_) |_   _     /  \\   _ __ ___   ___  _   _ _ __ | |_ \r\n       \\___ \\ / _ \\| \'__| __/ _ \\/ _` | |  _ <| | | |   / /\\ \\ | \'_ ` _ \\ / _ \\| | | | \'_ \\| __|\r\n       ____) | (_) | |  | ||  __/ (_| | | |_) | |_| |  / ____ \\| | | | | | (_) | |_| | | | | |_ \r\n      |_____/ \\___/|_|   \\__\\___|\\__,_| |____/ \\__, | /_/    \\_\\_| |_| |_|\\___/ \\__,_|_| |_|\\__|\r\n                                                __/ |                                           \r\n                                               |___/                                            \r\n");
			System.out.print("     ___________________________________________________________________________________________");
			// Sort by amount
			// Sort by amount
			for (int i = 0; i < totalAmount.length - 1; i++) {
				for (int j = 0; j < totalAmount.length - i - 1; j++) {
					if (totalAmount[j] < totalAmount[j + 1]) {
						// Swap amounts
						double tempAmount = totalAmount[j];
						totalAmount[j] = totalAmount[j + 1];
						totalAmount[j + 1] = tempAmount;
						
						// Swap corresponding elements in sizeList
						String tempSize = sizeList[j];
						sizeList[j] = sizeList[j + 1];
						sizeList[j + 1] = tempSize;
						
						// Swap corresponding elements in totalQty
						int tempQty = totalQty[j];
						totalQty[j] = totalQty[j + 1];
						totalQty[j + 1] = tempQty;
					}
			   }
			}
		}
		System.out.println("\n\n\t\t\t+-------------+---------+---------------+");
		System.out.println("\t\t\t|    Size     |   QTY   |  Total Amount |");
		System.out.println("\t\t\t+-------------+---------+---------------+");

		for (int i = 0; i < sizeList.length; i++) {
			System.out.println("\t\t\t|             |         |               |");
			String row = String.format("\t\t\t|  %-10s |   %-5d |%14.2f |", sizeList[i], totalQty[i], totalAmount[i]);
			System.out.println(row);

		}
		System.out.println("\t\t\t|             |         |               |");
		System.out.println("\t\t\t+-------------+---------+---------------+");
		while (true){
			System.out.print("\n\tTo access the Main menu, please enter 0 : ");
			int answer = input.nextInt();
			if (answer==0){
				clearConsole();
				homepage();
			}else{
				System.out.print("\033[2A"); 
				System.out.print("\033[0J"); ;
					
			}
		}

	}		
			
		 
	 //  Displays and handles the options for item reports.
	 public static void ItemReports(){
		Scanner input = new Scanner(System.in);
		System.out.print("\r\n       _____ _                   _____                       _       \r\n      |_   _| |                 |  __ \\                     | |      \r\n        | | | |_ ___ _ __ ___   | |__) |___ _ __   ___  _ __| |_ ___ \r\n        | | | __/ _ \\ \'_ ` _ \\  |  _  // _ \\ \'_ \\ / _ \\| \'__| __/ __|\r\n       _| |_| ||  __/ | | | | | | | \\ \\  __/ |_) | (_) | |  | |_\\__ \\\r\n      |_____|\\__\\___|_| |_| |_| |_|  \\_\\___| .__/ \\___/|_|   \\__|___/\r\n                                           | |                       \r\n                                           |_|                       \r");
		System.out.print("      _______________________________________________________________");
			
		System.out.println("\n\n\n\t\t[1] Best Selling Categories Sorted by QTY\n");
		System.out.println("\t\t[2]  Best Selling Categories Sorted by Amount\n");
			
			L1:while(true){
			System.out.print("\n\tEnter Option : ");
			int option = input.nextInt();
				
				if (option==1|| option==2) {
					clearConsole();
					ItemReportsOptions(DataBase,option);       // To display the results according to the option
					break;	
				} else { 
					System.out.println("\n\t\tInvalid Input..");
					L2:while (true){
						System.out.print("\n\n\tDo you want to enter option again? (y/n) : ");
						String answer = input.nextLine();
						if (answer.equalsIgnoreCase("y")) {
							System.out.print("\033[6A"); 
							System.out.print("\033[0J"); 
							break L2;
						}else if (answer.equalsIgnoreCase("n")) {
							clearConsole();
							ViewReports();
							break L1;
						}else {
							System.out.print("\033[2A"); 
							System.out.print("\033[0J");
							System.out.print("\033[1A"); 
							
						}
					}
				}
		}
		 
	}
	
	//-----------------------------------------------------------------------------------------------------------------------------------
	
	// This method handles the order reports options based on the provided option.
	public static void OrderReportsOptions(String[][] DataBase, int option){
		Scanner input = new Scanner(System.in);
		if (option ==1){
			System.out.print("\r\n      __      ___                  ____          _               \r\n      \\ \\    / (_)                / __ \\        | |              \r\n       \\ \\  / / _  _____      __ | |  | |_ __ __| | ___ _ __ ___ \r\n        \\ \\/ / | |/ _ \\ \\ /\\ / / | |  | | \'__/ _` |/ _ \\ \'__/ __|\r\n         \\  /  | |  __/\\ V  V /  | |__| | | | (_| |  __/ |  \\__ \\\r\n          \\/   |_|\\___| \\_/\\_/    \\____/|_|  \\__,_|\\___|_|  |___/\r\n                                                                 \r\n                                                                 \r");
			System.out.print("   _________________________________________________________________");
			
		
			
			System.out.print("\n\n\t+-------------+--------------+---------+--------+-------------+--------------+");
			System.out.print("\n\t|  Order ID   | Customer ID  |   Size  |   QTY  |    Amount   |     Status   |");
			System.out.print("\n\t+-------------+--------------+---------+--------+-------------+--------------+\n");
			
			for (int k = DataBase.length-1 ; k >=0 ; k--) {
				String[] qtyListStr = DataBase[k][3].replaceAll("[\\[\\]]", "").split(", ");
				String[] amountListStr = DataBase[k][4].replaceAll("[\\[\\]]", "").split(", ");
					
				int[] qtyArray = convertStringArrayToIntArray(qtyListStr);
				double[] amountArray = convertStringArrayToDoubleArray(amountListStr);
					
				String[] sizeList = {"XS", "S", "M", "L", "XL", "XXL"};
					
				for (int i = 0; i < amountArray.length - 1; i++) {
					for (int j = 0; j < amountArray.length - i - 1; j++) {
						if (amountArray[j] < amountArray[j + 1]) {
							// Swap elements
							double tempReserve = amountArray[j];
							amountArray[j] = amountArray[j + 1];
							amountArray[j + 1] = tempReserve;

							// Swap corresponding qty 
							Integer tempqty = qtyArray[j];
							qtyArray[j] = qtyArray[j + 1];
							qtyArray[j + 1] = tempqty;
								
							// Swap corresponding elements in sizeList
							String tempSize = sizeList[j];
							sizeList[j] = sizeList[j + 1];
							sizeList[j + 1] = tempSize;
						}
					}
				}
				String orderID = DataBase[k][1];
				String customerID = DataBase[k][0];
				String size = sizeList[0]; // Replace with the actual size value
				int qty = qtyArray[0]; // Replace with the actual qty value
				double amount = amountArray[0]; // Replace with the actual amount value
				String status = DataBase[k][5];
				System.out.printf("\t|  %-10s |   %-10s |  %-6s |   %-4d |%12.2f |   %-8s |\n", orderID, customerID, size, qty, amount, status);
			}
			System.out.print("\t+-------------+--------------+---------+--------+-------------+--------------+\n");	
			while (true){
				System.out.print("\n\tTo access the Main menu, please enter 0 : ");
				int answer = input.nextInt();
				if (answer==0){
					clearConsole();
					homepage();
				}else{
					System.out.print("\033[2A"); 
					System.out.print("\033[0J"); ;
						
				}
			}
		} else if (option == 2) {
			System.out.print("\r\n        ____          _                 ____                                               _   \r\n       / __ \\        | |               |  _ \\            /\\                               | |  \r\n      | |  | |_ __ __| | ___ _ __ ___  | |_) |_   _     /  \\   _ __ ___   ___  _   _ _ __ | |_ \r\n      | |  | | \'__/ _` |/ _ \\ \'__/ __| |  _ <| | | |   / /\\ \\ | \'_ ` _ \\ / _ \\| | | | \'_ \\| __|\r\n      | |__| | | | (_| |  __/ |  \\__ \\ | |_) | |_| |  / ____ \\| | | | | | (_) | |_| | | | | |_ \r\n       \\____/|_|  \\__,_|\\___|_|  |___/ |____/ \\__, | /_/    \\_\\_| |_| |_|\\___/ \\__,_|_| |_|\\__|\r\n                                               __/ |                                           \r\n                                              |___/                                            \r");
			System.out.print("     __________________________________________________________________________________________");

			System.out.print("\n\n\t+-------------+--------------+---------+--------+-------------+--------------+");
			System.out.print("\n\t|  Order ID   | Customer ID  |   Size  |   QTY  |    Amount   |     Status   |");
			System.out.print("\n\t+-------------+--------------+---------+--------+-------------+--------------+\n");

			String[] orderIDs = new String[DataBase.length];
			String[] customerIDs = new String[DataBase.length];
			String[] sizes = new String[DataBase.length];
			int[] quantities = new int[DataBase.length];
			double[] amounts = new double[DataBase.length];
			String[] statuses = new String[DataBase.length];

			for (int k = 0; k < DataBase.length; k++) {
				orderIDs[k] = DataBase[k][1];
				customerIDs[k] = DataBase[k][0];

				String[] qtyListStr = DataBase[k][3].replaceAll("[\\[\\]]", "").split(", ");
				String[] amountListStr = DataBase[k][4].replaceAll("[\\[\\]]", "").split(", ");

				int[] qtyArray = convertStringArrayToIntArray(qtyListStr);
				double[] amountArray = convertStringArrayToDoubleArray(amountListStr);
				String[] sizeList = {"XS", "S", "M", "L", "XL", "XXL"};

				for (int i = 0; i < amountArray.length - 1; i++) {
					for (int j = 0; j < amountArray.length - i - 1; j++) {
						if (amountArray[j] < amountArray[j + 1]) {
							// Swap elements
							double tempReserve = amountArray[j];
							amountArray[j] = amountArray[j + 1];
							amountArray[j + 1] = tempReserve;

							// Swap corresponding qty 
							Integer tempqty = qtyArray[j];
							qtyArray[j] = qtyArray[j + 1];
							qtyArray[j + 1] = tempqty;

							// Swap corresponding elements in sizeList
							String tempSize = sizeList[j];
							sizeList[j] = sizeList[j + 1];
							sizeList[j + 1] = tempSize;
						}
					}
				}

				sizes[k] = sizeList[0];
				quantities[k] = qtyArray[0];
				amounts[k] = amountArray[0];
				statuses[k] = DataBase[k][5];
			}
			

			// Sort the arrays again based on amounts
			for (int i = 0; i < amounts.length - 1; i++) {
				for (int j = 0; j < amounts.length - i - 1; j++) {
					if (amounts[j] < amounts[j + 1]) {
						double tempAmount = amounts[j];
						amounts[j] = amounts[j + 1];
						amounts[j + 1] = tempAmount;

						// Swap corresponding elements in other arrays
						String tempOrderID = orderIDs[j];
						orderIDs[j] = orderIDs[j + 1];
						orderIDs[j + 1] = tempOrderID;

						String tempCustomerID = customerIDs[j];
						customerIDs[j] = customerIDs[j + 1];
						customerIDs[j + 1] = tempCustomerID;

						String tempSize = sizes[j];
						sizes[j] = sizes[j + 1];
						sizes[j + 1] = tempSize;

						int tempQty = quantities[j];
						quantities[j] = quantities[j + 1];
						quantities[j + 1] = tempQty;

						String tempStatus = statuses[j];
						statuses[j] = statuses[j + 1];
						statuses[j + 1] = tempStatus;
					}
				}
			}

			for (int k = 0; k < DataBase.length; k++) {
				System.out.printf("\t|  %-10s |   %-10s |  %-6s |   %-4d |%12.2f |   %-8s |\n",
						orderIDs[k], customerIDs[k], sizes[k], quantities[k], amounts[k], statuses[k]);
			}

			System.out.print("\t+-------------+--------------+---------+--------+-------------+--------------+\n");

			while (true) {
				System.out.print("\n\tTo access the Main menu, please enter 0 : ");
				int answer = input.nextInt();
				if (answer == 0) {
					clearConsole();
					homepage();
				} else {
					System.out.print("\033[2A");
					System.out.print("\033[0J");
				}
			}
					
		}
	}

	 //  Displays and handles the options for order reports.
	public static void OrdersReports(){
		Scanner input = new Scanner(System.in);
		System.out.print("\r\n        ____          _             _____                       _       \r\n       / __ \\        | |           |  __ \\                     | |      \r\n      | |  | |_ __ __| | ___ _ __  | |__) |___ _ __   ___  _ __| |_ ___ \r\n      | |  | | \'__/ _` |/ _ \\ \'__| |  _  // _ \\ \'_ \\ / _ \\| \'__| __/ __|\r\n      | |__| | | | (_| |  __/ |    | | \\ \\  __/ |_) | (_) | |  | |_\\__ \\\r\n       \\____/|_|  \\__,_|\\___|_|    |_|  \\_\\___| .__/ \\___/|_|   \\__|___/\r\n                                              | |                       \r\n                                              |_|                       \r");
		System.out.print("      _________________________________________________________________");
			
		System.out.println("\n\n\n\t\t[1] All Orders\n");
		System.out.println("\t\t[2]  Orders by Amount\n");
			
			L1:while(true){
			System.out.print("\n\tEnter Option : ");
			int option = input.nextInt();
				
				if (option==1|| option==2) {
					clearConsole();
					OrderReportsOptions(DataBase,option);              // To display the results according to the option
					break;	
				} else { 
					System.out.println("\n\t\tInvalid Input..");
					L2:while (true){
						System.out.print("\n\n\tDo you want to enter option again? (y/n) : ");
						String answer = input.nextLine();
						if (answer.equalsIgnoreCase("y")) {
							System.out.print("\033[6A"); 
							System.out.print("\033[0J"); // Clear current line
							break L2;
						}else if (answer.equalsIgnoreCase("n")) {
							clearConsole();
							ViewReports();
							break L1;
						}else {
							System.out.print("\033[2A"); 
							System.out.print("\033[0J");
							System.out.print("\033[1A"); 
							
						}
					}
				}
		}
		 
	}
	 
	 
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
	
	// Updates the order status in the database. 
	public static String[][] UpdateOrderStatus(String[][] DataBase, int index, int option) {
		DataBase[index][5] = ORDER_STATUS_NAMES[option];      // Update the order status at the specified index with the new status
		return DataBase;
	}
	
	
	// Allows the user to change the status of an order.
	public static void ChangeOrderStatus(){
		Scanner input = new Scanner(System.in);
		System.out.print("\r\n        ____          _              _____ _        _             \r\n       / __ \\        | |            / ____| |      | |            \r\n      | |  | |_ __ __| | ___ _ __  | (___ | |_ __ _| |_ _   _ ___ \r\n      | |  | | \'__/ _` |/ _ \\ \'__|  \\___ \\| __/ _` | __| | | / __|\r\n      | |__| | | | (_| |  __/ |     ____) | || (_| | |_| |_| \\__ \\\r\n       \\____/|_|  \\__,_|\\___|_|    |_____/ \\__\\__,_|\\__|\\__,_|___/\r\n                                                                  \r\n                                                                  \r");
		System.out.print("   _________________________________________________________________");
		System.out.print("\n\n\n\n\tEnter order ID : ");
		String orderId = input.nextLine();
		
		boolean foundOrder = false;
		boolean orderDelivered = false;
		
		for (int k = 0; k < DataBase.length; k++) {
            if (orderId.equals(DataBase[k][1])) {
				SearchOrderDetails(DataBase,k);            //k = index of orderId
				String currentStatus = DataBase[k][5];
				if (currentStatus.equalsIgnoreCase(ORDER_STATUS_NAMES[2])) {           // Check if the order is already delivered
					System.out.println("\n\t\tCan't change this order status, Order already delivered..!");
					orderDelivered = true;
					break;
				}
				
				L1:while (true){
					System.out.print("\n\n\tDo you want to change this order status? (y/n) : ");
					String answer = input.nextLine();
					if (answer.equalsIgnoreCase("y")) {
						if (currentStatus.equalsIgnoreCase(ORDER_STATUS_NAMES[1])) {
							System.out.println("\n\t\t[1] Order Delivered"); 
						} else {
							System.out.println("\n\t\t[1] Order Delivering");
							System.out.println("\n\t\t[2] Order Delivered");
						}
						L2:while (true){
							System.out.print("\n\tEnter option : ");
							int option = input.nextInt();
							input.nextLine();
							if ((option == 1 | option==2) & currentStatus.equalsIgnoreCase(ORDER_STATUS_NAMES[0])){
								 System.out.println("\n\t\tStatus Updated..!");
								 DataBase = UpdateOrderStatus(DataBase, k, option);
								 foundOrder = true;
								 break;
							}else if (option == 1 & currentStatus.equalsIgnoreCase(ORDER_STATUS_NAMES[1])){
								System.out.println("\n\t\tStatus Updated..!");
								DataBase = UpdateOrderStatus(DataBase, k, (option+1));
								foundOrder = true;
								break;
							}else{
								System.out.println("\n\t\tInvalid input..");
							}
                        
						}
						L3:while(true){
							System.out.print("\n\tDo you want to change another order status? (y/n) : ");
							String answer1 = input.nextLine();
							if (answer1.equalsIgnoreCase("y")) {
								clearConsole();
								ChangeOrderStatus();
							}else if (answer1.equalsIgnoreCase("n")) {
								clearConsole();
								homepage();
							}else {
								System.out.print("\033[2A");
								System.out.print("\033[0J");
								continue L3;
							}
						}
						
				}else if (answer.equalsIgnoreCase("n")) {
					while(true){
						System.out.print("\n\tDo you want to change another order status? (y/n) : ");
						String answer1 = input.nextLine();
						if (answer1.equalsIgnoreCase("y")) {
							clearConsole();
							ChangeOrderStatus();
							break;
						}else if (answer1.equalsIgnoreCase("n")) {
							clearConsole();
							homepage();
							break;
						}else {
							System.out.print("\033[2A");
							System.out.print("\033[0J");
						}
					}
						
						break;
				}else {
					System.out.print("\033[3A");
					System.out.print("\033[0J"); 
				}
				
				}
			
			}
		}
		if (!foundOrder & !orderDelivered) {          // If order not found and if order not delivered
			System.out.println("\n\t\tInvalid ID..");
			while (true){
				System.out.print("\n\tDo you want to change another order status? (y/n) : ");
				String answer = input.nextLine();
				if (answer.equalsIgnoreCase("y")) {
					clearConsole();
					ChangeOrderStatus();
				}else if (answer.equalsIgnoreCase("n")) {
					clearConsole();
					homepage();
					break;
				}else {
					System.out.print("\033[2A");
					System.out.print("\033[0J"); 
				}
			}
		}
	} 
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
	
	// Updates the database after deleting an order with the specified orderId.
	public static String[][] UpdateAfterDelete(String[][] DataBase, String orderId) {
		
		boolean found = false; 

		for (int i = 0; i < DataBase.length; i++) {     // Check if the orderId exists in the database
			if (orderId.equals(DataBase[i][1])) {
				found = true;
				break;
			}
		}
		if (found){
			
			String[][] newDataBase = new String[DataBase.length - 1][6];  // Create a new array with one less length to hold the updated data

			int newDataBaseIndex = 0; // Index for the new array

			for (int i = 0; i < DataBase.length; i++) {
				if (!orderId.equals(DataBase[i][1])) { // Skip the row with the specified orderId
					for (int j = 0; j < 6; j++) {
						newDataBase[newDataBaseIndex][j] = DataBase[i][j];
					}
					newDataBaseIndex++; // Increment the index for newDataBase
				}
			}

			DataBase =  newDataBase;
		}
		return DataBase; // Return the updated database
	}
	 
    public static void DeleteOrder(){
		Scanner input = new Scanner(System.in);
		System.out.print("\r\n     _____       _      _          ____          _            \r\n    |  __ \\     | |    | |        / __ \\        | |           \r\n    | |  | | ___| | ___| |_ ___  | |  | |_ __ __| | ___ _ __  \r\n    | |  | |/ _ \\ |/ _ \\ __/ _ \\ | |  | | \'__/ _` |/ _ \\ \'__| \r\n    | |__| |  __/ |  __/ ||  __/ | |__| | | | (_| |  __/ |    \r\n    |_____/ \\___|_|\\___|\\__\\___|  \\____/|_|  \\__,_|\\___|_|    \r\n                                                              \r\n                                                              \r\n");
		System.out.print("   _________________________________________________________________");
		
		System.out.print("\n\n\n\n\tEnter order ID : ");
		String orderId = input.nextLine();
		
		for (int k = 0; k < DataBase.length; k++) {         // Search for the order based on the order ID
			if (orderId.equals(DataBase[k][1])) {
				SearchOrderDetails(DataBase,k);
				L3:while (true){
					System.out.print("\n\tDo you want to delete this order? (y/n) : ");
					String answer = input.nextLine();
					if (answer.equalsIgnoreCase("y")) {
						System.out.println("\n\t\tOrder Deleted!");
						DataBase = UpdateAfterDelete(DataBase,orderId);		 
						L1:while (true){
							System.out.print("\n\n\tDo you want to  delete another customer report? (y/n) : ");
							String answer1 = input.nextLine();
							if (answer1.equalsIgnoreCase("y")) {
								clearConsole();
								DeleteOrder();
							}else if (answer1.equalsIgnoreCase("n")) {
								clearConsole();
								homepage();
								break;
							}else {
								System.out.print("\033[3A");
								System.out.print("\033[0J");
								continue L1; 
							}
						}
				}else if (answer.equalsIgnoreCase("n")) {
					L2:while(true){
						System.out.print("\n\tDo you want to delete another customer report? (y/n) : ");
						String answer1 = input.nextLine();
						if (answer1.equalsIgnoreCase("y")) {
							clearConsole();
							DeleteOrder();
							break;
						}else if (answer1.equalsIgnoreCase("n")) {
							clearConsole();
							homepage();
							break;
						}else {
							System.out.print("\033[2A");
							System.out.print("\033[0J");
							continue L2;
						}
					}		
			}else {
					System.out.print("\033[2A");
					System.out.print("\033[0J"); 
					continue L3;
				}
			}
		}
		}
		System.out.println("\n\t\tInvalid input..");
		while (true){
			System.out.print("\n\tDo you want to delete another customer report? (y/n) : ");
			String answer = input.nextLine();
			if (answer.equalsIgnoreCase("y")) {
				clearConsole();
				DeleteOrder();
			}else if (answer.equalsIgnoreCase("n")) {
				clearConsole();
				homepage();
				break;
			}else {
				System.out.print("\033[2A");
				System.out.print("\033[0J"); 
			}
		}
	}		
	
}
