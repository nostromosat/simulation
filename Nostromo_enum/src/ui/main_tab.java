package ui;

import asteroid.Asteroid;
import cargo.Propulsion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
 import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import main.Mission;

public class main_tab extends JFrame {

	private static final long serialVersionUID = 1;

	private static final int marge = 20;
	private static final int sizeX = 200;
	private static final int sizeXlabel = 120;
	private static final int sizeN = 40;
	private static final int sizeY = 25;
	private static final int windowX = 1300;
	private static final int windowY = 720;

	private static final Color mission_background = Color.gray;
	private static final Color output_background = Color.lightGray;
	private static final Color selected_color = Color.white;


	static int label_position(int window,int i){
		return i*(window - 120)/6 - sizeY;
	}
	static int choice_position(int window,int i){
		return i*(window - 120)/6;
	}

	static void Set_label(JPanel pan, JLabel lab, int i){
		lab.setLocation(marge, label_position(pan.getHeight(),i));
		lab.setSize(sizeX, sizeY);
		pan.add(lab);		
	}

	static void Set_box(JPanel pan, JComboBox<String> box, int i) {
		box.setSize(sizeX, sizeY);
		box.setLocation(marge,choice_position(pan.getHeight(),i));
		box.addActionListener(box);
		pan.add(box);

		//			 public void actionPerformed(ActionEvent e) {
			//			        @SuppressWarnings("rawtypes")
		//					JComboBox cb = (JComboBox)e.getSource();
		//			        String petName = (String)cb.getSelectedItem();
		//			        updateLabel(petName);
		//			    }
	}

	static void Set_selected(JPanel pan, JComboBox<String> box, JTextField text, int i){
		text.setSize(sizeX-marge, sizeY);
		text.setBackground(mission_background);
		text.setForeground(selected_color);
		text.setBorder(null);
		text.setHorizontalAlignment(JTextField.CENTER);
		text.setLocation(marge*2, choice_position(pan.getHeight(),i)+marge*2);
		text.setText(box.getSelectedItem().toString());
		pan.add(text);
	}

	static void Set_output(JPanel pan,  JLabel label, JTextField text, String value, int i){
		text.setSize(sizeX-sizeXlabel+marge*2, sizeY);
		text.setBackground(output_background);
		text.setBorder(null);
		text.setHorizontalAlignment(JTextField.CENTER);
		text.setLocation(label.getLocation().x+label.getWidth(), marge * i);
		text.setText(value);
		pan.add(text);
	}


