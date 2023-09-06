import constant
from random import randint

# To generate dataset of size n
def generate_dataset(n):
    dataset = []
    for i in range(n):
        dataset.append(randint(1, n+1))
    return dataset


# Insertion sort
def insertion_sort(dataset):
    # insertion sort logic


# Merge sort
def merge_sort(dataset):
    # merge sort logic


# Driver function
def main():
    # Generate random dataset of size n
    data = generate_dataset(constant.n)
    
    # Time measurement from here?
    if constant.n > constant.S:
        merge_sort(data)
    else:
        insertion_sort(data)
    # End time measurement here?

    # Output sorted dataset
    for i in data:
        print(i)


if __name__ == '__main__':
    main()

