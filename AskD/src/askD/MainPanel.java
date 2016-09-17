package askD;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import comparators.OrderById;
import comparators.OrderByTitle;
import lists.ArchiveList;
import model.*;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSplitPane;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;

public class MainPanel extends JFrame {

	private JPanel contentPane;
	
	public static final int MAIN_WIDTH = 1900;
	public static final int MAIN_HEIGHT = 1000;
	public static final int X_LOCATION = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - MAIN_WIDTH) / 2;
	public static final int Y_LOCATION = 0;
	public static final int ROW_HEIGHT = 40;
	public static final String DATA_PATH = "DATABASE.txt";
	public static final String USAGE_FILE = "usage.txt";

	private JButton btnAddFile, btnAttachFile, btnResetFields, btnNewButton, btnDeleteFile;

	private JTextField typeInput, authorInput, tagsInput, titleInput, indexInput,
					commentsInput, titleSearch, tagsSearch, authorSearch, typeSearch;

	private JLabel lblFileAttached, labelInputComments, lblIndex, lblRequired, 
	lblTitle, lblTags, lblAuthor, lblType, previewTitle, previewAuthor, previewType, 
	previewTags, previewAttached, previewComments;
	private String fileToAttach, fileToOpen, previewName;
	private ArchiveList allFiles;
	private JList<ArchiveFile> currentFiles;
	private JList<String> currentTitles;
	private JList<Author> currentAuthors;
	private JList<FileType> currentTypes;
	private JList<Tag> currentTags;
	private Comparator masterComparator;
	private OrderById idComp;
	private OrderByTitle titleComp;
	private static MainPanel frame;
	private static boolean firstLaunch;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MainPanel();
					frame.setVisible(true);
					if(firstLaunch)
						openUsageFile();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainPanel() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainPanel.class.getResource("/images/d icon.png")));
		UIManager.put("OptionPane.messageFont", new Font("Microsoft Tai Le", Font.BOLD, 20));
		UIManager.put("OptionPane.ButtonFont", new Font("Microsoft Tai Le", Font.BOLD, 15));
		allFiles = new ArchiveList(DATA_PATH);
		
		idComp = new OrderById();
		titleComp = new OrderByTitle();
		masterComparator = idComp;
		
		

		setTitle("AskD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(X_LOCATION, Y_LOCATION, MAIN_WIDTH, MAIN_HEIGHT);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		mnNewMenu.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmUsage = new JMenuItem("Usage");
		mntmUsage.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 20));
		mnNewMenu.add(mntmUsage);
		
		JMenuItem mntmImport = new JMenuItem("Import");
		mntmImport.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 20));
		mntmImport.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				int result = chooser.showOpenDialog(null);
				if(result != JFileChooser.APPROVE_OPTION){
					JOptionPane.showMessageDialog(null, "You didn't select a file");
					return;
				}
				File importFile = chooser.getSelectedFile();
				allFiles.readToDatabase(importFile.getAbsolutePath());
				update(masterComparator);
			}
		});
		mnNewMenu.add(mntmImport);
		
		JMenuItem mntmExport = new JMenuItem("Export");
		mntmExport.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 20));
		mntmExport.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int result = chooser.showSaveDialog(null);
				if(result != JFileChooser.APPROVE_OPTION){
					JOptionPane.showMessageDialog(null, "You didn't select a file");
					return;
				}
				File exportFile = chooser.getSelectedFile();
				String absPath = exportFile.getAbsolutePath();
				int index = absPath.lastIndexOf('.');
				if(index > 0){
					String ext = absPath.substring(index);
					if(!ext.equals(".txt")){
						absPath = absPath.substring(0, index - 1) + ".txt";
					}
				}else{
					absPath = absPath + ".txt";
				}
				allFiles.writeToFile(absPath);
				update(masterComparator);
			}
		});
		mnNewMenu.add(mntmExport);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 20));
		mnNewMenu.add(mntmExit);
		mntmExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				frame.dispose();
			}
			
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1572, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
		);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setDividerLocation(MAIN_WIDTH - (MAIN_WIDTH / 4));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 1572, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
		);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setEnabled(false);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setDividerLocation((MAIN_HEIGHT / 2) - 50);
		splitPane.setLeftComponent(splitPane_1);
		
		JPanel panel_1 = new JPanel();
		splitPane_1.setLeftComponent(panel_1);
		
		JPanel panel_2 = new JPanel();
		
		JPanel panel_3 = new JPanel();
		
		JPanel panel_4 = new JPanel();
		
		JPanel panel_5 = new JPanel();
		
		commentsInput = new JTextField();
		commentsInput.setForeground(Color.DARK_GRAY);
		commentsInput.setFont(new Font("Microsoft Tai Le", Font.BOLD, 30));
		commentsInput.setColumns(10);
		
		JPanel panel_14 = new JPanel();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 1398, Short.MAX_VALUE)
						.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 1398, Short.MAX_VALUE)
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 1398, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_14, GroupLayout.DEFAULT_SIZE, 1042, Short.MAX_VALUE)
								.addComponent(commentsInput, GroupLayout.DEFAULT_SIZE, 1042, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(commentsInput, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_14, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)))
					.addContainerGap())
		);
		
		lblFileAttached = new JLabel("No File Attached");
		lblFileAttached.setFont(new Font("Microsoft Tai Le", Font.BOLD, 25));
		
		btnResetFields = new JButton("Reset Fields");
		btnResetFields.setFocusPainted(false);
		btnResetFields.setForeground(new Color(153, 102, 255));
		btnResetFields.setFont(new Font("Microsoft Tai Le", Font.BOLD, 25));
		btnResetFields.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				resetTextFields();
			}
			
		});
		GroupLayout gl_panel_14 = new GroupLayout(panel_14);
		gl_panel_14.setHorizontalGroup(
			gl_panel_14.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_14.createSequentialGroup()
					.addComponent(lblFileAttached, GroupLayout.PREFERRED_SIZE, 695, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnResetFields, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_14.setVerticalGroup(
			gl_panel_14.createParallelGroup(Alignment.LEADING)
				.addComponent(btnResetFields, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
				.addComponent(lblFileAttached, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
		);
		panel_14.setLayout(gl_panel_14);
		panel_5.setLayout(new GridLayout(2,1));
		
		labelInputComments = new JLabel("Comments/Other Info:");
		labelInputComments.setHorizontalAlignment(SwingConstants.CENTER);
		labelInputComments.setFont(new Font("Microsoft Tai Le", Font.BOLD, 25));
		panel_5.add(labelInputComments);
		
		btnAttachFile = new JButton("Attach File");
		btnAttachFile.setForeground(new Color(153, 102, 255));
		btnAttachFile.setFocusPainted(false);
		btnAttachFile.setFont(new Font("Microsoft Tai Le", Font.BOLD, 25));
		btnAttachFile.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if((fileToAttach != null && fileToAttach.length() > 0)){
					fileToAttach = null;
					lblFileAttached.setText("No file attached");
					btnAttachFile.setText("Attach File");
				}else{
					JFileChooser chooser = new JFileChooser();
					int result = chooser.showOpenDialog(null);
					if(result != JFileChooser.APPROVE_OPTION){
						JOptionPane.showMessageDialog(null, "You didn't select a file");
						return;
					}
					File attached = chooser.getSelectedFile();
					fileToAttach = attached.getAbsolutePath();
					lblFileAttached.setText("File Attached: " + attached.getName());
					btnAttachFile.setText("Clear attached");
				}
				
			}
			
		});
		panel_5.add(btnAttachFile);
		panel_4.setLayout(new GridLayout(1, 0, 0, 0));
		btnAddFile = new JButton("Add File");
		btnAddFile.setFocusPainted(false);
		btnAddFile.setForeground(new Color(153, 102, 255));
		btnAddFile.setFont(new Font("Microsoft Tai Le", Font.BOLD, 40));
		btnAddFile.addActionListener(new ActionListener(){


			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO Check for index and title
				boolean replace = false;
				
				String title = getText(titleInput);
				String index = getText(indexInput);
				if(title.isEmpty() || index.isEmpty()){
					showMessage("Need title and index to add a file");
					return;
				}
				if(allFiles.contains(title)){
					int result = JOptionPane.showConfirmDialog(null, "File with title \"" + title + "\" already exists\n"
							+ "Would you like to replace it?");
					if(result != JOptionPane.YES_OPTION)
						return;
					replace = true;
				}
				Integer id;
				try{
					id = Integer.parseInt(index);
				}catch(NumberFormatException e1){
					showMessage("Invalid index: " + index);
					return;
				}
				if(allFiles.contains(id) && !replace){
					int result = JOptionPane.showConfirmDialog(null, "File with index " + id + " already exists\n"
							+ "Are you sure you want to continue?\n(A duplicate index will be created)");
					if(!(result == JOptionPane.YES_OPTION))
						return;
				}
				ArchiveFile newFile = new ArchiveFile(title, id);
				allFiles.addFile(newFile, replace);
				String author = getText(authorInput);
				String type = getText(typeInput);
				String tags = getText(tagsInput);
				String comments = getText(commentsInput);
				allFiles.updateFile(newFile, author, type, tags, comments, fileToAttach);
				allFiles.update();
				update(masterComparator);
				
				if(replace){
					showMessage("File updated successfully.\nSee preview pane");
					setPreviewPane(newFile);
				}else{
					showMessage("File created successfully:\n" + newFile);
				}
				resetTextFields();
			}
		});
		
		
		panel_4.add(btnAddFile);
		
		lblIndex = new JLabel("*Index:");
		lblIndex.setHorizontalAlignment(SwingConstants.CENTER);
		lblIndex.setFont(new Font("Microsoft Tai Le", Font.BOLD, 30));
		panel_4.add(lblIndex);
		
		indexInput = new JTextField();
		indexInput.setForeground(Color.DARK_GRAY);
		indexInput.setFont(new Font("Microsoft Tai Le", Font.BOLD, 30));
		panel_4.add(indexInput);
		indexInput.setColumns(10);
		
		lblRequired = new JLabel("* Required Field");
		lblRequired.setFont(new Font("Microsoft Tai Le", Font.BOLD, 30));
		lblRequired.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblRequired);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));
		
		titleInput = new JTextField();
		titleInput.setForeground(Color.DARK_GRAY);
		titleInput.setFont(new Font("Microsoft Tai Le", Font.BOLD, 30));
		panel_3.add(titleInput);
		titleInput.setColumns(10);
		
		authorInput = new JTextField();
		authorInput.setForeground(Color.DARK_GRAY);
		authorInput.setFont(new Font("Microsoft Tai Le", Font.BOLD, 30));
		panel_3.add(authorInput);
		authorInput.setColumns(10);
		
		typeInput = new JTextField();
		typeInput.setForeground(Color.DARK_GRAY);
		typeInput.setFont(new Font("Microsoft Tai Le", Font.BOLD, 30));
		
		panel_3.add(typeInput);
		typeInput.setColumns(10);
		
		tagsInput = new JTextField();
		tagsInput.setForeground(Color.DARK_GRAY);
		tagsInput.setFont(new Font("Microsoft Tai Le", Font.BOLD, 30));
		panel_3.add(tagsInput);
		tagsInput.setColumns(10);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));
		
		lblTitle = new JLabel("*Title");
		lblTitle.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTitle.setFont(new Font("Microsoft Tai Le", Font.BOLD, 25));
		panel_2.add(lblTitle);
		
		lblAuthor = new JLabel("Author");
		lblAuthor.setVerticalAlignment(SwingConstants.BOTTOM);
		lblAuthor.setFont(new Font("Microsoft Tai Le", Font.BOLD, 25));
		panel_2.add(lblAuthor);
		
		lblType = new JLabel("Type");
		lblType.setVerticalAlignment(SwingConstants.BOTTOM);
		lblType.setFont(new Font("Microsoft Tai Le", Font.BOLD, 25));
		panel_2.add(lblType);
		
		lblTags = new JLabel("Tags");
		lblTags.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTags.setFont(new Font("Microsoft Tai Le", Font.BOLD, 25));
		panel_2.add(lblTags);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_6 = new JPanel();
		splitPane_1.setRightComponent(panel_6);
		
		JPanel panel_7 = new JPanel();
		panel_7.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_11 = new JPanel();
		panel_7.add(panel_11);
		panel_11.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel label_1 = new JLabel("Title");
		label_1.setVerticalAlignment(SwingConstants.BOTTOM);
		label_1.setFont(new Font("Microsoft Tai Le", Font.BOLD, 25));
		panel_11.add(label_1);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		panel_11.add(btnClear);
		btnClear.setFocusPainted(false);
		btnClear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				titleSearch.setText("");
				
			}
			
		});
		
		JPanel panel_8 = new JPanel();
		panel_8.setLayout(new GridLayout(1, 0, 0, 0));
		
		titleSearch = new JTextField();
		titleSearch.setForeground(Color.DARK_GRAY);
		titleSearch.setFont(new Font("Microsoft Tai Le", Font.BOLD, 30));
		titleSearch.setColumns(10);
		titleSearch.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				update(masterComparator);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				update(masterComparator);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update(masterComparator);
			}
			
		});
		panel_8.add(titleSearch);
		
		authorSearch = new JTextField();
		authorSearch.setForeground(Color.DARK_GRAY);
		authorSearch.setFont(new Font("Microsoft Tai Le", Font.BOLD, 30));
		authorSearch.setColumns(10);
		authorSearch.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				update(masterComparator);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				update(masterComparator);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update(masterComparator);
			}
			
		});
		panel_8.add(authorSearch);
		
		typeSearch = new JTextField();
		typeSearch.setForeground(Color.DARK_GRAY);
		typeSearch.setFont(new Font("Microsoft Tai Le", Font.BOLD, 30));
		typeSearch.setColumns(10);
		typeSearch.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				update(masterComparator);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				update(masterComparator);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update(masterComparator);
			}
			
		});
		panel_8.add(typeSearch);
		
		JPanel panel_12 = new JPanel();
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_6.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_12, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1394, Short.MAX_VALUE)
						.addComponent(panel_7, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1394, Short.MAX_VALUE)
						.addComponent(panel_8, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1394, Short.MAX_VALUE))
					.addGap(16))
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_8, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_12, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel panel_15 = new JPanel();
		panel_7.add(panel_15);
		panel_15.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblAuthor_1 = new JLabel("Author");
		lblAuthor_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblAuthor_1.setFont(new Font("Microsoft Tai Le", Font.BOLD, 25));
		panel_15.add(lblAuthor_1);
		
		JButton button_1 = new JButton("Clear");
		button_1.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		panel_15.add(button_1);
		button_1.setFocusPainted(false);
		button_1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				authorSearch.setText("");
				
			}
			
		});
		
		JPanel panel_16 = new JPanel();
		panel_7.add(panel_16);
		panel_16.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblType_1 = new JLabel("Type");
		lblType_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblType_1.setFont(new Font("Microsoft Tai Le", Font.BOLD, 25));
		panel_16.add(lblType_1);
		
		JButton button_2 = new JButton("Clear");
		button_2.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		panel_16.add(button_2);
		button_2.setFocusPainted(false);
		button_2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				typeSearch.setText("");
				
			}
			
		});
		
		JPanel panel_17 = new JPanel();
		panel_7.add(panel_17);
		panel_17.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblTag = new JLabel("Tag");
		lblTag.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTag.setFont(new Font("Microsoft Tai Le", Font.BOLD, 25));
		panel_17.add(lblTag);
		
		JButton button_3 = new JButton("Clear");
		button_3.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		panel_17.add(button_3);
		button_3.setFocusPainted(false);
		button_3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tagsSearch.setText("");
				
			}
			
		});
		
		tagsSearch = new JTextField();
		tagsSearch.setForeground(Color.DARK_GRAY);
		tagsSearch.setFont(new Font("Microsoft Tai Le", Font.BOLD, 30));
		tagsSearch.setColumns(10);
		tagsSearch.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				update(masterComparator);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				update(masterComparator);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update(masterComparator);
			}
			
		});
		panel_8.add(tagsSearch);
		
		currentTitles = new JList<String>();
		currentTitles.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		currentTitles.setListData(getCurrentTitles().toArray(new String[0]));
		currentTitles.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if(!e.getValueIsAdjusting()){
					setTextField(titleSearch, currentTitles);
				}	
			}
		});
		
		currentAuthors = new JList<Author>();
		currentAuthors.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		currentAuthors.setListData(getCurrentAuthors().toArray(new Author[0]));
		currentAuthors.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()){
					setTextField(authorSearch, currentAuthors);
				}	
			}
		});
		
		
		currentTypes = new JList<FileType>();
		currentTypes.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		currentTypes.setListData(getCurrentTypes().toArray(new FileType[0]));
		currentTypes.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()){
					setTextField(typeSearch, currentTypes);
				}	
			}
		});
		
		currentTags = new JList<Tag>();
		currentTags.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		currentTags.setListData(getCurrentTags().toArray(new Tag[0]));
		currentTags.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()){
					setTextField(tagsSearch, currentTags);
				}	
			}
		});
		
		panel_12.setLayout(new GridLayout(1, 0, 0, 0));
		
		int panel12Width = MAIN_WIDTH - (MAIN_WIDTH / 4) - 100;
		
		JSplitPane splitPane_3 = new JSplitPane();
		int splitPaneSize = panel12Width / 4;
		int firstDivider = 12 + splitPaneSize;
		splitPane_3.setDividerLocation(firstDivider);
		splitPane_3.setEnabled(false);
		panel_12.add(splitPane_3);
		
		JSplitPane splitPane_4 = new JSplitPane();
		splitPane_4.setDividerLocation(8 + splitPaneSize);
		splitPane_4.setEnabled(false);

		splitPane_3.setRightComponent(splitPane_4);
		
		JSplitPane splitPane_5 = new JSplitPane();
		splitPane_4.setDividerLocation(8 + splitPaneSize);
		splitPane_5.setEnabled(false);

		splitPane_4.setRightComponent(splitPane_5);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		splitPane_5.setLeftComponent(scrollPane_3);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		JScrollPane scrollPane_4 = new JScrollPane();
		splitPane_5.setRightComponent(scrollPane_4);
		
		splitPane_5.setDividerLocation(8 + splitPaneSize);
		splitPane_4.setLeftComponent(scrollPane_2);
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane_3.setLeftComponent(scrollPane_1);
		scrollPane_1.setViewportView(currentTitles);
		scrollPane_2.setViewportView(currentAuthors);
		scrollPane_3.setViewportView(currentTypes);
		scrollPane_4.setViewportView(currentTags);
		
		panel_6.setLayout(gl_panel_6);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setEnabled(false);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_2);
		splitPane_2.setDividerLocation(100);
		
		JScrollPane scrollPane = new JScrollPane();
		
		currentFiles = new JList<ArchiveFile>();
		currentFiles.setFont(new Font("Microsoft Tai Le", Font.BOLD, 25));
		currentFiles.setListData(allFiles.select(masterComparator).toArray(new ArchiveFile[0]));
		currentFiles.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				setPreviewPane(currentFiles.getSelectedValue());
			}
			
		});
		scrollPane.setViewportView(currentFiles);
		
		
		JPanel panel_13 = new JPanel();
		splitPane_2.setLeftComponent(panel_13);
		panel_13.setLayout(new GridLayout(2,1));
		
		JPanel panel_9 = new JPanel();
		panel_13.add(panel_9);
		panel_9.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel label = new JLabel("List of Files:");
		label.setFont(new Font("Microsoft Tai Le", Font.BOLD, 25));
		panel_9.add(label);
		
		JButton button = new JButton("Edit Existing File");
		button.setForeground(new Color(153, 102, 255));
		button.setFont(new Font("Microsoft Tai Le", Font.BOLD, 22));
		button.setFocusPainted(false);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArchiveFile file = currentFiles.getSelectedValue();
				titleInput.setText(file.getTitle());
				indexInput.setText("" + file.getId());
				authorInput.setText(file.getAuthor().getTitle());
				typeInput.setText(file.getType().getTitle());
				String tags = "";
				for(String tag : file.getTags().keySet()){
					tags = tags + tag + ", ";
				}
				tags = tags.substring(0,tags.length() - 2);
				tagsInput.setText(tags);
				commentsInput.setText(file.getComments());
				if(file.getAttachedFile() != null){
					lblFileAttached.setText("File attached: " + file.getAttachedFile().getAbsolutePath());
					fileToAttach = file.getAttachedFile().getAbsolutePath();
					btnAttachFile.setText("Clear Attached");
				}
			}
			
		});
		panel_9.add(button);
		
		JPanel panel_10 = new JPanel();
		panel_13.add(panel_10);
		panel_10.setLayout(new GridLayout(1, 0, 0, 0));
		
		ButtonGroup radioButtons = new ButtonGroup();
		
		JRadioButton rdbtnSortByIndex = new JRadioButton("Sort By Index");
		radioButtons.add(rdbtnSortByIndex);
		rdbtnSortByIndex.setSelected(true);
		rdbtnSortByIndex.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		rdbtnSortByIndex.setFocusPainted(false);
		rdbtnSortByIndex.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				masterComparator = idComp;
				update(masterComparator);
			}
			
		});
		panel_10.add(rdbtnSortByIndex);
		
		JRadioButton rdbtnSortByTitle = new JRadioButton("Sort By Title");
		radioButtons.add(rdbtnSortByTitle);
		rdbtnSortByTitle.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		rdbtnSortByTitle.setFocusPainted(false);
		rdbtnSortByTitle.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				masterComparator = titleComp;
				update(masterComparator);
			}
			
		});
		panel_10.add(rdbtnSortByTitle);
		
		JSplitPane splitPane_6 = new JSplitPane();
		splitPane_6.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_6.setEnabled(false);
		splitPane_6.setDividerLocation((MAIN_HEIGHT / 2) - 120);
		splitPane_6.setLeftComponent(scrollPane);
		splitPane_2.setRightComponent(splitPane_6);
		
		JPanel panel_18 = new JPanel();
		splitPane_6.setRightComponent(panel_18);
		panel_18.setLayout(new GridLayout(7,1));
		
		previewTitle = new JLabel("Title:");
		previewTitle.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		panel_18.add(previewTitle);
		
		previewAuthor = new JLabel("Author:");
		previewAuthor.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		panel_18.add(previewAuthor);
		
		previewType = new JLabel("Type:");
		previewType.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		panel_18.add(previewType);
		
		previewTags = new JLabel("Tags:");
		previewTags.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		panel_18.add(previewTags);
		
		JPanel panel_19 = new JPanel();
		panel_18.add(panel_19);
		
		previewAttached = new JLabel("Attached: ");
		previewAttached.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		
		btnNewButton = new JButton("Open File");
		btnNewButton.setFocusPainted(false);
		btnNewButton.setForeground(new Color(138, 43, 226));
		btnNewButton.setEnabled(false);
		btnNewButton.setFont(new Font("Microsoft Tai Le", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				File file = new File(fileToOpen);
				if(!file.exists()){
					JOptionPane.showMessageDialog(null, "File doesn't exist");
					return;
				}
				try {
					if(!Desktop.isDesktopSupported())
						JOptionPane.showMessageDialog(null, "Not supported");
					else
						Desktop.getDesktop().open(file.getAbsoluteFile());
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
			
		});
		GroupLayout gl_panel_19 = new GroupLayout(panel_19);
		gl_panel_19.setHorizontalGroup(
			gl_panel_19.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_19.createSequentialGroup()
					.addComponent(previewAttached, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 112, Short.MAX_VALUE)
					.addGap(3))
		);
		gl_panel_19.setVerticalGroup(
			gl_panel_19.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_19.createSequentialGroup()
					.addGroup(gl_panel_19.createParallelGroup(Alignment.BASELINE)
						.addComponent(previewAttached, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_19.setLayout(gl_panel_19);
		
		previewComments = new JLabel("Comments:");
		previewComments.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
		panel_18.add(previewComments);
		
		btnDeleteFile = new JButton("Delete File");
		btnDeleteFile.setFocusPainted(false);
		btnDeleteFile.setForeground(new Color(255, 0, 0));
		btnDeleteFile.setFont(new Font("Microsoft Tai Le", Font.BOLD, 25));
		btnDeleteFile.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete file: " + previewName);
				if(result != JOptionPane.YES_OPTION)
					return;
				allFiles.deleteFile(previewName);
				setPreviewPane(null);
				update(masterComparator);
			}
			
		});
		panel_18.add(btnDeleteFile);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	private String getText(JTextField textField){
		return textField.getText().trim();
	}
	
	private void setTextField(JTextField textField, JList list){
		Runnable makeChange = new Runnable(){

			@Override
			public void run() {
				textField.setText(list.getSelectedValue().toString());
			}

		};
		SwingUtilities.invokeLater(makeChange);
	}
	
	private void appendToTextField(JTextField textField, JList list){
		
	}

	private void resetTextFields() {
		indexInput.setText("");
		titleInput.setText("");
		tagsInput.setText("");
		authorInput.setText("");
		typeInput.setText("");
		commentsInput.setText("");
		lblFileAttached.setText("No File Attached");
	}

	public static void showMessage(String string) {
		//TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, string);
	}
	
	private void update(Comparator comparator){
		
		allFiles.setTitleConstraint(getText(titleSearch));
		allFiles.setAuthorConstraint(getText(authorSearch));
		allFiles.setTypeConstraint(getText(typeSearch));
		//TODO Will this work?
		allFiles.setTagConstraint(getText(tagsSearch));
		currentFiles.setListData(allFiles.select(comparator).toArray(new ArchiveFile[0]));
		currentTitles.setListData(getCurrentTitles().toArray(new String[0]));
		currentAuthors.setListData(getCurrentAuthors().toArray(new Author[0]));
		currentTypes.setListData(getCurrentTypes().toArray(new FileType[0]));
		currentTags.setListData(getCurrentTags().toArray(new Tag[0]));
	}
	
	private ArrayList<String> getCurrentTitles(){
		ArrayList<String> currentTitles = new ArrayList<String>();
		String titleConstraint = getText(titleSearch).toLowerCase();
		for(String title : allFiles.getAllTitles()){
			if(title.toLowerCase().contains(titleConstraint) || titleConstraint.equals(""))
				currentTitles.add(title);
		}
		return currentTitles;
	}
	
	private ArrayList<Author> getCurrentAuthors(){
		ArrayList<Author> currentAuthors = new ArrayList<Author>();
		String authorConstraint = getText(authorSearch).toLowerCase();
		for(Author author : allFiles.getAllAuthors()){
			if(author.toString().toLowerCase().contains(authorConstraint) || authorConstraint.equals(""))
				currentAuthors.add(author);
		}
		return currentAuthors;
	}
	
	private ArrayList<FileType> getCurrentTypes(){
		ArrayList<FileType> currentTypes = new ArrayList<FileType>();
		String typeConstraint = getText(typeSearch).toLowerCase();
		for(FileType type : allFiles.getAllTypes()){
			if(type.toString().toLowerCase().contains(typeConstraint) || typeConstraint.equals(""))
				currentTypes.add(type);
		}
		return currentTypes;
	}
	
	private ArrayList<Tag> getCurrentTags(){
		ArrayList<Tag> currentTags = new ArrayList<Tag>();
		String tagConstraint = getText(tagsSearch).toLowerCase();
		for(Tag tag : allFiles.getAllTags()){
			if(tag.toString().toLowerCase().contains(tagConstraint) || tagConstraint.equals(""))
				currentTags.add(tag);
		}
		return currentTags;
	}
	
	public void setPreviewPane(ArchiveFile file){
		if(file != null){
			String author, type, tags, attached;
			
			author = file.getAuthor() == null ? "" : file.getAuthor().toString();
			type = file.getType() == null ? "" : file.getType().toString();
			tags = file.getTags() == null ? "" : file.getTagsString();
			previewName = file.getTitle();
			previewTitle.setText("Title: " + file.getTitle());
			previewAuthor.setText("Author: " + author);
			previewType.setText("Type: " + type);
			previewTags.setText("Tags: " + tags);
			previewAttached.setText("Attached: " + file.getAttachedName(false));
			if(file.getAttachedName(false) != null && file.getAttachedName(false).length() > 0){
				btnNewButton.setEnabled(true);
				fileToOpen = file.getAttachedName(true);
			}
			else
				btnNewButton.setEnabled(false);
			previewComments.setText("Comments: " + file.getComments());
		}else{
			previewTitle.setText("Title: ");
			previewAuthor.setText("Author: ");
			previewType.setText("Type: ");
			previewTags.setText("Tags: ");
			previewAttached.setText("Attached: ");
			btnDeleteFile.setEnabled(false);
			btnNewButton.setEnabled(false);
		}
		
	}
	
	
	public static String capitalizeFirstLetter(String string){
		string = string.toLowerCase();
		Character first = string.charAt(0);
		string = Character.toUpperCase(first) + string.substring(1);
		return string;
	}

	public static void openUsageFile() {
		try {
			if(!Desktop.isDesktopSupported())
				JOptionPane.showMessageDialog(null, "Not desktop supported");
			else
				Desktop.getDesktop().open(new File(USAGE_FILE));
		}catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static void setFirstLaunch() {
		firstLaunch = true;
	}
}
