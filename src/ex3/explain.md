I think this problem can be solved as follow:
1. Split the large file into many small files so that each file can be stored in memory
2. Sort each small files by object_id
3. Remove the first element from each file and create a min heap base on object_id with elements are combination of the first element of each small file and name of file contains it
4. Create a new file to store sort result 
5. Perform while loop in until the min heap is empty.In each iteration: 
    - 5.1 Get the first element of the heap and add the data part to the new file in 4th step
    - 5.2 Determine the file which contains the first element of the heap 
    - 5.3 If the file which contained element that has been remove from the heap is not empty: 
            remove the first element of the file and add it along with its file name to the heap 
6. return the new sorted file