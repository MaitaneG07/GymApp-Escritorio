package controlador;

import java.util.ArrayList;
import java.util.List;

public class ControladorCronometros {
    private volatile boolean pausado = false;
    private final List<Thread> hilosActivos = new ArrayList<>();

    public synchronized void registrarHilo(Thread hilo) {
        if (!hilosActivos.contains(hilo)) {
            hilosActivos.add(hilo);
        }
    }

    public synchronized void eliminarHilo(Thread hilo) {
        hilosActivos.remove(hilo);
    }

    public synchronized void pausarTodos() {
        pausado = true;
    }

    public synchronized void reanudarTodos() {
        pausado = false;
        notifyAll();
    }

    public synchronized void reiniciarTodos() {
        for (Thread hilo : new ArrayList<>(hilosActivos)) {
            if (hilo.isAlive()) hilo.interrupt();
        }
        hilosActivos.clear();
        pausado = false;
    }

    public synchronized void esperarSiPausado() {
        while (pausado) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public synchronized boolean isPausado() {
        return pausado;
    }
}
