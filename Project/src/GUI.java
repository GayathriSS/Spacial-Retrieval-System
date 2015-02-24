import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame {
	DefaultTableModel model;
	JTable table;
	String col[] = { "SNo", "Content", "URL", "Judgement" };
	private JTextField txtTypeYourQuery;
	
	
	public static void main_gui() {
		new GUI().start();
	}

	public void start() {
		JPanel pane1 = new JPanel();
		JPanel pane2 = new JPanel();
		
		model = new DefaultTableModel(col, 2);
		table = new JTable() {
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
		};
		
		
		table.setCellSelectionEnabled(true);
		JScrollPane pane = new JScrollPane(table);
		
		txtTypeYourQuery = new JTextField("", 10);
		txtTypeYourQuery.setBounds(10, 10, 200, 25);
		
		pane1.add(txtTypeYourQuery);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				MainClass.buttonClicked(txtTypeYourQuery.getText());
				
				
				try
				{
				int rowCount = model.getRowCount();
				//Remove rows one by one from the end of the table
				for (int j = rowCount - 1; j >= 0; j--) {
				    model.removeRow(j);
				}
				
				table.getColumnModel().getColumn(0).setMaxWidth(30);
				
				int i =1;
				
				//System.out.println(MainClass.positive);
				for (String doc_id : MainClass.positive) {
					DocumentV doc_var = MainClass.doc_hashmap.get(doc_id);
					model.addRow(new Object[] { i,doc_var.paragraph.trim(),doc_var.url.trim(),"POSITIVE" });
					i++;
				}
				
				for (String doc_id : MainClass.negative) {
					DocumentV doc_var = MainClass.doc_hashmap.get(doc_id);
					model.addRow(new Object[] { i,doc_var.paragraph.trim(),doc_var.url.trim(),"NEGATIVE" });
					i++;
				}
				 model.fireTableDataChanged();
				MainClass.clearAll();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(220, 11, 89, 23);
		
		table.setModel(model);
		pane1.add(btnNewButton);
		
		pane2.add(pane);
		
		add(pane1);
		add(pane2);
		
		setVisible(true);
		setSize(500, 400);
		setLayout(new FlowLayout());
		setTitle("IR Project - Team 8");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}