	public static void WindowLaunch(Mission mission) {
		
		/****************************** Window characteristics ******************************/ 
		JFrame window = new JFrame();
		window.setTitle("Nostromo");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(windowX,windowY);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setVisible(true);
		
		/****************************** Tabs creation ******************************/
		JTabbedPane tabs = new JTabbedPane();
		tabs.setVisible(true);
		tabs.addTab("main", null);
		tabs.setMnemonicAt(0, KeyEvent.VK_1);
		tabs.addTab("cargo", null);
		tabs.setMnemonicAt(1, KeyEvent.VK_2);
		tabs.addTab("target", null);
		tabs.setMnemonicAt(2, KeyEvent.VK_3);
		tabs.addTab("results detailled", null);
		tabs.setMnemonicAt(3, KeyEvent.VK_4);

		/****************************** Mission Panel ******************************/
		JPanel mission_pan = new JPanel();
		mission_pan.setLayout(null);
		mission_pan.setBackground(mission_background);
		mission_pan.setSize(window.getWidth()/5, window.getHeight());
		mission_pan.setBorder(BorderFactory.createEtchedBorder());

		/********* Labels *********/

		JLabel mission_label = new JLabel("Mission creation",JLabel.CENTER);
		mission_label.setSize(sizeX,sizeY);
		mission_label.setLocation(marge,marge);
		mission_label.setForeground(Color.black);
		mission_pan.add(mission_label);

		JLabel target_label = new JLabel("Target selection",JLabel.LEFT);
		Set_label(mission_pan,target_label,1);

		JLabel launcher_label = new JLabel("Launcher selection",JLabel.LEFT);
		Set_label(mission_pan,launcher_label,2);

		JLabel injection_label = new JLabel("Injection orbit",JLabel.LEFT);
		Set_label(mission_pan,injection_label,3);

		JLabel propulsion_label = new JLabel("Propulsion selection",JLabel.LEFT);
		Set_label(mission_pan, propulsion_label, 4);

		JLabel transported_label = new JLabel("Transported mass",JLabel.LEFT);
		Set_label(mission_pan, transported_label, 5);


		/********* Selection boxes *********/
		JComboBox<String> asteroid_box = new JComboBox<String>(Asteroid.list());
		JTextField asteroid_selected = new JTextField();
		Set_box(mission_pan, asteroid_box, 1);
		Set_selected(mission_pan, asteroid_box, asteroid_selected,1);

		JComboBox<String> launcher_box = new JComboBox<String>(new String[]{"Ariane 64","Falcon Heavy"});
		JTextField launcher_selected = new JTextField();
		Set_box(mission_pan, launcher_box, 2);
		Set_selected(mission_pan, launcher_box, launcher_selected,2);

		JComboBox<String> injection_box = new JComboBox<String>(new String[]{"LEO","GTO","Escape"});
		JTextField injection_selected = new JTextField();
		Set_box(mission_pan, injection_box, 3);
		Set_selected(mission_pan, injection_box, injection_selected,3);

		JComboBox<Integer> n_propulsor = new JComboBox<Integer>(new Integer[] {1,2,3,4,5,6,7,8});
		n_propulsor.setSize(sizeN,sizeY);
		n_propulsor.setSelectedIndex(1);
		n_propulsor.setLocation(marge,choice_position(mission_pan.getHeight(),4));
		mission_pan.add(n_propulsor);

		JComboBox<String> propulsion_box = new JComboBox<String>(Propulsion.list());
		propulsion_box.setSize(sizeX-sizeN, sizeY);
		propulsion_box.setLocation(marge+sizeN,choice_position(mission_pan.getHeight(),4));
		mission_pan.add(propulsion_box);

		JTextField propulsion_selected = new JTextField();
		propulsion_selected.setSize(sizeX-marge, sizeY);
		propulsion_selected.setBackground(mission_background);
		propulsion_selected.setForeground(Color.white);
		propulsion_selected.setBorder(null);
		propulsion_selected.setHorizontalAlignment(JTextField.CENTER);
		propulsion_selected.setLocation(marge*2, choice_position(mission_pan.getHeight(),4)+marge*2);
		propulsion_selected.setText(n_propulsor.getSelectedItem().toString()+" x "+propulsion_box.getSelectedItem().toString());
		mission_pan.add(propulsion_selected);

		;

		JSlider transported = new JSlider(JSlider.HORIZONTAL,0,1000,200);
		transported.setBackground(mission_background);
		transported.setForeground(Color.white);
		transported.setSize(sizeX, sizeY*3);
		transported.setLocation(marge, choice_position(mission_pan.getHeight(),5));
		transported.setMajorTickSpacing(200);
		transported.setMinorTickSpacing(transported.getMajorTickSpacing()/5);
		transported.setPaintTicks(true);
		transported.setPaintLabels(true);
		mission_pan.add(transported);
//		mission.nostromo.getMass().setTransportableMass(transported.getMaximum());
//		mission.nostromo.getMass().setTransportedMass(transported.getValue());


		JButton launch = new JButton("Launch");
		launch.setSize(80,sizeY);
		launch.setForeground(Color.DARK_GRAY);
		launch.setLocation(mission_pan.getWidth()/2-launch.getWidth()/2, mission_pan.getHeight()-marge*4);
		launch.setText("Launch");
		mission_pan.add(launch);

		/****************************** Orbit Panel ******************************/
		JPanel orbit_pan = new JPanel();
		orbit_pan.setLayout(new BorderLayout());
		orbit_pan.setBackground(Color.black);
		orbit_pan.setSize(900, 420);
		orbit_pan.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		orbit_pan.setLocation(window.getWidth()*3/5-orbit_pan.getWidth()/2, marge*2);

		/********* Label *********/
		JLabel orbit_label = new JLabel("Orbit viewer",JLabel.CENTER);
		orbit_label.setFont(new Font("Sans_Serif", Font.PLAIN, 32));
		orbit_label.setLocation(marge*18, marge*10);
		orbit_label.setSize(sizeX, sizeY);
		orbit_label.setVisible(false);
		orbit_pan.add(orbit_label);

		/******** JPlot **********/
		OrbitGraph orbit_graph = new OrbitGraph(orbit_pan.getSize());
		orbit_pan.add(orbit_graph.new DrawStuff(), BorderLayout.CENTER);

		/****************************** Output Panel ******************************/
		JPanel output_pan = new JPanel();
		output_pan.setLayout(null);
		output_pan.setBackground(Color.LIGHT_GRAY);
		output_pan.setSize(700,150);
		output_pan.setLocation(window.getWidth()/3,orbit_pan.getHeight()+4*marge);
		output_pan.setBorder(BorderFactory.createLoweredBevelBorder());

		/********* Label *********/
		JLabel cost_label = new JLabel("Mission cost: ",JLabel.LEFT);
		cost_label.setLocation(3 * marge, marge);
		cost_label.setSize(sizeXlabel,sizeY);
		output_pan.add(cost_label);

		JLabel kmCost_label = new JLabel("Cost per km: ",JLabel.LEFT);
		kmCost_label.setLocation(3 * marge, 3*marge);
		kmCost_label.setSize(sizeXlabel,sizeY);
		output_pan.add(kmCost_label);

		JLabel kgCost_label = new JLabel("Cost per kg: ",JLabel.LEFT);
		kgCost_label.setLocation(3 * marge, 5*marge);
		kgCost_label.setSize(sizeXlabel,sizeY);
		output_pan.add(kgCost_label);

		JLabel duration_label = new JLabel("Mission duration: ",JLabel.LEFT);
		duration_label.setLocation(20 * marge, marge);
		duration_label.setSize(sizeXlabel,sizeY);
		output_pan.add(duration_label);

		JLabel distance_label = new JLabel("Distance travelled: ",JLabel.LEFT);
		distance_label.setLocation(20 * marge, 3 * marge);
		distance_label.setSize(sizeXlabel,sizeY);
		output_pan.add(distance_label);

		JLabel dV_label = new JLabel("dV total needed: ",JLabel.LEFT);
		dV_label.setLocation(20 * marge, 5 * marge);
		dV_label.setSize(sizeXlabel,sizeY);
		output_pan.add(dV_label);

		/** Outputs **/
		JTextField cost = new JTextField();
		Set_output(output_pan, cost_label, cost, mission.getcost().costStr(),1);

		JTextField kmCost = new JTextField();
		Set_output(output_pan, kmCost_label, kmCost, mission.getcost().otherCostStr(mission.distance),3);

		JTextField kgCost = new JTextField();
//		Set_output(output_pan, kgCost_label, kgCost, mission.getcost().otherCostStr(mission.nostromo.getMass().getTransportedMass()),5);

		JTextField duration = new JTextField();
		Set_output(output_pan, duration_label, duration, mission.date(mission.duration), 1);

		JTextField distance = new JTextField();
		Set_output(output_pan, distance_label, distance, mission.distance+" km", 3);

		JTextField dV_tot = new JTextField();
		Set_output(output_pan, dV_label, dV_tot, mission.dV_total+" m/s", 5);


		/****************************** Construction ******************************/
		orbit_pan.setVisible(true);
		mission_pan.setVisible(true);
		output_pan.setVisible(true);
		tabs.setVisible(true);
		
		window.add(orbit_pan);
		window.add(mission_pan);
		window.add(output_pan);
		window.add(tabs);
		/*
		*	window.setContentPane(mission_pan);
		*	window.setContentPane(orbit_pan);
		*	window.setContentPane(output_pan);
		*	window.setContentPane(tabs);
		*/
	}
}