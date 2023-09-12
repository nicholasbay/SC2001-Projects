import random
import pandas as pd
from timeit import default_timer as timer
import constant

# To generate dataset of size n (without duplicates)
def generate_dataset(n):
    # Create array of length n, consisting of unique elements from 1 to n inclusive
    arr = [x for x in range(1, n + 1)]
    # Shuffle array order
    random.shuffle(arr)
    return arr


# Insertion sort
# Added key_comp as arguments
def insertion_sort(arr, key_comp):
    for i in range(len(arr)):
        for j in range(i, 0, -1):
            key_comp += 1

            if (arr[j] < arr[j-1]):
                tmp = arr[j]
                arr[j] = arr[j-1]
                arr[j-1] = tmp
            else:
                break
    return key_comp


# Pure Mergesort
def merge_sort(arr, key_comp):
    mid = int(len(arr) / 2)
    
    left_half = merge_sort(arr[:mid], key_comp)
    right_half = merge_sort(arr[mid:], key_comp)

    return merge(left_half, right_half, key_comp)


# Hybrid sort (merge & insertion)
def hybrid_sort(arr, key_comp):
    mid = int(len(arr) / 2)

    if len(arr) <= constant.S:
        key_comp = insertion_sort(arr,key_comp)
        return arr, key_comp
    else:
        left_half, key_comp = hybrid_sort(arr[:mid], key_comp)
        right_half, key_comp = hybrid_sort(arr[mid:], key_comp)

    return merge(left_half, right_half,key_comp)


def merge(left, right, key_comp):
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
        key_comp = key_comp + 1

    result.extend(left[left_index:])
    result.extend(right[right_index:])

    return result, key_comp


# Driver function
def main():
    # c)i) Constant S, varying n
    print("i) Constant S, varying n")
    # Initialise S & n
    constant.S = 100
    constant.n = 1000
    # List of measurements for part i
    list_i = []

    while constant.n <= 10000000:
        # Generate random dataset of size n
        data = generate_dataset(constant.n)
        # Count key comparisons
        count = 0
        # To store sorted array
        data_sorted = []
        
        # Measure time at start of sorting
        start_time = timer()
        # Sort dataset
        data_sorted, count = hybrid_sort(data, count)
        # Measure time at end of sorting
        end_time = timer()
        
        # Ensure data is sorted
        is_sorted = all(a < b for a, b in zip(data_sorted, data_sorted[1:]))
        if not is_sorted:
            print("Error")
        
        # Collating measurements to dict
        dict_i = {
            'S': constant.S,
            'n': constant.n,
            'Key comp.': count,
            'CPU time': end_time - start_time
        }
        # Appending measurements to list_i
        list_i.append(dict_i)
        
        # Increment n in powers of 10
        constant.n *= 10

    # Collate all measurements for part i into DataFrame
    df_i = pd.DataFrame(list_i)
    # Print DataFrame
    print(repr(df_i))

    print()

    # c)ii) Constant n, varying S
    print("ii) Constant n, varying S")
    # Initialise n & S
    constant.S = 1
    constant.n = 100000
    # List of measurements for part ii
    list_ii = []

    while (constant.S <= constant.n):
        # Generate random dataset of size n
        data = generate_dataset(constant.n)
        # Count key comparisons
        count = 0
        # To store sorted array
        data_sorted = []
        
        # Measure time at start of sorting
        start_time = timer()
        # Sort dataset
        data_sorted, count = hybrid_sort(data, count)
        # Measure time at end of sorting
        end_time = timer()
        
        # Ensure data is sorted
        is_sorted = all(a < b for a, b in zip(data_sorted, data_sorted[1:]))
        if not is_sorted:
            print("Error")
        
        # Collating measurements to dict
        dict_ii = {
            'S': constant.S,
            'n': constant.n,
            'Key comp.': count,
            'CPU time': end_time - start_time
        }
        # Appending measurements to list_i
        list_ii.append(dict_ii)
        
        # Increment S in powers of 10
        constant.S *= 10

    # Collate all measurements for part ii into DataFrame
    df_ii = pd.DataFrame(list_ii)
    # Print DataFrame
    print(repr(df_ii))

    print()

    '''
    Takes forever to run
    # c)iii) Varying S & n
    print("iii) Varying S & n")
    # Initialise S & n
    constant.S = 1
    constant.n = 1000
    # List of measurements for part iii
    list_iii = []

    while constant.n <= 10000000:
        while constant.S <= constant.n:
            # Generate random dataset of size n
            data = generate_dataset(constant.n)
            # Count key comparisons
            count = 0
            # To store sorted array
            data_sorted = []
            
            # Measure time at start of sorting
            start_time = timer()
            # Sort dataset
            data_sorted, count = hybrid_sort(data, count)
            # Measure time at end of sorting
            end_time = timer()
            
            # Ensure data is sorted
            is_sorted = all(a < b for a, b in zip(data_sorted, data_sorted[1:]))
            if not is_sorted:
                print("Error")
            
            # Collating measurements to dict
            dict_iii = {
                'S': constant.S,
                'n': constant.n,
                'Key comp.': count,
                'CPU time': end_time - start_time
            }
            # Appending measurements to list_i
            list_iii.append(dict_iii)
            
            # Increment S in powers of 10
            constant.S *= 10
        # Increment n in powers of 10
        constant.n *= 10

    # Collate all measurements for part ii into DataFrame
    df_iii = pd.DataFrame(list_iii)
    # Print DataFrame
    print(repr(df_iii))
    '''


if __name__ == '__main__':
    main()
