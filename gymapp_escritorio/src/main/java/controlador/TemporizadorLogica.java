package controlador;

import javax.swing.Timer;

public class TemporizadorLogica {
	private Timer timer;
	private long tiempoRestante;
	private boolean corriendo = false;
	private final Runnable actualizar;
	private final Runnable alFinalizar;

	/**
	 * @param actualizador función que se ejecuta en cada actualización (ej:
	 *                     refrescar la UI)
	 * @param alFinalizar  función que se ejecuta cuando el temporizador llega a 0
	 *                     (puede ser null)
	 */
	public TemporizadorLogica(Runnable actualizador, Runnable alFinalizar) {
		this.actualizar = actualizador;
		this.alFinalizar = alFinalizar;
	}

	/**
	 * Inicia el temporizador con el tiempo indicado en segundos.
	 * 
	 * @param segundos tiempo inicial del temporizador
	 */
	public void iniciar(int segundos) {
	    if (corriendo)
	        return;

	    tiempoRestante = segundos * 1000L;
	    corriendo = true;

	    final long[] ultimaActualizacion = { System.currentTimeMillis() };

	    timer = new Timer(100, e -> {
	        long ahora = System.currentTimeMillis();
	        long delta = ahora - ultimaActualizacion[0];
	        ultimaActualizacion[0] = ahora;

	        tiempoRestante -= delta;

	        if (tiempoRestante <= 0) {
	            tiempoRestante = 0;
	            detener();
	            if (alFinalizar != null)
	                alFinalizar.run();
	        }

	        actualizar.run();
	    });

	    timer.start();
	}

	/**
	 * Pausa o reanuda el temporizador.
	 */
	public void pausarReanudar() {
		if (corriendo) {
			timer.stop();
			corriendo = false;
		} else {
			timer.start();
			corriendo = true;
		}
	}

	/**
	 * Detiene completamente el temporizador.
	 */
	public void detener() {
		if (timer != null)
			timer.stop();
		corriendo = false;
	}

	/**
	 * Reinicia el temporizador con un nuevo tiempo.
	 */
	public void reiniciar(int segundos) {
		detener();
		iniciar(segundos);
	}

	/**
	 * Devuelve el tiempo restante en formato MM:SS:CC.
	 */
	public String obtenerTiempoFormateado() {
		long minutos = (tiempoRestante / 1000) / 60;
		long segundos = (tiempoRestante / 1000) % 60;
		long centesimas = (tiempoRestante % 1000) / 10;

		return String.format("%02d:%02d:%02d", minutos, segundos, centesimas);
	}

	public boolean estaCorriendo() {
		return corriendo;
	}
}
