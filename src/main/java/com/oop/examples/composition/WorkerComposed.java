package com.oop.examples.encapsulation;

import com.oop.examples.shared.Fotografiable;
import com.oop.examples.shared.Picture;

public class WorkerComposed implements Fotografiable {
    private Person person;
    private String jobTitle;
    private double salary;
    private double bank;
    // Default values
    private static final double DEFAULT_BANK = 0.0;

    // Constructor
    public WorkerComposed(Person person, String jobTitle, double salary) {
        this.person = person;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.bank = DEFAULT_BANK;
    }

    // Constructor with parameters.
    // CAUTION: Please note that this constructor is not the best practice, as it needs updating if the Person class changes.
    public WorkerComposed(String name, int age, String email, String jobTitle, double salary) {
        this.person = new Person(name, age, email);
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.bank = DEFAULT_BANK;
    }

    // Getters & Setters
    public String getJobTitle() {
        return this.jobTitle;
    }

    public String getSalary() {
        return this.salary;
    }

    Public boolean setNewJob(String jobTitle, double salary) {
        boolean flag = setJobTitle(jobTitle);
        if (flag) {
            flag = setSalary(salary);
        }
        return flag;
    }

    private boolean setJobTitle(String jobTitle) {
        if (jobTitle != null && !jobTitle.trim().isEmpty()) {
            this.jobTitle = jobTitle;
            return true;
        }
        return false;
    }

    private boolean setSalary(double salary) {
        if (salary >= 0) {
            this.salary = salary;
            return true;
        }
        return false;
    }

    // Overriding methods from Person class
    @Override
    public Picture takePicture() {
        return person.takePicture();
    }

    @Override
    public void toString() {
        return "Worker{" +
                "jobTitle='" + getJobTitle() + '\'' +
                ", salary=" + getSalary() + '$/h' +
                '}\n' + this.person.toString();
    }

    // class methods
    public void work(int horas) {
        System.out.println("Trabajando " + horas + " horas...");
        postWork(horas);
    }

    private void postWork(int horas) {
        System.out.println("Tras trabajar por " + horas + " horas, " + person.getName() + " ha generado $" + (horas * getSalary()) + ".");
        this.bank += (horas * getSalary());
    }
}