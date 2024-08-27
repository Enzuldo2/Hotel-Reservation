package br.ufscar.dc.pooa.View;




import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

import static br.ufscar.dc.pooa.View.UserView.getjLabel;

public class MainApp {
    private JFrame frame;
    private JPanel panel1;

    public MainApp() {
        initializeUI();
    }

    private void initializeUI() {
        frame = createFrame("Bem vindo ao APP do nosso Hotel", 500, 400);
        panel1 = createPanel(new BorderLayout(), new Dimension(400, 300));
        frame.setContentPane(panel1);
        createMenuBarBasic();
    }



    private JFrame createFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(width, height));
        frame.setVisible(true);
        return frame;
    }

    private JPanel createPanel(LayoutManager layout, Dimension dimension) {
        JPanel panel = new JPanel(layout);
        panel.setPreferredSize(dimension);
        return panel;
    }

    private void createMenuBarBasic() {
        panel1.removeAll();
        JLabel welcomeLabel = createWelcomeLabel("C:\\Users\\enzod\\Desktop\\nova_foto.jpeg", "Bem-vindo ao Nosso Hotel!");
        panel1.add(welcomeLabel, BorderLayout.CENTER);
        frame.setJMenuBar(createMenuBarBasicItems());
        createMenuBarBasicItems();
        refreshPanel();
    }

    protected JLabel createWelcomeLabel(String imagePath, String welcomeMessage) {
        return getjLabel(imagePath, welcomeMessage);
    }

    private JMenuBar createMenuBarBasicItems() {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");

        JMenuItem loginItem = createMenuItem("Login", e -> showLoginDialog());
        JMenuItem createAccountItem = createMenuItem("Create Account", e -> {
            try {
                UserView.showCreateAccountDialog();
            } catch (SQLException | ClassNotFoundException | ParseException ex) {
                showErrorDialog(ex);
            }
        });
        JMenuItem viewJobsItem = createMenuItem("Informações Sobre Disponibilidade de Quartos", e -> {
            try {
                viewQuartos();
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        optionsMenu.add(loginItem);
        optionsMenu.add(createAccountItem);
        optionsMenu.add(viewJobsItem);
        menuBar.add(optionsMenu);

        return menuBar;
    }

    private void viewQuartos() throws SQLException, ClassNotFoundException {
        panel1.removeAll();
        JTextArea vagasTextArea = createTextArea("Caso Tenha Quartos disponiveis Para Reserva Hoje:\n" +
                "Quartos do tipo Familia por 120 reais a diaria, do tipo Single por 70 reais e do tipo Suite por 200 reais a diaria\n"+
                "Caso queira reservar um quarto, por favor, faça o login ou vá a recepção do Hotel.\n\n");
        panel1.add(new JScrollPane(vagasTextArea), BorderLayout.CENTER);

        UserView.viewQuartoSemLogin();

        refreshPanel();
    }



    private JMenuItem createMenuItem(String text, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(listener);
        return menuItem;
    }

    private JTextArea createTextArea(String text) {
        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        return textArea;
    }

    private void showLoginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {"Username:", usernameField, "Password:", passwordField};

        if (showConfirmDialog("Login", message)) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (isAdmin(username, password)) {
                showMessageDialog("Login successful!");
                new AdminView();
                closeWindow();
            } else {
                userLogin(username, password);
            }
        } else {
            showMessageDialog("Login failed!");
        }

    }

    private void refreshPanel() {
        panel1.revalidate();
        panel1.repaint();
    }

    private boolean showConfirmDialog(String title, Object[] message) {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION;
    }

    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private boolean isAdmin(String username, String password) {
        return "admin".equals(username) && "admin".equals(password);
    }

    private void userLogin(String username, String password) {
        UserView.login(username, password);
    }

    private void showErrorDialog(Exception ex) {
        JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void closeWindow() {
        frame.dispose();
    }
}
