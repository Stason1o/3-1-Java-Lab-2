import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.lang.Object;

@SuppressWarnings("serial")
public class MyJPanel extends JPanel {
	
	JMenuBar menuBar;
	JMenu menu;
	
	JMenuItem create;
	JMenuItem open;
	JMenuItem exit;
	
	JButton edit;
	JButton save;
	JButton rename;
	JButton add;
	JButton delete;
	
	JCheckBox priority;
	JList<Note> componentsList;
	JFileChooser fc;
	JTextArea log;
	
	ArrayList<Note> mainList;
	DefaultTableModel tableModel;
	JTable table;
	TableModelListener tableListener;
	static Object[][] data;
	
	JScrollPane logScrollPane;
	JScrollPane scrollPane;
	JOptionPane optPane;

	WorkWithFile wWFile;
	
	private static final Object[] columnNames = { "Record", "Day", "Month", "Year", "Importance" };
	private static final String newline = "\n";

	public MyJPanel() {
		initComponents();
		
		addListeners();
		add(menuBar, BorderLayout.CENTER);
		add(save,BorderLayout.CENTER);
		add(add,BorderLayout.CENTER);
		add(delete,BorderLayout.CENTER);
		add(priority,BorderLayout.CENTER);
		add(scrollPane,BorderLayout.CENTER);
	}
	
	private void initComponents(){
		menuBar = new JMenuBar();
		menu = new JMenu();
		create = new JMenuItem();
		open = new JMenuItem();
		exit = new JMenuItem();
		add = new JButton("Add");
		edit = new JButton("Edit");
		save = new JButton("Save");
		delete = new JButton("Delete");
		priority = new JCheckBox("Mark as important");
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        mainList = new ArrayList<Note>();		
        
        data = new Object[0][5];
        tableModel = new DefaultTableModel(data, columnNames);  
        table = new JTable(tableModel);
        table.setCellSelectionEnabled(true);
        scrollPane = new JScrollPane(table);
		
		menu.add(create);
		menu.addSeparator();
		menu.add(open);
		menu.addSeparator();
		menu.add(exit);
		menuBar.add(menu);
		
		menu.setText("Menu");
		create.setText("Create File");
		open.setText("Open File");
		exit.setText("Exit");
		
		setSize();
	}
	
	private void setSize(){
		menuBar.setPreferredSize(new Dimension(590,50));
		menu.setPreferredSize(new Dimension(100,90));
		create.setPreferredSize(new Dimension(90,30));
		open.setPreferredSize(new Dimension(100,30));
		exit.setPreferredSize(new Dimension(100,30));	
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setPreferredSize(new Dimension(380,300));
        scrollPane.setPreferredSize(new Dimension(380,400));
	}
	
