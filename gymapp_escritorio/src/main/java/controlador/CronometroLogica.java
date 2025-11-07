package controlador;

public class CronometroLogica extends Thread {

    private volatile boolean corriendo = false;
    private volatile boolean pausado = false;
    private long tiempoInicio = 0;
    private long tiempoPausado = 0;
    private final Runnable actualizar;
    private final ControladorCronometros controladorGlobal;

    public CronometroLogica(Runnable actualizador, ControladorCronometros controladorGlobal) {
        this.actualizar = actualizador;
        this.controladorGlobal = controladorGlobal;
        setDaemon(true); 
    }

    public void iniciar() {
    	if (this.isAlive()) {
            System.out.println("El hilo ya iniciado, no se puede volver a iniciar.");
            return;
        }

        if (!corriendo) {
            tiempoPausado = 0;
            tiempoInicio = System.currentTimeMillis();
            corriendo = true;
            this.start();
        }
    }

    /**
     * Alterna la pausa local (si está corriendo).
     */
    public void pausarReanudar() {
        if (corriendo) {
            pausado = !pausado;
            if (pausado) {
                tiempoPausado = System.currentTimeMillis() - tiempoInicio;
            } else {
                tiempoInicio = System.currentTimeMillis() - tiempoPausado;
            }
        }
    }
    
    /**
     * Detiene permanentemente el cronómetro.
     */
    public void detener() {
        this.corriendo = false;
        this.interrupt(); 
    }
    
    public void reiniciar() {
        detener();
        this.tiempoInicio = 0;
        this.tiempoPausado = 0;
        this.pausado = false;
        this.corriendo = false;
    }

    @Override
    public void run() {
        corriendo = true;
        while (corriendo) {
            if (controladorGlobal != null) {
                controladorGlobal.esperarSiPausado();
            }
            
            if (!pausado) {
                if (actualizar != null) {
                    actualizar.run(); 
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public String obtenerTiempoFormateado() {
        long tiempoActual = tiempoPausado;
        if (corriendo && !pausado) {
            tiempoActual = System.currentTimeMillis() - tiempoInicio;
        }

        long totalSegundos = tiempoActual / 1000;
        long minutos = totalSegundos / 60;
        long segundos = totalSegundos % 60;
        long milisegundos = (tiempoActual % 1000) / 10;

        return String.format("%02d:%02d:%02d", minutos, segundos, milisegundos);
    }

    public boolean estaCorriendo() {
        return corriendo && !pausado;
    }
    
    public void iniciarDesde(long milisegundosIniciales) {
        if (this.isAlive()) {
            System.out.println("El hilo ya ha sido iniciado, no se puede volver a iniciar.");
            return;
        }

        if (!corriendo) {
            tiempoPausado = milisegundosIniciales;
            tiempoInicio = System.currentTimeMillis() - milisegundosIniciales;
            corriendo = true;
            this.start();
        }
    }

}