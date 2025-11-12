/*
* Approach:
- The idea is to maintain two data structures:
    - one linear DS: queue or arraylist to keep track of all unassigned numbers (initially filled with 0 to maxnumber)
    - another one: Set to keep track of what all numbers are already assigned
    - get() -> will return the available number, top of the queue, add it to the set as its assigned now if the queue is empty that means there are no avaiable numbers to be assigned
    - check() -> check if the number is present in the set, if it is that means its not avaiable to assign
    - release() -> if the number is present in the set, remove it from the set and add it to the queue as this is again avaiable to assign
    - All the operations are O(1)
    - SC: O(n) -> where n is the range of 0 to maxnumber 
 */
class PhoneDirectory {
    HashSet<Integer> set; //maintain assigned numbers
    Queue<Integer> q;

    public PhoneDirectory(int maxNumbers) {
        this.set = new HashSet<>();
        this.q = new LinkedList<>();

        for(int i = 0; i < maxNumbers; i++){
            q.add(i);
        }
        
    }
    
    public int get() {
        if(q.isEmpty()) //no number available to give
            return -1;
        
        int num = q.poll();
        set.add(num);
        return num;
    }
    
    public boolean check(int number) {
        if(set.contains(number))
            return false;
        return true;
        
    }
    
    public void release(int number) {
        if(set.contains(number)){
            q.add(number);
            set.remove(number);
        }
    }
}

/**
 * Your PhoneDirectory object will be instantiated and called as such:
 * PhoneDirectory obj = new PhoneDirectory(maxNumbers);
 * int param_1 = obj.get();
 * boolean param_2 = obj.check(number);
 * obj.release(number);
 */