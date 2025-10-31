package controlador;

import javax.swing.Timer;

public class CronometroLogica {

	private Timer timer;
    private long tiempoInicio = 0;
    private long tiempoPausado = 0;
    private boolean corriendo = false;
    private final Runnable actualizar;

    public CronometroLogica(Runnable actualizador) {
        this.actualizar = actualizador;

        timer = new Timer(100, e -> actualizador.run());
    }

    public void iniciar() {
        if (!corriendo) {
            tiempoInicio = System.currentTimeMillis() - tiempoPausado;
            timer.start();
            corriendo = true;
        }
    }

    public void pausarReanudar() {
        if (corriendo) {
            tiempoPausado = System.currentTimeMillis() - tiempoInicio;
            timer.stop();
            corriendo = false;
        } else {
            tiempoInicio = System.currentTimeMillis() - tiempoPausado;
            timer.start();
            corriendo = true;
        }
    }

    public void reiniciar() {
        timer.stop();
        corriendo = false;
        tiempoInicio = 0;
        tiempoPausado = 0;
        actualizar.run();
    }

    public boolean estaCorriendo() {
        return corriendo;
    }

    public String obtenerTiempoFormateado() {
        long tiempoActual = corriendo
                ? System.currentTimeMillis() - tiempoInicio
                : tiempoPausado;

        long minutos = (tiempoActual / 1000) / 60;
        long segundos = (tiempoActual / 1000) % 60;
        long centesimas = (tiempoActual % 1000) / 10;

        return String.format("%02d:%02d:%02d", minutos, segundos, centesimas);
    }

}