	private void addListeners(){
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				create();
			}
		});
		
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				open();
			}
		});
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		priority.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				priority();
			}
		});

		table.getModel().addTableModelListener(new TableModelListener() {

		      public void tableChanged(TableModelEvent e) {
		         System.out.println(e);
		      }
		    });
		
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		
		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				add();
			}
		});
	}

	private void create() { // create file
		int returnVal = fc.showSaveDialog(MyJPanel.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            log.append("Saving: " + file.getName() + "." + newline);
            WorkWithFile wwFile = new WorkWithFile(file.getName());
            wwFile.writeInFile();
        } else {
            log.append("Save command cancelled by user." + newline);
        }
        log.setCaretPosition(log.getDocument().getLength());
	}
	
	private void open() { // open file
		int returnVal = fc.showOpenDialog(MyJPanel.this);
				
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File filename = fc.getSelectedFile();
			wWFile = new WorkWithFile(filename.getName());
			wWFile.setFile(fc.getSelectedFile());
			log.append("Opening: " + wWFile.getName() + "." + newline);
			
			wWFile.readFile(wWFile.file.getName());
			mainList = wWFile.getList();
			
			data = new Object[mainList.size()][5];
			data[0] = columnNames;
			
			mainList.forEach(item -> {
				int i = mainList.indexOf(item);
				data[i][0] = item.getRecord();
        		data[i][1] = item.getDay();
				data[i][2] = item.getMonth();
				data[i][3] = item.getYear();
				data[i][4] = item.getImportance();
				tableModel.addRow(data[i]);
			});
			
		} else 
			log.append("Open command cancelled by user." + newline);
		log.setCaretPosition(log.getDocument().getLength());
	}
	
	private void priority() {
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		if(priority.isSelected()){
			table.setRowSorter(sorter);
			ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
			 
			int columnIndexToSort = 4;
			sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));
			 
			sorter.setSortKeys(sortKeys);
			sorter.sort();
		} 
	}
	
	private void delete(){ // delete row from table
		DefaultTableModel model = (DefaultTableModel)table.getModel(); 
		if(table.getSelectedRow() == -1){ 
			if(table.getRowCount() == 0) { 
				JOptionPane.showMessageDialog(null, "Table is empty"); 
				}else 
					JOptionPane.showMessageDialog(null, "Choose record for editing"); 
		}else {
			int oldSize = data.length;
			Object[][] oldData = new Object[oldSize][5];
			int myRow = table.getSelectedRow();
			
			if(!(myRow == 0) || myRow == 0){
				model.removeRow(table.getSelectedRow());
				
				for (int i = 0; i < data.length; ++i)
					oldData[i] = data[i].clone();
				
				data = new Object[oldSize - 1][5];
				
				// Restore old data
				int j = 0;
				for (int i = 0; i < myRow; ++i){
					data[i] = oldData[i].clone();
					j++;
				}
				for(int i = myRow + 1; i < oldData.length; i++){
					data[j] = oldData[i].clone();
					j++;
				}
			}
		}
	}
	
	
	private void save() {// save data in file
		int returnVal = fc.showSaveDialog(MyJPanel.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {	
			File file = fc.getSelectedFile();
			log.append("Saving: " + file.getName() + "." + newline);
			
			WorkWithFile wwFile = new WorkWithFile(file.getName());
			clearData(file.getName());
			for (int i = 1; i < data.length; ++i) {
				wwFile.writeInFile(data[i]);
			}
		} else 
			log.append("Save command cancelled by user." + newline);
		log.setCaretPosition(log.getDocument().getLength());
	}
	
	
	private void add() { // add data in table
		DefaultTableModel model = (DefaultTableModel)table.getModel();		
				
		int oldSize = data.length;
		Object[][] oldData = new Object[oldSize][5];
		
		// Save old data
		for (int i = 0; i < data.length; ++i)
			oldData[i] = data[i].clone();
		
		data = new Object[oldSize + 1][5];
		
		int j = 0;
		// Restore old data
		for (int i = 0; i < oldData.length; ++i){
			data[i] = oldData[i].clone();
			j = i;
		}
		
		ArrayList<Note> tmp = new ArrayList<>(customDialogWindow());
		System.out.println(tmp);
		data[j][0] = tmp.get(0).getRecord();
		data[j][1] = tmp.get(0).getDay();
		data[j][2] = tmp.get(0).getMonth();
		data[j][3] = tmp.get(0).getYear();
		data[j][4] = tmp.get(0).getImportance();
		model.addRow(data[j]);
		
	}
	
	
	private void edit(){
		DefaultTableModel model = (DefaultTableModel)table.getModel();
	}
	
	private void clearData(String filename){//clears data in file
		PrintWriter writer;
		try {
			writer = new PrintWriter(filename);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	private ArrayList<Note> customDialogWindow(){ // creating dialog window for inserting row in column
		//MyJDialog dialog = new MyJDialog(new JFrame(),"hello", "hello[2]");
		JTextField Field1 = new JTextField(30);
	    JTextField Field2 = new JTextField(5);
	    JTextField Field3 = new JTextField(5);
	    JTextField Field4 = new JTextField(5);
	    JTextField Field5 = new JTextField(5);
	    ArrayList<Note> list = new ArrayList<>();
	    Note note = new Note();
	
	    JPanel myPanel = new JPanel();
	    myPanel.add(new JLabel("Task:"));
	    myPanel.add(Field1);
	    myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	    myPanel.add(new JLabel("day:"));
	    myPanel.add(Field2);
	    myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	    myPanel.add(new JLabel("month:"));
	    myPanel.add(Field3);
	    myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	    myPanel.add(new JLabel("year:"));
	    myPanel.add(Field4);
	    myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	    myPanel.add(new JLabel("importance:"));
	    myPanel.add(Field5);
	    
	    int result = JOptionPane.showConfirmDialog(null, myPanel, 
	            "Please Enter Values", JOptionPane.OK_CANCEL_OPTION);
	    
	    if(result == JOptionPane.OK_OPTION){
	    	note.setRecord(Field1.getText());
		    note.setDay(Integer.parseInt(Field2.getText()));
		    note.setMonth(Integer.parseInt(Field3.getText()));
		    note.setYear(Integer.parseInt(Field4.getText()));
		    note.setImportance(Boolean.parseBoolean(Field5.getText()));
	    	list.add(note);
	    	return list;
	    }
    	return list;

	}
}