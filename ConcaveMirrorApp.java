import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ConcaveMirrorApp extends JFrame {
    private JTextField txtU, txtV;
    private JButton btnCalculate, btnReset;
    private JTable table;
    private DefaultTableModel model;

    public ConcaveMirrorApp() {
        setTitle("Concave Mirror Focal Length Calculator");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------- Top Input Panel ----------
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Distances (in cm)"));

        inputPanel.add(new JLabel("Object Distance (u): "));
        txtU = new JTextField();
        inputPanel.add(txtU);

        inputPanel.add(new JLabel("Image Distance (v): "));
        txtV = new JTextField();
        inputPanel.add(txtV);

        add(inputPanel, BorderLayout.NORTH);

        // ---------- Table Panel ----------
        model = new DefaultTableModel(new String[]{"u (cm)", "v (cm)", "f (cm)", "Status"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ---------- Bottom Buttons Panel ----------
        JPanel buttonPanel = new JPanel();
        btnCalculate = new JButton("Calculate");
        btnReset = new JButton("Reset");

        buttonPanel.add(btnCalculate);
        buttonPanel.add(btnReset);
        add(buttonPanel, BorderLayout.SOUTH);

        // ---------- Button Actions ----------
        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateFocalLength();
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtU.setText("");
                txtV.setText("");
                model.setRowCount(0); // clear table
            }
        });
    }

    private void calculateFocalLength() {
        try {
            double u = Double.parseDouble(txtU.getText());
            double v = Double.parseDouble(txtV.getText());

            // Calculate focal length
            double f = 1 / ((1 / v) + (1 / u));

            // Determine status and icon
            String status;
            ImageIcon icon;

            if (f >= 21.0 && f <= 25.0) {
                status = "Acceptable ✅";
                icon = new ImageIcon(UIManager.getIcon("OptionPane.informationIcon").toString());
            } else {
                status = "Not Acceptable ❌";
                icon = new ImageIcon(UIManager.getIcon("OptionPane.errorIcon").toString());
            }

            // Add result to table
            model.addRow(new Object[]{u, v, String.format("%.2f", f), status});

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numeric values for u and v.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConcaveMirrorApp().setVisible(true));
    }
}
