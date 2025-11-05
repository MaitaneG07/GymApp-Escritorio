package controlador;

public class TemporizadorLogica extends Thread {

    private long tiempoRestante;
    private volatile boolean corriendo = false;
    private volatile boolean pausado = false;
    private final Runnable actualizar;
    private final Runnable alFinalizar;
    private final ControladorCronometros controladorGlobal;

    public TemporizadorLogica(Runnable actualizador, Runnable alFinalizar, ControladorCronometros controladorGlobal) {
        this.actualizar = actualizador;
        this.alFinalizar = alFinalizar;
        this.controladorGlobal = controladorGlobal;
        setDaemon(true); 
    }

    /**
     * Inicia el temporizador con una duración en segundos.
     */
    public void iniciar(int duracionSegundos) {
        if (!corriendo) {
            this.tiempoRestante = (long) duracionSegundos * 1000;
            this.corriendo = true;
            this.start();
        }
    }

    public void pausarReanudar() {
        if (corriendo) {
            pausado = !pausado;
        }
    }
    
    /**
     * Detiene permanentemente el temporizador.
     */
    public void detener() {
        this.corriendo = false;
        this.interrupt(); 
    }

    @Override
    public void run() {
        long ultimaActualizacion = System.currentTimeMillis();

        while (corriendo && tiempoRestante > 0) {
            if (controladorGlobal != null) {
                controladorGlobal.esperarSiPausado();
            }

            if (!pausado) {
                long ahora = System.currentTimeMillis();
                long delta = ahora - ultimaActualizacion;
                ultimaActualizacion = ahora;

                tiempoRestante -= delta;
                if (tiempoRestante < 0) tiempoRestante = 0;

                if (actualizar != null) actualizar.run();
            } else {
                ultimaActualizacion = System.currentTimeMillis(); 
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        corriendo = false;
        if (alFinalizar != null && tiempoRestante <= 0) {
            alFinalizar.run();
        }
    }

    public String obtenerTiempoFormateado() {
        long totalSegundos = tiempoRestante / 1000;
        long minutos = totalSegundos / 60;
        long segundos = totalSegundos % 60;
        long centesimas = (tiempoRestante % 1000) / 10;

        return String.format("%02d:%02d:%02d", minutos, segundos, centesimas);
    }

    public boolean estaCorriendo() {
        return this.isAlive() && corriendo && !pausado;
    }
    
    public String getTiempoRestanteFormateadoSinCentesimas() {
        long totalSegundos = (tiempoRestante / 1000) + (tiempoRestante % 1000 > 0 ? 1 : 0);
        long minutos = totalSegundos / 60;
        long segundos = totalSegundos % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }
}
