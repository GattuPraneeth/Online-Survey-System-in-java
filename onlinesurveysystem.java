import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class SurveyApp extends JFrame {
    private JTextField emailField;
    private JTextArea feedbackArea;
    private JCheckBox anonymousCheckbox;

    // Database connection parameters
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USERNAME = "hr";
    private static final String DB_PASSWORD = "hr";

    public SurveyApp() {
        setTitle("Survey App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Create UI components
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);

        JLabel feedbackLabel = new JLabel("Feedback:");
        feedbackArea = new JTextArea(5, 20);

        anonymousCheckbox = new JCheckBox("Submit Anonymously");

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitFeedback();
            }
        });

        // Set layout
        setLayout(new GridLayout(4, 2));
        add(emailLabel);
        add(emailField);
        add(feedbackLabel);
        add(new JScrollPane(feedbackArea));
        add(anonymousCheckbox);
        add(submitButton);

        setVisible(true);
    }

    private void submitFeedback() {
        String email = emailField.getText();
        String feedback = feedbackArea.getText();
        boolean anonymous = anonymousCheckbox.isSelected();

        // Save feedback to the database
        saveToDatabase(email, feedback, anonymous);

        // Send promotional emails or launch online surveys
        sendPromotionalEmails(email, anonymous);
        launchOnlineSurvey(email, anonymous);

        // Display a confirmation message
        JOptionPane.showMessageDialog(this, "Thank you for your feedback!");

        // Clear input fields
        emailField.setText("");
        feedbackArea.setText("");
        anonymousCheckbox.setSelected(false);
    }

    private void saveToDatabase(String email, String feedback, boolean anonymous) {
        // Insert the feedback into the database
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "INSERT INTO surveydata (email, feedback, anonymous) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, feedback);
            statement.setBoolean(3, anonymous);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendPromotionalEmails(String email, boolean anonymous) {
        // Implement the logic to send promotional emails to the targeted audience
        if (!anonymous) {
            // Send personalized email to the customer
            System.out.println("Sending promotional email to: " + email);
        }
    }

    private void launchOnlineSurvey(String email, boolean anonymous) {
        // Implement the logic to launch online surveys for the targeted audience
        if (!anonymous) {
            // Launch survey for the customer
            System.out.println("Launching online survey for: " + email);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SurveyApp();
            }
        });
    }
}