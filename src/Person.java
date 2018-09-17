/*
Author: Patrick Sullivan

A class to support the members of a family tree.

 */
import java.util.ArrayList;
import java.util.Collections;

public class Person {
    private String name;
    private Person currSpouse;
    private ArrayList<String> allSpouses;
    private Person parent1;
    private Person parent2;
    private ArrayList<String> children;

    //Name Constructor
    public Person(String name){
        this.name = name;
        this.currSpouse = null;
        this.allSpouses = new ArrayList<>();
        this.parent1 = null;
        this.parent2 = null;
        this.children = new ArrayList<>();
    }

    //Child Constructor
    public Person(String name, Person inParent1, Person inParent2){
        this.name = name;
        this.currSpouse = null;
        this.allSpouses = new ArrayList<>();
        this.parent1 = inParent1;
        this.parent2 = inParent2;
        this.children = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public Person getCurrSpouse() {
        return currSpouse;
    }

    public Person getParent1(){
        return parent1;
    }

    public Person getParent2() {
        return parent2;
    }

    public ArrayList<String> getChildren() {
        return children;
    }

    public ArrayList<String> getAllSpouses() { return allSpouses; }

    public void setCurrSpouse(Person spouse) {
        this.currSpouse = spouse;
    }

    public void setParent1(Person parent1) {
        this.parent1 = parent1;
    }

    public void setParent2(Person parent2) {
        this.parent2 = parent2;
    }

    //Add a child to this person's list of children, then sort the list alphabetically
    public void addChild(Person child){
        this.children.add(child.getName());
        Collections.sort(children);
    }

    public void addSpouse(Person spouse){
        if(!allSpouses.contains(spouse.getName())) this.allSpouses.add(spouse.getName());
        Collections.sort(allSpouses);
    }

    //Scan up from the family tree for the mention of p2
    public ArrayList<String> getAncestors(){
        ArrayList<String> ancestors = new ArrayList<>();
        if(parent1 == null && parent2 == null){
            return ancestors;
        } else if(parent1 == null){
            ancestors.add(parent2.getName());
            ancestors.addAll(parent2.getAncestors());
        } else if(parent2 == null){
            ancestors.add(parent1.getName());
            ancestors.addAll(parent1.getAncestors());
        } else {
            ancestors.add(parent1.getName());
            ancestors.add(parent2.getName());
            ancestors.addAll(parent1.getAncestors());
            ancestors.addAll(parent2.getAncestors());
        }

        Collections.sort(ancestors);

        return ancestors;
    }

    public ArrayList<String> getSiblings() {
        ArrayList<String> siblings = new ArrayList<>();
        if(this.getParent1() != null) {
            siblings = this.getParent1().getChildren();
        }
        if(this.getParent2() != null) {
            for (String sib : this.getParent2().getChildren()) {
                if (!siblings.contains(sib)) {
                    siblings.add(sib);
                }
            }
        }
        siblings.remove(this.getName());
        Collections.sort(siblings);
        return siblings;
    }

    //Check if this person is a direct child of the given person
    public boolean isChild(Person p2){
        if(parent1 == null || parent2 == null) return false;
        return parent1.equals(p2) || parent2.equals(p2);
    }

    //Check if this person is the spouse of the given person
    public boolean isSpouse(Person p2){
        if(currSpouse == null) return false;
        return currSpouse.equals(p2);
    }

    //Check if this person is a sibling of the given person (half-siblings count)
    public boolean isSibling(Person p2){
        if(this.equals(p2)) return false;
        else if(parent1 != null && (parent1 == p2.getParent1() || parent1 == p2.getParent2())) return true;
        else if(parent2 != null && (parent2 == p2.getParent1() || parent2 == p2.getParent2())) return true;
        else return false;
    }

    //Check if this person is the ancestor of the given person
    public boolean isAncestor(Person p2){
        return p2.getAncestors().contains(this.name);
    }

    //Check if this person and input p2 share a common ancestor but are not direct ancestors
    public boolean areCousins(Person p2){
        Person p1 = this; //p2.isChild(this) had this referring to p2 instead of the current object
        if(this.equals(p2)){
            return false;
        } else if(this.isChild(p2) || p2.isChild(p1)){
            return false;
        } else {
            ArrayList<String> ancestors1 = this.getAncestors();
            ArrayList<String> ancestors2 = p2.getAncestors();

            if(ancestors1.contains(p2.getName()) || ancestors2.contains(p1.getName())){
                return false;
            }

            for (String anc : ancestors1) {
                if (ancestors2.contains(anc)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean areUnrelated(Person p2){
        Person p1 = this;
        if(this.equals(p2)){
            return true;
        } else if(this.isChild(p2) || p2.isChild(p1)){
            return false;
        } else if(this.isSibling(p2)){
            return false;
        } else if(this.areCousins(p2)) {
            return false;
        } else if(this.isAncestor(p2) || p2.isAncestor(p1)){
            return false;
        } else {
            return true;
        }
    }

    public boolean equals(Person p2){
        return this.getName().equals(p2.getName());
    }

    @Override
    public String toString(){
        return this.name;
    }
}
