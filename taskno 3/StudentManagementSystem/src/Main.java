import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    // Getters and setters (optional)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade;
    }
}

class StudentManagementSystem extends JFrame {
    private ArrayList<Student> students;

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadStudentData();

        setTitle("Student Management System");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JList<Student> studentList = new JList<>(students.toArray(new Student[0]));
        mainPanel.add(new JScrollPane(studentList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
                studentList.setListData(students.toArray(new Student[0]));
                saveStudentData();
            }
        });

        JButton removeButton = new JButton("Remove Student");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeStudent(studentList.getSelectedValue());
                studentList.setListData(students.toArray(new Student[0]));
                saveStudentData();
                }

        });
        JButton searchButton = new JButton("Search Student");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchStudent();
            }
        });
        JButton editButton = new JButton("Edit Student");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student selectedStudent = studentList.getSelectedValue();
                studentList.setListData(students.toArray(new Student[0]));
                editStudent(selectedStudent);
                saveStudentData();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(editButton);

        mainPanel.add(buttonPanel, BorderLayout.EAST);

        add(mainPanel);
    }

    private void searchStudentData() {
    }

    private void addStudent() {
        String name = JOptionPane.showInputDialog(this, "Enter student name:");
        if (name == null || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String nameRegex = "^[a-zA-Z]+$";
        if (!name.matches("^[a-zA-Z\\s]+$")) { // Check if the name contains only letters and spaces
            JOptionPane.showMessageDialog(this, "Name must contain only characters and spaces.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!name.matches(".*\\S.*")) {
            JOptionPane.showMessageDialog(this, "Name must contain at least one non-space character.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        String rollNumberStr = JOptionPane.showInputDialog(this, "Enter roll number:");
        int rollNumber;
        try {
            rollNumber = Integer.parseInt(rollNumberStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid roll number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String grade = JOptionPane.showInputDialog(this, "Enter grade:");
        if (grade == null || grade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Grade cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String gradeRegex = "^[ABCDF]$";
        if (!grade.matches(gradeRegex)) {
            JOptionPane.showMessageDialog(this, "Invalid grade! Grade should be one of the following: A, B, C, D, or F.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        students.add(new Student(name, rollNumber, grade));
    }

    private void removeStudent(Student student) {
        if (student == null) {
            JOptionPane.showMessageDialog(this, "Select a student to remove!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirmResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this student?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
        if (confirmResult == JOptionPane.YES_OPTION) {
            students.remove(student);
            JOptionPane.showMessageDialog(this, "Student removed successfully!", "Removal Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void searchStudent() {
        String nameToSearch = JOptionPane.showInputDialog(this, "Enter student name to search:");
        if (nameToSearch == null || nameToSearch.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name to search!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<Student> matchingStudents = new ArrayList<>();

        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(nameToSearch)) {
                matchingStudents.add(student);
            }
        }
        if (!matchingStudents.isEmpty()) {
            StringBuilder message = new StringBuilder("Search Result:\n");
            for (Student student : matchingStudents) {
                message.append(student.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, message.toString(), "Search Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Student not found!", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void loadStudentData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"))) {
            students = (ArrayList<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading student data: " + e.getMessage());
        }
    }
    private void editStudent(Student student) {
        JTextField nameField = new JTextField(student.getName());
        JTextField rollNumberField = new JTextField(String.valueOf(student.getRollNumber()));
        JTextField gradeField = new JTextField(student.getGrade());

        Object[] message = {
                "Name:", nameField,
                "Roll Number:", rollNumberField,
                "Grade:", gradeField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String rollNumberStr = rollNumberField.getText();
            String grade = gradeField.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String nameRegex = "^[a-zA-Z]+$";
            if (!name.matches(nameRegex)) {
                JOptionPane.showMessageDialog(this, "Invalid name! Name should contain only alphabetic characters.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int rollNumber;
            try {
                rollNumber = Integer.parseInt(rollNumberStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid roll number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (grade.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Grade cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String gradeRegex = "^[ABCDF]$";
            if (!grade.matches(gradeRegex)) {
                JOptionPane.showMessageDialog(this, "Invalid grade! Grade should be one of the following: A, B, C, D, or F.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            student.setName(name);
            student.setRollNumber(rollNumber);
            student.setGrade(grade);
            JOptionPane.showMessageDialog(this, "Student information updated successfully!", "Edit Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void saveStudentData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("students.dat"))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error writing student data: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentManagementSystem().setVisible(true);
            }
        });
    }
}
