package controlador;

import javax.swing.Timer;

public class CronometroLogica {

	private Timer timer;
	private int centesimas = 0;
	private boolean corriendo = false;
	private Runnable actualizar;

	public CronometroLogica(Runnable actualizador) {
		this.actualizar = actualizador;
		
		timer = new Timer(10, e -> {
			centesimas++;
			actualizador.run();
		});
	}

	public void iniciar() {
		if (!corriendo) {
			timer.start();
			corriendo = true;
		}
	}

	public void pausarReanudar() {
		if (corriendo) {
			timer.stop();
			corriendo = false;
		} else {
			timer.start();
			corriendo = true;
		}
	}

	public void reiniciar() {
		timer.stop();
		corriendo = false;
		centesimas = 0;
		actualizar.run();
	}

	public boolean estaCorriendo() {
		return corriendo;
	}

	public String obtenerTiempoFormateado() {
		int minutos = centesimas / 6000;
		int segundos = (centesimas / 100) % 60;
		int cent = centesimas % 100;
		return String.format("%02d:%02d:%02d", minutos, segundos, cent);
	}

}
