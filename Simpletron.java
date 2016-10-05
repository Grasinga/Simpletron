import java.util.Scanner;

/**
 * Simpletron SML Program
 */
public class Simpletron {
    public static void main(String[] args){new Simpletron();}

    private Scanner input = new Scanner(System.in); // Used to get user input.

    private int[] memory = new int[100]; // Main memory for Simpletron.

    // Input/output operations:
    private final int READ = 10; // Read a word from the keyboard into a specific location in memory.
    private final int WRITE = 11; // Write a word from a specific location in memory to the screen.

    // Load/store operations:
    private final int LOAD = 20; // Load a word from a specific location in memory into the accumulator.
    private final int STORE = 21; // Store a word from the accumulator into a specific location in memory.

    // Arithmetic operations:
    private final int ADD = 30; // Add a word from a specific location in memory to the word in the accumulator.
    private final int SUBTRACT = 31; // Subtract a word from a specific location in memory from the word in the accumulator.
    private final int DIVIDE = 32; // Divide a word from a specific location in memory into the word in the accumulator.
    private final int MULTIPLY = 33; // Multiply a word from a specific location in memory by the word in the accumulator.

    // Transfer-of-control operations:
    private final int BRANCH = 40; // Branch to a specific location in memory.
    private final int BRANCHNEG = 41; // Branch to a specific location in memory if the accumulator is negative.
    private final int BRANCHZERO = 42; // Branch to a specific location in memory if the accumulator is zero.
    private final int HALT = 43; // Halt. The program has completed its task.

    private Simpletron(){
        System.out.println("*** Welcome to Simpletron! ***");
        System.out.println("*** Please enter your program one instruction ***");
        System.out.println("*** (or data word) at a time. I will display ***");
        System.out.println("*** the location number and a question mark (?). ***");
        System.out.println("*** You then type the word for that location. ***");
        System.out.println("*** Type -99999 to stop entering your program. ***");
        System.out.println();
        loadInstructions();
        System.out.println();
        System.out.println("*** Program loading completed ***");
        System.out.println("*** Program execution begins ***");
        System.out.println();
        executeInstructions();
    }

    /**
     * Gets the instructions from the user and loads them into memory.
     */
    private void loadInstructions(){
        int instruction = 0;
        int index = 0;

        // Handle first value:
        System.out.print("00 ? ");
        try {
            instruction = input.nextInt();
            while (instruction != (-99999) && (instruction < (-9999) || instruction > 9999)) { // Handle invalid instruction.
                System.out.println("Instruction must be between -9999 and +9999!");
                System.out.print("00 ? ");
                instruction = input.nextInt();
            }
        }catch (Exception e) { // Handle any form of error, print it, then exit the program.
            System.out.println("*** An error occurred! Error: " + e.getCause() + " ***");
            System.out.println("*** Simpletron loading phase abnormally terminated! ***");
            System.exit(1);
        }
        if(instruction != (-99999))
            memory[index] = instruction;
        index++;

        // Handle the rest of the values:
        while(instruction != (-99999) && index < memory.length){
            if(index < 10) { // Get instructions for 00 to 09
                System.out.print("0" + index + " ? ");
                try {
                    instruction = input.nextInt();
                    while (instruction != (-99999) && (instruction < (-9999) || instruction > 9999)) { // Handle invalid instruction.
                        System.out.println("Instruction must be between -9999 and +9999!");
                        System.out.print("0" + index + " ? ");
                        instruction = input.nextInt();
                    }
                }catch (Exception e) { // Handle any form of error, print it, then exit the program.
                    System.out.println("*** An error occurred! Error: " + e.getCause() + " ***");
                    System.out.println("*** Simpletron loading phase abnormally terminated! ***");
                    System.exit(1);
                }
                if(instruction != (-99999))
                    memory[index] = instruction;
            }
            else { // Get instructions for 10 to 99
                System.out.print(index + " ? ");
                try {
                    instruction = input.nextInt();
                    while (instruction != (-99999) && (instruction < (-9999) || instruction > 9999)) { // Handle invalid instruction.
                        System.out.println("Instruction must be between -9999 and +9999!");
                        System.out.print(index + " ? ");
                        instruction = input.nextInt();
                    }
                }catch (Exception e){ // Handle any form of error, print it, then exit the program.
                    System.out.println("*** An error occurred! Error: " + e.getCause() + " ***");
                    System.out.println("*** Simpletron loading phase abnormally terminated! ***");
                    System.exit(1);
                }
                if(instruction != (-99999))
                    memory[index] = instruction;
            }
            index++;
        }
    }

