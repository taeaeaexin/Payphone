package app.PayPhone.ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class EditPhoneDialog extends JDialog {
	private JTextField idField, modelField, companyField, priceField;
	private JButton saveButton;

	public EditPhoneDialog(JFrame parent, DefaultTableModel tableModel, int rowIndex) { // 선택된 row index
		setTitle("Phone Save Dialog");
		setSize(300, 200);
		setLayout(new GridLayout(5, 2));
		setLocationRelativeTo(parent); // 부모에 맞게

		// 선택된 row 의 각 항목의 값을 구하고 JTextFile 객체를 생성하면서 값을 전달
		String id = (String) tableModel.getValueAt(rowIndex, 0);
		String model = (String) tableModel.getValueAt(rowIndex, 1);
		String company = (String) tableModel.getValueAt(rowIndex, 2);
		String price = (String) tableModel.getValueAt(rowIndex, 3);

		// field
		idField = new JTextField(id);
		modelField = new JTextField(model);
		companyField = new JTextField(company);
		priceField = new JTextField(price);

		// button
		saveButton = new JButton("Save");

		// add field with label, button
		add(new JLabel("Id"));
		add(idField);
		add(new JLabel("Model"));
		add(modelField);
		add(new JLabel("Company"));
		add(companyField);
		add(new JLabel("Price"));
		add(priceField);
		add(new JLabel(""));
		add(saveButton);

		// add button actionListner
		saveButton.addActionListener( e -> {
//			tableModel.setValueAt(bookIdField.getText(), rowIndex, 0); //
			tableModel.setValueAt(idField.getText(), rowIndex, 1);
			tableModel.setValueAt(companyField.getText(), rowIndex, 2);
			tableModel.setValueAt(priceField.getText(), rowIndex, 3);

			dispose();
		});
	}
}