import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
public class InterfazAdministradorCreaciónMapa {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazAdministradorCreaciónMapa window = new InterfazAdministradorCreaciónMapa();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InterfazAdministradorCreaciónMapa() {
		initialize();
	}	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lbl_IdLaberinto = new JLabel("ID del Laberinto =");
		lbl_IdLaberinto.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_IdLaberinto.setBounds(10, 25, 152, 24);
		frame.getContentPane().add(lbl_IdLaberinto);
		
		JLabel lbl_AltoLaberinto = new JLabel("Alto =");
		lbl_AltoLaberinto.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_AltoLaberinto.setBounds(10, 64, 152, 24);
		frame.getContentPane().add(lbl_AltoLaberinto);
		
		JLabel lbl_AnchoLaberinto = new JLabel("Ancho =");
		lbl_AnchoLaberinto.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_AnchoLaberinto.setBounds(10, 98, 152, 24);
		frame.getContentPane().add(lbl_AnchoLaberinto);
		
		JLabel lbl_NumCocodrilos = new JLabel("Número de Cocodrilos =");
		lbl_NumCocodrilos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_NumCocodrilos.setBounds(10, 132, 182, 24);
		frame.getContentPane().add(lbl_NumCocodrilos);
		
		JLabel lbl_NumBotiquines = new JLabel("Número de Botiquines=");
		lbl_NumBotiquines.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_NumBotiquines.setBounds(10, 167, 182, 24);
		frame.getContentPane().add(lbl_NumBotiquines);
		
		JLabel lbl_PorcentajeDaño = new JLabel("% Daño =");
		lbl_PorcentajeDaño.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_PorcentajeDaño.setBounds(10, 225, 128, 24);
		frame.getContentPane().add(lbl_PorcentajeDaño);
		
		JLabel lbl_PorcentajeCura = new JLabel("% Cura =");
		lbl_PorcentajeCura.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_PorcentajeCura.setBounds(10, 273, 128, 24);
		frame.getContentPane().add(lbl_PorcentajeCura);
		
		JSlider sliderDano = new JSlider();
		sliderDano.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lbl_PorcentajeDaño.setText("% Daño = "+sliderDano.getValue());
			}
		});
		sliderDano.setValue(25);
		sliderDano.setSnapToTicks(true);
		sliderDano.setBounds(188, 225, 200, 26);
		frame.getContentPane().add(sliderDano);
		
		JSlider sliderCura = new JSlider();
		sliderCura.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lbl_PorcentajeCura.setText("% Cura = "+sliderCura.getValue());
			}
		});
		sliderCura.setValue(10);
		sliderCura.setSnapToTicks(true);
		sliderCura.setBounds(188, 271, 200, 26);
		frame.getContentPane().add(sliderCura);
		
		
		////
		SpinnerModel valorBotiquin = new SpinnerNumberModel(1, 0, 15, 1); //
		SpinnerModel valorCocodrilo = new SpinnerNumberModel(2, 0, 15, 1); //

		////	
		JSpinner spinnerNumBotiquines = new JSpinner(valorBotiquin);
		spinnerNumBotiquines.setBounds(199, 167, 63, 25);
		frame.getContentPane().add(spinnerNumBotiquines);
		
		JSpinner spinnerNumCocodrilos = new JSpinner(valorCocodrilo);
		spinnerNumCocodrilos.setBounds(199, 131, 63, 25);
		
		frame.getContentPane().add(spinnerNumCocodrilos);
		
	}
}
