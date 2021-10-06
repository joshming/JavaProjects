public class Dictionary implements DictionaryADT{

    private final DictEntry[] hashtable;
    private final DictEntry DELETED = new DictEntry("", -1);
    private final int secPrime; // smaller prime number for secondary hashing method

    public Dictionary(int size){
        hashtable = new DictEntry[size];
        secPrime = prevPrime();
    }

    public int insert(DictEntry pair) throws DictionaryException{
        String key = pair.getKey();
        int pos = hashFunction(key), count = 0; // initialize a counter and starting position for while loop
        if (hashtable[pos] != null) {
            // ensure the hashtable at this position isn't occupied
            while ((hashtable[pos] != null) && (hashtable[pos] != DELETED)){
                // only put the pair in if is a null space and not deleted.
                DictEntry hashEntry = hashtable[pos]; // to represent the current DictEntry object
                if (hashEntry.getKey().equals(key)) { // Checks to see if the key already exists in the hashtable
                    throw new DictionaryException("Dictionary Exception: " + key + " is in the dictionary.");
                }
                pos = (pos + secondaryHash(key)) % hashtable.length;
                if (count == hashtable.length-1){ // if we have the count equal to the hashtable length, it is full
                    throw new DictionaryException("Dictionary Exception: Dictionary is full.");
                }
            }
            hashtable[pos] = pair;
            return 1; // collision occurred
        }
        hashtable[pos] = pair;
        return 0; // collision didn't occur
    }

    public void remove(String key) throws DictionaryException{
        int pos = hashFunction(key); // initialize the first possible position of what we want to remove
        int count = 0; // to ensure we don't infinitely loop
        DictEntry record = hashtable[pos];
        while ((record != null) && !(record.getKey().equals(key))){
            pos += secondaryHash(key); // Since it wasn't in the initial position, try another position
            record = hashtable[pos];
            count++;
            if (count == hashtable.length) { // after they equal, we have checked every spot in the hashtable
                throw new DictionaryException("Dictionary Exception: " + key + " is not in the dictionary.");
            }
        }
        if (record == null){ // since it hit null, we know it hasn't been inserted in the hashtable yet
            throw new DictionaryException("Dictionary Exception: " + key + " is not in the dictionary.");
        }
        hashtable[pos] = DELETED;
    }

    public int numElements(){
        int  count = 0;
        for (DictEntry dictEntry : hashtable) {
            if (dictEntry != null) {
                count++; // only increase the count if the position in the table is not null
            }
        }

        return count;
    }

    public DictEntry find(String key){
        int pos = hashFunction(key); // first possible position
        int count = 0;
        DictEntry record = hashtable[pos]; // entry at that position
        while ((record != null) && !(record.getKey().equals(key))){
            pos = (pos + secondaryHash(key)) % hashtable.length; // increase the position since its not the right key
            record = hashtable[pos];
            count++;
            if (count == hashtable.length - 1) {
                // after going through the whole hashtable, it's not there
                return null;
            }
        }
        return record;
    }

    private int hashFunction(String key){
        int lastChar = key.length() - 1;
        int sum = (key.charAt(lastChar)); // initialize sum with the first value of the first character in string
        for (int i = lastChar - 1; i >= 0; i--){
            sum = (sum * 41 + (int) key.charAt(i)) % (hashtable.length) ;
        }
        return sum; // ensures the sum is not greater than the size of dictionary
    }

    private int secondaryHash(String key){
        int code = hashFunction(key);
        return secPrime - (code % secPrime);
    }

    private int prevPrime(){
        // finds the next prime number that is less than the hashtable's length
        // this will be used for the secondary hash function
        for (int i = hashtable.length - 1; i > 1; i--){
            if (isPrime(i)) {
                return i;
            }
        }
        return 2;
    }

    private boolean isPrime(int possPrime){
        for (int i = 2; i < Math.sqrt(possPrime); i++){
            // start at the first prime number, go to the square root of the int
            // the sqrt is the largest multiple before there are repeats
            if (possPrime % i == 0) {
                // if it is divisible by this number, it is not prime
                return false;
            }
        }
        return possPrime > 1; // since 1 can appear like a prime number but is not a prime number
    }


}
