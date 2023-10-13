# Project 1: Integration of Mergesort & Insertion Sort
- Theoretically, mergesort is more efficient than insertion sort
- But, for small subarrays, overhead of recursive calls in mergesort makes it inefficient
- TODO: Set a small integer **S**, whereby insertion sort is called instead of mergesort for subarrays smaller than **S**

## (a) Implement hybrid algorithm of mergesort & insertion sort
See ```hybrid_sort()``` in ```main.py```

## (b) Generate input data of size range from 1,000 to 10,000,000
See ```generate_dataset()``` in ```main.py```

## (c) Analyse time complexity of hybrid algorithm based on key comparisons made
### i. Fixed S, vary n (where n is the size of input dataset)
### ii. Fixed n, vary S
### iii. Determine optimal S using different sizes of input dataset

## (d) Compare hybrid algorithm with original mergesort by using optimal S from (c) & n = 10,000,000
See ```results.xlsx```