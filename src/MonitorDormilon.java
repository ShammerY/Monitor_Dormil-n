import java.util.concurrent.Semaphore;
import model.Monitor;
import model.Estudiante;

public class MonitorDormilon {
    public static void main(String[] args) {
        int numEstudiantes = 5;
        Semaphore semMonitor = new Semaphore(0);
        Semaphore semSillas = new Semaphore(3);
        Semaphore mutex = new Semaphore(1);
        
        Monitor monitor = new Monitor(semMonitor, semSillas, mutex);
        monitor.start();

        for (int i = 1; i <= numEstudiantes; i++) {
            new Estudiante(i, semMonitor, semSillas, monitor).start();
        }
    }
}
