# Lattice Reduction Algorithm Comparison

This project compares two different implementations of the Lenstra-Lenstra-Lovász (LLL) lattice reduction algorithm:
1. A pure implementation in Java
2. An implementation using the fpylll library in Python

## Project Structure

The project consists of two main files:
- `LatticeComparison.java`: Main program that generates random lattice matrices, runs both Java and Python implementations, and compares the results.
- `lll_reduce.py`: Performs LLL reduction using the Python fpylll library.

## Requirements

- Java 8 or higher
- Python 3.x
- fpylll library (`pip install fpylll`)

## Installation

1. Clone the repository:
```bash
git clone https://github.com/umurpasa/lattice-comparison
```

2. Install fpylll library:
```bash
pip install fpylll
```

3. Compile the Java file:
```bash
javac LatticeComparison.java
```

## Usage

To run the program:
```bash
javac LatticeComparison
```

## Example Output
```
Original Lattice:
4 -7 2
-3 8 -5
6 -1 9

Reduced Lattice (Java LLL):
2 1 1
-1 2 -2
1 -1 3

Reduced Lattice (Python fpylll):
2 1 1
-1 2 -2
1 -1 3

Java LLL Runtime: 15 ms
Python fpylll Time: 3 ms

Comparison Summary:
Java LLL Time: 15 ms, Python fpylll Time: 3 ms
Results for reduced lattice match: true
```

## Performance Comparison

- The fpylll library in Python uses an optimized implementation written in C++
- Performance difference might be minimal for small matrices
- The fpylll version generally performs faster for larger matrices
- The Java implementation includes additional overhead due to running the Python script as a separate process

## Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## References

Lenstra, A.K., Lenstra, H.W., Lovász, L. Factoring polynomials with rational coefficients. Mathematische Annalen (1982)
fpylll library: https://github.com/fplll/fpylll
