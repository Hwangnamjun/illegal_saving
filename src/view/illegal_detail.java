package view;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class illegal_detail extends JFrame{

		private JScrollPane scrolledTable;
		private JTable table;

		String header[]= {"회원번호","성함","결제카드","구매일","점코드","POS번호","영수증번호","항목","거래구분","지불수단","품번명","금액","원거래영수증"};
		
		DefaultTableModel model=new DefaultTableModel(header,0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		
		public illegal_detail(String s,String s_date,String e_date) {
			
			getContentPane().setLayout(new BorderLayout(10,10));
			
			table=new JTable(model);
			scrolledTable=new JScrollPane(table);
			scrolledTable.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			getContentPane().add("Center",scrolledTable);
			
			int[] column_width = {20,5,80,30,5,10,30,1,20,30,100,10,100};
			
			for(int i = 0; i < column_width.length; i++)
			{
				table.getColumnModel().getColumn(i).setPreferredWidth(column_width[i]);
			}
			
			table.getTableHeader().setReorderingAllowed(false);
			table.getTableHeader().setResizingAllowed(false);
			
			addRow(s, s_date, e_date);
			
			this.setSize(1250,720);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setTitle("세부구매내역");
			table.getTableHeader().setReorderingAllowed(false);
			setResizable(false);
			
	}
		public void addRow(String s, String s_date, String e_date) {

			createData cd = new createData();
			ArrayList<String[]> pos_list =  cd.checkDetail(s, s_date, e_date);
			for(int i = 0; i < pos_list.size(); i++)
			{
				model.insertRow(i,pos_list.get(i));
			}
		}
}