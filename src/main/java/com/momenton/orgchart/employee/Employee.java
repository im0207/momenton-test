package com.momenton.orgchart.employee;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Employee implements Comparable<Employee> {

    private final String id;
    private String name;
    private String managerId;
    private boolean provisioned = true;
    private int level;
    private Set<Employee> children = new TreeSet<>();

    public Employee(String name, String id, String managerId) {
        this.id = id;
        this.name = name;
        this.managerId = managerId;
    }

    public boolean isProvisioned() {
        return provisioned;
    }

    protected void setProvisioned(boolean provisioned) {
        this.provisioned = provisioned;
    }

    public String id() {
        return id;
    }
    protected void setName(String name) {
        this.name = name;
    }
    public String name() {
        return name;
    }

    public String getManagerId() {
        return managerId;
    }

    protected void setManagerId(String managerId) {
        this.managerId = managerId;
    }
    public int getLevel() {
        return level;
    }

    protected void setLevel(int level) {
        this.level = level;
    }

    protected void addChildren(Employee employee) {

        this.children.add(employee);
    }

    public Set<Employee> getChildren() {
        return Collections.unmodifiableSet(this.children);
    }

    protected void removeChild(Employee employee) {
        this.children.remove(employee);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Employee)) {
            return false;
        }
        Employee employee = (Employee) obj;
        return this.id().equals(employee.id());
    }


    public Stream<Employee> flattened() {
        return Stream.concat(
                Stream.of(this),
                getChildren().stream().flatMap(Employee::flattened));
    }

    public void print(int maxdepth, int maxweight) {
        String leftAlignFormat = "%-"+(maxdepth +1)+"s";
        System.out.print("|");
        for(int i=0; i<= maxweight; i++){

            if(i == getLevel()) {
                System.out.printf(leftAlignFormat, name);

            } else {
                System.out.printf(leftAlignFormat, "");

            }
            System.out.print("|");
        }
        System.out.println("");
    }

    @Override
    public int compareTo(Employee employee) {
        return this.id.compareTo(employee.id());
    }
}
