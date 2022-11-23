package photos;

public class Tag {

    private String tagName; //Change to tag type later
    private String tagValue;

    public Tag(String tagName, String tagValue){
        this.tagName = tagName;
        this.tagValue = tagValue;
    }

    public String getTagName(){
        return tagName;
    }
    public String getTagValue(){
        return tagValue;
    }

    //Single Tag-Value pair
    public boolean compareTo(Tag tag1){
        if(tag1.getTagName().equals(tagName) && tag1.getTagValue().equals(tagValue)){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return tagName + " " + tagValue;
    }
}
