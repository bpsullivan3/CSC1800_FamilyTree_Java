/*
Author: Patrick Sullivan

The main, runnable class for the Family Tree Project
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Sullivan {

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        FamilyTree tree = new FamilyTree();
        String query = null;
        try {
            query = in.readLine();
        } catch(IOException e) {
            System.out.println(e);
        }
        while(query != null){
            String[] parts = query.split(" ");
            switch(parts[0]){
                case("E"):
                    if(parts.length == 3){ //Indicates New Marriage
                        Person entry1 = tree.getPerson(parts[1]);
                        Person entry2 = tree.getPerson(parts[2]);

                        //initialize person if not exists
                        if(entry1 == null){
                            entry1 = new Person(parts[1]);
                            tree.addPerson(entry1);
                        }
                        if(entry2 == null){
                            entry2 = new Person(parts[2]);
                            tree.addPerson(entry2);
                        }

                        //If the entry already has a spouse, adjust accordingly
                        if(entry1.getSpouse() != null && !entry1.getSpouse().equals(entry2)){
                            entry1.getSpouse().setSpouse(null);
                        }
                        if(entry2.getSpouse() != null && !entry2.getSpouse().equals(entry1)){
                            entry2.getSpouse().setSpouse(null);
                        }

                        //Finally, set the proper spouse for the entry
                        entry1.setSpouse(entry2);
                        entry2.setSpouse(entry1);
                    }
                    else if(parts.length == 4){ //Indicates New Child
                        Person parent1 = tree.getPerson(parts[1]);
                        Person parent2 = tree.getPerson(parts[2]);

                        //initialize parents if not exists
                        if(parent1 == null){
                            parent1 = new Person(parts[1]);
                            tree.addPerson(parent1);
                        }
                        if(parent2 == null){
                            parent2 = new Person(parts[2]);
                            tree.addPerson(parent2);
                        }

                        //If the parents are not married, marry them the same way as above
                        if(parent1.getSpouse() != null && !parent1.getSpouse().equals(parent2)){
                            parent1.getSpouse().setSpouse(null);
                        }
                        if(parent2.getSpouse() != null && !parent2.getSpouse().equals(parent1)){
                            parent1.getSpouse().setSpouse(null);
                        }
                        parent1.setSpouse(parent2);
                        parent2.setSpouse(parent1);

                        //Create the child
                        if(tree.getPerson(parts[3]) != null){
                            System.out.println(parts[3] + " already exists! Child not created");
                        } else {
                            Person child = new Person(parts[3], parent1, parent2);
                            tree.addPerson(child);
                            parent1.addChild(child);
                            parent2.addChild(child);
                        }
                    } else {
                        System.out.println("Please enter a valid query.");
                    }
                    break;
                case("W"):
                    //List Everyone who is the <relation> of <person> in alphabetical order.
                    //<relation> can be child, spouse, sibling, ancestor, cousin, or unrelated.

                    if(parts.length != 3){
                        System.out.println("Please enter a valid query.");
                        break;
                    }

                    Person entry = tree.getPerson(parts[2]);
                    if(entry == null){
                        entry = new Person(parts[2]);
                        tree.addPerson(entry);
                    }

                    System.out.println(query);

                    switch(parts[1]){
                        case("child"):
                            for(String child : entry.getChildren()){
                                System.out.println(child);
                            }
                            break;
                        case("spouse"):
                            System.out.println(entry.getSpouse());
                            break;
                        case("sibling"):
                            for(String sibling : entry.getSiblings()){
                                System.out.println(sibling);
                            }
                            break;
                        case("ancestor"):
                            for(String ancestor : entry.getAncestors()){
                                System.out.println(ancestor);
                            }
                            break;
                        case("cousin"):
                            for(String cousin : tree.getCousins(entry)){
                                System.out.println(cousin);
                            }
                            break;
                        case("unrelated"):
                            for(String unrelated : tree.getUnrelated(entry)){
                                System.out.println(unrelated);
                            }
                            break;
                        default:
                            System.out.println("Please enter a valid query.");
                            break;
                    }
                    System.out.println();
                    break;
                case("X"):
                    //Is <person1> the <relation> of <person2>?
                    //<relation> can be child, spouse, sibling, ancestor, cousin, or unrelated.

                    if(parts.length != 4){
                        System.out.println("Please enter a valid query.");
                        break;
                    }

                    Person entry1 = tree.getPerson(parts[1]);
                    if(entry1 == null){
                        entry1 = new Person(parts[1]);
                        tree.addPerson(entry1);
                    }
                    Person entry2 = tree.getPerson(parts[3]);
                    if(entry2 == null){
                        entry2 = new Person(parts[3]);
                        tree.addPerson(entry2);
                    }
                    System.out.println(query);

                    switch(parts[2]){
                        case("child"):
                            if(entry1.isChild(entry2)){
                                System.out.println("Yes");
                            } else {
                                System.out.println("No");
                            }
                            break;
                        case("spouse"):
                            if(entry1.isSpouse(entry2)){
                                System.out.println("Yes");
                            } else {
                                System.out.println("No");
                            }
                            break;
                        case("sibling"):
                            if(entry1.isSibling(entry2)){
                                System.out.println("Yes");
                            } else {
                                System.out.println("No");
                            }
                            break;
                        case("ancestor"):
                            if(entry1.isAncestor(entry2)){
                                System.out.println("Yes");
                            } else {
                                System.out.println("No");
                            }
                            break;
                        case("cousin"):
                            if(entry1.areCousins(entry2)){
                                System.out.println("Yes");
                            } else {
                                System.out.println("No");
                            }
                            break;
                        case("unrelated"):
                            if(entry1.areUnrelated(entry2)){
                                System.out.println("Yes");
                            } else {
                                System.out.println("No");
                            }
                            break;
                        default:
                            System.out.println("Please enter a valid query.");
                            break;
                    }
                    System.out.println();
                    break;
                default:
                    System.out.println("Please enter a valid query.");
                    System.out.println();
                    break;
            }
            try {
                query = in.readLine();
            } catch(IOException e) {
                System.out.println(e);
            }
        }
    }
}
