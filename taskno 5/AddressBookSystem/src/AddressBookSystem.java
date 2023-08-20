
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    // Getters and setters (you can also generate these using your IDE)

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + emailAddress;
    }
}

class AddressBook implements Serializable {
    private ArrayList<Contact> contacts;

    public AddressBook() {
        contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public Contact searchContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    public ArrayList<Contact> getAllContacts() {
        return contacts;
    }
}

public class AddressBookSystem extends JFrame {
    private AddressBook addressBook;
    private JTextField nameField, phoneField, emailField;

    private void removeContact() {
        String name = nameField.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name to remove.");
            return;
        }

        Contact contactToRemove = addressBook.searchContact(name);
        if (contactToRemove != null) {
            addressBook.removeContact(contactToRemove);
            saveAddressBookData();
            JOptionPane.showMessageDialog(this, "Contact removed successfully.");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found.");
        }
    }
    public AddressBookSystem() {
        addressBook = new AddressBook();
        loadAddressBookData();

        setTitle("Address Book");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 3, 3, 3));

        JLabel nameLabel = new JLabel("Name:");
        JLabel phoneLabel = new JLabel("Phone:");
        JLabel emailLabel = new JLabel("Email:");

        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();

        JButton addButton = new JButton("Add Contact");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        JButton searchButton = new JButton("Search Contact");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchContact();
            }
        });

        JButton displayButton = new JButton("Display All Contacts");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllContacts();
            }
        });
        JButton removeButton = new JButton("Remove Contact");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeContact();
            }
        });

        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(phoneLabel);
        mainPanel.add(phoneField);
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);
        mainPanel.add(addButton);
        mainPanel.add(searchButton);
        mainPanel.add(removeButton);

        add(mainPanel, BorderLayout.CENTER);
        add(displayButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addContact() {
        String name = nameField.getText();
        String phoneNumber = phoneField.getText();
        String emailAddress = emailField.getText();

        if (name.isEmpty() || phoneNumber.isEmpty() || emailAddress.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        Contact newContact = new Contact(name, phoneNumber, emailAddress);
        addressBook.addContact(newContact);
        saveAddressBookData();
        JOptionPane.showMessageDialog(this, "Contact added successfully.");
        clearFields();
    }

    private void searchContact() {
        String name = nameField.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name to search.");
            return;
        }

        Contact contact = addressBook.searchContact(name);
        if (contact != null) {
            JOptionPane.showMessageDialog(this, contact.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found.");
        }
    }

    private void displayAllContacts() {
        ArrayList<Contact> contacts = addressBook.getAllContacts();
        if (contacts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Address book is empty.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Contact contact : contacts) {
            sb.append(contact.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    private void loadAddressBookData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("address_book_data.ser"))) {
            addressBook = (AddressBook) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // If the file doesn't exist or cannot be loaded, create a new address book.
            addressBook = new AddressBook();
        }
    }

    private void saveAddressBookData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("address_book_data.ser"))) {
            oos.writeObject(addressBook);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddressBookSystem();
            }
        });
    }
}
