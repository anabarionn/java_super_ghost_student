package studentCode;

import professorCode.AbstractDictionary;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;



public class Dictionary extends AbstractDictionary {

    Set<String> set = new HashSet<String>();

    public Dictionary(String path, FileManager dictionaryFileReader) throws IOException {
        super(path, dictionaryFileReader);
        set = getAllWords();
    }



    @Override
    public int countWordsThatStartWith(String prefix, int size, boolean ignoreCase) throws IllegalArgumentException {
        int count = 0;
        String st;
        for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
            st = it.next();
            if (ignoreCase) {
                prefix = prefix.toUpperCase();
                st = st.toUpperCase();
            }
            if (st.startsWith(prefix) && st.length() >= size) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean containsWordsThatStartWith(String prefix, int size, boolean ignoreCase) throws IllegalArgumentException {
        boolean result = false;
        int count = 0;

        String st;

        for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
            st = it.next();
            if (ignoreCase) {
                prefix = prefix.toUpperCase();
                st = st.toUpperCase();
            }
            if (st.startsWith(prefix) && st.length() >= size) {
                count++;
            }
        }
        if (count == 0) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    @Override
    public Set<String> getWordsThatStartWith(String prefix, int size, boolean ignoreCase) throws IllegalArgumentException {
        Set<String> seto = new HashSet<String>();
        String st;
        for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
            st = it.next();

            if (st.startsWith(prefix) && st.length() >= size) {
                seto.add(st);
            }
        }
        return seto;

    }
}