    /**
     * Executes the instructions in memory.
     */
    private void executeInstructions(){
        int accumulator = 0; // Accumulator register.
        int instructionCounter = 0; // Location in memory.
        int instructionRegister = 0; // Next instruction.
        int operationCode = 0; // Left two digits of instruction.
        int operand = 0; // Rightmost two digits of instruction.
        try {
            while (instructionCounter < memory.length && memory[instructionCounter] != (-99999)){
                instructionRegister = memory[instructionCounter];
                operationCode = instructionRegister / 100;
                operand = instructionRegister % 100;
                switch (operationCode){
                    case READ:
                        System.out.print("Enter an integer: ");
                        int n = input.nextInt();
                        while(n < -9999 || n > 9999){
                            System.out.println("Integer must be between -9999 and 9999!");
                            System.out.print("Enter an integer: ");
                            n = input.nextInt();
                        }
                        memory[operand] = n;
                        break;
                    case WRITE:
                        System.out.printf("%+06d",memory[operand]);
                        System.out.println();
                        break;
                    case LOAD:
                        accumulator = memory[operand];
                        break;
                    case STORE:
                        memory[operand] = accumulator;
                        break;
                    case ADD:
                        accumulator += memory[operand];
                        if(accumulator < -9999){
                            System.out.println("*** Sum of addition was less than -9999! ***");
                            System.out.println("*** Simpletron execution phase abnormally terminated! ***");
                            System.exit(1);
                        }
                        else if(accumulator > 9999){
                            System.out.println("*** Sum of addition was greater than 9999! ***");
                            System.out.println("*** Simpletron execution phase abnormally terminated! ***");
                            System.exit(1);
                        }
                        else break;
                    case SUBTRACT:
                        accumulator -= memory[operand];
                        if(accumulator < -9999){
                            System.out.println("*** Difference of subtraction was less than -9999! ***");
                            System.out.println("*** Simpletron execution phase abnormally terminated! ***");
                            System.exit(1);
                        }
                        else if(accumulator > 9999){
                            System.out.println("*** Difference of subtraction was greater than 9999! ***");
                            System.out.println("*** Simpletron execution phase abnormally terminated! ***");
                            System.exit(1);
                        }
                        else break;
                    case DIVIDE:
                        try {
                            accumulator /= memory[operand];
                            if(accumulator < -9999){
                                System.out.println("*** Quotient of division was less than -9999! ***");
                                System.out.println("*** Simpletron execution phase abnormally terminated! ***");
                                System.exit(1);
                            }
                            else if(accumulator > 9999){
                                System.out.println("*** Quotient of division was greater than 9999! ***");
                                System.out.println("*** Simpletron execution phase abnormally terminated! ***");
                                System.exit(1);
                            }
                        }catch (Exception e){ // Handle any form of error, print it, then exit the program.
                            System.out.println("*** Attempted to divide by zero! ***");
                            System.out.println("*** Simpletron execution phase abnormally terminated! ***");
                            System.exit(1);
                        }
                        break;
                    case MULTIPLY:
                        accumulator *= memory[operand];
                        if(accumulator < -9999){
                            System.out.println("*** Product of multiplication was less than -9999! ***");
                            System.out.println("*** Simpletron execution phase abnormally terminated! ***");
                            System.exit(1);
                        }
                        else if(accumulator > 9999){
                            System.out.println("*** Product of multiplication was greater than 9999! ***");
                            System.out.println("*** Simpletron execution phase abnormally terminated! ***");
                            System.exit(1);
                        }
                        else break;
                    case BRANCH:
                        instructionCounter = operand;
                        break;
                    case BRANCHNEG:
                        if (accumulator < 0)
                            instructionCounter = operand;
                        break;
                    case BRANCHZERO:
                        if (accumulator == 0)
                            instructionCounter = operand;
                        break;
                    case HALT:
                        System.out.println();
                        System.out.println("*** Simpletron execution terminated. ***");
                        break;
                }
                instructionCounter++;
            }
            System.out.println();
            computerDump(accumulator,instructionCounter,instructionRegister,operationCode,operand); // Output all info.
        }catch (Exception e){ // Handle any form of error, print it, then exit the program.
            System.out.println("*** An error occurred! Error: " + e.getCause() + " ***");
            System.out.println("*** Simpletron execution phase abnormally terminated! ***");
            System.exit(1);
        }
    }

    /**
     * Prints the info of Simpletron.
     *
     * @param accumulator Current accumulator value at execution's end.
     * @param instructionCounter Current spot in memory at execution's end.
     * @param instructionRegister Current instruction at execution's end.
     * @param operationCode Current operation code at execution's end.
     * @param operand Current operand at execution's end.
     */
    private void computerDump(int accumulator, int instructionCounter, int instructionRegister, int operationCode, int operand){
        System.out.println("REGISTERS:");
        System.out.print("accumulator: " + accumulator + "\n");
        System.out.print("instructionCounter: " + instructionCounter + "\n");
        System.out.print("instructionRegister: " + instructionRegister + "\n");
        System.out.print("operationCode: " + operationCode + "\n");
        System.out.print("operand: " + operand + "\n");
        System.out.println();
        System.out.println("MEMORY:");
        System.out.printf("%7d %6d %6d %6d %6d %6d %6d %6d %6d %6d",0,1,2,3,4,5,6,7,8,9);
        for(int i = 0; i < memory.length; i++){
            if(i%10 == 0) {
                System.out.println();
                if(i == 0)
                    System.out.print("0");
                System.out.print(i + " ");
            }
            System.out.printf("%+06d ",memory[i]);
        }
        System.out.println();
    }
}
