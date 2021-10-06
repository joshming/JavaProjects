import java.io.*;

public class Compress {
    public static void main(String[] args) {
        Dictionary fileDict = new Dictionary(5503); // Prime number larger than 4096
        try {
            initializeDict(fileDict);
            compressFile(args[0], fileDict);
        } catch (IOException | DictionaryException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void initializeDict(Dictionary dict) throws DictionaryException {
        // initialize the dictionary with characters of ASCII values of 0 to 255 (inclusive)
        for (int i = 0; i < 256; i++) {
            DictEntry initDict = new DictEntry(Character.toString((char) i), i);
            dict.insert(initDict);
        }
    }

    private static void compressFile(String file, Dictionary dict) throws IOException, DictionaryException {

        BufferedInputStream in;
        in = new BufferedInputStream(new FileInputStream(file));
        int nDictCode = 256; // stores the next dictionary code
        int readByte = in.read(); // stores the byte that has been read
        String newKey = Character.toString((char) readByte); // stores the next key to be found/inserted

        String newName = file + ".zzz";
        BufferedOutputStream outFile;
        outFile = new BufferedOutputStream(new FileOutputStream(newName)); // prepare the new output file (compressed)

        while (readByte != -1) { // repeat these lines until the end of the file, or don't start if its an empty file
            String next; // the next strings after the first byte read
            while ((readByte != -1) && dict.find(newKey) != null) { 
                // continue adding to the key until we have found a key that isn't in the dictionary
                readByte = in.read();
                next = Character.toString((char) readByte);
                newKey = newKey + next;
            }

            String key;
            if (dict.find(newKey) == null) {
                key = newKey.substring(0, newKey.length() - 1);
                // if newKey is not in the dictionary, the new key <key> will be the same key except the last char
            } else {
                key = newKey;
                // else newKey isn't in the dictionary, so <key> will be the same
            }
            DictEntry keyEntry = dict.find(key); // create a new DictEntry with the same key just created
            int compressedCode = keyEntry.getCode(); // get the code that is associated with the key
            MyOutput.output(compressedCode, outFile); // put this code into the compressed file


            if (dict.numElements() < 4096) {
                // As long as the dictionary isn't full, add the newly created DictEntry into it
                DictEntry newEntry = new DictEntry(newKey, nDictCode);
                dict.insert(newEntry);
            }
            newKey = newKey.substring(newKey.length() - 1); // define newKey as the last char of the old key
            nDictCode++; // increase the next dictionary code by 1

        }
        in.close();
        MyOutput.flush(outFile);
        outFile.close();
    }
}
