public class DictEntry {

    private String key;
    private int code;

    public DictEntry(String theKey, int theCode){
        key = theKey;
        code = theCode;
    }

    public String getKey(){
        return key;
    }

    public int getCode(){
        return code;
    }

    public boolean isEqual(DictEntry secondObject){
        return this.key.equals(secondObject.getKey()) && this.code == secondObject.getCode();
    }
}
