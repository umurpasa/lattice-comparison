import java.io.*;
import java.util.Random;

public class LatticeComparison {

    // Creates a random lattice matrix
    public static int[][] generateRandomLattice(int rows, int columns, int range) {
        Random rand = new Random();
        int[][] lattice = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                lattice[i][j] = rand.nextInt(range * 2) - range;
            }
        }
        return lattice;
    }

    // Prints Lattice to screen
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.printf("%d ", value);
            }
            System.out.println();
        }
    }

    // Helper function for time measurement
    public static long currentTimeMillis() {
        return System.nanoTime() / 1_000_000;
    }

    // Call Python script and get reduced lattice
    public static int[][] callPythonLLL(int[][] lattice, String pythonScriptPath) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("python3", pythonScriptPath);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // Send lattice matrix to Python script
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            writer.write(lattice.length + " " + lattice[0].length + "\n");
            for (int[] row : lattice) {
                for (int value : row) {
                    writer.write(value + " ");
                }
                writer.write("\n");
            }
            writer.flush();
        }

        // Read reduced matrix from Python
        int[][] reducedLattice;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line = reader.readLine();
            int rows = Integer.parseInt(line.split(" ")[0]);
            int columns = Integer.parseInt(line.split(" ")[1]);
            reducedLattice = new int[rows][columns];

            for (int i = 0; i < rows; i++) {
                String[] values = reader.readLine().split(" ");
                for (int j = 0; j < columns; j++) {
                    reducedLattice[i][j] = Integer.parseInt(values[j]);
                }
            }
        }

        process.waitFor();
        return reducedLattice;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // Create a random lattice
        int rows = 3, columns = 3, range = 10;
        int[][] lattice = generateRandomLattice(rows, columns, range);
        System.out.println("Original Lattice:");
        printMatrix(lattice);

        // Our LLL reduction algorithm (Java)
        long javaStart = currentTimeMillis();
        int[][] reducedLatticeJava = LatticeReduction.lllReduce(lattice, 0.75);
        long javaEnd = currentTimeMillis();
        System.out.println("Reduced Lattice (Java LLL):");
        printMatrix(reducedLatticeJava);
        System.out.printf("Java LLL Runtime: %d ms%n", (javaEnd - javaStart));

        // LLL reduction with Python (fpylll)
        String pythonScriptPath = "lll_reduce.py"; 
        long pythonStart = currentTimeMillis();
        int[][] reducedLatticePython = callPythonLLL(lattice, pythonScriptPath);
        long pythonEnd = currentTimeMillis();
        System.out.println("Reduced Lattice (Python fpylll):");
        printMatrix(reducedLatticePython);
        System.out.printf("Python fpylll Runtime: %d ms%n", (pythonEnd - pythonStart));

        // Performance and results comparison
        System.out.println("\nComparison Summary:");
        System.out.printf("Java LLL Time: %d ms, Python fpylll Time: %d ms%n", (javaEnd - javaStart), (pythonEnd - pythonStart));
        System.out.println("Results for reduceed lattice match: " + matricesAreEqual(reducedLatticeJava, reducedLatticePython));
    }

    // Checks if matrices are equal
    public static boolean matricesAreEqual(int[][] matrix_1, int[][] matrix_2) {
        if (matrix_1.length != matrix_2.length || matrix_1[0].length != matrix_2[0].length) {
            return false;
        }
        for (int i = 0; i < matrix_1.length; i++) {
            for (int j = 0; j < matrix_1[0].length; j++) {
                if (matrix_1[i][j] != matrix_2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
