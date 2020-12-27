package com.random.generator;

// Class to generate random elements according to given frequency distribution
class RandomValueProbability<T> {

    static int findCeil(int arr[], int r, int l, int h) {
        int mid;
        while (l < h) {
            mid = l + ((h - l) >> 1); // Same as mid = (l+h)/2
            if (r > arr[mid])
                l = mid + 1;
            else
                h = mid;
        }
        return (arr[l] >= r) ? l : -1;
    }

    // The main function that returns a random number from arr[] according to distribution array defined by freq[]. n is size of arrays.
    public T myRand(T[] arr, Integer[] freq, int n) {
        // Create and fill prefix array
        int prefix[] = new int[n], i;
        prefix[0] = freq[0];
        for (i = 1; i < n; ++i)
            prefix[i] = prefix[i - 1] + freq[i];

        // prefix[n-1] is sum of all frequencies.
        // Generate a random number with
        // value from 1 to this sum
        int r = ((int) (Math.random() * (323567)) % prefix[n - 1]) + 1;

        // Find index of ceiling of r in prefix arrat
        int indexc = findCeil(prefix, r, 0, n - 1);
        return arr[indexc];
    }
}



