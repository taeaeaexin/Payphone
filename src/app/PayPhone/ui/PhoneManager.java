package app.PayPhone.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame; // windows application
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import app.PayPhone.dao.PhoneDao;
import app.PayPhone.dto.Phone;

public class PhoneManager extends JFrame {
	private JTable table; // grid ui component
	private DefaultTableModel tableModel;// grid data
	private JButton searchButton, addButton, editButton, listButton;
	private JTextField searchWordField;
	
	private PhoneDao phoneDao = new PhoneDao();
	
	public PhoneManager() {
		// 화면 UI 와 관련된 설정
		setTitle("Phone Manager");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		// table
		tableModel = new DefaultTableModel(new Object[] {"id", "model", "company", "price" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are not editable
            }
		};
		table = new JTable(tableModel);
		
		phoneDao.listPhone();
		
		// search
		Dimension  textFieldSize = new Dimension(400, 28);
		searchWordField = new JTextField();
		searchWordField.setPreferredSize(textFieldSize);
		
		searchButton = new JButton("검색");
		
		JPanel searchPanel = new JPanel();
		searchPanel.add(new JLabel("제목 검색"));
		searchPanel.add(searchWordField);
		searchPanel.add(searchButton);
		
		// button
		addButton = new JButton("등록");
		editButton = new JButton("수정 & 삭제");
		listButton = new JButton("목록");
		
		// button 2개를 담는 JPanel 객체를 만들고 그 객체를 BookManager 에 담는다.
		JPanel buttonPanel = new JPanel(); // default layout : Flow Layout
		buttonPanel.add(addButton);
		buttonPanel.add(editButton);
		buttonPanel.add(listButton);
		

		// table, buttonPnel 을 BookManager 에 붙인다. 
		// BookManager 의 layout 에 따라 결정
		
		// BookManager 의 layout 설정 
		setLayout(new BorderLayout());
		add(searchPanel, BorderLayout.NORTH);
		add(new JScrollPane(table), BorderLayout.CENTER); // table < scroll pane < jframe
		add(buttonPanel, BorderLayout.SOUTH);
		
		// button action event 처리
		searchButton.addActionListener( e -> {
			String searchWord = searchWordField.getText();
			if( !searchWord.isBlank() ) {
				this.listPhone(searchWord);
			}
		});
		
		
		addButton.addActionListener( e -> {
			// AddBookDialog 를 띄운다.
			AddPhoneDialog addDialog = new AddPhoneDialog(this, this.tableModel);
			addDialog.setVisible(true);
		}); 
		
		editButton.addActionListener(e -> {
			// table 에 선택된 row 가 있으면 EditBookDialog 를 띄운다.
			// table 에 선택된 row
			int selectedRow = table.getSelectedRow();
			if( selectedRow >= 0 ) {
				EditPhoneDialog editDialog = new EditPhoneDialog(this, this.tableModel, selectedRow);
				editDialog.setVisible(true);
			}else {
				JOptionPane.showMessageDialog(this, "도서를 선택하세요.");
			}
			
		});
		
		listButton.addActionListener( e -> listPhone() );
		
		table.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
            	// double click
            	if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        EditPhoneDialog editDialog = new EditPhoneDialog(PhoneManager.this, tableModel, selectedRow);
                        editDialog.setVisible(true);
                    }
            	}
            }
		});
	}
	
	private void clearTable() {
		tableModel.setRowCount(0);
	}
	
	private void listPhone() {
		// 현재 tableModel 을 정리하고 
		clearTable();
		
		List<Phone> phoneList = phoneDao.listPhone();
		
		for (Phone phone : phoneList) {
			tableModel.addRow(new Object[] {phone.getId(), phone.getModel(), phone.getCompany(), phone.getPrice() });
		}
	}
	
	private void listPhone(String searchWor) {
		// 현재 tableModel 을 정리하고 
		clearTable();
		
		List<Phone> phoneList = phoneDao.listPhone(searchWor);
		
		for (Phone phone : phoneList) {
			tableModel.addRow(new Object[] {phone.getId(), phone.getModel(), phone.getCompany(), phone.getPrice() });
		}
	}
	
	Phone detailPhone(int bookId) {
		return phoneDao.detailPhone(bookId);
	}
	
	void insertPhone(Phone phone) {
		int ret = phoneDao.insertPhone(phone);
		if( ret == 1 ) {
			listPhone();
		}
	}
	
	void updatePhone(Phone phone) {
		int ret = phoneDao.updatePhone(phone);
		if( ret == 1 ) {
			listPhone();
		}
	}
	
	void deletePhone(int bookId) {
		int ret = phoneDao.deletePhone(bookId);
		if( ret == 1 ) {
			listPhone();
		}
	}
	
	public static void main(String[] args) {
		// main() thread 와 별개로 별도의 thread 로 화면을 띄운다.
		// thread 처리를 간단히 해주는 utility method 제공
		// invokeLater( thread 객체 <- runnable interface 를 구현한 <- runnable interface 가 functional interface
		//  결과적으로 invokeLater( lambda 식 표현 객체 )
		SwingUtilities.invokeLater( () -> {
			new PhoneManager().setVisible(true);
		});
		
	}

}
