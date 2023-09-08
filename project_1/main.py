import constant
import random

# To generate dataset (without duplicates) of size n
def generate_dataset(n):
    # Create array of length n, consisting of unique elements from 1 to n inclusive
    arr = [x for x in range(1, n+1)]
    # Suffle array order
    random.shuffle(arr)
    return arr


# Insertion sort
def insertion_sort(arr, n):
    for i in range(n):
        for j in range(i, 0, -1):
            if (arr[j] < arr[j-1]):
                tmp = arr[j]
                arr[j] = arr[j-1]
                arr[j-1] = tmp
            else:
                break


# Mergesort
#def merge_sort(arr, n):
    # merge sort logic


# Hybrid sort (merge & insertion)
#def hybrid_sort(arr, n):
    # merge sort for n > S, insertion sort for n < S


# Driver function
def main():
    # Generate random dataset of size n
    dataset = generate_dataset(constant.n)
    
    '''
    # Time measurement from here?
    if constant.n > constant.S:
        merge_sort(dataset, constant.n)
    else:
        insertion_sort(dataset, constant.n)
    # End time measurement here?
    '''

    # Output sorted dataset
    for i in range(len(dataset)):
        print(dataset[i])


if __name__ == '__main__':
    main()