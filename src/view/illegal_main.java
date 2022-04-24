package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class illegal_main extends JFrame{

		JLabel s_labels = new JLabel("시작일자");
		JLabel e_labels = new JLabel("종료일자");
		JLabel c_labels = new JLabel("기준");
		private HintTextField tSt = new HintTextField("ex)20210101");
		private HintTextField tEt = new HintTextField("ex)20210101");
		private HintTextField tCt = new HintTextField("ex)5");
		 
		private JScrollPane scrolledTable;
		private JTable table;
		private JButton addBtn;
		String header[]= {"회원번호","이름","종류","회원구분"};
		
		DefaultTableModel model=new DefaultTableModel(header,0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		
		public static void main(String[] args) {
			new illegal_main();
		}
		
		public illegal_main() {
			
			getContentPane().setLayout(new BorderLayout(10,10));
			JPanel topPanel=new JPanel(new GridLayout(1,1,5,5));
			s_labels.setHorizontalAlignment(SwingConstants.RIGHT);
			e_labels.setHorizontalAlignment(SwingConstants.RIGHT);
			c_labels.setHorizontalAlignment(SwingConstants.RIGHT);
			
			addBtn=new JButton("검색");
			
			topPanel.add(s_labels);
			topPanel.add(tSt);	
			topPanel.add(e_labels);
			topPanel.add(tEt);
			topPanel.add(c_labels);
			topPanel.add(tCt);
			topPanel.add(new JLabel());
			topPanel.add(addBtn);
			
			topPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			getContentPane().add("North",topPanel);
			
			table=new JTable(model);
			scrolledTable=new JScrollPane(table);
			scrolledTable.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			getContentPane().add("Center",scrolledTable);
			
			table.getColumnModel().getColumn(0).setPreferredWidth(40);
			table.getColumnModel().getColumn(1).setPreferredWidth(20);
			table.getColumnModel().getColumn(2).setPreferredWidth(1);
			table.getColumnModel().getColumn(3).setPreferredWidth(300);
			
			table.getTableHeader().setReorderingAllowed(false);
			table.getTableHeader().setResizingAllowed(false);
			
			
			addBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					addRow(tSt.getText(),tEt.getText(),tCt.getText());
				}
			});
			
			tSt.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyChar() == '\n')
					{
						addRow(tSt.getText(),tEt.getText(),tCt.getText());
					}
				}
			});
			tEt.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyChar() == '\n')
					{
						addRow(tSt.getText(),tEt.getText(),tCt.getText());
					}
				}
			});
			tCt.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyChar() == '\n')
					{
						addRow(tSt.getText(),tEt.getText(),tCt.getText());
					}
				}
			});
			
	        table.addMouseListener(new MouseAdapter() {
	        	
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
	        		JTable t = (JTable)e.getSource();
	        		if(e.getClickCount() == 2)
	        		{
	        			TableModel m = t.getModel();
	                    Point pt = e.getPoint();
	                    int i = t.rowAtPoint(pt);
	                    
	                    if(i>=0) {
	                        int row = t.convertRowIndexToModel(i);
	                        String s = String.format("%s", m.getValueAt(row, 0));
	                        new illegal_detail(s,tSt.getText(),tEt.getText());
	                    }
	        		}
	        	}
			});
			
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setSize(620,400);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setTitle("부정적립 의심회원 확인");
			table.getTableHeader().setReorderingAllowed(false);
			setResizable(false);
	}
		public void addRow(String s_date, String e_date, String c_num) {

			if(s_date.equals("ex)20210101") || e_date.equals("ex)20210101") || c_num.equals("ex)5"))
			{
				JOptionPane.showMessageDialog(null, "값을 입력해주세요","Message",JOptionPane.INFORMATION_MESSAGE);
				
				if(s_date.equals("ex)20210101"))
					tSt.requestFocus();
				else if(e_date.equals("ex)20210101"))
					tEt.requestFocus();
				else
					tCt.requestFocus();
				
				return;
			}
			else if(s_date.equals("") || e_date.equals("") || c_num.equals(""))
			{
				JOptionPane.showMessageDialog(null, "값을 입력해주세요","Message",JOptionPane.INFORMATION_MESSAGE);
				
				if(s_date.equals(""))
					tSt.requestFocus();
				else if(e_date.equals(""))
					tEt.requestFocus();
				else
					tCt.requestFocus();
				
				return;
			}
			model.setNumRows(0);
			createData cd = new createData();
			ArrayList<String[]> pos_list =  cd.checkData(s_date, e_date, c_num);
			for(int i = 0; i < pos_list.size(); i++)
			{
				model.insertRow(i,pos_list.get(i));
			}
		}
}