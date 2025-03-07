package app.PayPhone.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AddPhoneDialog extends JDialog {
	private JTextField idField, modelField, companyField, priceField;
	private JButton addButton;

	public AddPhoneDialog(JFrame parent, DefaultTableModel tableModel) {
		setTitle("Phone Add Dialog");
		setSize(300, 200);
		setLayout(new BorderLayout());
		setLocationRelativeTo(parent); // 부모에 맞게

		// input panel
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(4, 2));

		// field 초기화
		idField = new JTextField();
		modelField = new JTextField();
		companyField = new JTextField(); // 이 부분 추가
		priceField = new JTextField();

		// add field with label, button
		inputPanel.add(new JLabel("Id"));
		inputPanel.add(idField);
		inputPanel.add(new JLabel("Model"));
		inputPanel.add(modelField);
		inputPanel.add(new JLabel("Company"));
		inputPanel.add(companyField); // 이 부분 추가
		inputPanel.add(new JLabel("Price"));
		inputPanel.add(priceField);

		// button panel
		JPanel buttonPanel = new JPanel();

		// button
		addButton = new JButton("Add");

		buttonPanel.add(addButton);

		// add inputPanel, buttonPanel to Dialog
		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		// add button actionListener
		addButton.addActionListener(e -> {
			String id = idField.getText();
			String model = modelField.getText();
			String company = companyField.getText();
			String price = priceField.getText();

			tableModel.addRow(new Object[]{id, model, company, price});

			dispose();
		});
	}
}