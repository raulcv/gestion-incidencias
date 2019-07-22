package vista;

import java.awt.EventQueue;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bean.AreaBean;
import bean.SalaBean;
import dao.AreaDAO;
import dao.SalaDAO;
import utilidades.RenderizarCeldaImg;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import java.awt.Choice;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class prueba extends JFrame{

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel dtm;
	private JComboBox cbo;
	private JTextField txtTxtcod;
	AreaBean areab = new AreaBean();
	AreaDAO areaDAO = new AreaDAO();
	ArrayList<AreaBean> lista;
	int veces=0;
	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					prueba frame = new prueba();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the frame.
	 */
	public prueba() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable(){@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setDefaultRenderer(Object.class, new RenderizarCeldaImg());
		
		dtm = new DefaultTableModel();
		dtm.addColumn("ID");
		dtm.addColumn("Nombre");
		dtm.addColumn("Fecha");
		dtm.addColumn("img");
		
		SalaBean bean = new SalaBean();
		ArrayList<SalaBean> listaS = new ArrayList<SalaBean>();
		SalaDAO dao = new SalaDAO();
		
		listaS = dao.listarTodasSalas();
		ImageIcon iinuevo= new ImageIcon("iconos/nuevo.png");

		Icon inuevo = new ImageIcon(iinuevo.getImage().getScaledInstance(60,60, Image.SCALE_DEFAULT));
		JLabel l =  new JLabel();
		l.setIcon(inuevo);
		for (int i = 0; i < listaS.size(); i++) {
			Object[] obj = new Object[4];
			obj[0]=listaS.get(i).getCodigoS();
			obj[1]=listaS.get(i).getNombreS();
			obj[2]=listaS.get(i).getFechaS();
			obj[3]=l;
			//obj[3]=new JLabel(new ImageIcon(getClass().getResource("/iconos/excel.png")));
			dtm.addRow(obj);
		}
		table.setModel(dtm);
		table.setRowHeight(25);

		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		
		//table.setPreferredScrollableViewportSize(new Dimension(400, 100));
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(10, 126);
		scrollPane.setSize(401, 124);
		contentPane.add(scrollPane);
		
		cbo = new JComboBox<>();
		cbo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				String nom = cbo.getSelectedItem().toString();
				//areab = new AreaBean(areab.getCodigoA(), lista.get(0).getNombreA());
					//cbo.addItem(areab);
				
				JOptionPane.showMessageDialog(null, nom);
			}
			
		});
		cbo.setBounds(10, 19, 198, 20);
		contentPane.add(cbo);
		
		Choice choice = new Choice();
		choice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				
			}
		});
		choice.setBounds(289, 30, 135, 20);
		contentPane.add(choice);
		
		JButton btnBtnbuscar = new JButton("btnBuscar");
		btnBtnbuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cod = (txtTxtcod.getText());
				cbo.getModel().setSelectedItem(cod);
			}
		});
		btnBtnbuscar.setBounds(10, 78, 89, 23);
		contentPane.add(btnBtnbuscar);
		
		txtTxtcod = new JTextField();
		txtTxtcod.setText("txtCod");
		txtTxtcod.setBounds(109, 79, 86, 20);
		contentPane.add(txtTxtcod);
		txtTxtcod.setColumns(10);
		
	
		
		lista = new ArrayList<>();
		lista = areaDAO.listaA();
		for (int i = 0; i < lista.size(); i++) {
			choice.addItem(lista.get(i).getNombreA());
			cbo.addItem(lista.get(i).getNombreA());
		}
	}
}
