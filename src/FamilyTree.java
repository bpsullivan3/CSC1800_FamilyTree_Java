import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/*
Author: Patrick Main

A class to keep track of and manage a family tree of objects of type Person
 */
public class FamilyTree {
    private HashMap<String, Person> tree;

    public FamilyTree(){
        tree = new HashMap<>();
    }

    public void addPerson(Person p){
        tree.put(p.getName(), p);
    }

    public Person getPerson(String key){
        return tree.get(key);
    }

    public ArrayList<String> getCousins(Person p){
        ArrayList<String> cousins = new ArrayList<>();
        for(Person entry : tree.values()){
            if(entry.areCousins(p)){
                cousins.add(entry.getName());
            }
        }
        Collections.sort(cousins);
        return cousins;
    }

    public ArrayList<String> getUnrelated(Person p){
        ArrayList<String> unrelated = new ArrayList<>();
        for(Person entry : tree.values()){
            if(entry.areUnrelated(p)){
                unrelated.add(entry.getName());
            }
        }
        Collections.sort(unrelated);
        return unrelated;
    }
}
