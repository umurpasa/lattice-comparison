from fpylll import IntegerMatrix, LLL
import sys

def main():
    # Read lattice sizes and values ​
    input_lines = sys.stdin.read().strip().split("\n")
    rows, cols = map(int, input_lines[0].split())
    lattice = [list(map(int, line.split())) for line in input_lines[1:]]

    # Convert Lattice matrix to fpylll IntegerMatrix
    mat = IntegerMatrix.from_matrix(lattice)

    # Use LLL reduction
    LLL.reduction(mat)

    # Print reduced matrix to stdout
    print(f"{rows} {cols}")
    for row in mat:
        print(" ".join(map(str, row)))

if __name__ == "__main__":
    main()
