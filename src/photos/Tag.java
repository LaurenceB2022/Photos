package photos;

import java.util.ArrayList;

public class Tag {

    private String tagName; //Change to tag type later
    private ArrayList<String> tagValues;

    public Tag(String tagName, ArrayList<String> tag_values){
        this.tagName = tagName;
        this.tagValues = tag_values;
    }

    public String getTagName(){
        return tagName;
    }
    public ArrayList<String> getTagValues(){
        return tagValues;
    }
    public void addTagValue(String tag_value){
        tagValues.add(tag_value);
    }

    //Single Tag-Value pair
    public boolean compareTo(String tag_type, String tag_value){
        for(int index = 0; index < tagValues.size(); index++){
            if(tag_type.equals(tagName) && (tag_value.compareTo(tagValues.get(index)) == 0)){
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString(){
        return tagName + " " + tagValues.get(0);
    }
}
