package com.bax.interview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class Hierarchy {

    static class Person {
        String name;
        String mName;

        public Person(final String name, final String mName) {
            this.name = name;
            this.mName = mName;
        }

        public String getName() {
            return name;
        }

        public String getmName() {
            return mName;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static class TreeNode {
        String name;
        Collection<TreeNode> reports = new ArrayList<>();

        public TreeNode(final String name) {
            this.name = name;
        }

        void addReport(TreeNode report) {
            reports.add(report);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static void main(String[] args) {
        Collection<Person> people = new ArrayList<>();
        people.add(new Person("Paul", "PaulM"));
        people.add(new Person("PaulM", "PaulMM"));
        people.add(new Person("Jerry", "JerryM"));
        people.add(new Person("JerryM", "JerryMM"));
        people.add(new Person("JerryMM", "CEO"));
        people.add(new Person("PaulMM", "CEO"));
        people.add(new Person("CEO", null));
        Hierarchy h = new Hierarchy();
        TreeNode ceo = h.buildHierrchy(people);
        printTree(ceo);
    }

    private static void printTree(final TreeNode ceo) {
        printRecursively(ceo, "");
    }

    private static void printRecursively(final TreeNode node, String indentation) {
        System.out.println(indentation + node.name);
        for (TreeNode report : node.reports) {
            printRecursively(report, indentation + "  ");
        }
    }

    TreeNode buildHierrchy(Collection<Person> people) {
        Map<String, TreeNode> personMap = people.stream().collect(Collectors.toMap(Person::getName, p -> new TreeNode(p.getName())));
        TreeNode root = null;
        for (Person person : people) {
            if (person.getmName() == null) {
                root = personMap.get(person.getName());
                continue;
            }
            personMap.get(person.getmName()).addReport(personMap.get(person.getName()));
        }
        return root;
    }

}
