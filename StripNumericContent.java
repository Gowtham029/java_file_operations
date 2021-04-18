public class StripNumericContent {
  
    // Function to remove all the digit
    // from string
    public static String removeAllDigit(String str)
    {
        String result = "";
  
        // Traverse the String from start to end
        for (int i = 0; i < str.length(); i++) {
  
            // Check if the specified character is not digit
            // then add this character into result variable
            if (!Character.isDigit(str.charAt(i))) {
                result = result + str.charAt(i);
            }
        }
  
        // Return result
        return result;
    }

    // Driver Code
    public static void main(String args[])
    {   
        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    System.out.println(data);
                }
                myReader.close();
                // Given alphanumeric string str
                System.out.println();
                String alphabets = removeAllDigit(data);

                FileWriter myWriter = new FileWriter("output.txt");
                myWriter.write("Files in Java might be tricky, but it is fun enough!");
                myWriter.close();
                System.out.println("Successfully wrote to the file.");

            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
        }
  
        
    }
}
