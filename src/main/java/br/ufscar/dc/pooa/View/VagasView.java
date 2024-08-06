package br.ufscar.dc.pooa.View;

import br.ufscar.dc.pooa.domain.users.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VagasView {
    private JFrame frame;
    private JPanel panel1;
    private JTextArea jobListingsTextArea;

    public VagasView() {
        createUIComponents();
    }

    public void createUIComponents() {
        frame = new JFrame("Vagas");
        panel1 = new JPanel(); // Initialize panel1
        panel1.setPreferredSize(new Dimension(400, 300)); // Set preferred size for panel1
        panel1.setLayout(new BorderLayout()); // Set layout for panel1

        jobListingsTextArea = new JTextArea("Esta Procurando por uma Vaga para descansar?"); // Initialize jobListingsTextArea
        jobListingsTextArea.setEditable(false); // Make text area non-editable
        panel1.add(new JScrollPane(jobListingsTextArea), BorderLayout.CENTER); // Add text area to panel1

        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 400));
        frame.setVisible(true);
        frame.pack();

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem loginItem = new JMenuItem("Login");
        JMenuItem createAccountItem = new JMenuItem("Create Account");
        JMenuItem viewJobsItem = new JMenuItem("Quartos Disponíveis");

        // Add menu items to menu
        optionsMenu.add(loginItem);
        optionsMenu.add(createAccountItem);
        optionsMenu.add(viewJobsItem);
        menuBar.add(optionsMenu);
        frame.setJMenuBar(menuBar);

        // Add action listeners
        loginItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginDialog();
            }
        });

        createAccountItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreateAccountDialog();
            }
        });

        viewJobsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showVagasScreen();
            }
        });
    }

    private void showLoginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = new Object[] {
                "Username:", usernameField,
                "Password:", passwordField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            createRoomAdmin(password, username, panel1);
        } else {
            JOptionPane.showMessageDialog(null, "Login failed!");
        }
    }

    private void showCreateAccountDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = new Object[] {
                "Username:", usernameField,
                "Password:", passwordField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Create Account", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            // Handle account creation logic here
        }
    }

    private void showVagasScreen() {
        panel1.removeAll(); // Clear the existing content

        JTextArea vagasTextArea = new JTextArea("Quartos Disponíveis:\n");
        vagasTextArea.setEditable(false);
        panel1.add(new JScrollPane(vagasTextArea), BorderLayout.CENTER);

        JButton quero_vagaButton = new JButton("Quero Vaga");
        panel1.add(quero_vagaButton, BorderLayout.SOUTH);

        panel1.revalidate();
        panel1.repaint();
    }

    private void createRoomAdmin(String password, String username, JPanel panel1) {
        if (username.equals("admin") && password.equals("admin")) {
            JOptionPane.showMessageDialog(null, "Login successful!");
            panel1.removeAll(); // Clear the existing content

            JTextArea adminTextArea = new JTextArea("Criar quartos:\n");
            adminTextArea.setEditable(false);
            panel1.add(new JScrollPane(adminTextArea), BorderLayout.CENTER);

            JTextField roomTypeField = new JTextField();
            JTextField roomCapacityField = new JTextField();
            JTextField roomPriceField = new JTextField();
            JTextField roomDescriptionField = new JTextField();
            JTextField roomLengthField = new JTextField();
            JTextField roomWidthField = new JTextField();
            JTextField roomHeightField = new JTextField();
            Object[] roomMessage = new Object[]{
                    "Tipo de Quarto:", roomTypeField,
                    "Capacidade:", roomCapacityField,
                    "Preço:", roomPriceField,
                    "Descrição:", roomDescriptionField,
                    "Comprimento:", roomLengthField,
                    "Largura:", roomWidthField,
                    "Altura:", roomHeightField
            };
            int roomOption = JOptionPane.showConfirmDialog(null, roomMessage, "Criar Quarto", JOptionPane.OK_CANCEL_OPTION);
            if (roomOption == JOptionPane.OK_OPTION) {
                String roomType = roomTypeField.getText();
                int roomCapacity = Integer.parseInt(roomCapacityField.getText());
                float roomPrice = Float.parseFloat(roomPriceField.getText());
                String roomDescription = roomDescriptionField.getText();
                float roomLength = Float.parseFloat(roomLengthField.getText());
                float roomWidth = Float.parseFloat(roomWidthField.getText());
                float roomHeight = Float.parseFloat(roomHeightField.getText());
                Admin admin = Admin.getInstance();
                if (admin.createRoom(roomType, roomCapacity, roomPrice, roomDescription, roomLength, roomWidth, roomHeight)) {
                    JOptionPane.showMessageDialog(null, "Quarto criado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Falha ao criar quarto!");
                }

            }

            JButton criarQuarto = new JButton("Criar quarto");
            criarQuarto.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showCreateRoomDialog();
                }
            });
            panel1.add(criarQuarto, BorderLayout.SOUTH);
            panel1.revalidate();
            panel1.repaint();
        }
    }


        private void showCreateRoomDialog() {
                JFrame createRoomFrame = new JFrame("Criar Quarto");
                JPanel createRoomPanel = new JPanel();
                createRoomPanel.setLayout(new GridLayout(8, 2));

                JTextField roomTypeField = new JTextField();
                JTextField roomCapacityField = new JTextField();
                JTextField roomPriceField = new JTextField();
                JTextField roomDescriptionField = new JTextField();
                JTextField roomLengthField = new JTextField();
                JTextField roomWidthField = new JTextField();
                JTextField roomHeightField = new JTextField();

                createRoomPanel.add(new JLabel("Tipo de Quarto:"));
                createRoomPanel.add(roomTypeField);
                createRoomPanel.add(new JLabel("Capacidade:"));
                createRoomPanel.add(roomCapacityField);
                createRoomPanel.add(new JLabel("Preço:"));
                createRoomPanel.add(roomPriceField);
                createRoomPanel.add(new JLabel("Descrição:"));
                createRoomPanel.add(roomDescriptionField);
                createRoomPanel.add(new JLabel("Comprimento:"));
                createRoomPanel.add(roomLengthField);
                createRoomPanel.add(new JLabel("Largura:"));
                createRoomPanel.add(roomWidthField);
                createRoomPanel.add(new JLabel("Altura:"));
                createRoomPanel.add(roomHeightField);

                JButton submitButton = new JButton("Criar");
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String roomType = roomTypeField.getText();
                        int roomCapacity = Integer.parseInt(roomCapacityField.getText());
                        float roomPrice = Float.parseFloat(roomPriceField.getText());
                        String roomDescription = roomDescriptionField.getText();
                        float roomLength = Float.parseFloat(roomLengthField.getText());
                        float roomWidth = Float.parseFloat(roomWidthField.getText());
                        float roomHeight = Float.parseFloat(roomHeightField.getText());
                        Admin admin = Admin.getInstance();
                        if(admin.createRoom(roomType, roomCapacity, roomPrice, roomDescription, roomLength, roomWidth, roomHeight)){
                            JOptionPane.showMessageDialog(createRoomFrame, "Quarto criado com sucesso!");
                        } else {
                            JOptionPane.showMessageDialog(createRoomFrame, "Falha ao criar quarto!");
                        }
                    }
                });

                createRoomPanel.add(submitButton);
                createRoomFrame.add(createRoomPanel);
                createRoomFrame.pack();
                createRoomFrame.setVisible(true);
    }
}


