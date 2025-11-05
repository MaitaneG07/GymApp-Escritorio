package controlador;

public class ControladorCronometros {
	private volatile boolean pausado = false;

	public synchronized void pausarTodos() {
		pausado = true;
	}

	public synchronized void reanudarTodos() {
		pausado = false;
		notifyAll();
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
