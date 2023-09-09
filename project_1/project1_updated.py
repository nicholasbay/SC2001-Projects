import random
import constant


# To generate dataset (without duplicates) of size n
def generate_dataset(n):
    # Create array of length n, consisting of unique elements from 1 to n inclusive
    arr = [x for x in range(1, n + 1)]
    # Shuffle array order
    random.shuffle(arr)
    return arr


# Insertion sort
# Added keys as arguments
def insertion_sort(arr,keys):
    for i in range(len(arr)):
        for j in range(i, 0, -1):
            keys = keys+1

            if (arr[j] < arr[j - 1]):
                tmp = arr[j]
                arr[j] = arr[j - 1]
                arr[j - 1] = tmp
            else:
                break
    return keys


# Mergesort with additional keys as argument
def merge_sort(arr,keys):
    mid = int(len(arr) / 2)

    if len(arr) <= constant.S:
        keys = insertion_sort(arr,keys)
        return arr, keys
    else:
        left_half, keys = merge_sort(arr[:mid],keys)
        right_half, keys = merge_sort(arr[mid:],keys)

    return merge(left_half, right_half,keys)


def merge(left, right, keys):
    result = []
    left_index, right_index = 0, 0

    while (left_index < len(left)) and (right_index < len(right)):
        if (left[left_index] < right[right_index]):
            result.append(left[left_index])
            left_index += 1
        else:
            result.append(right[right_index])
            right_index += 1
        # Increase key comparisons
        keys = keys+1

    result.extend(left[left_index:])
    result.extend(right[right_index:])

    return result, keys

# Driver function
def main():
    # Generate random dataset of size n
    dataset = generate_dataset(constant.n)
    # Counting key comparisons
    keys = 0

    #Viewing dataset
    for i in range(len(dataset)):
        print(dataset[i], end=" ")

    #Using mergesort
    result = []
    result, keys = merge_sort(dataset,keys)
    print()
    for i in range(len(result)):
        print(result[i], end=" ")
    print()
    print("The number of key comparisons is", keys)

    '''
    # Time measurement from here?
    if constant.n > constant.S:
        merge_sort(dataset, constant.n)
    else:
        insertion_sort(dataset, constant.n)
    # End time measurement here?
    '''

if __name__ == '__main__':
    main()
